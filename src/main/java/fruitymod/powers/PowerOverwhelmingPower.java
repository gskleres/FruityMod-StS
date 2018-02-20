package fruitymod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import fruitymod.FruityMod;

public class PowerOverwhelmingPower extends AbstractPower {
	public static final String POWER_ID = "PowerOverwhelmingPower";
	public static final String NAME = "Power Overwhelming";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn gain 1 Vulnerable and deal ",
			" damage to ALL enemies."
	};
	
	private static final int VULNERABLE_AMT = 1;
	
	public PowerOverwhelmingPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getPowerOverwhelmingPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void atEndOfTurn(boolean isPlayer) {
		if (isPlayer) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, 
					DamageInfo.createDamageMatrix(this.amount, true),
					DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
			AbstractDungeon.actionManager.addToTop(
					new ApplyPowerAction(this.owner, this.owner, new VulnerablePower(this.owner, VULNERABLE_AMT, false), VULNERABLE_AMT));
		}
	}
}
