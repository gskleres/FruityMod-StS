package fruitymod;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fruitymod.cards.*;
import fruitymod.cards.tranquil.Tranquil_FlyingKick;
import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;

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

	@Override
	public void receiveEditCards() {
		logger.info("add cards for " + TheSeekerEnum.THE_SEEKER.toString());

		BaseMod.addCard(new Dazed_P());
		BaseMod.addCard(new Strike_Purple());
		BaseMod.addCard(new Defend_Purple());
		BaseMod.addCard(new Starburst());
		BaseMod.addCard(new Irradiate());
		BaseMod.addCard(new AstralHaze());
		BaseMod.addCard(new Brainstorm());
		BaseMod.addCard(new DarkMatter());
		BaseMod.addCard(new ArcaneArmor());
		BaseMod.addCard(new Entropy());
		BaseMod.addCard(new EssenceDart());
		BaseMod.addCard(new Flicker());
		BaseMod.addCard(new PlasmaWave());
		BaseMod.addCard(new PulseBarrier());
		BaseMod.addCard(new Nebula());
		BaseMod.addCard(new EtherBlast());
		BaseMod.addCard(new Flare());
		BaseMod.addCard(new NullStorm());
		BaseMod.addCard(new VoidRay());
		BaseMod.addCard(new DisruptionField());
		BaseMod.addCard(new UnstableOrb());
		BaseMod.addCard(new Hypothesis());
		BaseMod.addCard(new Comet());
		BaseMod.addCard(new ForceRipple());
		BaseMod.addCard(new PhaseCoil());
		BaseMod.addCard(new Overload());
		BaseMod.addCard(new Syzygy());
		BaseMod.addCard(new SiphonPower());
		BaseMod.addCard(new Shimmer());
		BaseMod.addCard(new ThoughtRaze());
		BaseMod.addCard(new Retrograde());
		BaseMod.addCard(new Singularity());
		BaseMod.addCard(new Umbra());
		BaseMod.addCard(new Genesis());
		BaseMod.addCard(new PrismaticSphere());
		BaseMod.addCard(new Flux());
		BaseMod.addCard(new Channel());
		BaseMod.addCard(new Implosion());
		BaseMod.addCard(new ChaosForm());
		BaseMod.addCard(new Vacuum());
		BaseMod.addCard(new DimensionDoor());
		BaseMod.addCard(new Wormhole());
		BaseMod.addCard(new Eureka());
		BaseMod.addCard(new Eclipse());
		BaseMod.addCard(new Echo());
		BaseMod.addCard(new EventHorizon());
		BaseMod.addCard(new Zenith());
		BaseMod.addCard(new ReflectionWard());
		BaseMod.addCard(new Creativity());
		BaseMod.addCard(new Transference());
		BaseMod.addCard(new Surge());
		BaseMod.addCard(new StrokeOfGenius());
		BaseMod.addCard(new SiphonSpeed());
		BaseMod.addCard(new Convergence());
		BaseMod.addCard(new GravityWell());
		BaseMod.addCard(new Coalescence());
		BaseMod.addCard(new PeriaptOfCelerity());
		BaseMod.addCard(new PeriaptOfPotency());
		BaseMod.addCard(new MeteorShower());
		BaseMod.addCard(new PowerOverwhelming());
		BaseMod.addCard(new MindOverMatter());
		BaseMod.addCard(new Disperse());
		BaseMod.addCard(new Magnetize());
		BaseMod.addCard(new Illuminate());
		BaseMod.addCard(new Flow());
		BaseMod.addCard(new Equinox());
		BaseMod.addCard(new Corona());
		BaseMod.addCard(new Archives());
		BaseMod.addCard(new MagicMissile());
		BaseMod.addCard(new Enigma());
		BaseMod.addCard(new Feedback());
		BaseMod.addCard(new Brilliance());
		BaseMod.addCard(new Anomaly());
		BaseMod.addCard(new Nova());
		BaseMod.addCard(new Vortex());
		BaseMod.addCard(new Nexus());

		// make sure everything is always unlocked
		UnlockTracker.unlockCard("Dazed_P");
		UnlockTracker.unlockCard("Strike_P");
		UnlockTracker.unlockCard("Defend_P");
		UnlockTracker.unlockCard("Starburst");
		UnlockTracker.unlockCard("Irradiate");
		UnlockTracker.unlockCard("AstralHaze");
		UnlockTracker.unlockCard("Brainstorm");
		UnlockTracker.unlockCard("DarkMatter");
		UnlockTracker.unlockCard("ArcaneArmor");
		UnlockTracker.unlockCard("Entropy");
		UnlockTracker.unlockCard("EssenceDart");
		UnlockTracker.unlockCard("Flicker");
		UnlockTracker.unlockCard("PlasmaWave");
		UnlockTracker.unlockCard("PulseBarrier");
		UnlockTracker.unlockCard("Nebula");
		UnlockTracker.unlockCard("EtherBlast");
		UnlockTracker.unlockCard("Flare");
		UnlockTracker.unlockCard("NullStorm");
		UnlockTracker.unlockCard("VoidRay");
		UnlockTracker.unlockCard("DisruptionField");
		UnlockTracker.unlockCard("UnstableOrb");
		UnlockTracker.unlockCard("Hypothesis");
		UnlockTracker.unlockCard("Comet");
		UnlockTracker.unlockCard("ForceRipple");
		UnlockTracker.unlockCard("PhaseCoil");
		UnlockTracker.unlockCard("Overload");
		UnlockTracker.unlockCard("Syzygy");
		UnlockTracker.unlockCard("SiphonPower");
		UnlockTracker.unlockCard("Shimmer");
		UnlockTracker.unlockCard("ThoughtRaze");
		UnlockTracker.unlockCard("Retrograde");
		UnlockTracker.unlockCard("Singularity");
		UnlockTracker.unlockCard("Umbra");
		UnlockTracker.unlockCard("Genesis");
		UnlockTracker.unlockCard("PrismaticSphere");
		UnlockTracker.unlockCard("Flux");
		UnlockTracker.unlockCard("Channel");
		UnlockTracker.unlockCard("Implosion");
		UnlockTracker.unlockCard("ChaosForm");
		UnlockTracker.unlockCard("Vacuum");
		UnlockTracker.unlockCard("DimensionDoor");
		UnlockTracker.unlockCard("Wormhole");
		UnlockTracker.unlockCard("Eureka");
		UnlockTracker.unlockCard("Eclipse");
		UnlockTracker.unlockCard("Echo");
		UnlockTracker.unlockCard("EventHorizon");
		UnlockTracker.unlockCard("Zenith");
		UnlockTracker.unlockCard("ReflectionWard");
		UnlockTracker.unlockCard("Creativity");
		UnlockTracker.unlockCard("Transference");
		UnlockTracker.unlockCard("Surge");
		UnlockTracker.unlockCard("StrokeOfGenius");
		UnlockTracker.unlockCard("SiphonSpeed");
		UnlockTracker.unlockCard("Convergence");
		UnlockTracker.unlockCard("GravityWell");
		UnlockTracker.unlockCard("Coalescence");
		UnlockTracker.unlockCard("PeriaptOfCelerity");
		UnlockTracker.unlockCard("PeriaptOfPotency");
		UnlockTracker.unlockCard("MeteorShower");
		UnlockTracker.unlockCard("PowerOverwhelming");
		UnlockTracker.unlockCard("MindOverMatter");
		UnlockTracker.unlockCard("Disperse");
		UnlockTracker.unlockCard("Magnetize");
		UnlockTracker.unlockCard("Illuminate");
		UnlockTracker.unlockCard("Flow");
		UnlockTracker.unlockCard("Equinox");
		UnlockTracker.unlockCard("Corona");
		UnlockTracker.unlockCard("Archives");
		UnlockTracker.unlockCard("MagicMissile");
		UnlockTracker.unlockCard("Enigma");
		UnlockTracker.unlockCard("Feedback");
		UnlockTracker.unlockCard("Brilliance");
		UnlockTracker.unlockCard("Anomaly");
		UnlockTracker.unlockCard("Nova");
		UnlockTracker.unlockCard("Vortex");
		UnlockTracker.unlockCard("Nexus");
	}

	@Override
	public void receiveEditStrings() {
		// RelicStrings
		String relicStrings = Gdx.files.internal("localization/Seeker-RelicStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
		// CardStrings
		String cardStrings = Gdx.files.internal("localization/Seeker-CardStrings.json").readString(
				String.valueOf(StandardCharsets.UTF_8));
		BaseMod.loadCustomStrings(CardStrings.class, cardStrings);

		logger.info("done editting strings");
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
