package fruitymod.seeker.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import fruitymod.seeker.AbstractSeekerCard;

public class DarkMatter extends AbstractSeekerCard {
    public static final String ID = "DarkMatter";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    private static final int COST = 1;
    private static final int DAMAGE_AMT = 3;
    private static final int UPGRADE_DAMAGE_AMT = 2;

    public DarkMatter() {
        super(ID, NAME, COST, DESCRIPTION,
                AbstractCard.CardType.ATTACK,
                AbstractCard.CardRarity.UNCOMMON,
                AbstractCard.CardTarget.ENEMY);
        this.magicNumber = this.baseMagicNumber = DAMAGE_AMT;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
        this.setDescription(false);
    }

    @Override
    public void onMoveToDiscard() {
        this.setDescription(false);
    }

    @Override
    public void applyPowers() {
        this.baseDamage = countEtherealInExhaustPile() * this.magicNumber;
        super.applyPowers();
        this.setDescription(true);
    }

    @Override
    public void calculateCardDamage(AbstractMonster mo) {
        super.calculateCardDamage(mo);
        this.setDescription(true);
    }

    private void setDescription(boolean addExtended) {
        this.rawDescription = (this.isEthereal ? "Ethereal. " : "") + DESCRIPTION;
        if (addExtended) {
            this.rawDescription += EXTENDED_DESCRIPTION[0];
        }
        this.initializeDescription();
    }

    @Override
    public AbstractCard makeCopy() {
        return new DarkMatter();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_DAMAGE_AMT);
            this.setDescription(false);
        }
    }
}
