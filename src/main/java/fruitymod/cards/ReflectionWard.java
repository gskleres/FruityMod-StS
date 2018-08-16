package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.ReflectionWardPower;

public class ReflectionWard extends CustomCard {
	public static final String ID = "ReflectionWard";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final	String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
	private static final int COST = 2;
	private static final int REFLECT_PER_STACK = 3;
	private static final int BLOCK_PER_STACK = 5;
	private static final int BASE_CARDS_PER_STACK = 4;
	private static final int UPGRADED_CARDS_PER_STACK = -1;

	public ReflectionWard() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = BASE_CARDS_PER_STACK;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int count = calculateStacks();
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionWardPower(p, count * REFLECT_PER_STACK), count * REFLECT_PER_STACK));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));

		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	@Override
	public void applyPowers() {
		int count = calculateStacks();
		this.baseBlock = BLOCK_PER_STACK * count;

		super.applyPowers();

		this.rawDescription =  DESCRIPTION + EXTENDED_DESCRIPTION[0] + (REFLECT_PER_STACK * count) +
				EXTENDED_DESCRIPTION[1];
		this.setDescription(true);
		initializeDescription();
	}

	@Override
	public void onMoveToDiscard() {
		this.rawDescription = DESCRIPTION;
		initializeDescription();
	}

	public int calculateStacks() {
		return AbstractDungeon.player.drawPile.size() / this.magicNumber;
	}

	private void setDescription(boolean addExtended) {
		int count = calculateStacks();
		this.rawDescription = (this.isEthereal ? "Ethereal. " : "") + (!this.upgraded? DESCRIPTION : UPGRADE_DESCRIPTION);
		if(addExtended) {
			this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0] + (REFLECT_PER_STACK * count) + cardStrings.EXTENDED_DESCRIPTION[1];
		}
		this.initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new ReflectionWard();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADED_CARDS_PER_STACK);
		}
	}
}

