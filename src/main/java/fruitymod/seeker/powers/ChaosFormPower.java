package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.actions.common.ReplayThisAction;

public class ChaosFormPower extends AbstractPower {
    public static final String POWER_ID = "ChaosFormPower";
    public static final String NAME = "Chaos Form";
    public static final String[] DESCRIPTIONS = new String[]{
            "Whenever you draw a #yEthereal card, #b",
            "% chance to play a copy of it."
    };

    public ChaosFormPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void onCardDraw(AbstractCard card) {
        if (card.isEthereal) {
            if (AbstractDungeon.cardRandomRng.random(0, 99) < amount) {
                flash();
                addToBot(new ReplayThisAction(card));
            }
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }
}
