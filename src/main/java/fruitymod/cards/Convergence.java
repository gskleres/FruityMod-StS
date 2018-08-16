package fruitymod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Convergence extends CustomCard {
	 public static final String ID = "Convergence";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	    public static final String NAME = cardStrings.NAME;
	    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	    private static final int COST = -2;

	 public Convergence() {
		 super(ID, NAME,  SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				 AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
				 AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	    	
	    }
	    
	    @Override
	    public boolean canUse(AbstractPlayer p, AbstractMonster mo) {
	    	this.cantUseMessage = getCantPlayMessage();
	    	return false;
	    }
	    
	    @Override
	    public AbstractCard makeCopy() {
	        return new Convergence();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	        	this.upgradeName();
	        	this.rawDescription = UPGRADE_DESCRIPTION;
	        	this.initializeDescription();
	        }
	    }
}