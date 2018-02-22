package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Vortex
extends CustomCard {
    public static final String ID = "Vortex";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int POOL = 1;

    public Vortex() {
        super(ID, NAME, FruityMod.makePath(FruityMod.VORTEX), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.exhaust = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {    	
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(m, p, m.currentBlock));
        if(this.upgraded) {
        	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(m, p, "Strength"));
        }
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    	if(m.hasPower("Weakened") || m.hasPower("Vulnerable")) {
			return true;
		}
		this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
		return false;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Vortex();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}


