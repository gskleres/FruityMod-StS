package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class DisruptionField
extends CustomCard {
    public static final String ID = "DisruptionField";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int BLOCK_UPGRADE = 3;
    private static final int DAZED_COUNT = 1;

    public DisruptionField() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.block = this.baseBlock = BLOCK;
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	 AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
         AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), DAZED_COUNT, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new DisruptionField();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);
        }
    }
}
