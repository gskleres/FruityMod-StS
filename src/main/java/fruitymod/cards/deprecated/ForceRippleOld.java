package fruitymod.cards.deprecated;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.common.ShuffleCardsToDrawPileAction;
import fruitymod.patches.AbstractCardEnum;

public class ForceRippleOld
extends CustomCard {
    public static final String ID = "ForceRipple";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_DMG = 17;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int POOL = 1;

    public ForceRippleOld() {
        super(ID, NAME, FruityMod.makePath(FruityMod.FORCE_RIPPLE), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    	
        ArrayList<AbstractCard> nonEtherialCards = new ArrayList<AbstractCard>();
        
        for(AbstractCard card : p.hand.group) {
        	if(card.isEthereal || card == this) continue;
        	nonEtherialCards.add(card);
        }
        
    	if(nonEtherialCards.size() > 0) {
    		AbstractDungeon.actionManager.addToBottom(new ShuffleCardsToDrawPileAction(p, p, nonEtherialCards, true, true));
    	}
    	
    }
    

    @Override
    public AbstractCard makeCopy() {
        return new ForceRippleOld();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}

