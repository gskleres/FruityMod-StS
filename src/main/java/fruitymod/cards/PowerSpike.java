package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class PowerSpike extends CustomCard {
	public static final String ID = "PowerSpike";
	public static final String NAME = "Power Spike";
	public static final String DESCRIPTION = "Gain [G]. NL Gain 2 Frail.";
	private static final int COST = 0;
	private static final int ENERGY_GAIN = 1;
	private static final int UPGRADE_ENERGY_AMT = 1;
	private static final int POOL = 1;
	private static final int FRAIL_AMT = 2;

	public PowerSpike() {
		super(ID, NAME, FruityMod.makePath(FruityMod.POWER_SPIKE), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = ENERGY_GAIN;
		this.magicNumber = ENERGY_GAIN;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMT, true), FRAIL_AMT));
	}

	@Override
	public AbstractCard makeCopy() {
		return new PowerSpike();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_ENERGY_AMT);
			this.rawDescription = "Gain [G][G]. NL Gain 2 Frail." + (this.isEthereal ? " NL Etherial." : "");
		}
	}
}