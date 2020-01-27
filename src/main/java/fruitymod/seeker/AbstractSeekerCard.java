package fruitymod.seeker;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public abstract class AbstractSeekerCard extends CustomCard {

    public AbstractSeekerCard(String ID, String NAME, int COST, String DESCRIPTION,
                              CardType TYPE, CardRarity RARITY, CardTarget TARGET)
    {
        super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
                TYPE, AbstractCardEnum.SEEKER_PURPLE, RARITY, TARGET);
    }

    public static int countEtherealInExhaustPile() {
        int exhaustedEtherealCount = 0;
        for (AbstractCard c : AbstractDungeon.player.exhaustPile.group) {
            if (!c.isEthereal)
                continue;
            exhaustedEtherealCount++;
        }
        return exhaustedEtherealCount;
    }
}
