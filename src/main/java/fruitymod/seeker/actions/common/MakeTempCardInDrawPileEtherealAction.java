package fruitymod.seeker.actions.common;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;

public class MakeTempCardInDrawPileEtherealAction extends com.megacrit.cardcrawl.actions.AbstractGameAction {
    private AbstractCard cardToMake;
    private boolean randomSpot = false;
    private boolean cardOffset = false;

    public MakeTempCardInDrawPileEtherealAction(AbstractCreature target, AbstractCreature source, AbstractCard card,
                                                int amount, boolean randomSpot, boolean cardOffset) {
        setValues(target, source, amount);
        this.actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = 0.5F;
        this.cardToMake = card;
        this.randomSpot = randomSpot;
        this.cardOffset = cardOffset;
    }

    public void update() {
        if (this.duration == 0.5F) {

            if (this.amount < 6) {
                for (int i = 0; i < this.amount; i++) {
                    AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                    if (!c.isEthereal) {
                        c.isEthereal = true;
                        c.rawDescription = "Ethereal. " + c.rawDescription;
                        c.initializeDescription();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, Settings.WIDTH / 2.0F,
                            Settings.HEIGHT / 2.0F, this.randomSpot, this.cardOffset));

                }

            } else {

                for (int i = 0; i < this.amount; i++) {
                    AbstractCard c = this.cardToMake.makeStatEquivalentCopy();
                    if (!c.isEthereal) {
                        c.isEthereal = true;
                        c.rawDescription = "Ethereal. " + c.rawDescription;
                        c.initializeDescription();
                    }
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, 0, 0, this.randomSpot));
                }
            }
            this.duration -= com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        }

        tickDuration();
    }
}