package fruitymod.cards;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

public class PowerSpike
extends CustomCard {
    public static final String ID = "PowerSpike";
    public static final String NAME = "Power Spike";
    public static final String DESCRIPTION = "Gain [G]. NL Gain 2 Frail.";
    private static final int COST = 0;
    private static final int POOL = 1;
    private static final int FRAIL_AMT = 2;

    public PowerSpike() {
        super(ID, NAME,"images/cards/locked_skill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = 1;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMT, true), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PowerSpike();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.rawDescription = "Gain [G][G]. NL Gain 2 Frail." + (this.isEthereal ? " NL Etherial." : "");
        }
    }
}

