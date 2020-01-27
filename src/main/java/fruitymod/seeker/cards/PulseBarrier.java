package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class PulseBarrier extends CustomCard {
    public static final String ID = "PulseBarrier";
    public static final String NAME = "Pulse Barrier";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 11;
    private static final int UPGRADE_BLOCK_AMT = 4;
    private static final int FRAIL_AMT = 2;

    public PulseBarrier() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF);

        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = FRAIL_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMT, true), FRAIL_AMT));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PulseBarrier();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
        }
    }
}
