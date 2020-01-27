package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class MindOverMatter extends CustomCard {
    public static final String ID = "MindOverMatter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int BLOCK_AMT = 8;
    private static final int UPGRADE_PLUS_BLOCK_AMT = 3;
    private static final int BONUS_BLOCK_AMT = 3;
    private static final int BONUS_UPGRADE_PLUS_BLOCK_AMT = 2;

    public MindOverMatter() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        this.baseBlock = BLOCK_AMT;
        this.magicNumber = this.baseMagicNumber = BONUS_BLOCK_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int frailCount = GetPowerCount(p, "Frail");
        int weakCount = GetPowerCount(p, "Weakened");
        int vulnCount = GetPowerCount(p, "Vulnerable");
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, ((frailCount + weakCount + vulnCount) * this.magicNumber) + BLOCK_AMT));
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power = c.getPower(powerId);
        return power != null ? power.amount : 0;
    }


    @Override
    public AbstractCard makeCopy() {
        return new MindOverMatter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(UPGRADE_PLUS_BLOCK_AMT);
            this.upgradeMagicNumber(BONUS_UPGRADE_PLUS_BLOCK_AMT);
        }
    }
}
