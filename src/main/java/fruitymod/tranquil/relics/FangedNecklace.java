package fruitymod.tranquil.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;
import fruitymod.TranquilMod;


public class FangedNecklace extends CustomRelic {
    public static final String ID = "FangedNecklace";
    private static final int ENERGY_PER_TURN = 1;
    private static final int BLOCK_PER_ATTACK = 4;

    public FangedNecklace() {
        super(ID, new Texture(TranquilMod.makeRelicImagePath(ID)),
                RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] +
                ENERGY_PER_TURN +
                DESCRIPTIONS[1] +
                BLOCK_PER_ATTACK +
                DESCRIPTIONS[2];
    }

    @Override
    public void atTurnStart() {
        AbstractDungeon.actionManager.addToBottom(
                new GainEnergyAction(ENERGY_PER_TURN));
        this.flash();
    }

    @Override
    public AbstractRelic makeCopy() {
        return new FangedNecklace();
    }
}

