package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.CreativityPower;

public class Creativity extends CustomCard {
	public static final String ID = "Creativity";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int COST_UPGRADED = 0;
    private static final int DRAW = 1;
    
    public Creativity() {
    	super(ID, NAME, FruityMod.makePath(SeekerMod.CREATIVITY), COST, DESCRIPTION,
    			AbstractCard.CardType.POWER, AbstractCardEnum.SEEKER_PURPLE,
    			CardRarity.UNCOMMON, AbstractCard.CardTarget.SELF);
    	this.magicNumber = this.baseMagicNumber = DRAW;
    }
    
    
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {  
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new CreativityPower(p, this.magicNumber), this.magicNumber));
    }    
    
    @Override
    public AbstractCard makeCopy() {
        return new Creativity();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(COST_UPGRADED);
        }
    }
}
