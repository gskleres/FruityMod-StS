package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import fruitymod.FruityMod;

public class BrilliancePower extends AbstractPower implements PostBattleSubscriber,
		PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final String POWER_ID = "Brilliance";
	public static final String NAME = "Brilliance";
	public static final String[] DESCRIPTIONS = new String[] {
		"Whenever you Draw a Dazed, draw ",
		" card.",
		" cards."
	};

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
	public void onInitialApplication() {
		BaseMod.subscribeToPostDraw(this);
		BaseMod.subscribeToPostBattle(this);
		BaseMod.subscribeToPostDungeonInitialize(this);
	}

	@Override
	public void receivePostDraw(AbstractCard c){
		if(c.cardID.equals("Dazed") || c.cardID.equals("Dazed_P")){
			AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, this.amount));
		}
	}

	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount +
				(this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}


	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		System.out.println("should be removed now!");
		BaseMod.unsubscribeFromPostDraw(this);
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
		BaseMod.unsubscribeFromPostBattle(this);
		BaseMod.unsubscribeFromPostDraw(this);
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