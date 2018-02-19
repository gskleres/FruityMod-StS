package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import fruitymod.FruityMod;

public class CoalescencePower extends AbstractPower {
	public static final String POWER_ID = "CoalescencePower";
	public static final String NAME = "Coalescence";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn gain 1 Weak and ",
			" Block."
	};
	
	private static final int WEAK_AMT = 1;
	
	public CoalescencePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getCoalescencePowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		this.flash();
		AbstractDungeon.actionManager.addToTop(
				new GainBlockAction(this.owner, this.owner, this.amount));
		AbstractDungeon.actionManager.addToTop(
				new ApplyPowerAction(this.owner, this.owner, new WeakPower(this.owner, this.amount, false), WEAK_AMT));
	}
}
