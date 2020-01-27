package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.SeekerMod;

public class EclipsePower extends AbstractPower {
    public static final String POWER_ID = "EclipsePower";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public EclipsePower(int magic) {
        name = NAME;
        ID = POWER_ID;
        this.amount = magic;
        this.owner = AbstractDungeon.player;
        type = PowerType.BUFF;
        this.img = new Texture(SeekerMod.makePowerImagePath(ChaosFormPower.POWER_ID));
        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (amount == 1) {
            description = "Whenever you draw an #yEthereal card, draw #b" + amount + " card.";
        } else {
            description = "Whenever you draw an #yEthereal card, draw #b" + amount + " cards.";
        }
    }

    public void onCardDraw(AbstractCard card) {
        if (card.isEthereal) {
            this.flash();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(this.owner, this.amount));// 38
        }
    }// 40
}