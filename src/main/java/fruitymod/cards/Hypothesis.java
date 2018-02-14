package fruitymod.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
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

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

public class Hypothesis
extends CustomCard {
    public static final String ID = "Hypothesis";
    public static final String NAME = "Hypothesis";
    public static final String DESCRIPTION = "Draw a card. If it is Ethereal, draw another card.";
    public static final String UPGRADE_DESCRIPTION = "Draw a card. If it is Ethereal, draw !M! more cards.";
    private static final int COST = 0;
    private static final int INITIAL_DRAW = 1;
    private static final int BONUS_DRAW = 1;
    private static final int BONUS_DRAW_UPGRADE = 1;
    private static final int POOL = 1;

    public Hypothesis() {
        super(ID, NAME, "images/cards/locked_skill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.baseMagicNumber = BONUS_DRAW;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {        
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
        
        if(card == null) return;
        
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, card.isEthereal ? this.magicNumber + INITIAL_DRAW : INITIAL_DRAW));
        
    }

    @Override
    public AbstractCard makeCopy() {
        return new Hypothesis();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(BONUS_DRAW_UPGRADE);
            this.rawDescription = UPGRADE_DESCRIPTION + (this.isEthereal ? " NL Etherial." : "");
            this.initializeDescription();
        }
    }
}
