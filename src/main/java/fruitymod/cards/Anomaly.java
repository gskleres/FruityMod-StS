package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.AnomalyPower;

public class Anomaly extends CustomCard {
	public static final String ID = "Anomaly";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int STACK_BONUS = 1;
	private static final int STACK_UPGRADE = 1;
	
	private static final int POOL = 1;

	public Anomaly() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ANOMALY), COST, DESCRIPTION, AbstractCard.CardType.POWER,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = STACK_BONUS;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AnomalyPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Anomaly();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(STACK_UPGRADE);
			this.rawDescription = (this.isEthereal ? "Ethereal." : "") + cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
