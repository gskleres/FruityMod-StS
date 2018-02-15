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

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Starfall extends CustomCard {
	private static final String ID = "Starfall";
	private static final String NAME = "Starfall";
	private static final String DESCRIPTION = "Ethereal. Do !D! damage !M! times. Exhaust";
	private static final int COST = 1;
	private static final int ATTACK_DMG = 3;
	private static final int TIMES = 4;
	private static final int UPGRADE_TIMES_AMT = 4;
	private static final int POOL = 1;
	
	public Starfall() {
		super (ID, NAME, FruityMod.makePath(FruityMod.STARFALL), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
		this.baseMagicNumber = UPGRADE_TIMES_AMT;
		this.magicNumber = TIMES;
		this.isEthereal = true;
		this.exhaust = true;
	}
	
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < this.magicNumber; i++) {
        	AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }
	
	@Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
	
	@Override
    public AbstractCard makeCopy() {
        return new Starfall();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_TIMES_AMT);
        }
    }
	
}