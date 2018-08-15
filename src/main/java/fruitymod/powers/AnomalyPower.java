package fruitymod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class AnomalyPower extends AbstractPower {
	public static final String POWER_ID = "Anomaly";
	public static final String NAME = "Anomaly";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you apply Vulnerable or Weak to an enemy, apply ",
			" additional stacks."
	};
	
	public AnomalyPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.img = SeekerMod.getAnomalyPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		if(target != this.owner && source==this.owner && (power.ID.equals("Weakened") || power.ID.equals("Vulnerable"))) {
			power.amount += this.amount;
			
			// when powers stack for some bizzare reason it uses the amount on the ApplyPowerAction
			// instead of the amount on the power being applied
			AbstractDungeon.actionManager.currentAction.amount += this.amount;
		}
	}	
}
