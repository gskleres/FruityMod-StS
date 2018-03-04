package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class PhaseCoil extends CustomCard {
	public static final String ID = "PhaseCoil";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 4;
	private static final int UPGRADE_COST = 3;
	private static final int ATTACK_DMG = 15;
	private static final int UPGRADE_PLUS_DMG = 3;
	private static final int POOL = 1;

	public PhaseCoil() {
		super(ID, NAME, FruityMod.makePath(FruityMod.PHASE_COIL), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY, POOL);

		this.baseDamage = ATTACK_DMG;
	}
	
    @Override
    public void triggerWhenDrawn() {
        super.triggerWhenDrawn();
        this.setCostForTurn(this.cost - getEtherealCount());
    }
    
    @Override
    public void applyPowers() {
    	super.applyPowers();
    	this.setCostForTurn(this.cost - getEtherealCount());
    }
    
    private int getEtherealCount() {
    	int count = 0;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (!c.isEthereal) continue;
            ++count;
        }
        return count;
    }


	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
					new DamageInfo(p, this.damage, this.damageTypeForTurn),
					AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	public AbstractCard makeCopy() {
		return new PhaseCoil();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBaseCost(UPGRADE_COST);
			upgradeDamage(UPGRADE_PLUS_DMG);
		}
	}
}