package fruitymod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class EclipseAction
extends AbstractGameAction {
    private AbstractPlayer p;
    private ArrayList<AbstractCard> invalidCards = new ArrayList();

    public EclipseAction(AbstractMonster target) {
        this.p = AbstractDungeon.player;
        this.setValues(this.p, AbstractDungeon.player, this.amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_FAST;
        this.target = target;
    }

    @Override
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (AbstractDungeon.player.hand.size() == 10) {
                AbstractDungeon.player.createHandIsFullDialog();
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.isDone = true;
                return;
            }
            if (this.p.exhaustPile.size() == 1) {
                AbstractCard c = this.p.exhaustPile.getTopCard();
                c.unfadeOut();
                this.p.hand.addToHand(c);
                this.p.exhaustPile.removeCard(c);                
                c.unhover();
                c.fadingOut = false;
                this.isDone = true;
                return;
            }
            for (AbstractCard c : this.p.exhaustPile.group) {
                c.stopGlowing();
                c.unhover();
                c.unfadeOut();
            }
            Iterator<AbstractCard> c = this.p.exhaustPile.group.iterator();
            while (c.hasNext()) {
                AbstractCard derp = c.next();
                if (!derp.cardID.equals("Eclipse") && derp.isEthereal) continue;
                c.remove();
                this.invalidCards.add(derp);
            }
            if (this.p.exhaustPile.isEmpty()) {
                this.p.exhaustPile.group.addAll(this.invalidCards);
                this.invalidCards.clear();
                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(this.p.exhaustPile, 1, "Select a card to play", false);
            this.tickDuration();
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
            	AbstractDungeon.actionManager.addToBottom(new PlayCardEffectAction(c, (AbstractMonster)this.target));
                this.p.exhaustPile.removeCard(c);                
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
            this.p.exhaustPile.group.addAll(this.invalidCards);
            this.invalidCards.clear();
            for (AbstractCard c : this.p.exhaustPile.group) {
                c.unhover();
                c.target_x = CardGroup.DISCARD_PILE_X;
                c.target_y = 0.0f;
            }
        }
        this.tickDuration();
    }
    
}


