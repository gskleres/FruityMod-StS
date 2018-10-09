package fruitymod.seeker.cards;

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
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class MeteorShower extends CustomCard {
	public static final String ID = "MeteorShower";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = " NL (Deals !D! damage)";
	private static final int COST = 1;
	private static final int ATTACK_DMG_PER_CARD = 1;

	public MeteorShower() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ALL_ENEMY);
		this.baseDamage = 0;
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY, false)));
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.NONE));
		this.setDescription(false);
	}
	
	@Override
	public void onMoveToDiscard() {
		this.setDescription(false);
	}

	@Override
	public void applyPowers() {
		this.baseDamage = AbstractDungeon.player.drawPile.size() * ATTACK_DMG_PER_CARD;
		super.applyPowers();		
		this.setDescription(true);
	}

	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);		
		this.setDescription(true);
	}
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = (this.isEthereal ? "Ethereal. " : "") + (!this.upgraded ? DESCRIPTION : UPGRADE_DESCRIPTION);
		if(addExtended) {
			this.rawDescription += EXTENDED_DESCRIPTION;
		}
		this.initializeDescription();
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
			this.setDescription(false);
		}
	}
}
