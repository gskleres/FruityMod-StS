package fruitymod;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import fruitymod.cards.*;
import fruitymod.cards.tranquil.Tranquil_FlyingKick;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
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
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.ModPanel;
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
import fruitymod.characters.TheTranquil;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheTranquilEnum;
import fruitymod.relics.CosmicSieve;
import fruitymod.relics.PaperPengwin;

@SpireInitializer
public class FruityMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber,
	EditStringsSubscriber, SetUnlocksSubscriber, OnCardUseSubscriber,
	EditKeywordsSubscriber, OnPowersModifiedSubscriber, PostExhaustSubscriber,
	PostBattleSubscriber, PostDungeonInitializeSubscriber, PostDrawSubscriber {
	public static final Logger logger = LogManager.getLogger(FruityMod.class.getName());
	private List<CharacterMod> mods;
	
    private static final String MODNAME = "FruityMod";
    private static final String AUTHOR = "Fruitstrike, ColdRain451, test447, fiiiiilth, Blank The Evil & Pal";
    private static final String DESCRIPTION = "v0.7\n Adds The Seeker as a playable third character";

    private static final Color TAN = CardHelper.getColor(210.0f, 180.0f, 140.0f);
    private static final String FRUITY_MOD_ASSETS_FOLDER = "img";

    private static final String ATTACK_TAN = "512/bg_attack_tan.png";
    private static final String SKILL_TAN = "512/bg_skill_tan.png";
    private static final String POWER_TAN = "512/bg_power_tan.png";
    private static final String ENERGY_ORB_TAN = "512/card_tan_orb.png";
    private static final String CARD_ENERGY_ORB_TAN = "512/card_tan_small_orb.png";

    private static final String ATTACK_TAN_PORTRAIT = "1024/bg_attack_tan.png";
    private static final String SKILL_TAN_PORTRAIT = "1024/bg_skill_tan.png";
    private static final String POWER_TAN_PORTRAIT = "1024/bg_power_tan.png";
    private static final String ENERGY_ORB_TAN_PORTRAIT = "1024/card_tan_orb.png";


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

    // tranquil card images
    public static final String STRIKE_TAN = "cards/strike_tan.png";
    public static final String DEFEND_TAN = "cards/defend_tan.png";
    public static final String TRANQUIL_FLYING_KICK = "cards/tranquil/flying_kick.png";
    public static final String FLURRY_OF_BLOWS = "cards/flurry_of_blows.png";

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

    // tranquil assets
    private static final String TRANQUIL_BUTTON = "charSelect/tranquilButton.png";
    private static final String TRANQUIL_PORTRAIT = "charSelect/TranquilPortraitBG.jpg";
    public static final String TRANQUIL_SHOULDER_1 = "char/tranquil/shoulder.png";
    public static final String TRANQUIL_SHOULDER_2 = "char/tranquil/shoulder2.png";
    public static final String TRANQUIL_CORPSE = "char/tranquil/corpse.png";
    
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
    
    public static Texture getWormholePowerTexture() {
    	return new Texture(makePath(WORMHOLE_POWER));
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

    public static Texture getArcanospherePowerTexture() {
        return new Texture(makePath(ARCANOSPHERE_POWER));
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

        mods = new ArrayList<>();
        mods.add(new SeekerMod());

        /*
         * Note that for now when installing FruityMod, in the `mods/` folder another folder named
         * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
         * `images/` folder must be relocated
         */
        logger.info("creating the color " + AbstractCardEnum.TRANQUIL_TAN.toString());
        BaseMod.addColor(AbstractCardEnum.TRANQUIL_TAN.toString(),
                TAN, TAN, TAN, TAN, TAN, TAN, TAN,
                makePath(ATTACK_TAN), makePath(SKILL_TAN),
                makePath(POWER_TAN), makePath(ENERGY_ORB_TAN),
                makePath(ATTACK_TAN_PORTRAIT), makePath(SKILL_TAN_PORTRAIT),
                makePath(POWER_TAN_PORTRAIT), makePath(ENERGY_ORB_TAN_PORTRAIT), makePath(CARD_ENERGY_ORB_TAN));
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

		for(EditCharactersSubscriber mod : mods) {
			mod.receiveEditCharacters();
		}

		if (isTranquilEnabled()) {
			logger.info("add " + TheTranquilEnum.THE_TRANQUIL.toString());
			BaseMod.addCharacter(TheTranquil.class, "The Tranquil", "Tranquil class string",
					AbstractCardEnum.TRANQUIL_TAN.toString(), "The Tranquil",
					makePath(TRANQUIL_BUTTON), makePath(TRANQUIL_PORTRAIT),
					TheTranquilEnum.THE_TRANQUIL.toString());
		}

		logger.info("done editting characters");
	}

	
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
        
		for(EditRelicsSubscriber mod : mods) {
			mod.receiveEditRelics();
		}

        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");

		for(EditCardsSubscriber mod : mods) {
			mod.receiveEditCards();
		}

        BaseMod.addCard(new Strike_Tan());
        BaseMod.addCard(new Defend_Tan());
        BaseMod.addCard(new Tranquil_FlyingKick());
        BaseMod.addCard(new FlurryOfBlows());

        UnlockTracker.unlockCard("Strike_T");
        UnlockTracker.unlockCard("Defend_T");
        UnlockTracker.unlockCard("Tranquil_FlyingKick");

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
	}
	

	@Override
	public void receiveEditKeywords() {
        logger.info("setting up custom keywords");
        BaseMod.addKeyword(new String[] {"reflect", "Reflect"}, "Whenever you are attacked this turn, deal this amount of damage back to ALL enemies.");
        BaseMod.addKeyword(new String[] {"top-cycle", "Top-Cycle"}, "When you Top-Cycle a card, place it on top of your draw pile.");
        BaseMod.addKeyword(new String[] {"recycle", "Recycle"}, "When you Recycle a card, shuffle it randomly into your draw pile.");
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
			AbstractDungeon.actionManager.addToTop(new DamageAllEnemiesAction(AbstractDungeon.player, 
					c.multiDamage,
					DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.FIRE, true));
			c.exhaustOnUseOnce = false;
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
	public void receivePostBattle(AbstractRoom arg0) {
		resetPaperPengwin();
	}

	@Override
	public void receivePostDungeonInitialize() {
		resetPaperPengwin();
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

	private boolean isTranquilEnabled() {
		return Boolean.parseBoolean(System.getProperty("tranquil_enabled","false"));
	}
}
