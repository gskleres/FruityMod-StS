package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Brainstorm extends CustomCard {
	public static final String ID = "Brainstorm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int COST_UPGRADED = 0;
    private static final int DRAW = 2;
    private static final int DRAW_UPGRADE = 0;
    private static final int DAZED_COUNT = 1;
    private static final int POOL = 1;
    
    public Brainstorm() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.BRAINSTORM), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
    	this.magicNumber = this.baseMagicNumber = DRAW;
    	
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  
    	AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), DAZED_COUNT, true, true));
        AbstractDungeon.actionManager.addToBottom(new ShuffleAction(p.drawPile));
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }    
    
    @Override
    public AbstractCard makeCopy() {
        return new Brainstorm();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADED);
            this.upgradeMagicNumber(DRAW_UPGRADE);
        }
    }
}