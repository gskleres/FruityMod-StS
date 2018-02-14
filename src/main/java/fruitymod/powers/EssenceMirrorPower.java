package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class EssenceMirrorPower
extends AbstractPower {
    public static final String POWER_ID = "EssenceMirror";
    public static final String NAME = "Essence Mirror";
    public static final String[] DESCRIPTIONS = new String[] {"Weak, Frail, and Vulnerable have the opposite effect"};
    private boolean justApplied = false;

    // TODO: Add better description, calculation for block is still off, basic block stays at 5.
    public EssenceMirrorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTIONS[0];
        this.loadRegion("anger");
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = true;
        this.priority = 8;
    }

    @Override
    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "EssenceMirror"));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(this.owner, this.owner, "EssenceMirror", 1));
        }
    }

    /*
    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? (this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane") ? DESCRIPTIONS[0] + 50 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2] : DESCRIPTIONS[0] + 25 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]) : (this.owner != null && !this.owner.isPlayer && AbstractDungeon.player.hasRelic("Paper Crane") ? DESCRIPTIONS[0] + 50 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3] : DESCRIPTIONS[0] + 25 + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[3]);
    }
    */

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
	   if (type == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Weakened")) {
	      return damage * 1.7f;
	   }
        return damage;
    }
    
    @Override
    public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
    	if (damageType == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Vulnerable")) {
  	      return damage * 0.34f;
  	   }
        return damage;
    }
    
    @Override
    public int modifyBlock(int blockAmount) {
    	if (this.owner.hasPower("Frail")) {
    		return blockAmount * 5 / 3;
    	}
        return blockAmount;
    }
}