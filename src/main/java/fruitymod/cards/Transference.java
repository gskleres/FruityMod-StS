package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import com.megacrit.cardcrawl.powers.VulnerablePower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Transference extends CustomCard {
	 public static final String ID = "Transference";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	    public static final String NAME = cardStrings.NAME;
	    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 1;
	    private static final int COST_UPGRADED = 0;
	    private static final int DRAW = 1;
	    
	 public Transference() {
		 super(ID, NAME,  FruityMod.makePath(SeekerMod.TRANSFERENCE), COST, DESCRIPTION,
				 AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
				 AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
		 this.baseMagicNumber = DRAW;
		 this.magicNumber = DRAW;
		 }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	    	int powerCount = GetPowerCount(p, "Weakened");
	    	if(powerCount > 0) {
	    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
	    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new WeakPower(m, powerCount, false), powerCount));
	    	}
	    	powerCount = GetPowerCount(p, "Vulnerable");
	    	if(powerCount > 0) {
	    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));
	    		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new VulnerablePower(m, powerCount, false), powerCount));
	    	}
	    	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
	    }
	    
	    private int GetPowerCount(AbstractCreature c, String powerId) {
	    	AbstractPower power =  c.getPower(powerId);    	
	    	return power != null ? power.amount : 0;
	    }


	    @Override
	    public AbstractCard makeCopy() {
	        return new Transference();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeBaseCost(COST_UPGRADED);
	        }
	    }
}