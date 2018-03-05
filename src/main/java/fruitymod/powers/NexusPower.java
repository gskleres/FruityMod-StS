package fruitymod.powers;

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
import fruitymod.actions.common.MakeTempCardInDrawPileEtherealAction;

public class NexusPower extends AbstractPower implements PostDrawSubscriber, PostBattleSubscriber,
	PostDungeonInitializeSubscriber {
	public static final String POWER_ID = "Nexus";
	public static final String NAME = "Nexus";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you draw a non-Ethereal Attack or Skill, shuffle ", 
			" Ethereal copy of it into your draw pile.",
			" Ethereal copies of it into your draw pile."
	};
	
	public NexusPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = FruityMod.getNexusPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + 
				(this.amount == 1 ? DESCRIPTIONS[1] : DESCRIPTIONS[2]);
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToPostDraw(this);
		BaseMod.subscribeToPostBattle(this);
		BaseMod.subscribeToPostDungeonInitialize(this);
	}
	
	@Override
	public void receivePostDraw(AbstractCard c) {
		if(!c.isEthereal && (c.type == AbstractCard.CardType.ATTACK) || c.type == AbstractCard.CardType.SKILL) {
			AbstractDungeon.actionManager.addToBottom(
					new MakeTempCardInDrawPileEtherealAction(this.owner, this.owner, c, this.amount,
							true, true));
		}
	}
	
	@Override
	public void receivePostBattle(AbstractRoom arg0) {
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
		BaseMod.unsubscribeFromPostDraw(this);
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
