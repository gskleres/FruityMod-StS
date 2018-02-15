package fruitymod.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;
import fruitymod.actions.ArcanosphereAction;

public class ArcanoSphere extends CustomRelic {
	private static final String ID = "Arcanosphere";
	private static final int CARDS_TO_RETAIN = 1;
	
	public ArcanoSphere() {
		super(ID, FruityMod.getArcanoSphereTexture(),
				RelicTier.STARTER, LandingSound.MAGICAL);
	}
	
	@Override
	public void onPlayerEndTurn() {
		AbstractDungeon.actionManager.addToBottom(
				new ArcanosphereAction(AbstractDungeon.player,
						CARDS_TO_RETAIN));
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + DESCRIPTIONS[1] +
				DESCRIPTIONS[2];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new ArcanoSphere();
	}	
}
