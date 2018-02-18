package fruitymod.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;
import fruitymod.powers.ReflectionWardPower;

public class ReflectionWard extends CustomCard {
	public static final String ID = "ReflectionWard";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int DAMAGE = 4;
	private static final int DAMAGE_UPGRADE = 2;
	private static final int BLOCK = 8;
	private static final int BLOCK_UPGRADE = 3;
	private static final int POOL = 1;

	public ReflectionWard() {
		super(ID, NAME, FruityMod.makePath(FruityMod.REFLECTION_WARD), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.SELF, POOL);
		this.baseBlock = BLOCK;
		this.magicNumber = this.baseMagicNumber = DAMAGE;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ReflectionWardPower(p, this.magicNumber), this.magicNumber));
	}

	@Override
	public AbstractCard makeCopy() {
		return new ReflectionWard();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeBlock(BLOCK_UPGRADE);
			this.upgradeMagicNumber(DAMAGE_UPGRADE);			
		}
	}
}

