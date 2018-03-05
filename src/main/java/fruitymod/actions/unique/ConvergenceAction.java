package fruitymod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ConvergenceAction extends AbstractGameAction {
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotUpgrade = new ArrayList<>();
	private ArrayList<AbstractCard> canUpgrade = new ArrayList<>();
	private boolean upgraded = false;
	
	public static final String TEXT = "Select a card to upgrade.";

	public ConvergenceAction(boolean upgraded) {
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_FAST;
		this.upgraded = upgraded;
	}

	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {

			if (!this.upgraded) {
				for (AbstractCard c : this.p.hand.group) {
					if (c.canUpgrade()) {
						canUpgrade.add(c);
					}
				}
				
				if (this.canUpgrade.size() == 0) {
					this.isDone = true;
					return;
				}
				
				AbstractCard selected = this.canUpgrade.get(AbstractDungeon.cardRng.random(this.canUpgrade.size() - 1));
				selected.upgrade();
				selected.superFlash();
				
				this.isDone = true;
				return;
			}

			for (AbstractCard c : this.p.hand.group) {
				if (!c.canUpgrade()) {
					this.cannotUpgrade.add(c);
				}
			}

			if (this.cannotUpgrade.size() == this.p.hand.group.size()) {
				this.isDone = true;
				return;
			}

			if (this.p.hand.group.size() - this.cannotUpgrade.size() == 1) {
				for (AbstractCard c : this.p.hand.group) {
					if (c.canUpgrade()) {
						c.upgrade();
						this.isDone = true;
						return;
					}
				}
			}

			this.p.hand.group.removeAll(this.cannotUpgrade);

			if (this.p.hand.group.size() > 1) {
				AbstractDungeon.handCardSelectScreen.open(TEXT, 1, false, false, false, true);
				tickDuration();
				return;
			}
			if (this.p.hand.group.size() == 1) {
				this.p.hand.getTopCard().upgrade();
				returnCards();
				this.isDone = true;
			}
		}

		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				c.upgrade();
				this.p.hand.addToTop(c);
			}

			returnCards();
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
			this.isDone = true;
		}

		tickDuration();
	}

	private void returnCards() {
		for (AbstractCard c : this.cannotUpgrade) {
			this.p.hand.addToTop(c);
		}
		this.p.hand.refreshHandLayout();
	}
}
