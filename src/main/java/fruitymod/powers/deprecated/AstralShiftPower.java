package fruitymod.powers.deprecated;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class AstralShiftPower extends AbstractPower {
	public static final String POWER_ID = "AstralShiftPower";
	public static final String NAME = "Intangible";
	public static final String[] DESCRIPTIONS = new String[] { "Reduce ALL damage taken and HP loss to 1 this turn." };
	
	public AstralShiftPower(AbstractCreature owner, int turns) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = turns;
		updateDescription();
		this.img = SeekerMod.getAstralShiftTexture();
		this.priority = 99;
	}

	public float atDamageReceive(float damage, DamageInfo.DamageType type) {
		if (damage > 0.0F) {
			damage = 1.0F;
		}
		return damage;
	}

	public void updateDescription() {
		this.description = DESCRIPTIONS[0];
	}

	public void atEndOfRound() {
		if (this.amount == 0) {
			AbstractDungeon.actionManager
					.addToBottom(new com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction(this.owner,
							this.owner, "AstralShiftPower"));
		} else {
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(
					this.owner, this.owner, "AstralShiftPower", 1));
		}
	}
}