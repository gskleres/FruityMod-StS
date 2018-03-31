package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Archives extends CustomCard {
	 	public static final String ID = "Archives";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		public static final String NAME = cardStrings.NAME;
		public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 0;
	    private static final int PER_DRAW = 10;
	    private static final int PER_DRAW_UPGRADE = -3;
	    private static final int POOL = 1;
	    
	 public Archives() {
		 super(ID, NAME, FruityMod.makePath(FruityMod.ARCHIVES), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				 AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
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