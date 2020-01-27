package fruitymod.tranquil.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.TranquilMod;

public class AttunedAttackPower extends AbstractPower {
    public static final String POWER_ID = "Tranquil_AttunedAttackPower";
    public static final String NAME = "Attuned (Attacks)";
    public static final String DESCRIPTION = "You are currently Attuned to Attacks.";

    public AttunedAttackPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = 0;
        this.updateDescription();
        this.img = new Texture(TranquilMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTION;
    }

    @Override
    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        super.onAfterUseCard(card, action);
        if (card.type != AbstractCard.CardType.ATTACK) {
            // remove all FlowPower and this power
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, this));
        }
    }

    @Override
    public void onInitialApplication() {
        super.onInitialApplication();
        if (!owner.hasPower("Tranquil_FlowPower")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner, new FlowPower(owner, 0)));
        }
        // TODO: if they have AttunedSkillPower, remove that here
    }
}
