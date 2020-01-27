package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;

import com.megacrit.cardcrawl.powers.WeakPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Overload extends CustomCard {
    public static final String ID = "Overload";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int UPGRADED_COST = 0;
	private static final int ENERGY_GAIN_AND_CARD_DRAW = 3;
	private static final int DEBUFF_AMT = 1;

	public Overload() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
				CardRarity.RARE, AbstractCard.CardTarget.SELF);
		this.magicNumber = this.baseMagicNumber = ENERGY_GAIN_AND_CARD_DRAW;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new VulnerablePower(p, DEBUFF_AMT, true), DEBUFF_AMT));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new WeakPower(p, DEBUFF_AMT, true), DEBUFF_AMT));
		AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, DEBUFF_AMT, true), DEBUFF_AMT));
	}

    @Override
    public AbstractCard makeCopy() {
        return new Overload();
    }
	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			upgradeBaseCost(UPGRADED_COST);
		}
	}
}
