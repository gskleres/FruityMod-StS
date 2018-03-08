package fruitymod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class PaperPengwin extends CustomRelic {
	private static final String ID = "PaperPengwin";
	public static final int MIN_STACKS = 1;
	public static final int STAT_GAIN = 1;
	
	public PaperPengwin() {
		super(ID, FruityMod.getPaperPengwinTexture(),
				RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	public void setPulse(boolean doPulse) {
		this.pulse = doPulse;
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + STAT_GAIN + DESCRIPTIONS[1] +
				STAT_GAIN + DESCRIPTIONS[2];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new PaperPengwin();
	}
	
}
