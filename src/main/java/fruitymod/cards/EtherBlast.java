package fruitymod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class EtherBlast extends CustomCard {
	public static final String ID = "EtherBlast";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int BASE_ATTACK_DMG = 6;
    private static final int BASE_ATTACK_DMG_UPGRADE = 4;
    private static final int PER_ETHEREAL_DMG = 2;
    private static final int PER_ETHEREAL_DMG_UPGRADE = 1;

	public EtherBlast() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		this.isEthereal = true;
		this.damage = this.baseDamage = BASE_ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = PER_ETHEREAL_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(new DamageAction(m, new DamageInfo(p, calculateDamage(), this.damageTypeForTurn), true));
		AbstractDungeon.actionManager.addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
		
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}
	
	public int calculateDamage() {
		int total = this.damage + (countAllEtherealCards() * this.magicNumber);
		return total;
	}
	
    public static int countAllEtherealCards() {
    	int count = 0;
    	for (AbstractCard c : AbstractDungeon.player.hand.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	return count;
    }
    
	@Override
	public void applyPowers() {
		super.applyPowers();
		this.setDescription(true);
	}
	
	@Override
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		this.setDescription(false);
	}

    @Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (addExtended) {
			this.rawDescription += EXTENDED_DESCRIPTION[0];
			this.rawDescription += Integer.toString(calculateDamage());
			this.rawDescription += EXTENDED_DESCRIPTION[1];
		}
		this.initializeDescription();
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new EtherBlast();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(BASE_ATTACK_DMG_UPGRADE);
			this.upgradeMagicNumber(PER_ETHEREAL_DMG_UPGRADE);
			this.setDescription(false);
		}
	}
}