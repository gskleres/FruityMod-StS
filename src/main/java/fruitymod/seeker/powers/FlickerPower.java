package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class FlickerPower extends AbstractPower {
	public static final String POWER_ID = "Flicker";
	public static final String NAME = "Flicker";
	public static final String[] DESCRIPTIONS = new String[] {
			"Weak, Frail, and Vulnerable have the opposite effect on you." };
	private boolean justApplied = false;

	public FlickerPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.description = DESCRIPTIONS[0];
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.priority = 4;
		this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
	}

	@Override
	public void atEndOfRound() {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
		if (this.amount == 0) {
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Flicker"));
		} else {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(this.owner, this.owner, "Flicker", 1));
		}
	}

	// Need to fix the below to also work with the relics 
	
	@Override
	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		if (type == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Weakened")) {
			return damage * 1.67f;
		}
		return damage;
	}

	@Override
	public float atDamageReceive(float damage, DamageInfo.DamageType damageType) {
		if (damageType == DamageInfo.DamageType.NORMAL && this.owner.hasPower("Vulnerable")) {
            if (AbstractDungeon.player.hasRelic("Odd Mushroom")) {
                return damage * 0.6f; // -25% (3/4) instead of +25% (5/4) damage 
            }
			return damage * 0.34f; // -50% (1/2) instead of +50% (3/2) damage
		}
		return damage;
	}

	@Override
	public float modifyBlock(float blockAmount) {
		if (this.owner.hasPower("Frail")) {
			return (int) (blockAmount * 1.67f);
		}
		return blockAmount;
	}
	
}