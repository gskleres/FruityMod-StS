package fruitymod.relics;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class AstralEgg extends CustomRelic {
	private static final String ID = "AstralEgg";
	
	public AstralEgg() {
		super(ID, FruityMod.getAstralEggTexture(),
				RelicTier.UNCOMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void onObtainCard(AbstractCard c) {
		if (c.isEthereal && c.canUpgrade()) {
			c.upgrade();
		}
	}
	
	@Override
	public String getUpdatedDescription() {
		return DESCRIPTIONS[0];
	}
	
	@Override
	public AbstractRelic makeCopy() {
		return new AstralEgg();
	}
	
}
