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
			"At the start of your turn gain ",
			" Vulnerable and deal ",
			" damage to ALL enemies."
	};
	
	private int vulnerableAmount;
	
	public PowerOverwhelmingPower(AbstractCreature owner, int vulnerableAmount, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.vulnerableAmount = vulnerableAmount;
		this.img = FruityMod.getPowerOverwhelmingPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.vulnerableAmount +
				DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
	
	@Override
	public void stackPower(int stackAmount) {
		this.fontScale = 8.0f;
		this.amount += stackAmount;
		this.vulnerableAmount += 1;
	}
	
	@Override
	public void atStartOfTurn() {
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(null, 
				DamageInfo.createDamageMatrix(this.amount, true),
				DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(this.owner, this.owner,
						new VulnerablePower(this.owner, this.vulnerableAmount, false), this.vulnerableAmount));
	}
}
