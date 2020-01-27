package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.SeekerMod;

public class EventHorizonPower
        extends AbstractPower {
    public static final String POWER_ID = "EventHorizon";
    public static final String NAME = "Event Horizon";
    public static final String DESCRIPTION = "Each enemy loses HP equal to the amount of Weak and Vulnerable it has at the start of its turn.";

    public EventHorizonPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.description = DESCRIPTION;
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                int stackCount = GetPowerCount(m, "Weakened") + GetPowerCount(m, "Vulnerable");
                if (stackCount > 0) {
                    AbstractDungeon.actionManager.addToBottom(
                            new DamageAction(m, new DamageInfo(null, this.amount * stackCount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
                }
            }
        }
    }

    private int GetPowerCount(AbstractMonster m, String powerId) {
        AbstractPower power = m.getPower(powerId);
        return power != null ? power.amount : 0;
    }
}

