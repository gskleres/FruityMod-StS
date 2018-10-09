package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import basemod.helpers.ModalChoice;
import basemod.helpers.ModalChoiceBuilder;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

import java.util.List;

public class StrokeOfGenius extends CustomCard implements ModalChoice.Callback
{
    public static final String ID = "StrokeOfGenius";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int UPGRADED_COST = 0;
    private ModalChoice modal;

    public StrokeOfGenius()
    {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE, CardRarity.UNCOMMON, CardTarget.NONE);

        modal = new ModalChoiceBuilder()
                .setCallback(this) // Sets callback of all the below options to this
                .setType(CardType.ATTACK)
                .setColor(AbstractCardEnum.SEEKER_PURPLE)
                .addOption("Attack","Add a random Attack to your hand. NL It costs 0 this turn.", CardTarget.NONE)
                .setType(CardType.SKILL)
                .setColor(AbstractCardEnum.SEEKER_PURPLE)
                .addOption("Skill","Add a random Skill to your hand. NL It costs 0 this turn.", CardTarget.NONE)
                .setType(CardType.POWER)
                .setColor(AbstractCardEnum.SEEKER_PURPLE)
                .addOption("Power","Add a random Power to your hand. NL It costs 0 this turn.", CardTarget.NONE)
                .create();

        this.exhaust = true;
    }

    // Uses the titles and descriptions of the option cards as tooltips for this card
    @Override
    public List<TooltipInfo> getCustomTooltips()
    {
        return modal.generateTooltips();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        modal.open();
    }

    // This is called when one of the option cards is chosen
    @Override
    public void optionSelected(AbstractPlayer p, AbstractMonster m, int i)
    {
        CardType type;
        switch (i) {
            case 0:
                type = CardType.ATTACK;
                break;
            case 1:
                type = CardType.SKILL;
                break;
            case 2:
                type = CardType.POWER;
                break;
            default:
                return;
        }

        AbstractCard c;
        if (type == CardType.ATTACK) {
            c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        }
        else if (type == CardType.SKILL) {
            c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.SKILL).makeCopy();
        }
        else {
            c = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.POWER).makeCopy();
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(c, true));
        c.setCostForTurn(0);
    }

    @Override
    public void upgrade()
    {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADED_COST);
        }
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new StrokeOfGenius();
    }
}