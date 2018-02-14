package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class UmbralWave extends CustomCard {
	public static final String ID = "UmbralWave";
    public static final String NAME = "Umbral Wave";
    public static final String DESCRIPTION = "Ethereal. Gain !B! Block. Deal !D! damage. Next turn gain !M! Energy";
    private static final int COST = 3;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_DMG_AMT = 3;
    private static final int BLOCK_AMT = 12;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final int ENERGY_GAIN = 1;
    private static final int UPGRADE_ENERGY_AMT = 1;
    private static final int POOL = 1;
    
    public UmbralWave() {
    	super(ID, NAME, FruityMod.makePath(FruityMod.UMBRAL_WAVE), COST, DESCRIPTION,
    			AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE,
    			AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
    	this.baseDamage = ATTACK_DMG;
    	this.baseBlock = BLOCK_AMT;
    	this.baseMagicNumber = ENERGY_GAIN;
    	this.isEthereal = true;
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	 AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
         AbstractDungeon.actionManager.addToBottom(new WaitAction(0.1f));
         if (p != null && m != null) {
             AbstractDungeon.actionManager.addToBottom(new VFXAction(new IronWaveEffect(p.hb.cX, p.hb.cY, m.hb.cX), 0.5f));
         }
         AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
    	 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber), 1));
    }
    
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}

    @Override
    public AbstractCard makeCopy() {
        return new UmbralWave();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
            this.upgradeMagicNumber(UPGRADE_ENERGY_AMT);
        }
    }
}
