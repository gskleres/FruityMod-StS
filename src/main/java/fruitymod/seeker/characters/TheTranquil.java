package fruitymod.seeker.characters;

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
import fruitymod.TranquilMod;
import fruitymod.seeker.patches.TheTranquilEnum;

import java.util.ArrayList;

public class TheTranquil extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;

	public static final String[] orbTextures = {
			TranquilMod.makeCharacterImagePath("orb/layer1"),
			TranquilMod.makeCharacterImagePath("orb/layer2"),
			TranquilMod.makeCharacterImagePath("orb/layer3"),
			TranquilMod.makeCharacterImagePath("orb/layer4"),
			TranquilMod.makeCharacterImagePath("orb/layer5"),
			TranquilMod.makeCharacterImagePath("orb/layer6"),
			TranquilMod.makeCharacterImagePath("orb/layer1d"),
			TranquilMod.makeCharacterImagePath("orb/layer2d"),
			TranquilMod.makeCharacterImagePath("orb/layer3d"),
			TranquilMod.makeCharacterImagePath("orb/layer4d"),
			TranquilMod.makeCharacterImagePath("orb/layer5d"),
	};

	public TheTranquil(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, TranquilMod.makeCharacterImagePath("orb/vfx"), new SpriterAnimation(TranquilMod.makeCharacterImagePath("animation/animation.scml")));
		
		initializeClass(null, TranquilMod.makeCharacterImagePath("shoulder2"),
				TranquilMod.makeCharacterImagePath("shoulder"),
				TranquilMod.makeCharacterImagePath("corpse"),
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
		retVal.add("Strike_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Defend_T");
		retVal.add("Tranquil_FlyingKick");
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
