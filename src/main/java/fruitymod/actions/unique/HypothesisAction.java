package fruitymod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;

public class HypothesisAction extends AbstractGameAction implements PostDrawSubscriber {
	private boolean firstDraw;
	private boolean listening;
	private int initialDraw;
	
	public HypothesisAction(AbstractCreature target, AbstractCreature source, int amount, int initialDraw) {
		this.setValues(target, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.duration = 0.5f;
		this.initialDraw = initialDraw;
		this.firstDraw = true;
		BaseMod.subscribeToPostDraw(this);
	}
	
	@Override
	public void update() {
		if (this.duration == 0.5f && this.firstDraw) {
			this.firstDraw = false;
			this.listening = true;
			AbstractDungeon.actionManager.addToTop(new DrawCardAction(
					AbstractDungeon.player, this.initialDraw));
		} else if (!this.listening) {
			this.isDone = true;
		}
	}
	
	@Override
	public void receivePostDraw(AbstractCard c) {
		if (listening) {
			if (c.isEthereal) {
				this.listening = false;
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(
						AbstractDungeon.player, this.amount));
			}
		}
	}
	
}
