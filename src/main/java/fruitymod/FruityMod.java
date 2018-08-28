package fruitymod;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

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
    private static final String FRUITY_MOD_ASSETS_FOLDER = "img";

	// badge
    public static final String BADGE_IMG = "FRelicBadge.png";

	/**
     * Makes a full path for a resource path
     * @param resource the resource, must *NOT* have a leading "/"
     * @return the full path
     */
    private static final String makePath(String resource) {
    	return FRUITY_MOD_ASSETS_FOLDER + "/" + resource;
    }
    
    public FruityMod() {
        BaseMod.subscribe(this);
        mods = new ArrayList<>();
        mods.add(new SeekerMod());
        if (TranquilMod.isEnabled()) {
			mods.add(new TranquilMod());
		}
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

        logger.info("done editting cards");
	}

	@Override
	public void receiveEditStrings() {
		logger.info("begin editting strings");

		for(EditStringsSubscriber mod : mods) {
			mod.receiveEditStrings();
		}

		logger.info("done editting strings");
	}

	@Override
	public void receiveSetUnlocks() {
	}
	

	@Override
	public void receiveEditKeywords() {
		logger.info("setting up custom keywords");
		for(EditKeywordsSubscriber mod : mods) {
			mod.receiveEditKeywords();
		}
	}

	@Override
	public void receiveCardUsed(AbstractCard c) {
		for(OnCardUseSubscriber mod : mods) {
			mod.receiveCardUsed(c);
		}
	}

	@Override
	public void receivePowersModified() {
		for(OnPowersModifiedSubscriber mod : mods) {
			mod.receivePowersModified();
		}
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		for(PostBattleSubscriber mod : mods) {
			mod.receivePostBattle(arg0);
		}
	}

	@Override
	public void receivePostDungeonInitialize() {
		for(PostDungeonInitializeSubscriber mod : mods) {
			mod.receivePostDungeonInitialize();
		}
	}

	@Override
	public void receivePostExhaust(AbstractCard c) {
    	for (PostExhaustSubscriber mod : mods) {
    		mod.receivePostExhaust(c);
		}
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
    	for (PostDrawSubscriber mod : mods) {
    		mod.receivePostDraw(c);
		}
	}
}
