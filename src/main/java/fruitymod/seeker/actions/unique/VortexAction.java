package fruitymod.seeker.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class VortexAction extends AbstractGameAction {
	private AbstractPlayer p;
	private int dazedAmt;
	
	
	public VortexAction(AbstractPlayer p, int dazedAmt) {
		this.p = p;
		this.dazedAmt = dazedAmt;
		this.duration = Settings.ACTION_DUR_FAST;
	}
	
	public void update() {
		if (this.duration == Settings.ACTION_DUR_FAST) {
			// exhaust hand
			int count = this.p.hand.size();
			
			for (int i = 0; i < count; i++) {
				AbstractDungeon.actionManager.addToTop(new ExhaustAction(p, p, 1, true, true));
			}
			
			// add dazed
			AbstractDungeon.actionManager.addToTop(
					new MakeTempCardInDrawPileAction(new Dazed(), count * this.dazedAmt, true, true));
		}
		
		tickDuration();
	}
	
}
