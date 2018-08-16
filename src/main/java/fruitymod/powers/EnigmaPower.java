package fruitymod.powers;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;
import fruitymod.cards.Dazed_P;

public class EnigmaPower extends AbstractPower {
	public static final String POWER_ID = "EnigmaPower";
	public static final String NAME = "Enigma";
	public static final String[] DESCRIPTIONS = new String[] {
			"Dazed can now be played to gain ",
			" Block and deal ",
			" damage to ALL enemies."
	};
	
	public EnigmaPower(AbstractCreature owner, int amount) {
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
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
	}
	
	@Override
	public void onInitialApplication() {
		replaceDazedWithDazed_P();
	}
	
	@Override
	public void onDrawOrDiscard() {
		replaceDazedWithDazed_P();
	}
	
	@Override
	public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
		replaceDazedWithDazed_P();
	}
	
	private void replaceDazedWithDazed_P() {
		ArrayList<AbstractCard> temp = new ArrayList<AbstractCard>();
		for (AbstractCard c : AbstractDungeon.player.hand.group) {
			if (c instanceof Dazed) {
				temp.add(c);
			}
		}
		while (temp.size() > 0)
		{
			AbstractCard c = temp.remove(0);
			AbstractDungeon.player.hand.removeCard(c);
			AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction((AbstractCard)new Dazed_P(), 1));
		}
	}
	
}
