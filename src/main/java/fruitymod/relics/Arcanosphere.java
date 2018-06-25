package fruitymod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;
import fruitymod.powers.ArcanospherePower;

public class Arcanosphere extends CustomRelic {
	private static final String ID = "Arcanosphere";
	private static final int CARDS_TO_RETAIN = 1;
	
	public Arcanosphere() {
		super(ID, FruityMod.getArcanoSphereTexture(),
				RelicTier.STARTER, LandingSound.MAGICAL);
	}
	
	/*@Override
	public void onPlayerEndTurn() {
		if (AbstractDungeon.player.hand.isEmpty()) {
			return;
		}
		AbstractDungeon.actionManager.addToBottom(
				new ArcanosphereAction(AbstractDungeon.player,
						CARDS_TO_RETAIN));
	}*/

	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ArcanospherePower(AbstractDungeon.player, CARDS_TO_RETAIN)));
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new Arcanosphere();
	}	
}
