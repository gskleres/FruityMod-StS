package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Archives extends CustomCard {
	 	public static final String ID = "Archives";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		public static final String NAME = cardStrings.NAME;
		public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 0;
	    private static final int PER_DRAW = 10;
	    private static final int PER_DRAW_UPGRADE = -3;
	    
	 public Archives() {
		 super(ID, NAME, FruityMod.makePath(SeekerMod.ARCHIVES), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				 AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
	        this.magicNumber = this.baseMagicNumber = PER_DRAW;
	        
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	        int energyGain = p.drawPile.size() / this.magicNumber;
	        
	       if(energyGain > 0) {
	    	   AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyGain));
	       }
	    }	   	 

	    @Override
	    public AbstractCard makeCopy() {
	        return new Archives();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeMagicNumber(PER_DRAW_UPGRADE);
	        }
	    }
}