package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Nova extends CustomCard {
    public static final String ID = "Nova";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 3;
    private static final int UPGRADED_COST = 2;
    private static final int ATTACK_DMG = 24;
    private static final int POOL = 1;

    public Nova() {
        super(ID, NAME, FruityMod.makePath(FruityMod.NOVA), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
                CardRarity.UNCOMMON, CardTarget.ALL_ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
        this.isMultiDamage = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        //AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(p, p, new Dazed(), 2, true, true));
    }

    @Override
    public AbstractCard makeCopy() {
        return new Nova();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(UPGRADED_COST);
        }
    }
}
