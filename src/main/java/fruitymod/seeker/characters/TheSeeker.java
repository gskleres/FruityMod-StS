package fruitymod.seeker.characters;

import java.util.ArrayList;

import basemod.animations.SpriterAnimation;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.TheSeekerEnum;
import fruitymod.seeker.relics.*;

public class TheSeeker extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;
	
	public static final String[] orbTextures = {
			SeekerMod.makePath("char/orb/layer1.png"),
			SeekerMod.makePath("char/orb/layer2.png"),
			SeekerMod.makePath("char/orb/layer3.png"),
			SeekerMod.makePath("char/orb/layer4.png"),
			SeekerMod.makePath("char/orb/layer5.png"),
			SeekerMod.makePath("char/orb/layer6.png"),
			SeekerMod.makePath("char/orb/layer1d.png"),
			SeekerMod.makePath("char/orb/layer2d.png"),
			SeekerMod.makePath("char/orb/layer3d.png"),
			SeekerMod.makePath("char/orb/layer4d.png"),
			SeekerMod.makePath("char/orb/layer5d.png"),
	};
	
	public TheSeeker(String name, PlayerClass setClass) {
		super(name, setClass, orbTextures, SeekerMod.makePath("char/orb/vfx.png"), new SpriterAnimation(SeekerMod.makePath("char/animation.scml")));
		
		initializeClass(null, SeekerMod.makePath(SeekerMod.SEEKER_SHOULDER_2),
				SeekerMod.makePath(SeekerMod.SEEKER_SHOULDER_1),
				SeekerMod.makePath(SeekerMod.SEEKER_CORPSE),
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
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
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Strike_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("Defend_P");
		retVal.add("AstralHaze");
		return retVal;
	}
	
	public static ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(Arcanosphere.ID);
		UnlockTracker.markRelicAsSeen(Arcanosphere.ID);
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Seeker", "A brilliant mage and astronomer. Channels power from the astral plane.",
				75, 75, 0, 99, 5,
			TheSeekerEnum.THE_SEEKER, getStartingRelics(), getStartingDeck(), false);
	}
	
}
