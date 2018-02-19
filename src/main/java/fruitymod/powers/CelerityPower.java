package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import fruitymod.FruityMod;

public class CelerityPower extends AbstractPower implements PostExhaustSubscriber, PostBattleSubscriber {
	public static final String POWER_ID = "CelerityPower";
	public static final String NAME = "Celerity";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever an Ethereal card is Exhausted, gain ",
			" Dexterity."
	};
	
	public CelerityPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getCelerityPowerTexture();
		BaseMod.subscribeToPostExhaust(this);
		BaseMod.subscribeToPostBattle(this);
	}
	
	@Override
	public void onRemove() {
		
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
					new DexterityPower(this.owner, this.amount), this.amount));
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom battleRoom) {
		BaseMod.unsubscribeFromPostExhaust(this);
		BaseMod.unsubscribeFromPostBattle(this);
	}
	
}
