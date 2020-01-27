package fruitymod.seeker.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class EtherealizeAction
        extends AbstractGameAction {
    public static final String[] TEXT = null;
    @SuppressWarnings("unused")
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("ArmamentsAction");
    private AbstractPlayer p;

    public EtherealizeAction() {
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.p = AbstractDungeon.player;
    }

    @Override
    public void update() {
        for (AbstractCard c : this.p.hand.group) {
            if (c.isEthereal) continue;
            c.isEthereal = true;
            c.rawDescription = "Ethereal. " + c.rawDescription;
            c.initializeDescription();
            c.superFlash();
        }
        // let cards update themselves with the new information
        for (AbstractCard c : this.p.hand.group) {
            c.applyPowers();
        }
        this.isDone = true;
    }
}


