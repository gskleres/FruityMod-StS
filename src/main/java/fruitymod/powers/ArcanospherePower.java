package fruitymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;
import fruitymod.actions.unique.ArcanosphereAction;

public class ArcanospherePower
        extends AbstractPower {
    public static final String POWER_ID = "ArcanospherePower";
    public static final String NAME = "Arcanosphere";
    public static final String DESCRIPTION = "At the end of your turn, you may Top-Cycle 1 card.";
    public ArcanospherePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
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
            AbstractDungeon.actionManager.addToBottom(new ArcanosphereAction(AbstractDungeon.player, 1));
        }
    }
}
