package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class DeflectionWard extends CustomCard {
	public static final String ID = "DeflectionWard";
	public static final String NAME = "Deflection Ward";
	public static final String DESCRIPTION = "Gain !B! Block.";
	private static final int COST = 2;
	private static final int BLOCK = 13;
	private static final int UPGRADE_PLUS_BLOCK = 5;
	private static final int POOL = 1;

	public DeflectionWard() {
		super(ID, NAME, FruityMod.makePath(FruityMod.DEFLECTION_WARD), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
		this.block = this.baseBlock = BLOCK;

	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	@Override
	public AbstractCard makeCopy() {
		return new DeflectionWard();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}
