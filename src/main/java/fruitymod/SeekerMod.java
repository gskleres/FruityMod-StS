package fruitymod;

import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
import fruitymod.characters.TheSeeker;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.patches.TheSeekerEnum;
import fruitymod.relics.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

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
	public static final String SEEKER_SHOULDER_1 = "char/shoulder.png";
	public static final String SEEKER_SHOULDER_2 = "char/shoulder2.png";
	public static final String SEEKER_CORPSE = "char/corpse.png";

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

		List<CustomCard> cards = new ArrayList<CustomCard>();
		cards.add(new Dazed_P());
		cards.add(new Strike_Purple());
		cards.add(new Defend_Purple());
		cards.add(new Starburst());
		cards.add(new Irradiate());
		cards.add(new AstralHaze());
		cards.add(new Brainstorm());
		cards.add(new DarkMatter());
		cards.add(new ArcaneArmor());
		cards.add(new Entropy());
		cards.add(new EssenceDart());
		cards.add(new Flicker());
		cards.add(new PlasmaWave());
		cards.add(new PulseBarrier());
		cards.add(new Nebula());
		cards.add(new EtherBlast());
		cards.add(new Flare());
		cards.add(new NullStorm());
		cards.add(new VoidRay());
		cards.add(new DisruptionField());
		cards.add(new UnstableOrb());
		cards.add(new Hypothesis());
		cards.add(new Comet());
		cards.add(new ForceRipple());
		cards.add(new PhaseCoil());
		cards.add(new Overload());
		cards.add(new Syzygy());
		cards.add(new SiphonPower());
		cards.add(new Shimmer());
		cards.add(new ThoughtRaze());
		cards.add(new Retrograde());
		cards.add(new Singularity());
		cards.add(new Umbra());
		cards.add(new Genesis());
		cards.add(new PrismaticSphere());
		cards.add(new Flux());
		cards.add(new Channel());
		cards.add(new Implosion());
		cards.add(new ChaosForm());
		cards.add(new Vacuum());
		cards.add(new DimensionDoor());
		cards.add(new Wormhole());
		cards.add(new Eureka());
		cards.add(new Eclipse());
		cards.add(new Echo());
		cards.add(new EventHorizon());
		cards.add(new Zenith());
		cards.add(new ReflectionWard());
		cards.add(new Creativity());
		cards.add(new Transference());
		cards.add(new Surge());
		cards.add(new StrokeOfGenius());
		cards.add(new SiphonSpeed());
		cards.add(new Convergence());
		cards.add(new GravityWell());
		cards.add(new Coalescence());
		cards.add(new PeriaptOfCelerity());
		cards.add(new PeriaptOfPotency());
		cards.add(new MeteorShower());
		cards.add(new PowerOverwhelming());
		cards.add(new MindOverMatter());
		cards.add(new Disperse());
		cards.add(new Magnetize());
		cards.add(new Illuminate());
		cards.add(new Flow());
		cards.add(new Equinox());
		cards.add(new Corona());
		cards.add(new Archives());
		cards.add(new MagicMissile());
		cards.add(new Enigma());
		cards.add(new Feedback());
		cards.add(new Brilliance());
		cards.add(new Anomaly());
		cards.add(new Nova());
		cards.add(new Vortex());
		cards.add(new Nexus());

		for(CustomCard card : cards) {
			BaseMod.addCard(card);
			UnlockTracker.unlockCard(card.cardID);
		}
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

	public static final String makeCardImagePath(String cardName) {
		return makePath("cards/" + cardName);
	}

	public static final String makeRelicImagePath(String power) {
		return makePath("relics/" + power);
	}

	public static final String makePowerImagePath(String power) {
		return makePath("powers/" + power);
	}

	/**
	 * Makes a full path for a resource path
	 * @param resource the resource, must *NOT* have a leading "/"
	 * @return the full path
	 */
	public static final String makePath(String resource) {
		String result = FRUITY_MOD_ASSETS_FOLDER + "/seeker/" + resource;

		if (! hasExtension(resource)) {
			result += ".png";
		}

		return result;
	}

	private static boolean hasExtension(String filename) {
		return filename.lastIndexOf('.') > 0;
	}
}
