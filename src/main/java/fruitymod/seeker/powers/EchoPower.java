package fruitymod.seeker.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.seeker.actions.common.PlayCardEffectAction;

public class EchoPower
extends AbstractPower {
    public static final String POWER_ID = "Echo";
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Double Tap");
    public static final String NAME = "Echo";

    public EchoPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.updateDescription();
        this.loadRegion("doubleTap");
    }

    @Override
    public void updateDescription() {
        this.description = this.amount == 1 ? "The next Ethereal card is played twice" : "The next " +this.amount+ " Ethereal cards are played twice";
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!card.purgeOnUse && card.isEthereal && this.amount > 0) {
            this.flash();
            AbstractMonster m = null;
            if (action.target != null) {
                m = (AbstractMonster)action.target;
            }            

        	AbstractDungeon.actionManager.addToBottom(new PlayCardEffectAction(card.makeStatEquivalentCopy(), m));
            --this.amount;
            if (this.amount == 0) {
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
            }
        }
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }
    }
}

