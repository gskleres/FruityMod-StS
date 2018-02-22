package fruitymod.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.FruityMod;

public class EnigmaPower extends AbstractPower {
	public static final String POWER_ID = "EnigmaPower";
	public static final String NAME = "Enigma";
	public static final String[] DESCRIPTIONS = new String[] {
			"Dazed can now be played to gain ",
			" Block and deal ",
			" damage to ALL enemies."
	};
	
	public EnigmaPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		updateDescription();
		this.img = FruityMod.getEnigmaPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
				this.amount + DESCRIPTIONS[2];
	}
	
}
