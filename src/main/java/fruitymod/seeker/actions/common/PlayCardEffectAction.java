package fruitymod.seeker.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PlayCardEffectAction extends AbstractGameAction {


    private AbstractCard card;

    public PlayCardEffectAction(AbstractCard card, AbstractMonster target) {
        this.card = card;
        this.target = target;
    }

    @Override
    public void update() {
        AbstractCard tmp = card.makeStatEquivalentCopy();
        AbstractDungeon.player.limbo.addToBottom(tmp);
        tmp.current_x = card.current_x;
        tmp.current_y = card.current_y;
        tmp.target_x = (float) Settings.WIDTH / 2.0f - 300.0f * Settings.scale;
        tmp.target_y = (float) Settings.HEIGHT / 2.0f;
        tmp.freeToPlayOnce = true;
        if (this.target != null) {
            tmp.calculateCardDamage((AbstractMonster) this.target);
        }
        tmp.purgeOnUse = true;
        AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(tmp, (AbstractMonster) this.target, card.energyOnUse));
        this.isDone = true;
    }
}