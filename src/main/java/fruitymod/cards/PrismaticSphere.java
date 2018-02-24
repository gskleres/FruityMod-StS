	package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
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

public class PrismaticSphere extends CustomCard {
	public static final String ID = "PrismaticSphere";
    public static final String NAME = "Prismatic Sphere";
    public static final String DESCRIPTION = "Ethereal. Deal !D! damage. NL Draw !M! cards.";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_DMG_AMT = 4;
    private static final int DRAW = 2;
    private static final int POOL = 1;
    
    public PrismaticSphere() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.VOID_BOLT), COST, DESCRIPTION,
    			AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
    	this.baseDamage = ATTACK_DMG;
    	this.baseMagicNumber = DRAW;
    	this.magicNumber = DRAW;
    	this.isEthereal = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        if (AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractDungeon.actionManager.addToBottom(new EmptyDeckShuffleAction());
        }
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, this.magicNumber));
    }
    
    @Override
    public void triggerOnEndOfPlayerTurn() {
    	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new PrismaticSphere();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
        }
    }
}
