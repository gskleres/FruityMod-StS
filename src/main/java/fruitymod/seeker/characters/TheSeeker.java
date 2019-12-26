package fruitymod.seeker.characters;

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
import fruitymod.SeekerMod;
import fruitymod.seeker.cards.AstralHaze;
import fruitymod.seeker.patches.AbstractCardEnum;
import fruitymod.seeker.relics.Arcanosphere;

import java.util.ArrayList;

public class TheSeeker extends CustomPlayer {
    public static final int ENERGY_PER_TURN = 3;

    public static final String[] orbTextures = {
            SeekerMod.makePath("char/orb/layer1.png"),
            SeekerMod.makePath("char/orb/layer2.png"),
            SeekerMod.makePath("char/orb/layer3.png"),
            SeekerMod.makePath("char/orb/layer4.png"),
            SeekerMod.makePath("char/orb/layer5.png"),
            SeekerMod.makePath("char/orb/layer6.png"),
            SeekerMod.makePath("char/orb/layer1d.png"),
            SeekerMod.makePath("char/orb/layer2d.png"),
            SeekerMod.makePath("char/orb/layer3d.png"),
            SeekerMod.makePath("char/orb/layer4d.png"),
            SeekerMod.makePath("char/orb/layer5d.png"),
    };

    public TheSeeker(String name, PlayerClass setClass) {
        super(name, setClass, orbTextures, SeekerMod.makePath("char/orb/vfx.png"), new SpriterAnimation(SeekerMod.makePath("char/animation.scml")));

        initializeClass(null, SeekerMod.makePath(SeekerMod.SEEKER_SHOULDER_2),
                SeekerMod.makePath(SeekerMod.SEEKER_SHOULDER_1),
                SeekerMod.makePath(SeekerMod.SEEKER_CORPSE),
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

    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("AstralHaze");
        return retVal;
    }


    @Override
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return "The Seeker";
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        return new AstralHaze();
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return AbstractCardEnum.SEEKER_PURPLE;
    }

    @Override
    public Color getCardTrailColor() {
        return SeekerMod.PURPLE.cpy();
    }

    @Override
    public String getSpireHeartText() {
        return "seeker heart text here";
    }

    @Override
    public Color getSlashAttackColor() {
        return SeekerMod.PURPLE.cpy();
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
        return "seeker vampire text here";
    }

    @Override
    public AbstractPlayer newInstance() {
        return new TheSeeker(name, chosenClass);
    }


    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        return "UNLOCK_PING";
    }

    @Override
    public String getLocalizedCharacterName() {
        return "The Seeker";
    }

    @Override
    public int getAscensionMaxHPLoss() {
        return 7;
    }

    @Override
    public Color getCardRenderColor() {
        return SeekerMod.PURPLE.cpy();
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

    public CharSelectInfo getLoadout() {
        return new CharSelectInfo("The Seeker", "A brilliant mage and astronomer. Channels power from the astral plane.",
                75, 75, 0, 99, 5,
                this, getStartingRelics(), getStartingDeck(), false);
    }

}
