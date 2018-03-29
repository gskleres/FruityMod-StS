package fruitymod.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

import fruitymod.FruityMod;

public class FlowPower extends AbstractPower {
	public static final String POWER_ID = "Flow";
	public static final String NAME = "Flow";
	public static final String[] DESCRIPTIONS = new String[] {
			"Retain all excess Energy at the end of turn."
	};

	private int energyRetained = 0;
	public FlowPower(AbstractCreature owner, int amount) {
		this.name = NAME;
		this.ID = POWER_ID;
		this.owner = owner;
		this.amount = amount;
		this.description = DESCRIPTIONS[0];
		this.type = AbstractPower.PowerType.BUFF;
		this.isTurnBased = true;
		this.img = FruityMod.getAstralHazePowerTexture(); // Replace with a different image
	}

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
        	energyRetained = EnergyPanel.getCurrentEnergy();
        }
    }
    
    @Override
    public void atStartOfTurn() {
    	if (((AbstractPlayer)this.owner).getRelic("Ice Cream") == null) {
    		AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(energyRetained));
    	}
        --this.amount;
        this.energyRetained=0;
        if(this.amount == 0 && ((AbstractPlayer)this.owner).getRelic("Ice Cream") == null) {
        	this.flash();
        	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "Flow"));
        }        
        
    }
}