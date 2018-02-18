package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.EclipseAction;
import fruitymod.patches.AbstractCardEnum;

public class Eclipse extends CustomCard {
	public static final String ID = "Eclipse";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int UPGRADE_COST = 1;
	private static final int POOL = 1;

	public Eclipse() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ECLIPSE), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.exhaust = true;
		this.isEthereal = true;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new EclipseAction(m));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Eclipse();
	}
	
    @Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
	
    @Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		canUse = false;
		for (AbstractCard c : p.exhaustPile.group) {
			if (c.isEthereal)
				continue;
			canUse = true;
			break;
		}
		this.cantUseMessage = "No Ethereal cards have been Exhausted.";
		return canUse;
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBaseCost(UPGRADE_COST);
		}
	}
}

