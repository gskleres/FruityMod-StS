
package fruitymod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.ExhaustAllEtherealAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

import fruitymod.SeekerMod;

// This is an invisible power than triggers ethereal cards should be exhausted at the end of the turn
public class EtherealizePower extends AbstractPower {
    public static final String POWER_ID = "EtherealizePower";
    @SuppressWarnings("unused")
	private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings("Weakened");
    public static final String NAME = "Etherealize Power";
    @SuppressWarnings("unused")
	private boolean justApplied = false;

    public EtherealizePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        //this.description = DESCRIPTIONS[0];
        //this.loadRegion("anger");
        this.type = AbstractPower.PowerType.BUFF;
        this.priority = 90;
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
    	if(isPlayer) {
    		AbstractDungeon.actionManager.addToTop(new ExhaustAllEtherealAction());
    	}
    	AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner, this.owner, "EtherealizePower"));
    }
    @Override
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
    }
    @Override
    public void renderAmount(SpriteBatch sb, float x, float y, Color c) {        
    }
}