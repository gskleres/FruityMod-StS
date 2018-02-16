
package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Entropy extends CustomCard {
	private static final String ID = "Entropy";
	private static final String NAME = "Entropy";
	private static final String DESCRIPTION = "Ethereal. Deal !D! damage. NL Enemy loses !M! Strength.";
	private static final int COST = 2;
	private static final int COST_UPGRADE = 1;
	private static final int ATTACK_DMG = 5;
	private static final int ATTACK_UPGRADE = 1;
	private static final int STR_LOSS = 1;
	private static final int POOL = 1;
	
	public Entropy() {
		super (ID, NAME, FruityMod.makePath(FruityMod.ENTROPY), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = STR_LOSS;
		this.isEthereal = true;
	}
	
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, p, new StrengthPower(m, - this.magicNumber), - this.magicNumber));
    }
	
	@Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
	
	@Override
    public AbstractCard makeCopy() {
        return new Entropy();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_UPGRADE);
            this.upgradeBaseCost(COST_UPGRADE);
        }
    }
	
}



