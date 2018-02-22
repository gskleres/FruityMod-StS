package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.FruityMod;

public class BrilliancePower extends AbstractPower {
	public static final String POWER_ID = "Brilliance";
	public static final String NAME = "Brilliance";
	public static final String[] DESCRIPTIONS = new String[] {
			"Adds ", " bonus damage based on dexterity."};
	private boolean justApplied = false;

	public BrilliancePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.priority = 2;
		this.img = FruityMod.getBrillancePowerTexture();
	}

	@Override
    public void updateDescription() {
		this.description = DESCRIPTIONS[0] + GetDexterityCount() + DESCRIPTIONS[1];
    }
	
	@Override
	public void atEndOfRound() {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
		if (this.amount == 0) {
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
		} else {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(this.owner, this.owner, POWER_ID, 1));
		}
	}

	@Override
	public float atDamageGive(float damage, DamageInfo.DamageType type) {
		return damage + GetDexterityCount();
	}
	
	private int GetDexterityCount() {
		AbstractPower power = this.owner.getPower("Dexterity");
		return power == null ? 0 : power.amount;
	}	
}