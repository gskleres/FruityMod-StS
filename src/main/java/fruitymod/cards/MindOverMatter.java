package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class MindOverMatter extends CustomCard {
	public static final String ID = "MindOverMatter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int BLOCK_AMT = 3;
	private static final int UPGRADE_BLOCK_AMT = 2;
	private static final int POOL = 1;

	public MindOverMatter() {
		super(ID, NAME, FruityMod.makePath(FruityMod.MIND_OVER_MATTER), COST, DESCRIPTION, AbstractCard.CardType.SKILL,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);

		this.magicNumber = this.baseMagicNumber = BLOCK_AMT;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		int frailCount = GetPowerCount(p, "Frail");
		int weakCount = GetPowerCount(p, "Weakened");
		int vulnCount = GetPowerCount(p, "Vulnerable");
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
		AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));
		AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, (frailCount + weakCount + vulnCount) * this.magicNumber));
	}
	
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
    	if(GetPowerCount(p, "Frail") > 0 || GetPowerCount(p, "Weakened") > 0 || GetPowerCount(p, "Vulnerable") > 0) {
    		return true;
    	}
    	this.cantUseMessage = cardStrings.EXTENDED_DESCRIPTION[0];
        return false;
    }
    
    private int GetPowerCount(AbstractCreature c, String powerId) {
    	AbstractPower power =  c.getPower(powerId);    	
    	return power != null ? power.amount : 0;
    }
    

	@Override
	public AbstractCard makeCopy() {
		return new MindOverMatter();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADE_BLOCK_AMT);
		}
	}
}
