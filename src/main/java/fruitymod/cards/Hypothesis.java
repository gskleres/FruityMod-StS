package fruitymod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.actions.unique.HypothesisAction;
import fruitymod.patches.AbstractCardEnum;

public class Hypothesis
extends CustomCard {
    public static final String ID = "Hypothesis";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int INITIAL_DRAW = 1;
    private static final int BONUS_DRAW = 1;
    private static final int BONUS_DRAW_UPGRADE = 1;
    private static final int POOL = 1;

    public Hypothesis() {
        super(ID, NAME, "images/cards/locked_skill.png", COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.magicNumber = this.baseMagicNumber = BONUS_DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToTop(new HypothesisAction(p,
    			p, this.magicNumber, INITIAL_DRAW));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hypothesis();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(BONUS_DRAW_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
