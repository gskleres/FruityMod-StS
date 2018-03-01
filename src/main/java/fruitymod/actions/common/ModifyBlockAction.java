package fruitymod.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;

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
        if (this.cardToModify.baseDamage < 0) {
            this.cardToModify.baseDamage = 0;
        }
        this.isDone = true;
    }
}

