package fruitymod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.VigorPower;

public class PeriaptOfVigor extends CustomCard {
	public static final String ID = "PeriaptofVigor";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int ENERGY_REDUCTION = 1;
	private static final int COST = 3;
	private static final int UPGRADED_COST = 2;
	
	public PeriaptOfVigor() {
		super(ID, NAME, FruityMod.makePath(SeekerMod.PERIAPT_OF_VIGOR), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = ENERGY_REDUCTION;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p,
				new VigorPower(p, this.magicNumber), this.magicNumber));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new PeriaptOfVigor();
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADED_COST);
		}
	}
	
}
