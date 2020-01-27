package fruitymod.tranquil.cards;

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
import fruitymod.TranquilMod;
import fruitymod.tranquil.patches.AbstractCardEnum;

public class FlurryOfBlows extends CustomCard {
    public static final String ID = "FlurryOfBlows";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 0;
    private static final int ATTACK_DMG = 1;
    private static final int TIMES = 6;
    private static final String IMAGE = TranquilMod.makeCardImagePath(ID);

    public FlurryOfBlows() {
        super(ID, NAME, IMAGE, COST, DESCRIPTION, CardType.ATTACK,
                AbstractCardEnum.TRANQUIL_TAN, CardRarity.RARE,
                CardTarget.ENEMY);

        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = TIMES;
        this.exhaust = true;
    }

    @Override
    public boolean isStrike() {
        return true;
    }

    public void use(AbstractPlayer player, AbstractMonster monster) {
        for (int i = 0; i < this.magicNumber; i++) {
            AbstractDungeon.actionManager.addToBottom(new DamageAction(monster,
                    new DamageInfo(player, this.damage, this.damageTypeForTurn),
                    AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        }
    }

    public AbstractCard makeCopy() {
        return new FlurryOfBlows();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.exhaust = false;
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}