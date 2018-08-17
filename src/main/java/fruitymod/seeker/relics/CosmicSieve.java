package fruitymod.seeker.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.SeekerMod;


public class CosmicSieve extends CustomRelic {
	public static final String ID = "CosmicSieve";
	public static final int HP_PER_CARD = 1;
	
	public CosmicSieve() {
		super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
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
