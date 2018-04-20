package fruitymod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

import fruitymod.actions.common.ITopCycleCallback;

public class ChannelAction extends AbstractGameAction {
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
	public static final String[] TEXT = uiStrings.TEXT;
	private ITopCycleCallback cb;

	public ChannelAction(AbstractCreature source, int amount, ITopCycleCallback cb) {
		setValues(AbstractDungeon.player, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cb = cb;
		this.amount = amount;
	}

	@Override
	public void update() {
		if (this.duration == 0.5f) {
			if (AbstractDungeon.player.hand.size() > this.amount)
			{
				AbstractDungeon.handCardSelectScreen.open("Top-Cycle.", this.amount, true, false);
				AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
				tickDuration();
				return;
			}
			else if (AbstractDungeon.player.hand.size() > 0)
			{
				ArrayList<AbstractCard> temp = new ArrayList<AbstractCard>();
				for (AbstractCard c : AbstractDungeon.player.hand.group)
				{
					temp.add(c);
				}
				for (AbstractCard c : temp)
				{
					AbstractDungeon.player.hand.moveToDeck(c, false);
					this.cb.processCard(c);
				}
				tickDuration();
				return;
			}
		}
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				AbstractDungeon.player.hand.moveToDeck(c, false);
				this.cb.processCard(c);
			}
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
		}
		tickDuration();
	}
}
