package fruitymod.tranquil.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import fruitymod.TranquilMod;

public class FlowPower extends AbstractPower {
    public static final String POWER_ID = "Tranquil_FlowPower";
    public static final String NAME = "Flow";
    public static final String DESCRIPTION = "You gain 1 Strength and 1 Dexterity per stack of Flow.";

    public FlowPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
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
        if (
                (owner.hasPower("Tranquil_AttunedAttackPower") && card.type == AbstractCard.CardType.ATTACK)
            //|| (owner.hasPower("Tranquil_AttunedSkillPower") && card.type == AbstractCard.CardType.SKILL)
        ) {
            this.amount++;
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, 1), 1));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new DexterityPower(owner, 1), 1));
        } else {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new StrengthPower(owner, -1 * amount), -1 * amount));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(owner, owner,
                    new DexterityPower(owner, -1 * amount), -1 * amount));
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(owner, owner, this));
        }
    }
}
