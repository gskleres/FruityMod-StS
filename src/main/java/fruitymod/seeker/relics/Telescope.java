package fruitymod.seeker.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;
import fruitymod.seeker.actions.unique.TelescopeAction;

public class Telescope extends CustomRelic {
    public static final String ID = "Telescope";

    public Telescope() {
        super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
                RelicTier.RARE, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public void atBattleStart() {
        AbstractDungeon.actionManager.addToBottom(
                new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new TelescopeAction());
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Telescope();
    }
}
