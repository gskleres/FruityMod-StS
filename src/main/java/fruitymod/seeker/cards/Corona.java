package fruitymod.seeker.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DrawCardNextTurnPower;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.seeker.actions.common.ModifyMagicNumberAction;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Corona
extends CustomCard {
    public static final String ID = "Corona";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String DESCRIPTION_PLURAL = cardStrings.EXTENDED_DESCRIPTION[0];
    private static final int COST = 2;
    private static final int COST_UPGRADED = 1;
    private static final int BASE_CARD_DRAW = 1;
    private static final int CARD_DRAW_INCREASE_PER_DRAW = 1;

    public Corona() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
        this.magicNumber = this.baseMagicNumber = BASE_CARD_DRAW;
        updateDescription(this.magicNumber);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new DrawCardNextTurnPower(p, this.magicNumber), this.magicNumber));
    }
    
	@Override
	public void triggerWhenDrawn(){
		AbstractDungeon.actionManager.addToTop(new ModifyMagicNumberAction(this, CARD_DRAW_INCREASE_PER_DRAW));
		updateDescription(this.magicNumber + CARD_DRAW_INCREASE_PER_DRAW);
	}
	
	public void updateDescription(int cardDrawAmount) {
   		this.rawDescription = (cardDrawAmount == 1) ? DESCRIPTION : DESCRIPTION_PLURAL;
   		this.initializeDescription();
	}
	
    @Override
    public AbstractCard makeCopy() {
        return new Corona();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADED);
        }
    }
}