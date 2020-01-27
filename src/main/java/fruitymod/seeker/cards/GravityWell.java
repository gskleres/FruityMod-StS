package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;
import fruitymod.seeker.powers.GravityWellPower;

public class GravityWell extends CustomCard {
    public static final String ID = "GravityWell";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int LOST_STRENGTH = 1;
    private static final int UPGRADE_LOST_STRENGTH_AMT = 1;

    public GravityWell() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.UNCOMMON
                , CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = LOST_STRENGTH;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new GravityWellPower(p, magicNumber), magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new GravityWell();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_LOST_STRENGTH_AMT);
        }
    }

}
