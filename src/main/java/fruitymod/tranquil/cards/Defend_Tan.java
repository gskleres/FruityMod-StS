package fruitymod.tranquil.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.TranquilMod;
import fruitymod.patches.AbstractCardEnum;

public class Defend_Tan extends CustomCard {
	public static final String ID = "Defend_T";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 5;
	private static final int UPGRADE_PLUS_BLOCK = 3;

	public Defend_Tan() {
		super(ID, NAME, TranquilMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.TRANQUIL_TAN, CardRarity.BASIC, CardTarget.SELF);

		this.baseBlock = BLOCK_AMT;
	}

	@Override
	public boolean isDefend() {
		return true;
	}

	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
	}

	public AbstractCard makeCopy() {
		return new Defend_Tan();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeBlock(UPGRADE_PLUS_BLOCK);
		}
	}
}