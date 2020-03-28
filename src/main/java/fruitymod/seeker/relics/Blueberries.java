package fruitymod.seeker.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.MaxHPChangeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;

public class Blueberries extends CustomRelic {
    public static final String ID = "Blueberries";
    private static final int HP_PER_CARD = 1;

    public Blueberries() {
        super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
                RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.isEthereal) {
                count++;
            }
        }
        AbstractDungeon.player.increaseMaxHp(count, true);
    }

    @Override
    public void onObtainCard(AbstractCard c) {
        if (c.isEthereal) {
            AbstractDungeon.player.increaseMaxHp(1, true);
        }
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Blueberries();
    }

}
