package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.BaseMod;
import basemod.interfaces.PostExhaustSubscriber;
import fruitymod.FruityMod;

public class PotencyPower extends AbstractPower implements PostExhaustSubscriber {
	public static final String POWER_ID = "PotencyPower";
	public static final String NAME = "Potency";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever an Ethereal card is Exhausted, gain ",
			" Strength."
	};
	
	public PotencyPower(AbstractCreature owner, int amount) {
		this.amount = amount;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getPotencyPowerTexture();
		BaseMod.subscribeToPostExhaust(this);
	}
	
	@Override
	public void onRemove() {
		BaseMod.unsubscribeFromPostExhaust(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void receivePostExhaust(AbstractCard c) {
		if (c.isEthereal) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
					this.owner, this.owner,
					new StrengthPower(this.owner, this.amount), this.amount));
		}
	}
	
}
