package fruitymod.cards;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
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
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

public class MysticBolt extends CustomCard {
	 public static final String ID = "MysticBolt";
	    public static final String NAME = "MysticBolt";
	    public static final String DESCRIPTION = "Can only be played if all other cards in your hand are Ethereal. Deal !D! damage.";
	    private static final int COST = 0;
	    private static final int ATTACK_DMG = 15;
	    private static final int POOL = 1;
	    
	 public MysticBolt() {
		 super(ID, NAME, "images/cards/locked_attack.png", COST, DESCRIPTION, AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
	        this.damage=this.baseDamage = ATTACK_DMG;
	    }
	 
	    @Override
	    public void use(AbstractPlayer p, AbstractMonster m) {
	        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	    }
	    
	    @Override
	    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
	        boolean canUse = super.canUse(p, m);
	        if (!canUse) {
	            return false;
	        }
	        for (AbstractCard c : p.hand.group) {
	            if (c.isEthereal || c.cardID == this.ID) continue;
	            canUse = false;
	            this.cantUseMessage = "Can only be played if all other cards in your hand are Ethereal.";
	        }
	        return canUse;
	    }

	    @Override
	    public AbstractCard makeCopy() {
	        return new MysticBolt();
	    }

	    @Override
	    public void upgrade() {
	        if (!this.upgraded) {
	            this.upgradeName();
	            this.upgradeDamage(5);
	        }
	    }
}