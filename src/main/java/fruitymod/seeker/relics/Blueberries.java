package fruitymod.seeker.relics;

import basemod.abstracts.CustomRelic;
import basemod.interfaces.MaxHPChangeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import fruitymod.SeekerMod;

public class Blueberries extends CustomRelic implements MaxHPChangeSubscriber {
    public static final String ID = "Blueberries";
    private static final int HP_PER_CARD = 1;

    int original_max_hp = 0;

    public Blueberries() {
        super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
                RelicTier.UNCOMMON, LandingSound.MAGICAL);
    }

    @Override
    public int receiveMaxHPChange(int amount) {
        original_max_hp += amount;
        return amount;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1];
    }

    @Override
    public void onEquip() {
        original_max_hp = AbstractDungeon.player.maxHealth;
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.isEthereal) {
                count++;
            }
        }
        AbstractDungeon.player.increaseMaxHp(count * HP_PER_CARD, true);
    }

    @Override
    public void onMasterDeckChange() {
        int count = 0;
        for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
            if (c.isEthereal) {
                count++;
            }
        }
        int amt_to_change = (AbstractDungeon.player.maxHealth - (original_max_hp + count));
        if (amt_to_change > 0) AbstractDungeon.player.increaseMaxHp(amt_to_change, true);
        if (amt_to_change < 0) AbstractDungeon.player.decreaseMaxHealth(amt_to_change);
    }

    @Override
    public AbstractRelic makeCopy() {
        return new Blueberries();
    }

}
