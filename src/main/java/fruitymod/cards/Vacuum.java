package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

import java.util.ArrayList;

public class Vacuum
extends CustomCard {
    public static final String ID = "Vacuum";
    public static final String NAME = "Vacuum";
    public static final String DESCRIPTION = "Remove all Weak, Frail, and Vulnerable. Deal !M! extra damage for each stack removed.";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 0;
    private static final int ATTACK_UPGRADE = 0;
    private static final int STACK_BONUS = 4;
    private static final int STACK_BONUS_UPGRADE = 3;
    private static final int POOL = 1;

    public Vacuum() {
        super(ID, NAME, FruityMod.makePath(FruityMod.VACUUM), COST, DESCRIPTION, 
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE, 
        		AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
        this.damage=this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = STACK_BONUS;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	
    	int debuffCount = GetAllDebuffCount(p);
    	

        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Weak"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Frail"));
        AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p, p, "Vulnerable"));

    	if (m != null) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0f, m.hb.cY - m.hb.height / 4.0f)));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage + (this.magicNumber*debuffCount), this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        
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
    public AbstractCard makeCopy() {
        return new Vacuum();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_UPGRADE);
            this.upgradeMagicNumber(STACK_BONUS_UPGRADE);
        }
    }
}



