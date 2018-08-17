package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;
import fruitymod.seeker.cards.Retrograde;

public class RetrogradeUpgradedPower
        extends AbstractPower {
    public static final String POWER_ID = "RetrogradeUpgradedPower";
    public static final String NAME = "Retrograde";
    public static final String[] DESCRIPTIONS = new String[] {
            "At the end of your turn, Shuffle-Cycle ",
            " Retrograde+ into your draw pile."
    };

    public RetrogradeUpgradedPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer)
        this.flash();
        AbstractCard toAdd = new Retrograde();
        toAdd.upgrade();
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(toAdd, this.amount, true, true));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "RetrogradeUpgradedPower"));
    }
}




