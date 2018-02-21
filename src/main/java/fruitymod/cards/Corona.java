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
import fruitymod.actions.unique.CoronaAction;

public class Corona
extends CustomCard {
    public static final String ID = "Corona";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int COST_UPGRADED = 0;
    private static final int POOL = 1;

    public Corona() {
        super(ID, NAME, FruityMod.makePath(FruityMod.CORONA), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF, POOL);
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {        
        AbstractDungeon.actionManager.addToBottom(new CoronaAction());
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    	for(AbstractCard c: p.hand.group) {
    		if(c.isEthereal) return true;
    	}
    	this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
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