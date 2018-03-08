package fruitymod.relics;

import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class CosmicSieve extends CustomRelic {
	private static final String ID = "CosmicSieve";
	public static final int HP_PER_CARD = 1;
	
	public CosmicSieve() {
		super(ID, FruityMod.getCosmicSieveTexture(),
				RelicTier.BOSS, LandingSound.MAGICAL);
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new CosmicSieve();
	}
	
}
