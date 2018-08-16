package fruitymod.cards.deprecated;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.deprecated.CoalescencePower;

public class Coalescence extends CustomCard {
	public static final String ID = "Coalescence";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 4;
	private static final int UPGRADED_BLOCK_AMT = 2;
	private static final int STARTING_WEAK_AMT = 1;
	
	public Coalescence() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = BLOCK_AMT;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(
				new ApplyPowerAction(p, p,
						new CoalescencePower(p, STARTING_WEAK_AMT, this.magicNumber), this.magicNumber));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Coalescence();
	}
	
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADED_BLOCK_AMT);
		}
	}
}
