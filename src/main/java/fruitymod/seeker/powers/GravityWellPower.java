package fruitymod.seeker.powers;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import fruitymod.SeekerMod;

public class GravityWellPower extends AbstractPower {
    public static final String POWER_ID = "Gravity Well";
    public static final String NAME = "Gravity Well";
    public static final String[] DESCRIPTIONS = new String[]{
            "Receive #b", " less damage from from attacks for each stack of Weak or Vulnerable the enemy has."
    };

    public GravityWellPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = false;
        this.img = new Texture(SeekerMod.makePowerImagePath(POWER_ID));
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        int i = 0;
        if (info.owner instanceof AbstractMonster) {
            for (AbstractPower p : info.owner.powers) {
                if (p instanceof WeakPower || p instanceof VulnerablePower) {
                    i += p.amount;
                }
            }
        }
        return damageAmount - i;// 349
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
