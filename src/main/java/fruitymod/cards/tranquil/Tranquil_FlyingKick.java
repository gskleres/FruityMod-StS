package fruitymod.cards.tranquil;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Tranquil_FlyingKick extends CustomCard {
    public static final String ID = "Tranquil_FlyingKick";
    public static final String NAME = "Flying Kick";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_DMG = 6;
    private static final int UPGRADE_PLUS_DMG = 3;

    public Tranquil_FlyingKick()
    {
        super(ID, NAME, FruityMod.makePath(FruityMod.TRANQUIL_FLYING_KICK), COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.TRANQUIL_TAN, CardRarity.BASIC, CardTarget.ENEMY);

        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m)
    {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m,
                    new DamageInfo(p, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    @Override
    public AbstractCard makeCopy()
    {
        return new Tranquil_FlyingKick();
    }

    @Override
    public void upgrade()
    {
        if (!this.upgraded)
        {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}
