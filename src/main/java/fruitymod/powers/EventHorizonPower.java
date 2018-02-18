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

public class EventHorizonPower
extends AbstractPower {
    public static final String POWER_ID = "EventHorizon";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Tap");
    public static final String NAME = "EventHorizon";

    public EventHorizonPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.description = "Each enemy loses HP equal to the amount of Weak and Vulnerable it has at the start of its turn.";
        this.img = ImageMaster.loadImage("images/powers/32/doubleTap.png");
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
        	for(AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
        		int stackCount = GetPowerCount(m, "Weakened") + GetPowerCount(m, "Vulnerable");        		
        		if(stackCount > 0) continue;
        		AbstractDungeon.actionManager.addToBottom(new DamageAction(this.owner, new DamageInfo(null, this.amount * stackCount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
        	}
        }
    }
    
    private int GetPowerCount(AbstractMonster m, String powerId) {
    	AbstractPower power =  m.getPower(powerId);    	
    	return power != null ? power.amount : 0;
    }
}

