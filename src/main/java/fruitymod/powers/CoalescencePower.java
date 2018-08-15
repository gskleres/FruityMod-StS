package fruitymod.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

public class CoalescencePower extends AbstractPower {
	public static final String POWER_ID = "CoalescencePower";
	public static final String NAME = "Coalescence";
	public static final String[] DESCRIPTIONS = new String[] {
			"At the end of your turn, gain ",
			" Block for each stack of Frail, Weak, or Vulnerable that you have."
	};
	
	public CoalescencePower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = false;
		this.priority = 90;
		updateDescription();
		this.img = SeekerMod.getCoalescencePowerTexture();
	}
	
	@Override
	public void updateDescription() {
		this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
	}
	
    @Override
    public void atEndOfTurn(boolean isPlayer) {
		int frailCount = GetPowerCount(this.owner, "Frail");
		int weakCount = GetPowerCount(this.owner, "Weakened");
		int vulnCount = GetPowerCount(this.owner, "Vulnerable");
        this.flash();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(this.owner, this.owner, this.amount * (frailCount + weakCount + vulnCount)));
    }
    
    private int GetPowerCount(AbstractCreature c, String powerId) {
    	AbstractPower power =  c.getPower(powerId);    	
    	return power != null ? power.amount : 0;
    }
}
