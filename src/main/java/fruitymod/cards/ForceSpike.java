package fruitymod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

public class ForceSpike extends CustomCard {
	 public static final String ID = "ForceSpike";
	    public static final String NAME = "Force Spike";
	    public static final String DESCRIPTION = "Deal !D! damage. Gain 2 Frail.";
	    private static final int COST = 1;
	    private static final int ATTACK_DMG = 13;
	    private static final int FRAIL_AMT = 2;
	    private static final int POOL = 1;
	    
	 public ForceSpike() {
		 super(ID, NAME, "images/cards/locked_attack.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
	        this.damage=this.baseDamage = ATTACK_DMG;
	        this.baseMagicNumber = FRAIL_AMT;
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, this.magicNumber, false), 1, true, AbstractGameAction.AttackEffect.NONE));
	    }
	   	 

	    @Override
	    public AbstractCard makeCopy() {
	        return new ForceSpike();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeDamage(5);
	        }
	    }
}