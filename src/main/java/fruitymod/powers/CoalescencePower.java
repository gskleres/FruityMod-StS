package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import fruitymod.FruityMod;

public class CoalescencePower extends AbstractPower implements OnPowersModifiedSubscriber,
		PostBattleSubscriber, PostDungeonInitializeSubscriber {
	public static final String POWER_ID = "CoalescencePower";
	public static final String NAME = "Coalescence";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you gain Frail, Weak, or Vulnerable, gain ",
			" Block."
	};
	
	private int weakAmt;
	private int vulnerableAmt;
	private int frailAmt;
	
	public CoalescencePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		updateDescription();
		this.img = FruityMod.getCoalescencePowerTexture();
		this.weakAmt = countPower("Weakened");
		this.vulnerableAmt = countPower("Vulnerable");
		this.frailAmt = countPower("Frail");
	}
	
	private static int countPower(String id) {
		if (AbstractDungeon.player.hasPower(id)) {
			return AbstractDungeon.player.getPower(id).amount;
		}
		return 0;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToOnPowersModified(this);
		BaseMod.subscribeToPostBattle(this);
		BaseMod.subscribeToPostDungeonInitialize(this);
	}
	
	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		BaseMod.unsubscribeFromOnPowersModified(this);
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
		BaseMod.unsubscribeFromOnPowersModified(this);
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
	
	@Override
	public void receivePowersModified() {
		int newWeakAmt = countPower("Weakened");
		int newVulnerableAmt = countPower("Vulnerable");
		int newFrailAmt = countPower("Frail");
		if (newWeakAmt > weakAmt ||
				newVulnerableAmt > vulnerableAmt ||
				newFrailAmt > frailAmt) {
			AbstractDungeon.actionManager.addToBottom(
					new GainBlockAction(this.owner, this.owner, this.amount));
		}
		weakAmt = newWeakAmt;
		vulnerableAmt = newVulnerableAmt;
		frailAmt = newFrailAmt;
	}

}
