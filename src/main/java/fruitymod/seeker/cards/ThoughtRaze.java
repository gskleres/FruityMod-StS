package fruitymod.seeker.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class ThoughtRaze
        extends CustomCard {
    public static final String ID = "ThoughtRaze";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 7;
    private static final int UPGRADE_PLUS_DMG = 3;

    public ThoughtRaze() {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
                AbstractCard.CardRarity.RARE, AbstractCard.CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.magicNumber = this.baseMagicNumber = 0;
    }

    public static int countEtherealInDrawPile() {
        int etherealCount = 0;
        for (AbstractCard c : AbstractDungeon.player.drawPile.group) {
            if (!c.isEthereal)
                continue;
            etherealCount++;
        }
        return etherealCount;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int count = 0;
        for (AbstractCard c : p.drawPile.group) {
            if (c.isEthereal) {
                AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(c, p.drawPile));
                count++;
            }
        }

        for (int i = 0; i < count; i++) {
            AbstractDungeon.actionManager.addToBottom(
                    new DamageAction((AbstractCreature) m,
                            new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        }
    }

    @Override
    public void applyPowers() {
        this.magicNumber = countEtherealInDrawPile();
        super.applyPowers();
        setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = (this.isEthereal ? "Ethereal. " : "") + DESCRIPTION;
        if (addExtended && this.magicNumber == 1) {
            this.rawDescription += EXTENDED_DESCRIPTION[0] + this.magicNumber + EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[2] + EXTENDED_DESCRIPTION[4];
        } else {
            this.rawDescription += EXTENDED_DESCRIPTION[0] + this.magicNumber + EXTENDED_DESCRIPTION[1] + EXTENDED_DESCRIPTION[3] + EXTENDED_DESCRIPTION[4];
        }
        this.initializeDescription();
    }

    @Override
    public void onMoveToDiscard() {
        this.setDescription(false);
    }

    @Override
    public AbstractCard makeCopy() {
        return new ThoughtRaze();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}


