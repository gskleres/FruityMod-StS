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
import fruitymod.cards.ArcaneBarrage;
import fruitymod.cards.ArcaneVolley;
import fruitymod.cards.AstralHaze;
import fruitymod.cards.Defend_Purple;
import fruitymod.cards.DeflectionWard;
import fruitymod.cards.EssenceMirror;
import fruitymod.cards.EssenceSpike;
import fruitymod.cards.EtherBarrier;
import fruitymod.cards.FluxBlast;
import fruitymod.cards.FluxBolt;
import fruitymod.cards.FluxShield;
import fruitymod.cards.ForceSpike;
import fruitymod.cards.Hypothesis;
import fruitymod.cards.MagicMissile;
import fruitymod.cards.MysticBolt;
import fruitymod.cards.PowerSpike;
import fruitymod.cards.Pulsar;
import fruitymod.cards.Shimmer;
import fruitymod.cards.SpiritLance;
import fruitymod.cards.Strike_Purple;
import fruitymod.cards.Vex;
import fruitymod.cards.VoidRipple;
import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.Homunculus;
import fruitymod.relics.RabbitsFoot;

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
    public static final String STRIKE_PURPLE = "cards/locked_attack.png";
    public static final String DEFEND_PURPLE = "cards/locked_skill.png";
    
    // seeker assets
    private static final String SEEKER_BUTTON = "charSelect/seekerButton.png";
    private static final String SEEKER_PORTRAIT = "charSelect/seekerPortrait.jpg";
    
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
		BaseMod.addCard(new MysticBolt());
		BaseMod.addCard(new PowerSpike());
		BaseMod.addCard(new Pulsar());
		BaseMod.addCard(new Shimmer());
		BaseMod.addCard(new SpiritLance());
		BaseMod.addCard(new Vex());
		BaseMod.addCard(new VoidRipple());
		
		logger.info("done editting cards");
	}
}