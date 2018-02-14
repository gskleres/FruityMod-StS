package fruitymod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;

import fruitymod.FruityMod;

public class AstralHazePower extends AbstractPower {
	public static final String POWER_ID = "AstralHazePower";
	public static final String NAME = "Astral Haze";
	private boolean justApplied = false;

	// TODO: Add icon and description for power.
	public AstralHazePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.priority = 90;
		this.img = FruityMod.getAstralHazePowerTexture();
	}

	@Override
	public void atEndOfRound() {
		if (this.justApplied) {
			this.justApplied = false;
			return;
		}
		if (this.amount == 0) {
			AbstractDungeon.actionManager
					.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "AstralHazePower"));
		} else {
			AbstractDungeon.actionManager
					.addToBottom(new ReducePowerAction(this.owner, this.owner, "AstralHazePower", 1));
		}
	}

	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
				&& info.owner != null && info.owner != this.owner) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, this.owner,
					new WeakPower(info.owner, this.amount, false), 1, true, AbstractGameAction.AttackEffect.NONE));
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(info.owner, this.owner, new VulnerablePower(info.owner, this.amount, false), 1,
							true, AbstractGameAction.AttackEffect.NONE));
		}
		return damageAmount;
	}
}