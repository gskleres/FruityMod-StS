package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.TenacityPower;

public class PeriaptOfTenacity extends CustomCard {
	public static final String ID = "PeriaptOfTenacity";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int POWER_UP_AMT = 1;
	private static final int COST = 2;
	private static final int UPGRADED_COST = 1;
	private static final int POOL = 1;

	public PeriaptOfTenacity() {
		super(ID, NAME, FruityMod.makePath(FruityMod.PERIAPT_OF_TENACITY), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON,
				AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = POWER_UP_AMT;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new ApplyPowerAction(p, p,
						new TenacityPower(p, this.magicNumber, this.upgraded), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new PeriaptOfTenacity();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
		}
	}

}
