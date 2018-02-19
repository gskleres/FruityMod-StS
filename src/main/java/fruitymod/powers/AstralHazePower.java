package fruitymod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
	public static final String NAME = "Astral Barrier";
	public static final String[] DESCRIPTIONS = new String[] {
			"When you are attacked this turn apply ",
			" Vulnerable and ",
			" Weak to the attacker."
	};
	private boolean justApplied = false;

	public AstralHazePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
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
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "AstralHazePower"));
	}

	@Override
	public int onAttacked(DamageInfo info, int damageAmount) {
		if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS
				&& info.owner != null && info.owner != this.owner) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(info.owner, this.owner,
					new WeakPower(info.owner, this.amount, true), this.amount, true, AbstractGameAction.AttackEffect.NONE));
			AbstractDungeon.actionManager.addToBottom(
					new ApplyPowerAction(info.owner, this.owner, new VulnerablePower(info.owner, this.amount, true), this.amount,
							true, AbstractGameAction.AttackEffect.NONE));
		}
		return damageAmount;
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
				this.amount + DESCRIPTIONS[2];
	}
}