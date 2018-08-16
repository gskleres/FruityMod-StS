package fruitymod.powers.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;

import fruitymod.SeekerMod;

public class CoalescencePower extends AbstractPower {
	public static final String POWER_ID = "CoalescencePower";
	public static final String NAME = "Coalescence";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn gain ",
			" Weak. At the end of your turn gain ",
			" Block."
	};
	
	private int weakAmount;
	
	public CoalescencePower(AbstractCreature owner, int weakAmount, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.weakAmount = weakAmount;
		updateDescription();
		this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.weakAmount +
				DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
	
	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0f;
		this.amount += stackAmount;
		this.weakAmount += 1;
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(this.owner, this.owner,
						new WeakPower(this.owner, this.weakAmount, false), this.weakAmount));
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(
					new GainBlockAction(this.owner, this.owner, this.amount));
		}
	}
	
}
