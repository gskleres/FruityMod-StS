package fruitymod;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fruitymod.tranquil.cards.*;
import fruitymod.tranquil.cards.FlyingKick;
import fruitymod.tranquil.characters.TheTranquil;
import fruitymod.tranquil.patches.AbstractCardEnum;
import fruitymod.tranquil.patches.TheTranquilEnum;
import fruitymod.tranquil.relics.FangedNecklace;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TranquilMod implements CharacterMod {

	public static final Logger logger = LogManager.getLogger(TranquilMod.class.getName());

	public static final Color TAN = CardHelper.getColor(210.0f, 180.0f, 140.0f);

	private static final String ATTACK = "attack";
	private static final String SKILL = "skill";
	private static final String POWER = "power";
	private static final String ENERGY_ORB = "orb";
	private static final String SMALL_ENERGY_ORB = "small_orb";

	private static final String TRANQUIL_BUTTON = "charSelect/tranquilButton.png";
	private static final String TRANQUIL_PORTRAIT = "charSelect/TranquilPortraitBG.jpg";

	public TranquilMod() {
		logger.info("creating the color " + AbstractCardEnum.TRANQUIL_TAN.toString());
		BaseMod.addColor(AbstractCardEnum.TRANQUIL_TAN,
				TAN, TAN, TAN, TAN, TAN, TAN, TAN,
				makeCardBgSmallPath(ATTACK), makeCardBgSmallPath(SKILL),
				makeCardBgSmallPath(POWER), makeCardBgSmallPath(ENERGY_ORB),
				makeCardBgLargePath(ATTACK), makeCardBgLargePath(SKILL),
				makeCardBgLargePath(POWER), makeCardBgLargePath(ENERGY_ORB), makeCardBgSmallPath(SMALL_ENERGY_ORB));
	}

	@Override
	public void receiveEditCards() {
		List<CustomCard> cards = new ArrayList<CustomCard>();
		cards.add(new Strike_Tan());
		cards.add(new Defend_Tan());
		cards.add(new FlyingKick());
		cards.add(new FlurryOfBlows());
		cards.add(new Blockpocalypse());

		for(CustomCard card : cards) {
			BaseMod.addCard(card);
			if (isEnabled()) {
				UnlockTracker.unlockCard(card.cardID);
			}
		}
	}

	@Override
	public void receiveEditCharacters() {
		if (!isEnabled()) {
			return;
		}

		BaseMod.addCharacter(new TheTranquil("the Tranquil", TheTranquilEnum.THE_TRANQUIL), makePath(TRANQUIL_BUTTON), makePath(TRANQUIL_PORTRAIT), TheTranquilEnum.THE_TRANQUIL);
	}

	@Override
	public void receiveEditKeywords() {

	}

	@Override
	public void receiveEditRelics() {
		if (!isEnabled()) {
			return;
		}

		// Add relics
		BaseMod.addRelicToCustomPool(new FangedNecklace(), AbstractCardEnum.TRANQUIL_TAN);
	}

	@Override
	public void receiveEditStrings() {
		// RelicStrings
		String relicStrings = Gdx.files.internal("localization/Tranquil-RelicStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		// CardStrings
		String cardStrings = Gdx.files.internal("localization/Tranquil-CardStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
	}

	@Override
	public void receiveCardUsed(AbstractCard abstractCard) {

	}

	@Override
	public void receivePowersModified() {

	}

	@Override
	public void receivePostBattle(AbstractRoom abstractRoom) {

	}

	@Override
	public void receivePostDraw(AbstractCard abstractCard) {

	}

	@Override
	public void receivePostDungeonInitialize() {

	}

	@Override
	public void receivePostExhaust(AbstractCard abstractCard) {

	}

	/**
	 * Makes a full path for a small card background
	 * @param cardType the card type, must *NOT* have a leading "/"
	 * @return the full path to the card background
	 */
	public static final String makeCardBgSmallPath(String cardType) {
		return makePath("512/" + cardType);
	}

	/**
	 * Makes a full path for a large card background
	 * @param cardType the card type, must *NOT* have a leading "/"
	 * @return the full path to the card background
	 */
	public static final String makeCardBgLargePath(String cardType) {
		return makePath("1024/" + cardType);
	}

	/**
	 * Makes a full path for a card image
	 * @param cardName the resource, must *NOT* have a leading "/"
	 * @return the full path to the card image
	 */
	public static final String makeCardImagePath(String cardName) {
		return makePath("cards/" + cardName);
	}

	public static final String makeRelicImagePath(String relicName) {
		return makePath("relics/" + relicName);
	}

	/**
	 * Makes a full path for a character image
	 * @param image the image, must *NOT* have a leading "/"
	 * @return the full path to the character image
	 */
	public static final String makeCharacterImagePath(String image) {
		return makePath("char/" + image);
	}

	/**
	 * Makes a full path for a resource path
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	private static final String makePath(String resource) {
		String result = "img/tranquil/" + resource;

		if (! hasExtension(resource)) {
			result += ".png";
		}

		return result;
	}

	/**
	 * Makes a full path for a power path
	 * @param power the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	public static final String makePowerImagePath(String power) {
		return makePath("powers/" + power.replaceFirst("Tranquil_", ""));
	}

	public static boolean isEnabled() {
		return Boolean.parseBoolean(System.getProperty("tranquil_enabled","false"));
	}

	private static boolean hasExtension(String filename) {
		return filename.lastIndexOf('.') > 0;
	}
}
