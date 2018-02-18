package fruitymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.actions.PlayCardEffectAction;

import java.util.ArrayList;

public class ReflectionWardPower
extends AbstractPower {
    public static final String POWER_ID = "ReflectionWard";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Tap");
    public static final String NAME = "Reflection Ward";
    private DamageInfo thornsInfo;


    public ReflectionWardPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.thornsInfo = new DamageInfo(this.owner, this.amount, DamageInfo.DamageType.THORNS);
        this.updateDescription();
        this.img = ImageMaster.loadImage("images/powers/32/doubleTap.png");
    }
    
    @Override
    public void updateDescription() {
        this.description = "Whenever you are attacked this turn, deal "+ this.amount + " damage to ALL enemies.";
    }
    
    @Override
    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        this.thornsInfo = new DamageInfo(null, this.amount, DamageInfo.DamageType.THORNS);
        this.updateDescription();
    }
    
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner) {
            this.flash();
            for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
        		AbstractDungeon.actionManager.addToTop(new DamageAction(m, this.thornsInfo, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        	}
        }
        return damageAmount;
    }
    
	@Override
	public void atEndOfRound() {
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
	}
}





