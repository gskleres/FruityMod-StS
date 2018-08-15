package fruitymod.powers;

import java.util.ArrayList;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class ChaosFormPower extends AbstractPower {
	public static final String POWER_ID = "ChaosFormPower";
	public static final String NAME = "Chaos Form";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the start of your turn, Recycle ",
			"a random Ethereal card into your draw pile.",
			" random Ethereal cards into your draw pile."
	};
	
	public ChaosFormPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = SeekerMod.getChaosFormPowerTexture();
	}

	@Override
	public void atStartOfTurn(){
		for (int i = 0; i < this.amount; i++) {
			addRandomEtherealCard();
		}
	}
	
	private void addRandomEtherealCard() {
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

		AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(card, 1, true, true));
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] +
				((this.amount == 1) ?
						DESCRIPTIONS[1] :
						(this.amount + DESCRIPTIONS[2]));
	}
	
}
