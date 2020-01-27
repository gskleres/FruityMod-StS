package fruitymod.tranquil.characters;

import basemod.abstracts.CustomPlayer;
import basemod.animations.SpriterAnimation;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import fruitymod.TranquilMod;
import fruitymod.seeker.relics.Arcanosphere;
import fruitymod.tranquil.cards.FlyingKick;
import fruitymod.tranquil.patches.AbstractCardEnum;

import java.util.ArrayList;

public class TheTranquil extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;

    public static final String[] orbTextures = {
            TranquilMod.makeCharacterImagePath("orb/layer1"),
            TranquilMod.makeCharacterImagePath("orb/layer2"),
            TranquilMod.makeCharacterImagePath("orb/layer3"),
            TranquilMod.makeCharacterImagePath("orb/layer4"),
            TranquilMod.makeCharacterImagePath("orb/layer5"),
            TranquilMod.makeCharacterImagePath("orb/layer6"),
            TranquilMod.makeCharacterImagePath("orb/layer1d"),
            TranquilMod.makeCharacterImagePath("orb/layer2d"),
            TranquilMod.makeCharacterImagePath("orb/layer3d"),
            TranquilMod.makeCharacterImagePath("orb/layer4d"),
            TranquilMod.makeCharacterImagePath("orb/layer5d"),
    };

    public TheTranquil(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, TranquilMod.makeCharacterImagePath("orb/vfx"), new SpriterAnimation(TranquilMod.makeCharacterImagePath("animation/animation.scml")));

        initializeClass(null, TranquilMod.makeCharacterImagePath("shoulder2"),
                TranquilMod.makeCharacterImagePath("shoulder"),
                TranquilMod.makeCharacterImagePath("corpse"),
                getLoadout(), 20.0F, -10.0F, 220.0F, 290.0F, new EnergyManager(ENERGY_PER_TURN));
    }

    @Override
    public void applyEndOfTurnTriggers() {
        for (AbstractPower p : this.powers) {
            p.atEndOfTurn(true);
        }
        // make sure that cards that get changed to ethereal are
        // always exhausted
        AbstractDungeon.actionManager.addToBottom(new ExhaustAllEtherealAction());
    }

    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "The Tranquil";
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new FlyingKick();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.TRANQUIL_TAN;
    }

    @Override
    public Color getCardTrailColor() {
        return TranquilMod.TAN.cpy();
    }

    @Override
    public String getSpireHeartText() {
        return "tranquil heart text here";
    }

    @Override
    public Color getSlashAttackColor() {
        return TranquilMod.TAN.cpy();
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{
                AbstractGameAction.AttackEffect.FIRE,
                AbstractGameAction.AttackEffect.BLUNT_HEAVY,
                AbstractGameAction.AttackEffect.FIRE};
    }

    @Override
    public String getVampireText() {
        return "tranquil vampire text here";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheTranquil(name, chosenClass);
    }


    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "the Tranquil";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public Color getCardRenderColor() {
        return TranquilMod.TAN.cpy();
    }


    @Override
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.sound.playA("UNLOCK_PING", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.LOW, ScreenShake.ShakeDur.SHORT,
                false);
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontRed;
    }

    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Arcanosphere.ID);
        UnlockTracker.markRelicAsSeen(Arcanosphere.ID);
        return retVal;
    }


    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike_T");
        retVal.add("Strike_T");
        retVal.add("Strike_T");
        retVal.add("Strike_T");
        retVal.add("Strike_T");
        retVal.add("Defend_T");
        retVal.add("Defend_T");
        retVal.add("Defend_T");
        retVal.add("Defend_T");
        retVal.add("Tranquil_FlyingKick");
        return retVal;
    }

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Tranquil", "A dude that likes earth tones and yoga.",
                75, 75, 0, 99, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

}
