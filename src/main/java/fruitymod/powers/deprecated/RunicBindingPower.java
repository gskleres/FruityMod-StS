package fruitymod.powers.deprecated;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;

import fruitymod.SeekerMod;

public class RunicBindingPower extends AbstractPower {
	public static final String POWER_ID = "RunicBindingPower";
	public static final String NAME = "Runic Binding";
	public static final String[] DESCRIPTIONS = new String[] { "At the start of your turn, gain ", " Artifact."};
	public static final String[] UPGRADED_DESCRIPTIONS = new String[] { "At the start of your turn, gain ", " Artifact."};
	private boolean upgraded;

	public RunicBindingPower(AbstractCreature owner, int amount, boolean upgraded) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.upgraded = upgraded;
		updateDescription();
		this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
	}
	
	@Override
	public void updateDescription() {
		if (this.upgraded) {
			this.description = UPGRADED_DESCRIPTIONS[0] + this.amount +
					UPGRADED_DESCRIPTIONS[1];
		} else {
			this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
		}
	}
	
	@Override
	public void atStartOfTurnPostDraw() {
		flash();
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
				this.owner, this.owner,
				new ArtifactPower(this.owner, this.amount), this.amount));
	}
}
