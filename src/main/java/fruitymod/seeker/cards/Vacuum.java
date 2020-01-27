package fruitymod.seeker.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class Vacuum
extends CustomCard {
    public static final String ID = "Vacuum";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 5;
    private static final int UPGRADE_PLUS_ATTACK_DMG = 3;
    private static final int BONUS_DMG_PER_STACK = 3;
    private static final int UPGRADE_PLUS_BONUS_DMG_PER_STACK = 2;

    public Vacuum() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = BONUS_DMG_PER_STACK;
        this.damage = this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weakened"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));

    	if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0f, m.hb.cY - m.hb.height / 4.0f)));
        }

		AbstractDungeon.actionManager.addToBottom(
				new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    	
    	this.rawDescription = (this.isEthereal ? "Ethereal. NL " : "") + DESCRIPTION;
    	initializeDescription();
    }
    
    @Override
    public void applyPowers() {
    	int count = GetAllDebuffCount(AbstractDungeon.player);
    	this.baseDamage = count * this.magicNumber;
    	
    	super.applyPowers();

    	// manual damage calculation - I can't think of a better way to do this
    	this.damage = count * this.magicNumber;
		if (AbstractDungeon.player.hasPower("Strength")) {
			this.damage += AbstractDungeon.player.getPower("Strength").amount;
		}
    	
    	if (this.damage == count * this.magicNumber) {
    		this.isDamageModified = false;
    	}
    	
    	this.rawDescription = (this.isEthereal ? "Ethereal. NL " : "") + DESCRIPTION + 
    			(count > 0 ? EXTENDED_DESCRIPTION[0] : "");
    	initializeDescription();
    }
    
    @Override
    public void calculateCardDamage(AbstractMonster mo) {
    	int count = GetAllDebuffCount(AbstractDungeon.player);
    	super.calculateCardDamage(mo);
    	
    	if (AbstractDungeon.player.hasPower("Weakened")) {
    		// cancel out effect of weak b/c we are going
    		// to remove it before dealing damage
    		this.damage *= 1.34f;
    	}
    	
    	if (this.damage == count * this.magicNumber) {
    		this.isDamageModified = false;
    	}
    }
    
    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        canUse = GetAllDebuffCount(p) > 0;
        this.cantUseMessage = "Must be Weak, Frail, or Vulnerable.";
        return canUse;
    }
    
    private int GetAllDebuffCount(AbstractPlayer p) {
    	return GetDebuffCount(p, "Weakened") + GetDebuffCount(p, "Vulnerable") + GetDebuffCount(p, "Frail");
    }
    
    private int GetDebuffCount(AbstractPlayer p, String powerId) {
    	int debuffCount = 0;    	
    	AbstractPower power = p.getPower(powerId);    	
    	if(power != null) debuffCount = power.amount;
    	return debuffCount;    	
    }    
    
    @Override
    public void onMoveToDiscard() {
    	this.rawDescription = (this.isEthereal ? "Ethereal. NL " : "") + DESCRIPTION;
    	initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new Vacuum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_BONUS_DMG_PER_STACK);
            this.upgradeDamage(UPGRADE_PLUS_ATTACK_DMG);
        }
    }
}



