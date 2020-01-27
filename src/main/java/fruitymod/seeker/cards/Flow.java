package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import fruitymod.SeekerMod;
import fruitymod.seeker.actions.common.PerformXAction;
import fruitymod.seeker.actions.unique.FlowAction;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Flow
        extends CustomCard {
    public static final String ID = "Flow";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = -1;

    public Flow() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.isEthereal = true;
        baseMagicNumber = magicNumber = 1;
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean bruh = false;
        for (AbstractCard c : AbstractDungeon.actionManager.cardsPlayedThisTurn) {
            if (c.isEthereal)
                bruh = true;
        }
        cantUseMessage = "I haven't played an Ethereal card this turn ... yet.";
        return bruh && super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (energyOnUse < EnergyPanel.totalCount) {
            energyOnUse = EnergyPanel.totalCount;
        }
        FlowAction r = new FlowAction(magicNumber);
        addToBot(new PerformXAction(r, p, energyOnUse, freeToPlayOnce));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Flow();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeMagicNumber(1);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}


