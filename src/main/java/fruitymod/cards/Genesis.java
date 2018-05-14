package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Genesis extends CustomCard {
	public static final String ID = "Genesis";
    public static final String NAME = "Genesis";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final int DRAW = 2;
    
    public Genesis() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.VOID_BARRIER), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
    			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
    	this.baseBlock = BLOCK_AMT;
    	this.baseMagicNumber = DRAW;
    	this.magicNumber = DRAW;
    	this.isEthereal = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  
    	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }   
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
    
	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}
    
    @Override
    public AbstractCard makeCopy() {
        return new Genesis();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}