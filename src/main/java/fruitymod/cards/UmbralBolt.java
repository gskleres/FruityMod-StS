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

public class UmbralBolt extends CustomCard {
	 	public static final String ID = "UmbralBolt";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		public static final String NAME = cardStrings.NAME;
		public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 1;
	    private static final int ATTACK_DMG = 6;
	    private static final int ATTACK_UPGRADE = 3;
	    private static final int ENERGY_GAIN = 1;
	    private static final int DRAW = 1;
	    private static final int POOL = 1;
	    
	 public UmbralBolt() {
		 super(ID, NAME, FruityMod.makePath(FruityMod.UMBRAL_BOLT), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				 AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
	        this.damage = this.baseDamage = ATTACK_DMG;
	        
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	        
	        if(p.getPower("Frail") != null) {
	        	AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(ENERGY_GAIN));
	        	AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, DRAW));
	        }	        
	    }	   	 

	    @Override
	    public AbstractCard makeCopy() {
	        return new UmbralBolt();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeDamage(ATTACK_UPGRADE);
	        }
	    }
}