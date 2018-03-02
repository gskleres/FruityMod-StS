package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Feedback extends CustomCard {
	public static final String ID = "Feedback";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int STACK_DMG = 6;
	private static final int STACK_UPGRADE = 3;
	private static final int POOL = 1;

	public Feedback() {
		super(ID, NAME, FruityMod.makePath(FruityMod.FEEDBACK), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);

		this.magicNumber = this.baseMagicNumber = STACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int debuffCount = GetPowerCount(m, "Weakened") + GetPowerCount(m, "Vulnerable");
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Weakened"));
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Vulnerable"));
		this.baseDamage = this.magicNumber * debuffCount;
		AbstractDungeon.actionManager.addToTop(new LoseHPAction(m, m, this.baseDamage));
	}
	
    private int GetPowerCount(AbstractCreature c, String powerId) {
    	AbstractPower power =  c.getPower(powerId);    	
    	return power != null ? power.amount : 0;
    }

	@Override
	public AbstractCard makeCopy() {
		return new Feedback();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(STACK_UPGRADE);
		}
	}
}
