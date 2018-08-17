package fruitymod.seeker.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;
import fruitymod.seeker.powers.NexusPower;

public class Nexus extends CustomCard {
	public static final String ID = "Nexus";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int COST_UPGRADED = 1;
    
    public Nexus() {
    	super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
    			AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
    }
    
    
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new NexusPower(p, 1), 1));
    }    
    
    @Override
    public AbstractCard makeCopy() {
        return new Nexus();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADED);
        }
    }
}
