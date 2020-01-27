
package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Entropy extends CustomCard {
    private static final String ID = "Entropy";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int STR_LOSS = 5;
    private static final int STR_LOSS_UPGRADE = 1;

    public Entropy() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
                AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.isEthereal = true;
        this.magicNumber = this.baseMagicNumber = STR_LOSS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                new com.megacrit.cardcrawl.powers.StrengthPower(m, -1 * this.magicNumber), -1 * this.magicNumber));
        if (!m.hasPower("Artifact")) {
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p,
                    new com.megacrit.cardcrawl.powers.LoseStrengthPower(m, -1 * this.magicNumber), -1 * this.magicNumber));
        }
    }

    @Override
    public void triggerWhenDrawn() {
        this.baseMagicNumber++;
        if (upgraded) {
            baseMagicNumber++;
        }
        this.magicNumber = this.baseMagicNumber;
        superFlash();
    }

    @Override
    public void triggerOnEndOfPlayerTurn() {
        AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Entropy();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STR_LOSS_UPGRADE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
