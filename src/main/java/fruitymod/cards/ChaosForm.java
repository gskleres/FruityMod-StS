package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.ChaosFormPower;

public class ChaosForm extends CustomCard {
	public static final String ID = "ChaosForm";
	public static final String NAME = "Chaos Form";
	public static final String DESCRIPTION = "Whenever you draw an Ethereal card, "
			+ "deal !M! damage to a random enemy.";
	private static final int COST = 3;
	private static final int DMG = 3;
	private static final int UPGRADE_DMG_AMT = 2;
	private static final int POOL = 1;
	
	public ChaosForm() {
		super (ID, NAME, FruityMod.makePath(FruityMod.ASTRAL_FORM), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = DMG;
		this.magicNumber = DMG;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ChaosFormPower(p, this.magicNumber), this.magicNumber));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new ChaosForm();
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_DMG_AMT);
		}
	}
	
}
