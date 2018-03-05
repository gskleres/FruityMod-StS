package fruitymod.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
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
	
	@Override
	public void onDrawOrDiscard() {
		updateDazedDescriptions(null);
	}
	
	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		updateDazedDescriptions(power);
	}
	
	private void updateDazedDescriptions(AbstractPower applied) {
		int damage = this.amount;
		int block = this.amount;
		if (AbstractDungeon.player.hasPower("Strength")) {
			damage += AbstractDungeon.player.getPower("Strength").amount;
		}
		if (AbstractDungeon.player.hasPower("Dexterity")) {
			block += AbstractDungeon.player.getPower("Dexterity").amount;
		}
		if (applied != null && applied.ID.equals("Strength")) {
			damage += applied.amount;
		}
		if (applied != null && applied.ID.equals("Dexterity")) {
			block += applied.amount;
		}
		
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof Dazed) {
				c.rawDescription = "Ethereal. Gain " + block + " Block. Deal " + damage + " damage to all enemies.";
				c.initializeDescription();
			}
		}
	}
	
}
