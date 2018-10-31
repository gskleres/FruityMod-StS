package fruitymod.tranquil.characters;

import basemod.abstracts.CustomPlayer;
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
import fruitymod.TranquilMod;
import fruitymod.tranquil.cards.FlyingKick;
import fruitymod.tranquil.patches.AbstractCardEnum;
import fruitymod.tranquil.patches.TheTranquilEnum;


import java.util.ArrayList;

public class TheTranquil extends CustomPlayer {
	public static final int ENERGY_PER_TURN = 3;

	public static final String NAME = "The Tranquil";

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

	@Override
	public ArrayList<String> getStartingRelics() {
		ArrayList<String> retVal = new ArrayList<>();
		retVal.add("Arcanosphere");
		UnlockTracker.markRelicAsSeen("Arcanosphere");
		return retVal;
	}

	@Override
	public CharSelectInfo getLoadout() {
		return new CharSelectInfo(
				NAME
				, "A dude that likes earth tones and yoga."
				, 75
				, 75
				, 0
				, 99
				, 5
				, this
				, getStartingRelics()
				, getStartingDeck()
				, false);
	}

	@Override
	public String getTitle(PlayerClass playerClass) {
		return NAME;
	}

	@Override
	public AbstractCard.CardColor getCardColor() {
		return AbstractCardEnum.TRANQUIL_TAN;
	}

	@Override
	public Color getCardRenderColor() {
		return Color.TAN;
	}

	@Override
	public AbstractCard getStartCardForEvent() {
		return new FlyingKick();
	}

	@Override
	public Color getCardTrailColor() {
		return Color.TAN;
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
		return new TheTranquil(NAME, TheTranquilEnum.THE_TRANQUIL);
	}

	@Override
	public String getSpireHeartText() {
		return "NL You ready your Weapon...";
	}

	@Override
	public Color getSlashAttackColor() {
		return Color.TAN;
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
