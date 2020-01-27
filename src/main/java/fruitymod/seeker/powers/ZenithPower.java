package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.SeekerMod;

public class ZenithPower extends AbstractPower {
    public static final String POWER_ID = "Anomaly";
    public static final String NAME = "Anomaly";
    public static final String[] DESCRIPTIONS = new String[]{
            "Whenever you shuffle your discard pile into your draw pile, gain #b",
            " #yVulnerable."
    };

    public ZenithPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
