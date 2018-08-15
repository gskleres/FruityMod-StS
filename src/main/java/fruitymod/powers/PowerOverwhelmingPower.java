package fruitymod.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class PowerOverwhelmingPower extends AbstractPower {
	public static final String POWER_ID = "PowerOverwhelmingPower";
	public static final String NAME = "Power Overwhelming";
	public static final String DESCRIPTION = "Whenever you play an attack, Recycle a Dazed into your draw pile.";

	
	public PowerOverwhelmingPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		updateDescription();
		this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
	}

	@Override
	public void onUseCard(AbstractCard card, UseCardAction action){
		if(card.type == AbstractCard.CardType.ATTACK){
			AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new Dazed(), this.amount, true, true));
		}
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTION;
	}
}
