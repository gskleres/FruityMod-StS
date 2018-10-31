package fruitymod.seeker.characters;

import java.util.ArrayList;

import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.abstracts.CustomPlayer;
import fruitymod.SeekerMod;
import fruitymod.seeker.cards.AstralHaze;
import fruitymod.seeker.patches.AbstractCardEnum;
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
    public static final String NAME = "The Seeker";

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

	@Override
	public ArrayList<String> getStartingDeck() {
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

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add(Arcanosphere.ID);
		UnlockTracker.markRelicAsSeen(Arcanosphere.ID);
		return retVal;
	}

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(NAME, "A brilliant mage and astronomer. Channels power from the astral plane.",
				75, 75, 0, 99, 5,
			this, getStartingRelics(), getStartingDeck(), false);
	}

	@Override
	public String getTitle(PlayerClass playerClass) {
		return NAME;
	}

	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.SEEKER_PURPLE;
	}

	@Override
	public Color getCardRenderColor() {
		return Color.PURPLE;
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new AstralHaze();
	}

	@Override
	public Color getCardTrailColor() {
		return Color.PURPLE;
	}

	@Override
	public int getAscensionMaxHPLoss() {
		return 4;
	}

	@Override
	public BitmapFont getEnergyNumFont() {
		return FontHelper.energyNumFontRed;
	}

	@Override
	public void doCharSelectScreenSelectEffect() {
		CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2f, 0.2f));
		CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, true);
	}

	@Override
	public String getCustomModeCharacterButtonSoundKey() {
		return "ATTACK_HEAVY";
	}

	@Override
	public String getLocalizedCharacterName() {
		return NAME;
	}

	@Override
	public AbstractPlayer newInstance() {
		return new TheSeeker(NAME, TheSeekerEnum.THE_SEEKER);
	}

	@Override
	public String getSpireHeartText() {
		return "NL You ready your Weapon...";
	}

	@Override
	public Color getSlashAttackColor() {
		return Color.MAROON;
	}

	@Override
	public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
		return new AbstractGameAction.AttackEffect[0];
	}

	//TODO: Character Specific Dialog
	@Override
	public String getVampireText() {
		return "Navigating an unlit street, you come across several hooded figures in the midst of some dark ritual. As you approach, they turn to you in eerie unison. The tallest among them bares fanged teeth and extends a long, pale hand towards you. NL ~\"Join~ ~us,~ ~oh Mighty Warrior,~ ~and~ ~feel~ ~the~ ~warmth~ ~of~ ~the~ ~Spire.\"~";
	}
}
