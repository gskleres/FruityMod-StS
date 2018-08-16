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

public class Blockpocalypse extends CustomCard {
	public static final String ID = "Blockpocalypse";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 0;
	private static final int BLOCK_AMT = 1;
	private static final int TIMES = 5;
	private static final String IMAGE = TranquilMod.makeCardImagePath(ID);

	public Blockpocalypse() {
		super(ID, NAME, IMAGE, COST, DESCRIPTION, CardType.SKILL,
				AbstractCardEnum.TRANQUIL_TAN, CardRarity.RARE,
				CardTarget.SELF);

		this.baseBlock = BLOCK_AMT;
		this.magicNumber = this.baseMagicNumber = TIMES;
		this.exhaust = true;
	}

	@Override
	public boolean isDefend() {
		return true;
	}

	public void use(AbstractPlayer player, AbstractMonster monster) {
		for (int i = 0; i < magicNumber; i++) {
			AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, this.baseBlock));
		}
	}

	public AbstractCard makeCopy() {
		return new Blockpocalypse();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			this.exhaust = false;
			this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
		}
	}
}