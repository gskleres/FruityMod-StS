package fruitymod.seeker.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import fruitymod.seeker.powers.ZenithPower;

@SpirePatch(
        clz = ShuffleAction.class,
        method = "update"
)
public class ZenithPatch {
    public static void Prefix(ShuffleAction __instance) {
        if (AbstractDungeon.player.hasPower(ZenithPower.POWER_ID)) {
            AbstractPower q = AbstractDungeon.player.getPower(ZenithPower.POWER_ID);
            q.flash();
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VulnerablePower(AbstractDungeon.player, q.amount, false), q.amount));
        }
    }
}