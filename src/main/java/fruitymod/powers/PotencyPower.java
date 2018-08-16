package fruitymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import fruitymod.SeekerMod;

public class PotencyPower extends AbstractPower implements PostExhaustSubscriber, PostBattleSubscriber,
		PostDungeonInitializeSubscriber {
	public static final String POWER_ID = "PotencyPower";
	public static final String NAME = "Potency";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever an Ethereal card is Exhausted, gain ",
			" Strength."
	};
	
	public PotencyPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToPostExhaust(this);
		BaseMod.subscribeToPostBattle(this);
		BaseMod.subscribeToPostDungeonInitialize(this);
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

	@Override
	public void receivePostBattle(AbstractRoom battleRoom) {
		BaseMod.unsubscribeFromPostExhaust(this);
		BaseMod.unsubscribeFromPostDungeonInitialize(this);
		/*
		 *  calling unsubscribeFromPostBattle inside the callback
		 *  for receivePostBattle means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostBattle on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostBattle(this);
		});
		delayed.start();
	}
	
	@Override
	public void receivePostDungeonInitialize() {
		BaseMod.unsubscribeFromPostExhaust(this);
		BaseMod.unsubscribeFromPostBattle(this);
		/*
		 *  calling unsubscribeFromPostDungeonInitialize inside the callback
		 *  for receivePostDungeonInitialize means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostDungeonInitialize on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostDungeonInitialize(this);
		});
		delayed.start();
	}
	
}
