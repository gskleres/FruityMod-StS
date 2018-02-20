package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class FluxBolt
extends CustomCard {
    public static final String ID = "FluxBolt";
    public static final String NAME = "Flux Bolt";
    public static final String DESCRIPTION = "Deal !D! damage. Shuffle 1 Dazed into your Draw pile.";
    private static final int COST = 1;
    private static final int ATTACK_DMG = 10;
    private static final int UPGRADE_DMG_AMT = 3;
    private static final int POOL = 1;

    public FluxBolt() {
        super(ID, NAME, FruityMod.makePath(FruityMod.FLUX_BOLT), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature)m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));
        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), 1, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new FluxBolt();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_DMG_AMT);
        }
    }
}

