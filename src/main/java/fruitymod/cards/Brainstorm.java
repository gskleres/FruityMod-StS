package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Brainstorm extends CustomCard {
	public static final String ID = "Brainstorm";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 0;
    private static final int DRAW = 1;
    private static final int DRAW_UPGRADE = 1;
    private static final int DAZED_COUNT = 1;
    private static final int POOL = 1;

    private boolean triggerOnMoveToDiscard = false;

    public Brainstorm() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.BRAINSTORM), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
    	this.magicNumber = this.baseMagicNumber = DRAW;
    	
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.triggerOnMoveToDiscard = true;
    }

    @Override
    public void onMoveToDiscard(){
        if (this.triggerOnMoveToDiscard) {
            this.triggerOnMoveToDiscard = false;

            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(AbstractDungeon.player, AbstractDungeon.player, new Dazed(), DAZED_COUNT, true, true));
            AbstractDungeon.actionManager.addToBottom(new ShuffleAction(AbstractDungeon.player.drawPile));
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new DrawCardNextTurnPower(AbstractDungeon.player, this.magicNumber), this.magicNumber));
        }
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new Brainstorm();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(DRAW_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
