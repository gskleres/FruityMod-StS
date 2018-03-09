package fruitymod.cards;


import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Umbra extends CustomCard {
	 	public static final String ID = "Umbra";
	    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
		public static final String NAME = cardStrings.NAME;
		public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	    private static final int COST = 1;
	    private static final int UPGRADED_COST = 0;
	    private static final int ATTACK_DMG = 6;
	    private static final int ATTACK_UPGRADE = 3;
	    private static final int ENERGY_GAIN = 1;
	    private static final int DRAW = 1;
	    private static final int POOL = 1;
	    
	 public Umbra() {
		 super(ID, NAME, FruityMod.makePath(FruityMod.UMBRAL_BOLT), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				 AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, CardTarget.SELF, POOL);
	        this.damage = this.baseDamage = ATTACK_DMG;
	        
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
			int debuffCount = GetAllDebuffCount(p);

			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), debuffCount, true, true));
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
			AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));
	    }

		private int GetPowerCount(AbstractCreature c, String powerId) {
			AbstractPower power =  c.getPower(powerId);
			return power != null ? power.amount : 0;
		}

		private int GetAllDebuffCount(AbstractPlayer p) {
		return GetPowerCount(p, "Weakened") + GetPowerCount(p, "Vulnerable") + GetPowerCount(p, "Frail");
	}

	    @Override
	    public AbstractCard makeCopy() {
	        return new Umbra();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeDamage(ATTACK_UPGRADE);
	            this.upgradeBaseCost(UPGRADED_COST);
	        }
	    }
}