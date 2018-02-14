package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class EtherBarrier extends CustomCard {
	public static final String ID = "EtherBarrier";
	public static final String NAME = "Ether Barrierd";
	public static final String DESCRIPTION = "Gain !B! Block for every Ethereal card in your hand.";
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_BLOCK_AMT = 3;
	private static final int POOL = 1;

	public EtherBarrier() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ETHER_BARRIER), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);

		this.baseBlock = BLOCK_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int etherealCount = 0;
		for (AbstractCard c : p.hand.group) {
			if (!c.isEthereal)
				continue;
			etherealCount++;
		}
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block * etherealCount));
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		canUse = false;
		for (AbstractCard c : p.hand.group) {
			if (!c.isEthereal)
				continue;
			canUse = true;
			break;
		}
		this.cantUseMessage = "No Ethereal cards in hand.";
		return canUse;
	}

	@Override
	public AbstractCard makeCopy() {
		return new EtherBarrier();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_BLOCK_AMT);
		}
	}
}
