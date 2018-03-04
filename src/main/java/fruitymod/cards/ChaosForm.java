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
import fruitymod.powers.ChaosFormPower;
import fruitymod.powers.ChaosFormUpgradePower;

public class ChaosForm extends CustomCard {
	public static final String ID = "ChaosForm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 3;
	private static final int POOL = 1;
	
	public ChaosForm() {
		super (ID, NAME, FruityMod.makePath(FruityMod.ASTRAL_FORM), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.magicNumber = this.baseMagicNumber = 1;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (this.upgraded){
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChaosFormUpgradePower(p, this.magicNumber), this.magicNumber));
		}
		else {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChaosFormPower(p, this.magicNumber), this.magicNumber));
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new ChaosForm();
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.rawDescription = UPGRADED_DESCRIPTION;
			this.initializeDescription();
		}
	}
	
}
