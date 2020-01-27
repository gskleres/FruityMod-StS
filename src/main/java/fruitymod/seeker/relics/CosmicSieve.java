package fruitymod.seeker.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;


public class CosmicSieve extends CustomRelic {
    public static final String ID = "CosmicSieve";

    public CosmicSieve() {
        super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
                RelicTier.BOSS, LandingSound.MAGICAL);
    }

    @Override
    public void onEnterRestRoom() {
        flash();
        int i = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.isEthereal) i++;
        }
        if (i > 0) AbstractDungeon.player.heal(i, true);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new CosmicSieve();
    }

}
