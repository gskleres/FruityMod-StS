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
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.EnergizedPower;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Singularity extends CustomCard {
	public static final String ID = "Singularity";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int ATTACK_DMG = 12;
    private static final int UPGRADE_DMG_AMT = 3;
    private static final int BLOCK_AMT = 12;
    private static final int UPGRADE_BLOCK_AMT = 3;
    private static final int ENERGY_GAIN = 1;
    
    public Singularity() {
    	super(ID, NAME, FruityMod.makePath(SeekerMod.SINGULARITY), COST, DESCRIPTION,
    			AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
    			CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY);
    	this.baseDamage = ATTACK_DMG;
    	this.baseBlock = BLOCK_AMT;
    	this.baseMagicNumber = this.magicNumber = ENERGY_GAIN;
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
    	 AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new EnergizedPower(p, this.magicNumber), this.magicNumber));
    }
    
	public void triggerOnEndOfPlayerTurn() {
		AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
	}

    @Override
    public AbstractCard makeCopy() {
        return new Singularity();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
            this.upgradeBlock(UPGRADE_BLOCK_AMT);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
