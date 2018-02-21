package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class MeteorShower extends CustomCard {
	public static final String ID = "MeteorShower";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = " NL (Deals !D! damage)";
	private static final int COST = 1;
	private static final int ATTACK_DMG_PER_CARD = 1;
	private static final int POOL = 1;

	public MeteorShower() {
		super(ID, NAME, FruityMod.makePath(FruityMod.METEOR_SHOWER), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.baseDamage = 0;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY)));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		if (!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		} else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		initializeDescription();
	}

	@Override
	public void applyPowers() {
		this.baseDamage = AbstractDungeon.player.drawPile.size() * ATTACK_DMG_PER_CARD;
		super.applyPowers();
		if (!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		} else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		this.rawDescription += EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		if (!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		} else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		this.rawDescription += EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new MeteorShower();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.isInnate = true;
			this.rawDescription = UPGRADE_DESCRIPTION;
			initializeDescription();
		}
	}
}
