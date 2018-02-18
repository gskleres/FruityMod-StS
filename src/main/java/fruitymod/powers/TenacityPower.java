package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

import fruitymod.FruityMod;

public class TenacityPower extends AbstractPower {
	public static final String POWER_ID = "TenacityPower";
	public static final String NAME = "Tenacity";
	public static final String[] DESCRIPTIONS = new String[] { "At the start of your turn, gain ", " Strength and ",
			" Dexterity." };
	public static final String[] UPGRADED_DESCRIPTIONS = new String[] { "At the start of your turn, gain ", " Strength, ",
	" Dexterity, and ", " Artifact" };
	private boolean upgraded;

	public TenacityPower(AbstractCreature owner, int amount, boolean upgraded) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.upgraded = upgraded;
		updateDescription();
		this.img = FruityMod.getTenacityPowerTexture();
	}
	
	@Override
	public void updateDescription() {
		if (this.upgraded) {
			this.description = UPGRADED_DESCRIPTIONS[0] + this.amount +
					UPGRADED_DESCRIPTIONS[1] + this.amount +
					UPGRADED_DESCRIPTIONS[2] + this.amount + UPGRADED_DESCRIPTIONS[3];
		} else {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] +
					this.amount + DESCRIPTIONS[2];
		}
	}
	
	@Override
	public void atStartOfTurnPostDraw() {
		flash();
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
				this.owner, this.owner,
				new StrengthPower(this.owner, this.amount), this.amount));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
				this.owner, this.owner,
				new DexterityPower(this.owner, this.amount), this.amount));
		if(this.upgraded) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
					this.owner, this.owner,
					new ArtifactPower(this.owner, this.amount), this.amount));
		}
	}
}
