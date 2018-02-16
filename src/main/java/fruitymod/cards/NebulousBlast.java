package fruitymod.cards;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.vfx.combat.LightningEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.common.ShuffleCardsToDrawPileAction;
import fruitymod.patches.AbstractCardEnum;

public class NebulousBlast
extends CustomCard {
    public static final String ID = "NebulousBlast";
    public static final String NAME = "Nebulous Blast";
    public static final String DESCRIPTION = "Deal 17 damage. Shuffle all non-Ethereal cards into your draw pile.";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 17;
    private static final int UPGRADE_PLUS_DMG = 5;
    private static final int POOL = 1;

    public NebulousBlast() {
        super(ID, NAME, FruityMod.makePath(FruityMod.NEBULOUS_BLAST), COST, DESCRIPTION,
        		AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
        		AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
        this.damage=this.baseDamage = ATTACK_DMG;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    	AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.DamageAction(m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn),
				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
    	
        ArrayList<AbstractCard> nonEtherialCards = new ArrayList<AbstractCard>();
        
        for(AbstractCard card : p.hand.group) {
        	if(card.isEthereal) continue;
        	nonEtherialCards.add(card);
        }
        
    	if(nonEtherialCards.size() > 0) {
    		AbstractDungeon.actionManager.addToBottom(new ShuffleCardsToDrawPileAction(p, p, nonEtherialCards, true, true));
    	}
    	
    }
    
    

    @Override
    public AbstractCard makeCopy() {
        return new NebulousBlast();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_DMG);
        }
    }
}

