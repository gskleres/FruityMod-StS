package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import fruitymod.SeekerMod;

public class PremonitionsPower extends AbstractPower {
    public static final String POWER_ID = "PremonitionsPower";
    public static final String NAME = "Premonitions";
    public static final String DESCRIPTION = "Whenever you play a #yAttack, gain #b";

    public PremonitionsPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.priority = 90;
        updateDescription();
        this.img = new Texture(SeekerMod.makePowerImagePath(PowerOverwhelmingPower.POWER_ID));
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new WeakPower(owner, amount, false), amount));
        }
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION + amount + " #yWeak.";
    }
}
