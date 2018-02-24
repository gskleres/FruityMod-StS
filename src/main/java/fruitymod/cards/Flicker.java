package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.FlickerPower;

public class Flicker extends CustomCard {
	public static final String ID = "Flicker";
	public static final String NAME = "Flicker";
	public static final String DESCRIPTION = "Frail, Weak, and Vulnerable have the opposite effect on you for 1 turn.";
	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;
	private static final int POOL = 1;

	public Flicker() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ESSENCE_MIRROR), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FlickerPower(p, 1), 1));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Flicker();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_COST);
		}
	}
}
