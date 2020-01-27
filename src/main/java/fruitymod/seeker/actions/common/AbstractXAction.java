package fruitymod.seeker.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;

public abstract class AbstractXAction extends AbstractGameAction {
    public void initialize(int totalAmount) {
        this.amount = totalAmount;
    }
}