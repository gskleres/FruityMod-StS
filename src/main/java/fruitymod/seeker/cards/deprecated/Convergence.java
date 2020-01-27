package fruitymod.seeker.cards.deprecated;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Convergence extends CustomCard {
    public static final String ID = "Convergence";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int UPGRADED_COST = 1;
    private static final int ARTIFACT_AMT = 1;

    public Convergence() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ALL_ENEMY);
        this.isInnate = true;
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int totalPowerCount = 0;

        for (AbstractMonster mo : AbstractDungeon.getCurrRoom().monsters.monsters) {
            int powerCount = GetPowerCount(mo, "Artifact");
            if (powerCount == 0) continue;
            totalPowerCount += powerCount;
            AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(mo, p, "Artifact"));
        }

        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_AMT), ARTIFACT_AMT));

        if (totalPowerCount > 0) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ArtifactPower(p, totalPowerCount), totalPowerCount));
        }
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    private int GetPowerCount(AbstractCreature c, String powerId) {
        AbstractPower power = c.getPower(powerId);
        return power != null ? power.amount : 0;
    }


    @Override
    public AbstractCard makeCopy() {
        return new Convergence();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}