package fruitymod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class Blueberries extends CustomRelic {
	public static final String ID = "Blueberries";
	private static final int HP_PER_CARD = 1;
	
	public Blueberries() {
		super(ID, FruityMod.getBlueberriesTexture(),
				RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0] + HP_PER_CARD + DESCRIPTIONS[1];
	}
	
	@Override
	public void onEquip() {
		int count = 0;
		for (AbstractCard c : AbstractDungeon.player.masterDeck.group) {
			if (c.isEthereal) {
				count++;
			}
		}
		AbstractDungeon.player.increaseMaxHp(count * HP_PER_CARD, true);
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new Blueberries();
	}
	
}
