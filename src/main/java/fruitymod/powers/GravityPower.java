package fruitymod.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.FruityMod;

public class GravityPower extends AbstractPower {
	public static final String POWER_ID = "GravityPower";
	public static final String NAME = "Gravity";
	public static final String[] DESCRIPTIONS = new String[] {
			"While Weak, this creature is losing ",
			"Strength"
	};
	
	public GravityPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.DEBUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getGravityPowerTexture();
	}
	
	@Override
	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		if (type == DamageInfo.DamageType.NORMAL) {
			return damage - this.amount;
		}
		return damage;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
}
