package fruitymod.powers;

import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import basemod.BaseMod;
import basemod.interfaces.PostDrawSubscriber;
import fruitymod.FruityMod;

public class AstralFormPower extends AbstractPower implements PostDrawSubscriber {
	public static final String POWER_ID = "AstralFormPower";
	public static final String NAME = "Astral Form";
	public static final String[] DESCRIPTIONS = new String[] {
			"Whenever you draw an Ethereal card deal ",
			" damage to a random enemy."
	};
	
	public AstralFormPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		updateDescription();
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		this.img = FruityMod.getAstralFormPowerTexture();
		BaseMod.subscribeToPostDraw(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		AbstractPlayer player = (AbstractPlayer) owner;
		if (c.isEthereal) {
			AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(
					AbstractDungeon.getMonsters().getRandomMonster(true),
					new DamageInfo(player, this.amount), 1));
		}
		
	}
	
}
