package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.PutOnDeckAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.IronWaveEffect;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

import java.util.ArrayList;

public class EtherBarrier
extends CustomCard {
    public static final String ID = "EtherBarrier";
    public static final String NAME = "Ether Barrierd";
    public static final String DESCRIPTION = "Gain !B! Block for every Ethereal card in your hand.";
    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int POOL = 1;

    public EtherBarrier() {
        super(ID, NAME, "images/cards/locked_skill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.block = this.baseBlock = BLOCK;
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	int etherealCount = 0;
    	for (AbstractCard c : p.hand.group) {
            if (!c.isEthereal) continue;
            etherealCount++;
        }    	
    	 AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block * etherealCount));
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        }
        canUse = false;
        for (AbstractCard c : p.hand.group) {
            if (!c.isEthereal) continue;
            canUse = true;
            break;
        }
        this.cantUseMessage = "No Ethereal cards in hand.";
        return canUse;
    }
    
    @Override
    public AbstractCard makeCopy() {
        return new EtherBarrier();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(3);
        }
    }
}
