package fruitymod.cards;

import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ThrowDaggerEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class EtherBlast extends CustomCard {
	public static final String ID = "EtherBlast";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = " NL (Deals !D! damage)";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int ATTACK_UPGRADE = 3;
	private static final int POOL = 1;

	public EtherBlast() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ETHER_BLAST), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToTop(
				new DamageAction(m,
						new DamageInfo(p, this.damage, this.damageTypeForTurn), true));
		AbstractDungeon.actionManager.addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
		this.rawDescription = (this.isEthereal ? "Ethereal." : "") + DESCRIPTION;
		initializeDescription();
	}
	
	@Override
	public void applyPowers() {
		int count  = 0;
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c.isEthereal) {
				count++;
			}
		}
		this.baseDamage = count * this.magicNumber;
		super.applyPowers();
		this.rawDescription = (this.isEthereal ? "Ethereal." : "") + DESCRIPTION + EXTENDED_DESCRIPTION;
		initializeDescription();
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.rawDescription = (this.isEthereal ? "Ethereal." : "") + DESCRIPTION + EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new EtherBlast();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(ATTACK_UPGRADE);
		}
	}
}