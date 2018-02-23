package fruitymod.cards;

import java.util.ArrayList;
import java.util.Objects;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.EventHorizonPower;

public class StrokeOfGenius
extends CustomCard {
    public static final String ID = "StrokeOfGenius";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int POOL = 1;

    public StrokeOfGenius() {
        super(ID, NAME, FruityMod.makePath(FruityMod.STROKE_OF_GENIUS), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        this.exhaust = true;
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {        
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.type == AbstractCard.CardType.POWER) list.add(c);
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.type == AbstractCard.CardType.POWER) list.add(c);
        }
        AbstractCard card = ((AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1))).makeCopy();
        if(this.upgraded) {
        	card.costForTurn = 0;
        }
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(card, 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new StrokeOfGenius();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = (this.isEthereal ? "Ethereal." : "") + cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}


