package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class PulseBarrier extends CustomCard {
	public static final String ID = "PulseBarrier";
	public static final String NAME = "Pulse Barrier";
	public static final String DESCRIPTION = "Gain !B! Block. Gain 2 Frail.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 11;
	private static final int UPGRADE_BLOCK_AMT = 4;
	private static final int FRAIL_AMT = 2;
	private static final int POOL = 1;

	public PulseBarrier() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ESSENCE_SPIKE), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);

		this.baseBlock = BLOCK_AMT;
		this.baseMagicNumber = FRAIL_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMT, true), FRAIL_AMT));
	}

	@Override
	public AbstractCard makeCopy() {
		return new PulseBarrier();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_BLOCK_AMT);
		}
	}
}
