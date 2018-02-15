package fruitymod;

import java.nio.charset.StandardCharsets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.RelicLibrary;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.EditCharactersSubscriber;
import basemod.interfaces.EditRelicsSubscriber;
import basemod.interfaces.PostInitializeSubscriber;

import fruitymod.cards.*;
import fruitymod.cards.rare.*;

import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.*;

public class FruityMod implements PostInitializeSubscriber,
	EditCardsSubscriber, EditRelicsSubscriber, EditCharactersSubscriber {
	public static final Logger logger = LogManager.getLogger(FruityMod.class.getName());
	
    private static final String MODNAME = "FruityMod";
    private static final String AUTHOR = "Fruitstrike & fiiiiilth & Test447";
    private static final String DESCRIPTION = "v0.2.0";
    
    private static final Color PURPLE = CardHelper.getColor(139.0f, 0.0f, 139.0f);
    private static final String FRUITY_MOD_ASSETS_FOLDER = "fruity_mod_assets";
    private static final String MOD_FOLDER = "mods";
    
    // card backgrounds
    private static final String ATTACK_PURPLE = "512/bg_attack_purple.png";
    private static final String SKILL_PURPLE = "512/bg_attack_purple.png";
    private static final String POWER_PURPLE = "512/bg_attack_purple.png";
    private static final String ENERGY_ORB_PURPLE = "512/card_purple_orb.png";
    
    // card images
    
    // common + uncommon
    public static final String STRIKE_PURPLE = "cards/strike_purple.png";
    public static final String DEFEND_PURPLE = "cards/defend_purple.png";
    public static final String ARCANE_BARRAGE = "cards/arcane_barrage.png";
    public static final String ARCANE_VOLLEY = "cards/arcane_volley.png";
    public static final String ASTRAL_HAZE = "cards/astral_haze.png";
    public static final String DEFLECTION_WARD = "cards/deflection_ward.png";
    public static final String ESSENCE_MIRROR = "cards/essence_mirror.png";
    public static final String ESSENCE_SPIKE = "cards/essence_spike.png";
    public static final String ETHER_BARRIER = "cards/ether_barrier.png";
    public static final String ETHER_BOLT = "cards/ether_bolt.png";
    public static final String FLUX_BLAST = "cards/flux_blast.png";
    public static final String FLUX_BOLT = "cards/flux_bolt.png";
    public static final String POWER_SPIKE = "cards/power_spike.png";
    public static final String METEOR_SHOWER = "cards/meteor_shower.png";
    public static final String SHIMMER = "cards/shimmer.png";
    public static final String STARFALL = "cards/starfall.png";
    public static final String SURGE = "cards/surge.png";
    public static final String UMBRAL_WAVE = "cards/umbral_wave.png";
    public static final String VACUUM = "cards/vacuum.png";
    public static final String VOID_BARRIER = "cards/void_barrier.png";
    public static final String VOID_BOLT = "cards/void_bolt.png";
    public static final String VOID_RIPPLE = "cards/void_ripple.png";
    
    // rare
    public static final String CHANNEL = "cards/channel.png";
    public static final String IMPLOSION = "cards/implosion.png";
    public static final String ASTRAL_FORM = "cards/astral_form.png";
    
    // power images
    public static final String ASTRAL_HAZE_POWER = "powers/astral_haze.png";
    public static final String ESSENCE_MIRROR_POWER = "powers/essence_mirror.png";
    public static final String ETHEREALIZE_POWER = "powers/essence_mirror.png";
    public static final String ASTRAL_FORM_POWER = "powers/astral_form.png";
    
    // relic images
    public static final String ARCANOSPHERE_RELIC = "relics/arcanosphere.png";
    
    // seeker assets
    private static final String SEEKER_BUTTON = "charSelect/seekerButton.png";
    private static final String SEEKER_PORTRAIT = "charSelect/seekerPortrait.jpg";
    
    // texture loaders
    public static Texture getAstralHazePowerTexture() {
    	return new Texture(makePath(ASTRAL_HAZE_POWER));
    }
    
    public static Texture getEssenceMirrorPowerTexture() {
    	return new Texture(makePath(ESSENCE_MIRROR_POWER));
    }
    
    public static Texture getEtherealizePowerTexture() {
    	return new Texture(makePath(ETHEREALIZE_POWER));
    }
    
    public static Texture getAstralFormPowerTexture() {
    	return new Texture(makePath(ASTRAL_FORM_POWER));
    }
    
    public static Texture getArcanoSphereTexture() {
    	return new Texture(makePath(ARCANOSPHERE_RELIC));
    }
    
    /**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    public static final String makePath(String resource) {
    	return MOD_FOLDER + "/" + FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
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

        /*
         * Note that for now when installing FruityMod, in the `mods/` folder another folder named
         * the value of FRUITY_MOD_ASSETS_FOLDER must be created into which all the contents of the
         * `images/` folder must be relocated
         */
        logger.info("creating the color " + AbstractCardEnum.PURPLE.toString());
        BaseMod.addColor(AbstractCardEnum.PURPLE.toString(),
        		PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE, PURPLE,
        		makePath(ATTACK_PURPLE), makePath(SKILL_PURPLE),
        		makePath(POWER_PURPLE), makePath(ENERGY_ORB_PURPLE));
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
        Texture badgeTexture = new Texture(Gdx.files.internal("img/FRelicBadge.png"));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("This mod does not have any settings (yet)", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
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

	
	@SuppressWarnings("deprecation")
	@Override
	public void receiveEditRelics() {
		logger.info("begin editting relics");
		
        // RelicStrings
        String jsonString = Gdx.files.internal("localization/FruityMod-RelicStrings.json").readString(
        		String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomRelicStrings(jsonString);
        
        // Add relics
        RelicLibrary.add(new Homunculus());
        RelicLibrary.add(new RabbitsFoot());
        RelicLibrary.add(new Arcanosphere());
        
        logger.info("done editting relics");
	}

	@Override
	public void receiveEditCards() {
		logger.info("begin editting cards");
		
		logger.info("add cards for " + TheSeekerEnum.THE_SEEKER.toString());
		
		BaseMod.addCard(new Strike_Purple());
		BaseMod.addCard(new Defend_Purple());
		
		BaseMod.addCard(new ArcaneBarrage());
		BaseMod.addCard(new ArcaneVolley());
		BaseMod.addCard(new AstralHaze());
		BaseMod.addCard(new DeflectionWard());
		BaseMod.addCard(new EssenceMirror());
		BaseMod.addCard(new EssenceSpike());
		BaseMod.addCard(new EtherBarrier());
		BaseMod.addCard(new FluxBlast());
		BaseMod.addCard(new FluxBolt());
		BaseMod.addCard(new FluxShield());
		BaseMod.addCard(new ForceSpike());
		BaseMod.addCard(new Hypothesis());
		BaseMod.addCard(new MagicMissile());
		BaseMod.addCard(new EtherBolt());
		BaseMod.addCard(new PowerSpike());
		BaseMod.addCard(new Pulsar());
		BaseMod.addCard(new Shimmer());
		BaseMod.addCard(new SpiritLance());
		BaseMod.addCard(new Retrograde());
		BaseMod.addCard(new UmbralWave());
		BaseMod.addCard(new Vacuum());
		BaseMod.addCard(new VoidBarrier());
		BaseMod.addCard(new VoidBolt());
		BaseMod.addCard(new VoidRipple());
		
		BaseMod.addCard(new Channel());
		BaseMod.addCard(new Implosion());
		BaseMod.addCard(new AstralForm());
		
		logger.info("done editting cards");
	}
}