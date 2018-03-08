package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.FruityMod;

public class CreativityPower
extends AbstractPower {
    public static final String POWER_ID = "Creativity";
    public static final String NAME = "Creativity";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn, Shuffle-Cycle ",
			" Dazed into your draw pile, then draw ",
			"a card.",
			" cards."
	};

    public CreativityPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = FruityMod.getCreativityPowerTexture();
    }
    
    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + (this.amount == 1 ? DESCRIPTIONS[2] : this.amount + DESCRIPTIONS[3]);
    }

    @Override
    public void atStartOfTurn() {
    	this.flash();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(this.owner, this.owner, new Dazed(), this.amount, true, true));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));
    }
}




