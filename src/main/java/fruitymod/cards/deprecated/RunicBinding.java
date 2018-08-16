package fruitymod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.deprecated.RunicBindingPower;

public class RunicBinding extends CustomCard {
	public static final String ID = "Wormhole";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int POWER_UP_AMT = 1;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;

	public RunicBinding() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = POWER_UP_AMT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p,
						new RunicBindingPower(p, this.magicNumber, this.upgraded), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new RunicBinding();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
		}
	}

}
