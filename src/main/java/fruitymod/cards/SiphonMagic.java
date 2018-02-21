package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class SiphonMagic extends CustomCard {
	 public static final String ID = "SiphonMagic";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	    public static final String NAME = cardStrings.NAME;
	    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 2;
	    private static final int POOL = 1;
	    
	 public SiphonMagic() {
		 super(ID, NAME,  FruityMod.makePath(FruityMod.TRANSFERENCE), COST, DESCRIPTION,
				 AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
				 AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
	 		this.exhaust = true;
	 		this.isEthereal = true;
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	    	int totalPowerCount = GetPowerCount(p, "Artifact");
	    	
	    	for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {	    		
	    		int powerCount = GetPowerCount(mo, "Artifact");
	    		if(powerCount == 0) continue;
	    		totalPowerCount += powerCount;
	    		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, p, "Artifact"));
	    	}
	    	
	    	if(totalPowerCount > 0) {
	            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, totalPowerCount), totalPowerCount));
	    	}
	    }
	    
		@Override
	    public void triggerOnEndOfPlayerTurn() {
	    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	    }
	    
	    @Override
	    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
	    	int totalPowerCount = 0;
	    	for(AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {	    		
	    		totalPowerCount += GetPowerCount(mo, "Artifact");
	    	}
	    	
	    	this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION == null ? "EXTENDED_DESCRIPTION is null!" : cardStrings.EXTENDED_DESCRIPTION[0];
	        return totalPowerCount > 0;
	    }
	    
	    private int GetPowerCount(AbstractCreature c, String powerId) {
	    	AbstractPower power =  c.getPower(powerId);    	
	    	return power != null ? power.amount : 0;
	    }


	    @Override
	    public AbstractCard makeCopy() {
	        return new SiphonMagic();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	        	this.isInnate = true;
	        	this.rawDescription = cardStrings.UPGRADE_DESCRIPTION + (this.isEthereal ? " NL Ethereal." : "");
	        	this.initializeDescription();
	        }
	    }
}