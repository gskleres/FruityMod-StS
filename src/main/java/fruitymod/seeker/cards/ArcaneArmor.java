package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.SeekerMod;
import fruitymod.seeker.actions.common.ModifyBlockAction;
import fruitymod.seeker.patches.AbstractCardEnum;

public class ArcaneArmor extends CustomCard {
    public static final String ID = "ArcaneArmor";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int BLOCK = 9;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int DRAW_INCREASE = 1;
    private static final int UPGRADE_PLUS_DRAW_INCREASE = 1;

    public ArcaneArmor() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);
        this.block = this.baseBlock = BLOCK;
        this.magicNumber = this.baseMagicNumber = DRAW_INCREASE;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public void triggerWhenDrawn() {
        AbstractDungeon.actionManager.addToBottom(new ModifyBlockAction(this, this.magicNumber));
    }


    @Override
    public AbstractCard makeCopy() {
        return new ArcaneArmor();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK);
            this.upgradeMagicNumber(UPGRADE_PLUS_DRAW_INCREASE);
        }
    }
}
