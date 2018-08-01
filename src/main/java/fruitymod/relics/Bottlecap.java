package fruitymod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;
import fruitymod.powers.BottlecapPower;

public class Bottlecap extends CustomRelic {
	private static final String ID = "Bottlecap";
	private static final int CARDS_TO_RETAIN = 1;

	public Bottlecap() {
		super(ID, FruityMod.getBottlecapTexture(),
				RelicTier.STARTER, LandingSound.MAGICAL);
	}

	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new BottlecapPower(AbstractDungeon.player, CARDS_TO_RETAIN)));
	}

	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}

	@Override
	public AbstractRelic makeCopy() {
		return new Bottlecap();
	}
}
