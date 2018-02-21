package fruitymod.characters;

import java.util.ArrayList;

import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import fruitymod.FruityMod;
import fruitymod.patches.TheSeekerEnum;

public class TheSeeker extends AbstractPlayer {
	public static final int ENERGY_PER_TURN = 3;
	
	
	public TheSeeker(String name, PlayerClass setClass) {
		super(name, setClass);
		
		this.dialogX = (this.drawX + 0.0F * Settings.scale);
		this.dialogY = (this.drawY + 220.0F * Settings.scale);
		
		initializeClass(null, FruityMod.makePath(FruityMod.SEEKER_SHOULDER_2),
				FruityMod.makePath(FruityMod.SEEKER_SHOULDER_1),
				FruityMod.makePath(FruityMod.SEEKER_CORPSE), 
				getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
		
		loadAnimation(FruityMod.makePath(FruityMod.SEEKER_SKELETON_ATLAS),
				FruityMod.makePath(FruityMod.SEEKER_SKELETON_JSON), 1.0F);
		
		AnimationState.TrackEntry e = this.state.setAnimation(0, "animation", true);
		e.setTime(e.getEndTime() * MathUtils.random());
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
		retVal.add("Arcanosphere");
		UnlockTracker.markRelicAsSeen("Arcanosphere");
		return retVal;
	}
	
	public static CharSelectInfo getLoadout() {
		return new CharSelectInfo("The Seeker", "A brilliant mage and astronomer. Channels power from the astral plane.",
				75, 75, 99, 5,
			TheSeekerEnum.THE_SEEKER, getStartingRelics(), getStartingDeck(), false);
	}
	
}
