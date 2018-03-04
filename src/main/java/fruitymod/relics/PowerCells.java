package fruitymod.relics;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;
import fruitymod.actions.EtherealizeAction;

public class PowerCells extends CustomRelic {
	private static final String ID = "PowerCells";
	private static final int CARDS_TO_DRAW = 2;
	private static final int ENERGY_TO_GAIN = 1;

	public PowerCells() {
		super(ID, FruityMod.getPowerCellsTexture(), RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + ENERGY_TO_GAIN + DESCRIPTIONS[1] + CARDS_TO_DRAW + DESCRIPTIONS[2];
	}

	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToBottom(
				new RelicAboveCreatureAction(AbstractDungeon.player, this));
		AbstractDungeon.actionManager
				.addToBottom(new DrawCardAction(AbstractDungeon.player, CARDS_TO_DRAW));
		AbstractDungeon.actionManager
				.addToBottom(new GainEnergyAction(ENERGY_TO_GAIN));
		AbstractDungeon.actionManager
				.addToBottom(new EtherealizeAction());
	}

	@Override
	public AbstractRelic makeCopy() {
		return new PowerCells();
	}
}
