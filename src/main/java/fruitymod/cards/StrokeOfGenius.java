package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class StrokeOfGenius
extends CustomCard {
    public static final String ID = "StrokeOfGenius";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;

    public StrokeOfGenius() {
        super(ID, NAME, FruityMod.makePath(FruityMod.STROKE_OF_GENIUS), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.exhaust = true;
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {        
        AbstractCard c = AbstractDungeon.returnTrulyRandomCard(CardType.POWER, AbstractDungeon.cardRandomRng).makeCopy();
        if (this.upgraded){
            c.setCostForTurn(0);
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrokeOfGenius();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = (this.isEthereal ? "Ethereal." : "") + cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}


