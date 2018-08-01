package fruitymod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.FruityMod;
import fruitymod.actions.unique.BottlecapAction;

public class BottlecapPower
        extends AbstractPower {
    public static final String POWER_ID = "BottlecapPower";
    public static final String NAME = "Bottlecap";
    public static final String DESCRIPTION = "At the end of your turn, you may cap your bottle";
    public BottlecapPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = FruityMod.getBottlecapPowerTexture();
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION;
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
            this.flash();

        if (AbstractDungeon.player.hand.isEmpty()) {
            return;
        }

        else {
            AbstractDungeon.actionManager.addToBottom(new BottlecapAction(AbstractDungeon.player, 1));
        }
    }
}
