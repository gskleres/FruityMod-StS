package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Zenith extends CustomCard {
	public static final String ID = "Zenith";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	public static final String EXTENDED_DESCRIPTION = " NL (Will have !M! [R] after use)";
	private static final int COST = 1;
	private static final int POOL = 1;

	public Zenith() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ZENITH), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(EnergyPanel.totalCount - 1));
		if (!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		} else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		initializeDescription();
	}
	
	@Override
	public void applyPowers() {
		this.magicNumber = this.baseMagicNumber = (EnergyPanel.totalCount - 1) * 2;
		super.applyPowers();
		if (!this.upgraded) {
			this.rawDescription = DESCRIPTION;
		} else {
			this.rawDescription = UPGRADE_DESCRIPTION;
		}
		this.rawDescription += EXTENDED_DESCRIPTION;
		initializeDescription();
	}

	@Override
	public AbstractCard makeCopy() {
		return new Zenith();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}

