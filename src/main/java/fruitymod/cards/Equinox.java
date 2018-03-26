package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Equinox extends CustomCard {
	public static final String ID = "Equinox";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 20;
	private static final int UPGRADE_DMG_AMT = 10;
	private static final int POOL = 1;

	public Equinox() {
		super(ID, NAME, FruityMod.makePath(FruityMod.EQUINOX), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SMASH));
	}
	
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {    	
    	if(p.drawPile.size()==0 || p.discardPile.size() == 0) {
    		return true;
    	}
    	this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }

	@Override
	public AbstractCard makeCopy() {
		return new Equinox();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_DMG_AMT);
		}
	}
}