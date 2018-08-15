package fruitymod.cards;

import fruitymod.SeekerMod;
import fruitymod.actions.unique.IlluminateAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Illuminate extends CustomCard {
	public static final String ID = "Illuminate";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 0;
	private static final int DUPLICATE_AMT = 1;
	private static final int DUPLICATE_UPGRADE = 1;

	public Illuminate() {
		super(ID, NAME, FruityMod.makePath(SeekerMod.ILLUMINATE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);

		this.magicNumber = this.baseMagicNumber = DUPLICATE_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new IlluminateAction(p, this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Illuminate();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(DUPLICATE_UPGRADE);
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
