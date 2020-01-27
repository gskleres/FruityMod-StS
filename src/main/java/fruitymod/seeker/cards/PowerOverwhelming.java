package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;
import fruitymod.seeker.powers.PowerOverwhelmingPower;

public class PowerOverwhelming extends CustomCard {
    public static final String ID = "PowerOverwhelming";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int STRENGTH = 3;
    private static final int STRENGTH_UPGRADED = 2;

    public PowerOverwhelming() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = STRENGTH;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new StrengthPower(p, this.magicNumber), this.magicNumber));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new PowerOverwhelmingPower(p, 1), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new PowerOverwhelming();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(STRENGTH_UPGRADED);
        }
    }
}
