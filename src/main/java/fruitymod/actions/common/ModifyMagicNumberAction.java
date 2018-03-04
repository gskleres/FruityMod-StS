package fruitymod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ModifyMagicNumberAction extends AbstractGameAction {
	AbstractCard cardToModify;
	
	
	public ModifyMagicNumberAction(AbstractCard card, int amount) {
		this.setValues(this.target, this.source, amount);
		this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
		this.cardToModify = card;
	}
	
	@Override
	public void update() {
		this.cardToModify.baseMagicNumber += this.amount;
		if (this.cardToModify.baseMagicNumber < 0) {
			this.cardToModify.baseMagicNumber = 0;
		}
		this.cardToModify.magicNumber = this.cardToModify.baseMagicNumber;
		this.isDone = true;
	}
	
}
