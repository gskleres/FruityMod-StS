package fruitymod.seeker.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;

public class HypothesisAction extends AbstractGameAction implements PostDrawSubscriber {
	private boolean listening;
	private int initialDraw;
	
	public HypothesisAction(AbstractCreature target, AbstractCreature source, int amount, int initialDraw) {
		this.setValues(target, source, amount);
		this.actionType = ActionType.WAIT;
		this.initialDraw = initialDraw;
		BaseMod.subscribe(this);
	}
	
	@Override
	public void update() {
		this.listening = true;
		AbstractDungeon.actionManager.addToTop(new DrawCardAction(
				AbstractDungeon.player, this.initialDraw));
		this.isDone = true;
	}
	
	@Override
	public void receivePostDraw(AbstractCard c) {
		if (listening) {
			this.listening = false;
			if (c.isEthereal) {
				AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(new Dazed(), 1, true));
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, this.amount));
			}
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
				BaseMod.unsubscribe(this);
			});
			delayed.start();
		}
	}
	
}
