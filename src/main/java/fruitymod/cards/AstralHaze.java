package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
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
import fruitymod.powers.AstralHazePower;

public class AstralHaze
extends CustomCard {
    public static final String ID = "AstralHaze";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 6;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int WEAK_VUL_AMT = 1;
    private static final int UPGRADE_PLUS_WEAK_VUL = 1;
    private static final int POOL = 1;

    public AstralHaze() {
        super(ID, NAME, FruityMod.makePath(FruityMod.ASTRAL_HAZE), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.BASIC, AbstractCard.CardTarget.SELF, POOL);
        this.isEthereal = true;
        this.baseMagicNumber = WEAK_VUL_AMT;
        this.magicNumber = WEAK_VUL_AMT;
        this.baseBlock = BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
   	 	AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new AstralHazePower(p, this.magicNumber), 1));
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public AbstractCard makeCopy() {
        return new AstralHaze();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_PLUS_WEAK_VUL);
        }
    }
}


