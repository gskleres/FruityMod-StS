package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Nova extends CustomCard {
    public static final String ID = "Nova";
    public static final String NAME = "Nova";
    public static final String DESCRIPTION = "Deal !D! damage to ALL enemies. NL Shuffle 2 Dazed into your draw pile.";
    private static final int COST = 3;
    private static final int ATTACK_DMG = 20;
    private static final int UPGRADE_DMG_AMT = 10;
    private static final int POOL = 1;

    public Nova() {
        super(ID, NAME, FruityMod.makePath(FruityMod.NOVA), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
                AbstractCard.CardRarity.RARE, CardTarget.ALL_ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(m, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), 2, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Nova();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
        }
    }
}
