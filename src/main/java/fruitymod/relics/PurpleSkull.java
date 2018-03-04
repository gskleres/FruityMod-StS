package fruitymod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class PurpleSkull extends CustomRelic {
	private static final String ID = "PurpleSkull";
	public static final int MIN_STACKS = 2;
	public static final int DEX_GAIN = 3;
	
	public PurpleSkull() {
		super(ID, FruityMod.getPurpleSkullTexture(),
				RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	public void setPulse(boolean doPulse) {
		this.pulse = doPulse;
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + MIN_STACKS + DESCRIPTIONS[1] +
				DEX_GAIN + DESCRIPTIONS[2];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new PurpleSkull();
	}
	
}
