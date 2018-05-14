package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class GravityWell extends CustomCard {
	public static final String ID = "GravityWell";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int LOST_STRENGTH = 3;
	private static final int UPGRADE_LOST_STRENGTH_AMT = 1;
	
	public GravityWell() {
		super(ID, NAME, FruityMod.makePath(FruityMod.GRAVITY_WELL), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
				AbstractCard.CardRarity.UNCOMMON
				, AbstractCard.CardTarget.ENEMY);
		this.magicNumber = this.baseMagicNumber = LOST_STRENGTH;
		this.exhaust = true;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		if (m.hasPower("Weakened")) {
			AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(
					m, p, new StrengthPower(m, -1 * this.magicNumber), -1 * this.magicNumber, true));
		}
	}
	
	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		if (m != null && m.hasPower("Weakened")) {
			return true;
		} else {
			this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
			return false;
		}
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new GravityWell();
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_LOST_STRENGTH_AMT);
		}
	}
	
}
