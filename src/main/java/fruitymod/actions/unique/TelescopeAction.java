package fruitymod.actions.unique;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TelescopeAction extends AbstractGameAction {

	private static final String SELECT_TEXT = "make Ethereal.";
	
	private AbstractPlayer p;
	private ArrayList<AbstractCard> cannotEtherealize = new ArrayList<>();
	
	public TelescopeAction() {	
		this.p = AbstractDungeon.player;
		this.duration = Settings.ACTION_DUR_MED;
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
	}
	
	public void update() {
		if (this.duration == Settings.ACTION_DUR_MED) {
			System.out.println("looking for cards in hand");
			for (AbstractCard c : this.p.hand.group) {
				System.out.println("card " + c.name + " found in hand");
				if (c.isEthereal) {
					this.cannotEtherealize.add(c);
				}
			}
			
			for (AbstractCard c : this.cannotEtherealize) {
				System.out.println("card " + c.name + " added to pile of cards that cannot be made ethereal");
			}
			
			if (this.cannotEtherealize.size() == this.p.hand.group.size()) {
				System.out.println("all cards are already ethereal");
				this.isDone = true;
				return;
			}
			
			System.out.println("removing ethereal cards from the hand");
			this.p.hand.group.removeAll(this.cannotEtherealize);
			
			for (AbstractCard c : this.p.hand.group) {
				System.out.println("card " + c.name + " left in hand after removing ethereal cards");
			}
			
			System.out.println("opening card select for " + this.p.hand.size() + " cards");
			AbstractDungeon.handCardSelectScreen.open(
					SELECT_TEXT, 99, true, true);
			AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.WaitAction(0.25F));
			tickDuration();
			return;
		}
		
		if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
			System.out.println("cards were retrieved!");
			for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
				c.isEthereal = true;
				c.rawDescription = "Ethereal. NL " + c.rawDescription;
				c.initializeDescription();
				System.out.println("adding the card " + c.name + " back to the player's hand as an ethereal card");
				this.p.hand.addToTop(c);
			}
			
			System.out.println("returning ethereal cards to hand");
			returnCards();
			AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
			AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
			this.isDone = true;
		}

		tickDuration();
	}
	
	private void returnCards() {
		for (AbstractCard c : this.cannotEtherealize) {
			this.p.hand.addToTop(c);
		}
		
		// apply powers
		for (AbstractCard c : this.p.hand.group) {
			c.applyPowers();
		}
		
		this.p.hand.refreshHandLayout();
	}
	
}
