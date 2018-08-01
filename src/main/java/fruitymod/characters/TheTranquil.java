package fruitymod.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.DailyMods;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fruitymod.FruityMod;
import fruitymod.patches.TheTranquilEnum;

import java.util.ArrayList;

public class TheTranquil extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 4;

	public static final String[] orbTextures = {
			"img/char/tranquil/orb/layer1.png",
			"img/char/tranquil/orb/layer2.png",
			"img/char/tranquil/orb/layer3.png",
			"img/char/tranquil/orb/layer4.png",
			"img/char/tranquil/orb/layer5.png",
			"img/char/tranquil/orb/layer6.png",
			"img/char/tranquil/orb/layer1d.png",
			"img/char/tranquil/orb/layer2d.png",
			"img/char/tranquil/orb/layer3d.png",
			"img/char/tranquil/orb/layer4d.png",
			"img/char/tranquil/orb/layer5d.png",
	};

	public TheTranquil(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, "img/char/tranquil/orb/vfx.png", new SpriterAnimation("img/char/tranquil/animation.scml"));
		
		initializeClass(null, FruityMod.makePath(FruityMod.TRANQUIL_SHOULDER_2),
				FruityMod.makePath(FruityMod.TRANQUIL_SHOULDER_1),
				FruityMod.makePath(FruityMod.TRANQUIL_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));

		if (Settings.dailyModsEnabled() && DailyMods.cardMods.get("Diverse")) {
			this.masterMaxOrbs = 1;
		}
	}
	
	@Override
	public void applyEndOfTurnTriggers() {
		for (AbstractPower p : this.powers) {
			p.atEndOfTurn(true);
		}
		// make sure that cards that get changed to ethereal are
		// always exhausted
		AbstractDungeon.actionManager.addToBottom(new ExhaustAllEtherealAction());
	}

	public static ArrayList<String> getStartingDeck() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Strike_T");
		retVal.add("Strike_T");
		retVal.add("Strike_T");
		retVal.add("Strike_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Arcanosphere");
		UnlockTracker.markRelicAsSeen("Arcanosphere");
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Tranquil", "A dude that likes earth tones and yoga.",
				75, 75, 0, 99, 5,
			TheTranquilEnum.THE_TRANQUIL, getStartingRelics(), getStartingDeck(), false);
	}
	
}
