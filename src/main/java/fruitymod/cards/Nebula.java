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
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 1;
	private static final int COST_UPGRADED = 0;
	private static final int BLOCK_AMT = 2;
	private static final int POOL = 1;

	public Nebula() {
		super(ID, NAME, FruityMod.makePath(FruityMod.NEBULA), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);

		this.magicNumber = this.baseMagicNumber = BLOCK_AMT;
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
		this.baseBlock = (AbstractDungeon.player.hand.size() - 1) * this.magicNumber;
		super.applyPowers();
		this.setDescription(true);
	}
	
	@Override
	public void calculateCardDamage(AbstractMonster mo) {
		super.calculateCardDamage(mo);
		this.setDescription(true);
	}
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = DESCRIPTION;
		if (addExtended) {
			this.rawDescription += EXTENDED_DESCRIPTION[0];
		}
		this.initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new Nebula();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(COST_UPGRADED);
		}
	}
}
