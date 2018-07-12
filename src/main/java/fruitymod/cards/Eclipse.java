package fruitymod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.unique.EclipseAction;
import fruitymod.patches.AbstractCardEnum;

public class Eclipse extends CustomCard {
	public static final String ID = "Eclipse_S";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADE_COST = 0;

	public Eclipse() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ECLIPSE), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.exhaust = true;
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
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		canUse = false;
		for (AbstractCard c : p.exhaustPile.group) {
			if (c.isEthereal) {
				canUse = true;
			}
		}
		this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
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

