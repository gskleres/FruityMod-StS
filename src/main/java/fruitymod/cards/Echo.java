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
import fruitymod.powers.EchoPower;

public class Echo
extends CustomCard {
    public static final String ID = "Echo";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int DOUBLE_AMT = 1;
    private static final int DOUBLE_UPGRADE = 1;

    public Echo() {
        super(ID, NAME, FruityMod.makePath(SeekerMod.ECHO), COST, DESCRIPTION,
        		AbstractCard.CardType.SKILL, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF);
				this.baseMagicNumber = this.magicNumber = DOUBLE_AMT;
        }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EchoPower(p, this.magicNumber), this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Echo();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(DOUBLE_UPGRADE);            
            this.rawDescription = (this.isEthereal ? "Ethereal." : "") + cardStrings.UPGRADE_DESCRIPTION;
			this.initializeDescription();
        }
    }
}


