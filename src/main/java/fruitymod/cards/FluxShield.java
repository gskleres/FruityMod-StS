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
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class FluxShield
extends CustomCard {
    public static final String ID = "FluxShield";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK = 9;
    private static final int BLOCK_UPGRADE = 3;
    private static final int DAZED_COUNT = 1;
    private static final int POOL = 1;

    public FluxShield() {
        super(ID, NAME, FruityMod.makePath(FruityMod.FLUX_SHIELD), COST, DESCRIPTION, 
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, 
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.block = this.baseBlock = BLOCK;
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	 AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
         AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), DAZED_COUNT, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FluxShield();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(BLOCK_UPGRADE);
        }
    }
}
