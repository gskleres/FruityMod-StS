package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class VoidBarrier extends CustomCard {
	public static final String ID = "VoidBarrier";
    public static final String NAME = "Void Barrier";
    public static final String DESCRIPTION = "Ethereal. Gain !B! Block. Draw !M! cards.";
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final int DRAW = 2;
    private static final int POOL = 1;
    
    public VoidBarrier() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.VOID_BARRIER), COST, DESCRIPTION,
    			AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
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
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        
        if(card == null) return;
        
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
    
	@Override
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}
    
    @Override
    public AbstractCard makeCopy() {
        return new VoidBarrier();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}