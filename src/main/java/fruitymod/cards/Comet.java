package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.WeightyImpactEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Comet
extends CustomCard {
    public static final String ID = "Comet";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 5;
    private static final int UPGRADE_DMG_AMT = 3;
    private static final int ETHERAL_DMG = 1;
    private static final int UPGRADE_ETHEREAL_DMG_AMT = 1;
    private static final int POOL = 1;

    public Comet() {
        super(ID, NAME, FruityMod.makePath(FruityMod.COMET), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = ETHERAL_DMG;
    }
    
    public static int countCards() {
    	int count = 0;
    	for (AbstractCard c : AbstractDungeon.player.hand.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	for (AbstractCard c : AbstractDungeon.player.discardPile.group) {
    		if (c.isEthereal) {
    			count++;
    		}
    	}
    	return count;
    }
    
    @Override
    public float calculateModifiedCardDamage(AbstractPlayer player, AbstractMonster mo, float tmp) {
    	return tmp + this.magicNumber * countCards();
    }
    
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
        	AbstractDungeon.actionManager.addToBottom(new VFXAction(new WeightyImpactEffect(m.hb.cX, m.hb.cY)));
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Comet();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
            this.upgradeMagicNumber(UPGRADE_ETHEREAL_DMG_AMT);
        }
    }
}

