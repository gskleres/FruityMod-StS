package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.EtherealizeAction;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.EtherealizePower;

public class Shimmer extends CustomCard {
	public static final String ID = "Shimmer";
	public static final String NAME = "Shimmer";
	public static final String DESCRIPTION = "All cards in hand become Ethereal. Gain [G].";
	public static final String UPGRADE_DESCRIPTION = "All cards in hand become Ethereal. Gain [G][G].";
	private static final int COST = 0;
	private static final int ENERGY_GAIN = 1;
	private static final int UPGRADE_ENERGY_AMT = 1;
	private static final int POOL = 1;

	public Shimmer() {
		super(ID, NAME, FruityMod.makePath(FruityMod.SHIMMER), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
		this.baseMagicNumber = ENERGY_GAIN;
		// required to avoid 1st turn bug
		this.magicNumber = ENERGY_GAIN;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new EtherealizeAction());
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EtherealizePower(p, 1), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Shimmer();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_ENERGY_AMT);
			this.rawDescription = UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}
