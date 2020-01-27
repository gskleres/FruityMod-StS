package fruitymod.seeker.actions.common;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardWithCallbackAction extends DiscardAction {

    private static final float DURATION = Settings.ACTION_DUR_XFAST;
    private AbstractPlayer p;
    private boolean isRandom;
    private boolean endTurn;
    private boolean canPickZero;
    private boolean anyNumber;
    private IDiscardCallback cb;

    public DiscardWithCallbackAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom, boolean canPickZero, boolean anyNumber, IDiscardCallback cb) {
        this(target, source, amount, isRandom, canPickZero, anyNumber, false, cb);
    }

    public DiscardWithCallbackAction(AbstractCreature target, AbstractCreature source, int amount, boolean isRandom,
                                     boolean canPickZero, boolean anyNumber, boolean endTurn, IDiscardCallback cb) {
        super(target, source, amount, isRandom, endTurn);

        this.p = (AbstractPlayer) target;
        this.isRandom = isRandom;
        this.endTurn = endTurn;
        this.canPickZero = canPickZero;
        this.anyNumber = anyNumber;
        this.cb = cb;
    }

    @Override
    public void update() {
        System.out.println("updating custom discard action");
        int i;
        if (this.duration == DURATION) {
            System.out.println("in duration");
            System.out.println("discard amt is " + this.amount);
            if (this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                int tmp = this.p.hand.size();

                for (i = 0; i < tmp; i++) {
                    AbstractCard c = this.p.hand.getTopCard();
                    this.p.hand.moveToDiscardPile(c);
                    if (!this.endTurn) {
                        c.triggerOnManualDiscard();
                    }
                    GameActionManager.incrementDiscard(this.endTurn);
                }

                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }

            if (this.isRandom) {
                for (i = 0; i < this.amount; i++) {
                    AbstractCard c = this.p.hand.getRandomCard(false);
                    this.p.hand.moveToDiscardPile(c);
                    c.triggerOnManualDiscard();
                    GameActionManager.incrementDiscard(this.endTurn);
                }
            } else {
                if (this.amount < 0) {
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], 99, true, true);
                    AbstractDungeon.player.hand.applyPowers();
                    tickDuration();
                    return;
                }
                if (this.p.hand.size() > this.amount) {
                    System.out.println("trying to open hand card select screen");
                    AbstractDungeon.handCardSelectScreen.open(TEXT[0], this.amount, this.anyNumber, this.canPickZero);
                }

                AbstractDungeon.player.hand.applyPowers();
                tickDuration();
                return;
            }
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
            numDiscarded = 0;
            for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                this.p.hand.moveToDiscardPile(c);
                c.triggerOnManualDiscard();
                this.cb.processCard(c);
                numDiscarded++;
                GameActionManager.incrementDiscard(this.endTurn);
            }
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }
}