package fruitymod.seeker.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;

public class SolarEgg extends CustomRelic {
    public static final String ID = "SolarEgg";

    public SolarEgg() {
        super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
                RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.isEthereal && c.canUpgrade()) {
            c.upgrade();
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return new SolarEgg();
    }

}
