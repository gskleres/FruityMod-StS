package fruitymod.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.SeekerMod;
import fruitymod.actions.unique.TelescopeAction;

public class Telescope extends CustomRelic {
	private static final String ID = "Telescope";

	public Telescope() {
		super(ID, SeekerMod.getTelescopeTexture(),
				RelicTier.RARE, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToBottom(
				new RelicAboveCreatureAction(AbstractDungeon.player, this));
		this.flash();
		AbstractDungeon.actionManager.addToBottom(new TelescopeAction());
	}

	@Override
	public AbstractRelic makeCopy() {
		return new Telescope();
	}
}
