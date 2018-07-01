package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Dazed_P extends CustomCard {
	public static final String ID = "Dazed_P";
	public static final String NAME = "Dazed";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = -2;

	public Dazed_P() {
		super(ID, NAME, FruityMod.makePath(FruityMod.VOID_RIPPLE), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
				CardRarity.SPECIAL, AbstractCard.CardTarget.ALL_ENEMY);

		this.setBackgroundTexture("img/512/DazedP_BG_small.png", "img/1024/DazedP_BG_large.png");

		this.baseDamage = 0;
		this.baseBlock = 0;
		this.isMultiDamage = true;
		this.isEthereal = true;
	}

	@Override
	public void applyPowers() {
		super.applyPowers();
		updateEnigmaValue();
	}
	
	
	
	private void updateEnigmaValue() {
		AbstractPlayer p = AbstractDungeon.player;
		if (p != null) {
			int enigmaAmount = p.getPower("EnigmaPower").amount;
			if (this.baseBlock != enigmaAmount || this.baseDamage != enigmaAmount)
			{
				this.baseBlock = enigmaAmount;
				this.baseDamage = enigmaAmount;
				this.initializeDescription();
			}
		}
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		this.updateEnigmaValue();
		if (p != null) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
			//AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
			AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShockWaveEffect(p.hb.cX, p.hb.cY, Settings.BLUE_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.50f));
	        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true));
		}
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Dazed_P();
	}

	@Override
	public void upgrade() {
		return;
	}
}

