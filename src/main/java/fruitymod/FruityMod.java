package fruitymod;

import basemod.BaseMod;
import basemod.ModPanel;
import basemod.interfaces.PostInitializeSubscriber;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import java.nio.charset.StandardCharsets;
import fruitymod.relics.*;

public class FruityMod implements PostInitializeSubscriber {
    private static final String MODNAME = "FruityMod";
    private static final String AUTHOR = "Fruitstrike & fiiiiilth";
    private static final String DESCRIPTION = "v0.1.0 - 2 New relics.";
    
    public FruityMod() {
        BaseMod.subscribeToPostInitialize(this);
    }

    public static void initialize() {
        FruityMod fm = new FruityMod();

    }

    public void receivePostInitialize() {
        // Mod badge
        Texture badgeTexture = new Texture(Gdx.files.internal("img/FRelicBadge.png"));
        ModPanel settingsPanel = new ModPanel();
        settingsPanel.addLabel("This mod does not have any settings (yet)", 400.0f, 700.0f, (me) -> {});
        BaseMod.registerModBadge(badgeTexture, MODNAME, AUTHOR, DESCRIPTION, settingsPanel);
        
        // RelicStrings
        String jsonString = Gdx.files.internal("localization/FruityMod-RelicStrings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        BaseMod.loadCustomRelicStrings(jsonString);
        
        // Add relics
        RelicLibrary.add(new Homunculus());
        RelicLibrary.add(new RabbitsFoot());
    }
}