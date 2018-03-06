package fruitymod;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.abstracts.CustomUnlockBundle;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditKeywordsSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.EditStringsSubscriber;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.OnPowersModifiedSubscriber;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostExhaustSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.SetUnlocksSubscriber;
import fruitymod.actions.unique.ConvergenceAction;
import fruitymod.cards.Anomaly;
import fruitymod.cards.ArcaneArmor;
import fruitymod.cards.Archives;
import fruitymod.cards.AstralHaze;
import fruitymod.cards.AstralShift;
import fruitymod.cards.Brainstorm;
import fruitymod.cards.Brilliance;
import fruitymod.cards.Channel;
import fruitymod.cards.ChaosForm;
import fruitymod.cards.Coalescence;
import fruitymod.cards.Comet;
import fruitymod.cards.Convergence;
import fruitymod.cards.Corona;
import fruitymod.cards.Creativity;
import fruitymod.cards.DarkMatter;
import fruitymod.cards.Defend_Purple;
import fruitymod.cards.Disperse;
import fruitymod.cards.Echo;
import fruitymod.cards.Eclipse;
import fruitymod.cards.Enigma;
import fruitymod.cards.Entropy;
import fruitymod.cards.Equinox;
import fruitymod.cards.EssenceDart;
import fruitymod.cards.EtherBlast;
import fruitymod.cards.Eureka;
import fruitymod.cards.EventHorizon;
import fruitymod.cards.Feedback;
import fruitymod.cards.Flare;
import fruitymod.cards.Flicker;
import fruitymod.cards.Flow;
import fruitymod.cards.Flux;
import fruitymod.cards.DisruptionField;
import fruitymod.cards.ForceRipple;
import fruitymod.cards.Genesis;
import fruitymod.cards.GravityWell;
import fruitymod.cards.Hypothesis;
import fruitymod.cards.Illuminate;
import fruitymod.cards.Implosion;
import fruitymod.cards.Irradiate;
import fruitymod.cards.MagicMissile;
import fruitymod.cards.Magnetize;
import fruitymod.cards.MeteorShower;
import fruitymod.cards.MindOverMatter;
import fruitymod.cards.Nebula;
import fruitymod.cards.Nexus;
import fruitymod.cards.Nova;
import fruitymod.cards.NullStorm;
import fruitymod.cards.Overload;
import fruitymod.cards.PeriaptOfCelerity;
import fruitymod.cards.PeriaptOfPotency;
import fruitymod.cards.PhaseCoil;
import fruitymod.cards.PlasmaWave;
import fruitymod.cards.PowerOverwhelming;
import fruitymod.cards.PrismaticSphere;
import fruitymod.cards.PulseBarrier;
import fruitymod.cards.ReflectionWard;
import fruitymod.cards.Retrograde;
import fruitymod.cards.RunicBinding;
import fruitymod.cards.Shimmer;
import fruitymod.cards.Singularity;
import fruitymod.cards.SiphonPower;
import fruitymod.cards.SiphonSpeed;
import fruitymod.cards.Starburst;
import fruitymod.cards.Strike_Purple;
import fruitymod.cards.StrokeOfGenius;
import fruitymod.cards.Surge;
import fruitymod.cards.Syzygy;
import fruitymod.cards.ThoughtRaze;
import fruitymod.cards.Transference;
import fruitymod.cards.UmbralBolt;
import fruitymod.cards.UnstableOrb;
import fruitymod.cards.Vacuum;
import fruitymod.cards.VoidRay;
import fruitymod.cards.Vortex;
import fruitymod.cards.Zenith;
import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.Arcanosphere;
import fruitymod.relics.AstralEgg;
import fruitymod.relics.GhostlyHand;
import fruitymod.relics.MechanicalCore;
import fruitymod.relics.PowerCells;
import fruitymod.relics.PurpleSkull;

@SpireInitializer
public class FruityMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
	EditStringsSubscriber, SetUnlocksSubscriber, OnCardUseSubscriber,
	EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final Logger logger = LogManager.getLogger(FruityMod.class.getName());
	
    private static final String MODNAME = "FruityMod";
    private static final String AUTHOR = "Fruitstrike, ColdRain451, test447, fiiiiilth, & Pal";
    private static final String DESCRIPTION = "v0.4.3\n Adds The Seeker as a playable third character";
    
    private static final Color PURPLE = CardHelper.getColor(139.0f, 0.0f, 139.0f);
    private static final String FRUITY_MOD_ASSETS_FOLDER = "img";
    
    // card backgrounds
    private static final String ATTACK_PURPLE = "512/bg_attack_purple.png";
    private static final String SKILL_PURPLE = "512/bg_attack_purple.png";
    private static final String POWER_PURPLE = "512/bg_attack_purple.png";
    private static final String ENERGY_ORB_PURPLE = "512/card_purple_orb.png";
    
    private static final String ATTACK_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String SKILL_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String POWER_PURPLE_PORTRAIT = "1024/bg_attack_purple.png";
    private static final String ENERGY_ORB_PURPLE_PORTRAIT = "1024/card_purple_orb.png";
    
    // card images
    
    public static final String ANOMALY = "cards/anomaly.png";
    public static final String ARCANE_BARRAGE = "cards/starburst.png";
    public static final String ARCANE_TEMPEST = "cards/arcane_tempest.png";
    public static final String ARCANE_VOLLEY = "cards/irradiate.png";
    public static final String ARCHIVES = "cards/archives.png";
    public static final String ASTRAL_FORM = "cards/chaos_form.png";
    public static final String ASTRAL_HAZE = "cards/astral_haze.png";
    public static final String ASTRAL_SHIFT = "cards/dimension_door.png";
    public static final String BRAINSTORM = "cards/brainstorm.png";
    public static final String BRILLIANCE = "cards/brilliance.png";
    public static final String CHANNEL = "cards/channel.png";
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
    public static final String ETHER_BOLT = "cards/essence_dart.png";
    public static final String EUREKA = "cards/eureka.png";
    public static final String EVENT_HORIZON = "cards/event_horizon.png";
    public static final String EXPERIMENT = "cards/experiment.png";
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
    public static final String PERIAPT_OF_POTENCY = "cards/periapt_of_potency.png";
    public static final String PERIAPT_OF_TENACITY = "cards/runic_binding.png";
    public static final String PERIAPT_OF_VIGOR = "cards/periapt_of_vigor.png";
    public static final String POWER_OVERWHELMING = "cards/power_overwhelming.png";
    public static final String POWER_SPIKE = "cards/overload.png";
    public static final String PROTECTION_WARD = "cards/disperse.png";
    public static final String REFLECTION_WARD = "cards/reflection_ward.png";
    public static final String RETROGRADE = "cards/retrograde.png";
    public static final String SHIMMER = "cards/shimmer.png";
    public static final String SIPHON_MAGIC = "cards/convergence.png";
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
    public static final String VOID_SHACKLES = "cards/void_shackles.png";
    public static final String VORTEX = "cards/vortex.png";
    public static final String ZENITH = "cards/zenith.png";
    public static final String PHASE_COIL = "cards/phase_coil.png";
    
    // power images
    public static final String ASTRAL_HAZE_POWER = "powers/astral_haze.png";
    public static final String CHAOS_FORM_POWER = "powers/chaos_form.png";
    public static final String ESSENCE_MIRROR_POWER = "powers/essence_mirror.png";
    public static final String ETHEREALIZE_POWER = "powers/essence_mirror.png";
    public static final String RUNIC_BINDING_POWER = "powers/runic_binding.png";
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
    public static final String NEXUS_POWER = "powers/nexus.png";

    // relic images
    public static final String ARCANOSPHERE_RELIC = "relics/arcanosphere.png";
    public static final String PURPLE_SKULL_RELIC = "relics/purpleSkull.png";
    public static final String GHOSTLY_HAND_RELIC = "relics/ghostlyHand.png";
    public static final String ASTRAL_EGG_RELIC = "relics/astralEgg.png";
    public static final String MECHANICAL_CORE_RELIC = "relics/mechanicalCore.png";
    public static final String POWER_CELLS_RELIC = "relics/powerCells.png";
    
    // seeker assets
    private static final String SEEKER_BUTTON = "charSelect/seekerButton.png";
    private static final String SEEKER_PORTRAIT = "charSelect/SeekerPortraitBG.jpg";
    public static final String SEEKER_SHOULDER_1 = "char/seeker/shoulder.png";
    public static final String SEEKER_SHOULDER_2 = "char/seeker/shoulder2.png";
    public static final String SEEKER_CORPSE = "char/seeker/corpse.png";
    public static final String SEEKER_SKELETON_ATLAS = "char/seeker/skeleton.atlas";
    public static final String SEEKER_SKELETON_JSON = "char/seeker/skeleton.json";
    
    // badge
    public static final String BADGE_IMG = "FRelicBadge.png";
    
    // texture loaders
    public static Texture getAstralHazePowerTexture() {
    	return new Texture(makePath(ASTRAL_HAZE_POWER));
    }
    
    public static Texture getChaosFormPowerTexture() {
    	return new Texture(makePath(CHAOS_FORM_POWER));
    }
    
    public static Texture getEssenceMirrorPowerTexture() {
    	return new Texture(makePath(ESSENCE_MIRROR_POWER));
    }
    
    public static Texture getEtherealizePowerTexture() {
    	return new Texture(makePath(ETHEREALIZE_POWER));
    }
    
    public static Texture getRunicBindingPowerTexture() {
    	return new Texture(makePath(RUNIC_BINDING_POWER));
    }
    
    public static Texture getVigorPowerTexture() {
    	return new Texture(makePath(VIGOR_POWER));
    }
    
    public static Texture getAstralShiftTexture() {
    	return new Texture(makePath(ASTRAL_SHIFT_POWER));
    }
    
    public static Texture getTenacityPowerTexture() {
    	return new Texture(makePath(TENACITY_POWER));
    }
    
    public static Texture getCelerityPowerTexture() {
    	return new Texture(makePath(CELERITY_POWER));
    }
    
    public static Texture getPotencyPowerTexture() {
    	return new Texture(makePath(POTENCY_POWER));
    }
    
    public static Texture getCoalescencePowerTexture() {
    	return new Texture(makePath(COALESCENCE_POWER));
    }
    
    public static Texture getCreativityPowerTexture() {
    	return new Texture(makePath(CREATIVITY_POWER));
    }
    
    public static Texture getPowerOverwhelmingPowerTexture() {
    	return new Texture(makePath(POWER_OVERWHELMING_POWER));
    }
    
    public static Texture getEventHorizonPowerTexture() {
    	return new Texture(makePath(EVENT_HORIZON_POWER));
    }
    
    public static Texture getEnigmaPowerTexture() {
    	return new Texture(makePath(ENIGMA_POWER));
    }
    
    public static Texture getBrillancePowerTexture() {
    	return new Texture(makePath(BRILLIANCE_POWER));
    }
    
    public static Texture getAnomalyPowerTexture() {
    	return new Texture(makePath(ANOMALY_POWER));
    }
    
    public static Texture getNexusPowerTexture() {
    	return new Texture(makePath(VIGOR_POWER));
    }
    
    public static Texture getArcanoSphereTexture() {
    	return new Texture(makePath(ARCANOSPHERE_RELIC));
    }
    
    public static Texture getPurpleSkullTexture() {
    	return new Texture(makePath(PURPLE_SKULL_RELIC));
    }
    
    public static Texture getGhostlyHandTexture() {
    	return new Texture(makePath(GHOSTLY_HAND_RELIC));
    }
    
    public static Texture getAstralEggTexture() {
    	return new Texture(makePath(ASTRAL_EGG_RELIC));
    }
    
    public static Texture getMechanicalCoreTexture() {
    	return new Texture(makePath(MECHANICAL_CORE_RELIC));
    }
    
    public static Texture getPowerCellsTexture() {
    	return new Texture(makePath(POWER_CELLS_RELIC));
    }

    /**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
    	return FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
    }
    
    public FruityMod() {
    	logger.info("subscribing to postInitialize event");
        BaseMod.subscribeToPostInitialize(this);
        
        logger.info("subscribing to editCharacters event");
        BaseMod.subscribeToEditCharacters(this);
        
        logger.info("subscribing to editRelics event");
        BaseMod.subscribeToEditRelics(this);
        
        logger.info("subscribing to editCards event");
        BaseMod.subscribeToEditCards(this);

        logger.info("subscribing to editStrings event");
        BaseMod.subscribeToEditStrings(this);
        
        /* Disable this during playtesting for being counterproductive */
        // logger.info("subscribing to setUnlocks event");
        // BaseMod.subscribeToSetUnlocks(this);
        
        logger.info("subscribing to onCardUse event");
        BaseMod.subscribeToOnCardUse(this);
        
        logger.info("subscribing to editKeywords event");
        BaseMod.subscribeToEditKeywords(this);
        
        BaseMod.subscribeToOnPowersModified(this);
        BaseMod.subscribeToPostExhaust(this);
        BaseMod.subscribeToPostBattle(this);
        BaseMod.subscribeToPostDraw(this);
        
        /*
         * Note that for now when installing FruityMod, in the `mods/` folder another folder named
         * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
         * `images/` folder must be relocated
         */
        logger.info("creating the color " + AbstractCardEnum.PURPLE.toString());
        BaseMod.addColor(AbstractCardEnum.PURPLE.toString(),
        		PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE,
        		makePath(ATTACK_PURPLE), makePath(SKILL_PURPLE),
        		makePath(POWER_PURPLE), makePath(ENERGY_ORB_PURPLE),
        		makePath(ATTACK_PURPLE_PORTRAIT), makePath(SKILL_PURPLE_PORTRAIT),
        		makePath(POWER_PURPLE_PORTRAIT), makePath(ENERGY_ORB_PURPLE_PORTRAIT));
    }

    public static void initialize() {
    	logger.info("========================= FRUITYMOD INIT =========================");
		
		@SuppressWarnings("unused")
		FruityMod fruit = new FruityMod();
		
		logger.info("================================================================");
    }

    @Override
    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(makePath(BADGE_IMG));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("FruityMod does not have any settings (yet)!", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        Settings.isDailyRun = false;
        Settings.isTrial = false;
        Settings.isDemo = false;
    }

	@Override
	public void receiveEditCharacters() {
		logger.info("begin editting characters");
		
		logger.info("add " + TheSeekerEnum.THE_SEEKER.toString());
		BaseMod.addCharacter(TheSeeker.class, "The Seeker", "Seeker class string",
				AbstractCardEnum.PURPLE.toString(), "The Seeker",
				makePath(SEEKER_BUTTON), makePath(SEEKER_PORTRAIT),
				TheSeekerEnum.THE_SEEKER.toString());
		
		logger.info("done editting characters");
	}

	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
        // Add relics
		BaseMod.addRelicToCustomPool(new Arcanosphere(), AbstractCardEnum.PURPLE.toString());
		BaseMod.addRelicToCustomPool(new PurpleSkull(), AbstractCardEnum.PURPLE.toString());
		BaseMod.addRelicToCustomPool(new GhostlyHand(), AbstractCardEnum.PURPLE.toString());
		BaseMod.addRelicToCustomPool(new AstralEgg(), AbstractCardEnum.PURPLE.toString());
		BaseMod.addRelicToCustomPool(new MechanicalCore(), AbstractCardEnum.PURPLE.toString());
		BaseMod.addRelicToCustomPool(new PowerCells(), AbstractCardEnum.PURPLE.toString());
        
        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		
		logger.info("add cards for " + TheSeekerEnum.THE_SEEKER.toString());
		
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
		BaseMod.addCard(new UmbralBolt());
		BaseMod.addCard(new Genesis());
		BaseMod.addCard(new PrismaticSphere());
		BaseMod.addCard(new Flux());
		BaseMod.addCard(new Channel());
		BaseMod.addCard(new Implosion());
		BaseMod.addCard(new ChaosForm());
		BaseMod.addCard(new Vacuum());
		BaseMod.addCard(new AstralShift());
		BaseMod.addCard(new RunicBinding());
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
		UnlockTracker.unlockCard("UmbralBolt");
		UnlockTracker.unlockCard("Genesis");
		UnlockTracker.unlockCard("PrismaticSphere");
		UnlockTracker.unlockCard("Flux");
		UnlockTracker.unlockCard("Channel");
		UnlockTracker.unlockCard("Implosion");
		UnlockTracker.unlockCard("ChaosForm");
		UnlockTracker.unlockCard("Vacuum");
		UnlockTracker.unlockCard("AstralShift");
		UnlockTracker.unlockCard("RunicBinding");
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
		
		logger.info("done editting cards");
	}

	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");
		
        // RelicStrings
        String relicStrings = Gdx.files.internal("localization/FruityMod-RelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(RelicStrings.class, relicStrings);
        // CardStrings
        String cardStrings = Gdx.files.internal("localization/FruityMod-CardStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomStrings(CardStrings.class, cardStrings);
		
		logger.info("done editting strings");
	}

	@Override
	public void receiveSetUnlocks() {
		UnlockTracker.addCard("Flicker");
		UnlockTracker.addCard("Transference");
		UnlockTracker.addCard("ForceRipple");
		// seeker unlock 1
		BaseMod.addUnlockBundle(new CustomUnlockBundle(
				"Flicker", "Transference", "ForceRipple"
				), TheSeekerEnum.THE_SEEKER, 1);
		
		// seeker unlock 2
		BaseMod.addUnlockBundle(new CustomUnlockBundle(
				"Channel", "Shimmer", "ThoughtRaze"
				), TheSeekerEnum.THE_SEEKER, 2);
		UnlockTracker.addCard("Channel");
		UnlockTracker.addCard("Shimmer");
		UnlockTracker.addCard("ThoughtRaze");
		
		// seeker unlock 3 (Vacuum tmp in place of Feedback)
		BaseMod.addUnlockBundle(new CustomUnlockBundle(
				"Convergence", "Hypothesis", "Nexus"
				), TheSeekerEnum.THE_SEEKER, 3);
		UnlockTracker.addCard("Convergence");
		UnlockTracker.addCard("Hypothesis");
		UnlockTracker.addCard("Nexus");
	}
	

	@Override
	public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] {"reflect", "Reflect"}, "Whenever you are attacked this turn, deal this amount of damage back back to the attacker.");
        BaseMod.addKeyword(new String[] {"recycle", "Recycle"}, "When you Recycle a card, place it on top of your draw pile.");
	}
	
	//
	// Enigma hooks and functionality 	
	//
	
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
	
	@Override
	public void receiveCardUsed(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;
		if (p.hasPower("EnigmaPower") && c.cardID.equals("Dazed")) {
			AbstractDungeon.actionManager.addToTop(new GainBlockAction(p, p, c.block));
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(null, 
					DamageInfo.createDamageMatrix(c.damage, true),
					DamageInfo.DamageType.THORNS, AbstractGameAction.AttackEffect.FIRE));
			c.exhaustOnUseOnce = true;
			
			// this bit here is question - it fixes a bug that the base game has where dazes are played double
			// for hex or dead branch, or etc...
			c.dontTriggerOnUseCard = true;
			if (p.hasPower("Hex")) {
				p.getPower("Hex").onUseCard(c, null);
			}
		}
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
	
	private boolean isApplyingPurpleSkull = false;
	
	private void resetPurpleSkull() {
		isApplyingPurpleSkull = false;
		if (AbstractDungeon.player.hasRelic("PurpleSkull")) {
			((PurpleSkull) AbstractDungeon.player.getRelic("PurpleSkull")).setPulse(false);
		}
	}
	
	@Override
	public void receivePowersModified() {
		AbstractPlayer p = AbstractDungeon.player;
		
		if (p != null && p.hasRelic("PurpleSkull")) {
			if (moreThanXStacks(p, "Weakened", PurpleSkull.MIN_STACKS) ||
					moreThanXStacks(p, "Vulnerable", PurpleSkull.MIN_STACKS) ||
					moreThanXStacks(p, "Frail", PurpleSkull.MIN_STACKS)) {
				if (!isApplyingPurpleSkull) {
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new DexterityPower(p, PurpleSkull.DEX_GAIN), PurpleSkull.DEX_GAIN));
					isApplyingPurpleSkull = true;
					p.getRelic("PurpleSkull").flash();
					((PurpleSkull) p.getRelic("PurpleSkull")).setPulse(true);
				}
			} else {
				if (isApplyingPurpleSkull) {
					AbstractDungeon.actionManager.addToTop(
							new ApplyPowerAction(p, p, new DexterityPower(p, -1 * PurpleSkull.DEX_GAIN), -1 * PurpleSkull.DEX_GAIN));
					isApplyingPurpleSkull = false;
					((PurpleSkull) p.getRelic("PurpleSkull")).setPulse(false);
				}
			}
		}
	}

	@Override
	public void receivePostExhaust(AbstractCard c) {
		AbstractPlayer p = AbstractDungeon.player;
		
		if (p != null && p.hasRelic("GhostlyHand")) {
			if (c.isEthereal) {
				p.heal(GhostlyHand.HP_PER_CARD);
				p.getRelic("GhostlyHand").flash();
				AbstractDungeon.actionManager.addToBottom(
						new RelicAboveCreatureAction(AbstractDungeon.player, p.getRelic("GhostlyHand")));
			}
		}
		
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		resetPurpleSkull();
	}

	@Override
	public void receivePostDungeonInitialize() {
		resetPurpleSkull();
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		if (c instanceof Convergence) {
			c.superFlash();
			AbstractDungeon.actionManager.addToBottom(new WaitAction(Settings.ACTION_DUR_FAST));
			AbstractDungeon.actionManager.addToBottom(new ConvergenceAction(c.upgraded));
		}
	}
}
