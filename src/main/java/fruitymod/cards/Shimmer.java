package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.actions.EtherealizeAction;
import fruitymod.patches.AbstractCardEnum;

public class Shimmer
extends CustomCard {
    public static final String ID = "Shimmer";
    public static final String NAME = "Shimmer";
    public static final String DESCRIPTION = "All cards in hand become Ethereal. Gain [G].";
    public static final String UPGRADE_DESCRIPTION = "All cards in hand become Ethereal. Gain [G][G].";
    private static final int COST = 0;
    private static final int POOL = 1;

    public Shimmer() {
        super(ID, NAME, "images/cards/locked_skill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new EtherealizeAction());
    }

    @Override
    public AbstractCard makeCopy() {
        return new Shimmer();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = UPGRADE_DESCRIPTION + (this.isEthereal ? " NL Etherial." : "");
            this.initializeDescription();
        }
    }
}

