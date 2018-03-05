package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Nebula extends CustomCard {
	public static final String ID = "Nebula";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = " (You will gain !B! Block)";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 4;
	private static final int UPGRADE_BLOCK_AMT = 3;
	private static final int POOL = 1;

	public Nebula() {
		super(ID, NAME, FruityMod.makePath(FruityMod.NEBULA), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);

		this.magicNumber = this.baseMagicNumber = BLOCK_AMT;
		this.isEthereal = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		this.setDescription(false);
	}
	
	@Override
	public void onMoveToDiscard() {
		this.setDescription(false);
	}
	
	@Override
	public void applyPowers() {
		this.baseBlock = countEtherealInHand() * this.magicNumber;
		super.applyPowers();
		this.setDescription(true);
	}
	
	public static int countEtherealInHand() {
		int etherealCount = 0;
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (!c.isEthereal)
				continue;
			etherealCount++;
		}
		return etherealCount;
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.setDescription(true);
	}
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (addExtended) {
			this.rawDescription += EXTENDED_DESCRIPTION;
		}
		this.initializeDescription();
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		canUse = false;
		for (AbstractCard c : p.hand.group) {
			if (!c.isEthereal)
				continue;
			canUse = true;
			break;
		}
		this.cantUseMessage = "No Ethereal cards in hand.";
		return canUse;
	}

	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
	}

	@Override
	public AbstractCard makeCopy() {
		return new Nebula();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BLOCK_AMT);
		}
	}
}
