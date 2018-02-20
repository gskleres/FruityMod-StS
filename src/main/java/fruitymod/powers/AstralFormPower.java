package fruitymod.powers;

import com.megacrit.cardcrawl.actions.unique.SwordBoomerangAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

import basemod.BaseMod;
import basemod.interfaces.PostBattleSubscriber;
import basemod.interfaces.PostDrawSubscriber;
import fruitymod.FruityMod;

public class AstralFormPower extends AbstractPower implements PostDrawSubscriber, PostBattleSubscriber {
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
	}
	
	@Override
	public void onInitialApplication() {
		BaseMod.subscribeToPostDraw(this);
		BaseMod.subscribeToPostBattle(this);
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}

	@Override
	public void receivePostDraw(AbstractCard c) {
		AbstractPlayer player = (AbstractPlayer) owner;
		if (c.isEthereal) {
			this.flash();
			AbstractDungeon.actionManager.addToBottom(new SwordBoomerangAction(
					AbstractDungeon.getMonsters().getRandomMonster(true),
					new DamageInfo(player, this.amount), 1));
		}
		
	}

	@Override
	public void receivePostBattle(AbstractRoom arg0) {
		System.out.println("should be removed now!");
		BaseMod.unsubscribeFromPostDraw(this);
		/*
		 *  calling unsubscribeFromPostBattle inside the callback
		 *  for receivePostBattle means that when we're calling it
		 *  there is currently an iterator going over the list
		 *  of subscribers and calling receivePostBattle on each of
		 *  them therefore if we immediately try to remove the this
		 *  callback from the post battle subscriber list it will
		 *  throw a concurrent modification exception in the iterator
		 *  
		 *  for now we just add a delay - yes this is an atrocious solution
		 *  PLEASE someone with a better idea replace it
		 */
		Thread delayed = new Thread(() -> {
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("could not delay unsubscribe to avoid ConcurrentModificationException");
				e.printStackTrace();
			}
			BaseMod.unsubscribeFromPostBattle(this);
		});
		delayed.start();
	}
	
}
