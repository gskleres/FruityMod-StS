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
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int ATTACK_UPGRADE = 3;
	private static final int POOL = 1;

	public EtherBlast() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ETHER_BLAST), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.isEthereal = true;
		this.magicNumber = this.baseMagicNumber = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
			AbstractDungeon.actionManager.addToTop(
					new DamageAction(m,
							new DamageInfo(p, this.damage, this.damageTypeForTurn), true));
			AbstractDungeon.actionManager.addToTop(new VFXAction(new ThrowDaggerEffect(m.hb.cX, m.hb.cY)));
		
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}
	
	public static int countEtherealCardsInHand() {
		int count  = 0;
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c.isEthereal) {
				count++;
			}
		}
		return count;
	}
	
	@Override
	public void applyPowers() {
		int count = countEtherealCardsInHand();
		this.baseDamage = count * this.magicNumber;
		
		super.applyPowers();

		this.rawDescription =  DESCRIPTION + 
				(EXTENDED_DESCRIPTION[0] + count);
		if (count == 1) {
			this.rawDescription += EXTENDED_DESCRIPTION[1];
		} else {
			this.rawDescription += EXTENDED_DESCRIPTION[2];
		}
		initializeDescription();
	}
	
	@Override
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
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