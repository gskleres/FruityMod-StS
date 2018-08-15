package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Comet extends CustomCard {
    public static final String ID = "Comet";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int PER_CARD_DAMAGE = 2;
    private static final int PER_CARD_DAMAGE_UPGRADED = 1;

    public Comet() {
        super(ID, NAME, FruityMod.makePath(SeekerMod.COMET), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = PER_CARD_DAMAGE;
    }
    
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
        	AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
	}
	
	@Override
	public void onMoveToDiscard() {
		this.setDescription(false);
	}
	
	@Override
	public void applyPowers() {
		this.damage = this.baseDamage = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
		super.applyPowers();
		this.setDescription(true);
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.setDescription(true);
	}
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (addExtended) {
			this.rawDescription += EXTENDED_DESCRIPTION[0];
		}
		this.initializeDescription();
	}

    @Override
    public AbstractCard makeCopy() {
        return new Comet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(PER_CARD_DAMAGE_UPGRADED);
        }
    }
}

