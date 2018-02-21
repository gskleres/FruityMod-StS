package fruitymod.actions.unique;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

public class CoronaAction
extends AbstractGameAction {
    private AbstractPlayer p;

    public CoronaAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
        this.duration = Settings.ACTION_DUR_FAST;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            boolean betterPossible = false;
            boolean possible = false;
            for (AbstractCard c : this.p.hand.group) {
                if (c.costForTurn > 0 && c.isEthereal) {
                    betterPossible = true;
                    continue;
                }
                if (c.cost <= 0 || !c.isEthereal) continue;
                possible = true;
            }
            if (betterPossible || possible) {
                this.findAndModifyCard(betterPossible);
            }
        }
        this.tickDuration();
    }

    private void findAndModifyCard(boolean better) {
        AbstractCard c = this.p.hand.getRandomCard(false);
        if (better) {
            if (c.costForTurn > 0  && c.isEthereal) {
                c.costForTurn = 0;
                c.isCostModified = true;
                c.superFlash(Color.GOLD.cpy());
            } else {
                this.findAndModifyCard(better);
            }
        } else if (c.cost > 0  && c.isEthereal) {
            c.costForTurn = 0;
            c.isCostModified = true;
            c.superFlash(Color.GOLD.cpy());
        } else {
            this.findAndModifyCard(better);
        }
    }
}

