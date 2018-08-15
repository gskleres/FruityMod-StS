package fruitymod;

import basemod.BaseMod;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.RelicStrings;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fruitymod.actions.unique.ConvergenceAction;
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

	// seeker card images
	public static final String ANOMALY = "cards/anomaly.png";
	public static final String ARCANE_BARRAGE = "cards/starburst.png";
	public static final String ARCANE_TEMPEST = "cards/arcane_tempest.png";
	public static final String ARCANE_VOLLEY = "cards/irradiate.png";
	public static final String ARCHIVES = "cards/archives.png";
	public static final String ASTRAL_HAZE = "cards/astral_haze.png";
	public static final String DIMENSION_DOOR = "cards/dimension_door.png";
	public static final String BRAINSTORM = "cards/brainstorm.png";
	public static final String BRILLIANCE = "cards/brilliance.png";
	public static final String CHANNEL = "cards/channel.png";
	public static final String CHAOS_FORM = "cards/chaos_form.png";
	public static final String COALESCENCE = "cards/coalescence.png";
	public static final String COMET = "cards/comet.png";
	public static final String CONVERGENCE = "cards/convergence.png";
	public static final String CORONA = "cards/corona.png";
	public static final String CREATIVITY = "cards/creativity.png";
	public static final String DARK_MATTER = "cards/dark_matter.png";
	public static final String DEFEND_PURPLE = "cards/defend_purple.png";
	public static final String ARCANE_ARMOR = "cards/arcane_armor.png";
	public static final String ECHO = "cards/echo.png";
	public static final String ECLIPSE = "cards/eclipse.png";
	public static final String ENIGMA = "cards/enigma.png";
	public static final String ENTROPY = "cards/entropy.png";
	public static final String EQUINOX = "cards/equinox.png";
	public static final String ESSENCE_DART = "cards/phase_coil.png";
	public static final String ESSENCE_MIRROR = "cards/flicker.png";
	public static final String ESSENCE_SHRED = "cards/plasma_wave.png";
	public static final String ESSENCE_SPIKE = "cards/pulse_barrier.png";
	public static final String NEBULA = "cards/nebula.png";
	public static final String ETHER_BLAST = "cards/ether_blast.png";
	public static final String EUREKA = "cards/eureka.png";
	public static final String EVENT_HORIZON = "cards/event_horizon.png";
	public static final String FEEDBACK = "cards/feedback.png";
	public static final String FLARE = "cards/flare.png";
	public static final String FLOW = "cards/flow.png";
	public static final String FLUX_BLAST = "cards/null_storm.png";
	public static final String FLUX_BOLT = "cards/void_ray.png";
	public static final String FLUX_SHIELD = "cards/flux_shield.png";
	public static final String FORCE_RIPPLE = "cards/force_ripple.png";
	public static final String FORCE_SPIKE = "cards/unstable_orb.png";
	public static final String GRAVITY_WELL = "cards/gravity_well.png";
	public static final String HYPOTHESIS = "cards/hypothesis.png";
	public static final String ILLUMINATE = "cards/illuminate.png";
	public static final String IMPLOSION = "cards/implosion.png";
	public static final String MAGIC_MISSILE = "cards/magic_missile.png";
	public static final String MAGNETIZE = "cards/magnetize.png";
	public static final String METEOR_SHOWER = "cards/meteor_shower.png";
	public static final String MIND_OVER_MATTER = "cards/mind_over_matter.png";
	public static final String NEXUS = "cards/nexus.png";
	public static final String NOVA = "cards/nova.png";
	public static final String PERIAPT_OF_CELERITY = "cards/periapt_of_celerity.png";
	public static final String PERIAPT_OF_TENACITY = "cards/wormhole.png";
	public static final String PERIAPT_OF_VIGOR = "cards/periapt_of_vigor.png";
	public static final String POWER_OVERWHELMING = "cards/power_overwhelming.png";
	public static final String POWER_SPIKE = "cards/overload.png";
	public static final String PROTECTION_WARD = "cards/disperse.png";
	public static final String REFLECTION_WARD = "cards/reflection_ward.png";
	public static final String RETROGRADE = "cards/retrograde.png";
	public static final String WORMHOLE = "cards/runic_binding.png";
	public static final String SHIMMER = "cards/shimmer.png";
	public static final String SIPHON_POWER = "cards/siphon_power.png";
	public static final String SIPHON_SPEED = "cards/siphon_speed.png";
	public static final String STRIKE_PURPLE = "cards/strike_purple.png";
	public static final String STROKE_OF_GENIUS = "cards/stroke_of_genius.png";
	public static final String SURGE = "cards/surge.png";
	public static final String SYZYGY = "cards/syzygy.png";
	public static final String THOUGHT_RAZE = "cards/thought_raze.png";
	public static final String TRANSFERENCE = "cards/transference.png";
	public static final String UMBRAL_BOLT = "cards/umbral_bolt.png";
	public static final String SINGULARITY = "cards/singularity.png";
	public static final String VACUUM = "cards/vacuum.png";
	public static final String VOID_BARRIER = "cards/genesis.png";
	public static final String VOID_BOLT = "cards/prismatic_sphere.png";
	public static final String VOID_RIPPLE = "cards/flux.png";
	public static final String VORTEX = "cards/vortex.png";
	public static final String ZENITH = "cards/zenith.png";
	public static final String PHASE_COIL = "cards/phase_coil.png";

	// power images
	public static final String ARCANOSPHERE_POWER = "powers/arcanosphere.png";
	public static final String ASTRAL_HAZE_POWER = "powers/astral_haze.png";
	public static final String CHAOS_FORM_POWER = "powers/chaos_form.png";
	public static final String ESSENCE_MIRROR_POWER = "powers/essence_mirror.png";
	public static final String ETHEREALIZE_POWER = "powers/essence_mirror.png";
	public static final String WORMHOLE_POWER = "powers/wormhole.png";
	public static final String VIGOR_POWER = "powers/vigor.png";
	public static final String ASTRAL_SHIFT_POWER = "powers/astral_shift.png";
	public static final String TENACITY_POWER = "powers/tenacity.png";
	public static final String CELERITY_POWER = "powers/celerity.png";
	public static final String POTENCY_POWER = "powers/potency.png";
	public static final String COALESCENCE_POWER = "powers/coalescence.png";
	public static final String CREATIVITY_POWER = "powers/creativity.png";
	public static final String POWER_OVERWHELMING_POWER = "powers/power_overwhelming.png";
	public static final String EVENT_HORIZON_POWER = "powers/event_horizon.png";
	public static final String ENIGMA_POWER = "powers/enigma.png";
	public static final String BRILLIANCE_POWER = "powers/brilliance.png";
	public static final String ANOMALY_POWER = "powers/anomaly.png";

	// relic images
	public static final String ARCANOSPHERE_RELIC = "relics/arcanosphere.png";
	public static final String PAPER_PENGWIN_RELIC = "relics/paperPengwin.png";
	public static final String COSMIC_SIEVE_RELIC = "relics/cosmicSieve.png";
	public static final String SOLAR_EGG_RELIC = "relics/solarEgg.png";
	public static final String ROD_OF_NEGATION_RELIC = "relics/rodOfNegation.png";
	public static final String TELESCOPE_RELIC = "relics/telescope.png";
	public static final String BLUEBERRIES_RELIC = "relics/blueberries.png";

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

	@Override
	public void receiveEditKeywords() {
		logger.info("setting up custom keywords");
		BaseMod.addKeyword(new String[] {"reflect", "Reflect"}, "Whenever you are attacked this turn, deal this amount of damage back to ALL enemies.");
		BaseMod.addKeyword(new String[] {"top-cycle", "Top-Cycle"}, "When you Top-Cycle a card, place it on top of your draw pile.");
		BaseMod.addKeyword(new String[] {"recycle", "Recycle"}, "When you Recycle a card, shuffle it randomly into your draw pile.");
	}

	//
	// Relic code
	// (yes we're doing the exact same things the devs did where relic code
	// isn't in the actual relics - oh well)
	//

	private boolean moreThanXStacks(AbstractPlayer player, String powerID, int stacksWanted) {
		if (player != null && player.hasPower(powerID) && player.getPower(powerID).amount >= stacksWanted) {
			return true;
		} else {
			return false;
		}
	}

	private boolean isApplyingPaperPengwin = false;

	private void resetPaperPengwin() {
		isApplyingPaperPengwin = false;
		if (AbstractDungeon.player.hasRelic("PaperPengwin")) {
			((PaperPengwin) AbstractDungeon.player.getRelic("PaperPengwin")).setPulse(false);
		}
	}

	@Override
	public void receivePowersModified() {
		AbstractPlayer p = AbstractDungeon.player;

		if (p != null && p.hasRelic("PaperPengwin")) {
			if (moreThanXStacks(p, "Weakened", PaperPengwin.MIN_STACKS) ||
					moreThanXStacks(p, "Vulnerable", PaperPengwin.MIN_STACKS) ||
					moreThanXStacks(p, "Frail", PaperPengwin.MIN_STACKS)) {
				if (!isApplyingPaperPengwin) {
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new DexterityPower(p, PaperPengwin.MIN_STACKS), PaperPengwin.MIN_STACKS));
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new StrengthPower(p, PaperPengwin.MIN_STACKS), PaperPengwin.MIN_STACKS));
					isApplyingPaperPengwin = true;
					p.getRelic("PaperPengwin").flash();
					((PaperPengwin) p.getRelic("PaperPengwin")).setPulse(true);
				}
			} else {
				if (isApplyingPaperPengwin) {
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new DexterityPower(p, -1 * PaperPengwin.MIN_STACKS), -1 * PaperPengwin.MIN_STACKS));
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new StrengthPower(p, -1 * PaperPengwin.MIN_STACKS), -1 * PaperPengwin.MIN_STACKS));
					isApplyingPaperPengwin = false;
					((PaperPengwin) p.getRelic("PaperPengwin")).setPulse(false);
				}
			}
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		resetPaperPengwin();
	}

	@Override
	public void receivePostDungeonInitialize() {
		resetPaperPengwin();
	}

	@Override
	public void receivePostExhaust(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;

		if (p != null && p.hasRelic("CosmicSieve")) {
			if (c.isEthereal) {
				p.heal(CosmicSieve.HP_PER_CARD);
				p.getRelic("CosmicSieve").flash();
				AbstractDungeon.actionManager.addToBottom(
						new RelicAboveCreatureAction(AbstractDungeon.player, p.getRelic("CosmicSieve")));
			}
		}

	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		if (c instanceof Convergence) {
			c.superFlash();
			if (c.upgraded) {
				AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
			}
			AbstractDungeon.actionManager.addToBottom(new WaitAction(Settings.ACTION_DUR_FAST));
			AbstractDungeon.actionManager.addToBottom(new ConvergenceAction(c.upgraded));
		}
	}

	//
	// Enigma hooks and functionality
	//

	@Override
	public void receiveCardUsed(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("EnigmaPower") && c.cardID.equals("Dazed")) {
			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, c.block));
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player,
					c.multiDamage,
					DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
			c.exhaustOnUseOnce = false;
		}
	}

	// used by fruitymod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CanUsedDazed
	public static boolean hasRelicCustom(String relicID, AbstractCard card) {
		System.out.println("I was checked!");
		// if it's checking for relicID.equals("Medical Kit") then we know we're in the block where
		// we are saying if we can use a status card so also check if we have enigma and the card is Dazed
		if (relicID.equals("Medical Kit") && AbstractDungeon.player.hasPower("EnigmaPower") && card.cardID.equals("Dazed")) {
			return true;
		} else {
			// otherwise leave normal behavior intact
			return AbstractDungeon.player.hasRelic(relicID);
		}
	}

	// used by fruitmod.patches.com.megacrit.cardcrawl.cards.status.Dazed.UseDazed
	public static void maybeUseDazed(Dazed dazed) {
		System.out.println("maybe use dazed");
		if (!AbstractDungeon.player.hasPower("EnigmaPower")) {
			System.out.println("do use dazed");
			AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.utility.UseCardAction(dazed));
		} else {
			System.out.println("don't use dazed");
		}
	}

	public static Texture getArcanoSphereTexture() {
		return new Texture(makePath(ARCANOSPHERE_RELIC));
	}

	public static Texture getPaperPengwinTexture() {
		return new Texture(makePath(PAPER_PENGWIN_RELIC));
	}

	public static Texture getCosmicSieveTexture() {
		return new Texture(makePath(COSMIC_SIEVE_RELIC));
	}
	public static Texture getSolarEggTexture() {
		return new Texture(makePath(SOLAR_EGG_RELIC));
	}
	public static Texture getRodOfNegationTexture() {
		return new Texture(makePath(ROD_OF_NEGATION_RELIC));
	}
	public static Texture getTelescopeTexture() {
		return new Texture(makePath(TELESCOPE_RELIC));
	}

	public static Texture getBlueberriesTexture() {
		return new Texture(makePath(BLUEBERRIES_RELIC));
	}

	// texture loaders
	public static Texture getAstralHazePowerTexture() {
		return new Texture(FruityMod.makePath(ASTRAL_HAZE_POWER));
	}

	public static Texture getChaosFormPowerTexture() {
		return new Texture(FruityMod.makePath(CHAOS_FORM_POWER));
	}

	public static Texture getEssenceMirrorPowerTexture() {
		return new Texture(FruityMod.makePath(ESSENCE_MIRROR_POWER));
	}

	public static Texture getEtherealizePowerTexture() {
		return new Texture(FruityMod.makePath(ETHEREALIZE_POWER));
	}

	public static Texture getWormholePowerTexture() {
		return new Texture(FruityMod.makePath(WORMHOLE_POWER));
	}

	public static Texture getVigorPowerTexture() {
		return new Texture(FruityMod.makePath(VIGOR_POWER));
	}

	public static Texture getAstralShiftTexture() {
		return new Texture(FruityMod.makePath(ASTRAL_SHIFT_POWER));
	}

	public static Texture getTenacityPowerTexture() {
		return new Texture(FruityMod.makePath(TENACITY_POWER));
	}

	public static Texture getCelerityPowerTexture() {
		return new Texture(FruityMod.makePath(CELERITY_POWER));
	}

	public static Texture getPotencyPowerTexture() {
		return new Texture(FruityMod.makePath(POTENCY_POWER));
	}

	public static Texture getCoalescencePowerTexture() {
		return new Texture(FruityMod.makePath(COALESCENCE_POWER));
	}

	public static Texture getCreativityPowerTexture() {
		return new Texture(FruityMod.makePath(CREATIVITY_POWER));
	}

	public static Texture getPowerOverwhelmingPowerTexture() {
		return new Texture(FruityMod.makePath(POWER_OVERWHELMING_POWER));
	}

	public static Texture getEventHorizonPowerTexture() {
		return new Texture(FruityMod.makePath(EVENT_HORIZON_POWER));
	}

	public static Texture getEnigmaPowerTexture() {
		return new Texture(FruityMod.makePath(ENIGMA_POWER));
	}

	public static Texture getBrillancePowerTexture() {
		return new Texture(FruityMod.makePath(BRILLIANCE_POWER));
	}

	public static Texture getAnomalyPowerTexture() {
		return new Texture(FruityMod.makePath(ANOMALY_POWER));
	}

	public static Texture getNexusPowerTexture() {
		return new Texture(FruityMod.makePath(VIGOR_POWER));
	}

	public static Texture getArcanospherePowerTexture() {
		return new Texture(FruityMod.makePath(ARCANOSPHERE_POWER));
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
