package fruitymod.seeker.actions.common;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToDrawPileEffect;
import java.util.ArrayList;

public class ShuffleCardsToDrawPileAction
extends AbstractGameAction {
    private ArrayList<AbstractCard> cardsToShuffle;
    private boolean randomSpot = false;
    private boolean cardOffset = false;

    public ShuffleCardsToDrawPileAction(AbstractCreature target, AbstractCreature source, ArrayList<AbstractCard> cards, boolean randomSpot, boolean cardOffset) {
        this.setValues(target, source, cards.size());
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.amount = cards.size();
        this.duration = 0.5f;
        this.cardsToShuffle = cards;
        this.randomSpot = randomSpot;
        this.cardOffset = cardOffset;
    }

    @Override
    public void update() {
        if (this.duration == 0.5f) {
            if (this.amount < 6) {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractCard c = cardsToShuffle.get(i);
                    AbstractDungeon.player.hand.removeCard(c);
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, (float)Settings.WIDTH / 2.0f, (float)Settings.HEIGHT / 2.0f, this.randomSpot, this.cardOffset));
                }
            } else {
                for (int i = 0; i < this.amount; ++i) {
                    AbstractCard c = cardsToShuffle.get(i);
                    AbstractDungeon.player.hand.removeCard(c);
                    AbstractDungeon.effectList.add(new ShowCardAndAddToDrawPileEffect(c, this.randomSpot));
                }
            }
            this.duration -= Gdx.graphics.getDeltaTime();
        }
        this.tickDuration();
    }
}

