package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.GravityPower;

public class GravityWell extends CustomCard {
	public static final String ID = "GravityWell";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int LOST_STRENGTH = 1;
	private static final int UPGRADE_LOST_STRENGTH_AMT = 1;
	private static final int POOL = 1;
	
	public GravityWell() {
		super(ID, NAME, FruityMod.makePath(FruityMod.GRAVITY_WELL), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.UNCOMMON
				, AbstractCard.CardTarget.ALL_ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = LOST_STRENGTH;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
			AbstractDungeon.actionManager
					.addToBottom(new ApplyPowerAction(mo, p,
							new GravityPower(mo, this.magicNumber),
							this.magicNumber, true));
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