package fruitymod.seeker.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

public class ModifyBlockAction
        extends AbstractGameAction {
    AbstractCard cardToModify;

    public ModifyBlockAction(AbstractCard card, int amount) {
        this.setValues(this.target, this.source, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.cardToModify = card;
    }

    @Override
    public void update() {
        this.cardToModify.baseBlock += this.amount;
        if (this.cardToModify.baseBlock < 0) {
            this.cardToModify.baseBlock = 0;
        }
        this.isDone = true;
    }
}

