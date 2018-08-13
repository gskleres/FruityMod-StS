package fruitymod;

import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;
import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeekerMod implements CharacterMod {
	public static final Logger logger = LogManager.getLogger(FruityMod.class.getName());

	private static final Color PURPLE = CardHelper.getColor(139.0f, 0.0f, 139.0f);

	private static final String FRUITY_MOD_ASSETS_FOLDER = "img";

	// card backgrounds
	private static final String ATTACK_PURPLE = "512/bg_attack_purple.png";
	private static final String SKILL_PURPLE = "512/bg_skill_purple.png";
	private static final String POWER_PURPLE = "512/bg_power_purple.png";
	private static final String ENERGY_ORB_PURPLE = "512/card_purple_orb.png";
	private static final String CARD_ENERGY_ORB_PURPLE = "512/card_purple_small_orb.png";

	private static final String ATTACK_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
	private static final String SKILL_PURPLE_PORTRAIT = "1024/bg_skill_purple.png";
	private static final String POWER_PURPLE_PORTRAIT = "1024/bg_power_purple.png";
	private static final String ENERGY_ORB_PURPLE_PORTRAIT = "1024/card_purple_orb.png";

	// seeker assets
	private static final String SEEKER_BUTTON = "charSelect/seekerButton.png";
	private static final String SEEKER_PORTRAIT = "charSelect/SeekerPortraitBG.jpg";
	public static final String SEEKER_SHOULDER_1 = "char/seeker/shoulder.png";
	public static final String SEEKER_SHOULDER_2 = "char/seeker/shoulder2.png";
	public static final String SEEKER_CORPSE = "char/seeker/corpse.png";

	public SeekerMod() {
		/*
		 * Note that for now when installing FruityMod, in the `mods/` folder another folder named
		 * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
		 * `images/` folder must be relocated
		 */
		logger.info("creating the color " + AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addColor(AbstractCardEnum.SEEKER_PURPLE.toString(),
				PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE,
				makePath(ATTACK_PURPLE), makePath(SKILL_PURPLE),
				makePath(POWER_PURPLE), makePath(ENERGY_ORB_PURPLE),
				makePath(ATTACK_PURPLE_PORTRAIT), makePath(SKILL_PURPLE_PORTRAIT),
				makePath(POWER_PURPLE_PORTRAIT), makePath(ENERGY_ORB_PURPLE_PORTRAIT), makePath(CARD_ENERGY_ORB_PURPLE));

	}

	@Override
	public void receiveEditCharacters() {
		logger.info("add " + TheSeekerEnum.THE_SEEKER.toString());
		BaseMod.addCharacter(TheSeeker.class, "The Seeker", "Seeker class string",
				AbstractCardEnum.SEEKER_PURPLE.toString(), "#pThe #pSeeker",
				makePath(SEEKER_BUTTON), makePath(SEEKER_PORTRAIT),
				TheSeekerEnum.THE_SEEKER.toString());
	}

	@Override
	public void receiveEditRelics() {
		// Add relics
		BaseMod.addRelicToCustomPool(new Arcanosphere(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new Blueberries(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new PaperPengwin(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new CosmicSieve(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new SolarEgg(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new RodOfNegation(), AbstractCardEnum.SEEKER_PURPLE.toString());
		BaseMod.addRelicToCustomPool(new Telescope(), AbstractCardEnum.SEEKER_PURPLE.toString());
	}

	/**
	 * Makes a full path for a resource path
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	private static final String makePath(String resource) {
		return FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
	}
}
