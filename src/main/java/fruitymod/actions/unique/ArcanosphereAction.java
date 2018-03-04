package fruitymod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class ArcanosphereAction extends AbstractGameAction {
	private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("RetainCardsAction");
	public static final String[] TEXT = uiStrings.TEXT;

	public ArcanosphereAction(AbstractCreature source, int amount) {
		setValues(AbstractDungeon.player, source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	}

	@Override
	public void update() {
		if (this.duration == 0.5f) {
			AbstractDungeon.handCardSelectScreen.open("Recycle.", this.amount, false);
			AbstractDungeon.actionManager.addToBottom(new WaitAction(0.25F));
			tickDuration();
			return;
		}
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				AbstractDungeon.player.hand.moveToDeck(c, false);
			}
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
		}
		tickDuration();
	}
}