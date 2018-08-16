package fruitymod.cards;


import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

import fruitymod.SeekerMod;
import fruitymod.patches.AbstractCardEnum;

public class Umbra extends CustomCard {
    public static final String ID = "Umbra";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 4;
    private static final int ATTACK_UPGRADE = 2;
    private static final int BLOCK = 3;
    private static final int BLOCK_UPGRADE = 2;

    public Umbra() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.SEEKER_PURPLE, AbstractCard.CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.damage = this.baseDamage = ATTACK_DMG;
        this.block = this.baseBlock = BLOCK;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (p.hasPower("Frail") || p.hasPower("Vulnerable") || p.hasPower("Weakened")) {
            AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
            AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
        }
        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
        AbstractDungeon.actionManager.addToBottom(new VFXAction(p, new CleaveEffect(), 0.1f));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Umbra();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(ATTACK_UPGRADE);
            this.upgradeBlock(BLOCK_UPGRADE);
        }
    }
}