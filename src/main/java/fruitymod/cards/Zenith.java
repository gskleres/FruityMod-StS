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
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Zenith extends CustomCard {
	public static final String ID = "Zenith";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;

	public Zenith() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(EnergyPanel.totalCount - this.costForTurn));
		this.setDescription(false);
	}
	
	@Override
	public void applyPowers() {
		this.magicNumber = this.baseMagicNumber = (EnergyPanel.totalCount - this.costForTurn) * 2;
		super.applyPowers();
		this.setDescription(true);
	}
	
	private void setDescription(boolean addExtended) {
		this.rawDescription = (this.isEthereal ? "Ethereal. " : "") + (!this.upgraded? DESCRIPTION : UPGRADE_DESCRIPTION);
		if(addExtended) {
			this.rawDescription += cardStrings.EXTENDED_DESCRIPTION[0];
		}
		this.initializeDescription();
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
			this.setDescription(false);
		}
	}
}

