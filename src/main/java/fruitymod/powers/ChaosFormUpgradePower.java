package fruitymod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;
import fruitymod.actions.common.MakeTempCardInDrawPileFreeAction;

public class ChaosFormUpgradePower extends AbstractPower {
    public static final String POWER_ID = "ChaosFormUpgradePower";
    public static final String NAME = "Chaos Form+";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn, Recycle ",
			"a random Ethereal card into your draw pile. It costs 0 until played.",
			" random Ethereal cards into your draw pile. They cost 0 until played."
	};

    public ChaosFormUpgradePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
        this.isTurnBased = false;
        this.priority = 90;
        this.img = new Texture(SeekerMod.makePowerImagePath(ChaosFormPower.POWER_ID));
    }

    @Override
    public void atStartOfTurn(){
    	for (int i = 0; i < this.amount; i++) {
    		addRandomFreeEtherealCard();
    	}
    }
    
    private void addRandomFreeEtherealCard() {
        ArrayList<AbstractCard> list = new ArrayList<AbstractCard>();
        for (AbstractCard c : AbstractDungeon.srcCommonCardPool.group) {
            if (c.isEthereal) list.add(c);
        }
        for (AbstractCard c : AbstractDungeon.srcUncommonCardPool.group) {
            if (c.isEthereal) list.add(c);
        }
        for (AbstractCard c : AbstractDungeon.srcRareCardPool.group) {
            if (c.isEthereal) list.add(c);
        }
        AbstractCard card = ((AbstractCard)list.get(AbstractDungeon.cardRandomRng.random(list.size() - 1))).makeCopy();

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileFreeAction(AbstractDungeon.player, AbstractDungeon.player, card, 1, true, true));
    }

    @Override
    public void updateDescription() {
		this.description = DESCRIPTIONS[0] +
				((this.amount == 1) ?
						DESCRIPTIONS[1] :
						(this.amount + DESCRIPTIONS[2]));
    }

}
