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
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import basemod.abstracts.CustomCard;
import fruitymod.patches.AbstractCardEnum;

import java.util.ArrayList;

public class EssenceSpike
extends CustomCard {
    public static final String ID = "EssenceSpike";
    public static final String NAME = "Essence Spike";
    public static final String DESCRIPTION = "Gain !B! Block. Gain 2 Frail.";
    private static final int COST = 1;
    private static final int BLOCK = 15;
    private static final int FRAIL_AMT = 2;
    private static final int POOL = 1;

    public EssenceSpike() {
        super(ID, NAME, "images/cards/locked_skill.png", COST, DESCRIPTION, AbstractCard.CardType.SKILL, AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.SELF, POOL);
        this.block = this.baseBlock = BLOCK;
        this.baseMagicNumber = FRAIL_AMT;
        
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	 AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
         AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new FrailPower(p, FRAIL_AMT, true), 1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new EssenceSpike();
    }
    
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBlock(5);
        }
    }
}

