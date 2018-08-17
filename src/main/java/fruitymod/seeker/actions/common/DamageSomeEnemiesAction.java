package fruitymod.seeker.actions.common;

import java.util.ArrayList;
import java.util.function.Predicate;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

// This is a copy of DamageAllEnemiesAction with a parameter to skip certain monsters. Couldn't think of a way to avoid duplicating the code.
public class DamageSomeEnemiesAction
extends AbstractGameAction {
	public static boolean[] CheckTargets(Predicate<AbstractMonster> condition) {
		ArrayList<AbstractMonster> monsters = AbstractDungeon.getCurrRoom().monsters.monsters;
		boolean[] targets = new boolean[monsters.size()];
		for (int i = 0, l=targets.length; i < l; ++i) {
			targets[i] = condition.test(monsters.get(i));
		}
		return targets;
		
	}
	
    public int[] damage;
    public boolean[] targets;
    public boolean targetsAll = false;
    private boolean firstFrame = true;

    public DamageSomeEnemiesAction(AbstractCreature source, boolean[] targets, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect, boolean isFast) {
        this.setValues(null, source, amount[0]);
        this.damage = amount;
        this.targets = targets;
        this.actionType = AbstractGameAction.ActionType.DAMAGE;
        this.damageType = type;
        this.attackEffect = effect;
        this.duration = isFast ? Settings.ACTION_DUR_XFAST : Settings.ACTION_DUR_FAST;
        
    	int targetCount = 0;
    	for (boolean target : targets) {
    		 if(target) ++targetCount;       	
    	}
    	this.targetsAll = targetCount == targets.length;
    }

    public DamageSomeEnemiesAction(AbstractCreature source, boolean[] targets, int[] amount, DamageInfo.DamageType type, AbstractGameAction.AttackEffect effect) {
        this(source, targets, amount, type, effect, false);
    }

    @Override
    public void update() {
        if (this.firstFrame) {
            boolean playedMusic = false;
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for (int i = 0; i < temp; ++i) {
            	if(!targets[i]) continue;
                if (AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).isDying || AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).currentHealth <= 0 || AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).isEscaping) continue;
                if (playedMusic) {
                    AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).hb.cY, this.attackEffect, true));
                    continue;
                }
                playedMusic = true;
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).hb.cX, AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).hb.cY, this.attackEffect));
            }
            this.firstFrame = false;
        }
        this.tickDuration();
        if (this.isDone) {        			
        	if(this.targetsAll) {
                for (AbstractPower p : AbstractDungeon.player.powers) {
                    p.onDamageAllEnemies(this.damage);
                }
        	}
            int temp = AbstractDungeon.getCurrRoom().monsters.monsters.size();
            for (int i = 0; i < temp; ++i) {
            	if(!targets[i]) continue;
                if (AbstractDungeon.getCurrRoom().monsters.monsters.get(i).isDeadOrEscaped()) continue;
                if (this.attackEffect == AbstractGameAction.AttackEffect.POISON) {
                    AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).tint.color = Color.CHARTREUSE.cpy();
                    AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).tint.changeColor(Color.WHITE.cpy());
                } else if (this.attackEffect == AbstractGameAction.AttackEffect.FIRE) {
                    AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).tint.color = Color.RED.cpy();
                    AbstractDungeon.getCurrRoom().monsters.monsters.get((int)i).tint.changeColor(Color.WHITE.cpy());
                }
                AbstractDungeon.getCurrRoom().monsters.monsters.get(i).damage(new DamageInfo(this.source, this.damage[i], this.damageType));
            }
            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
            AbstractDungeon.actionManager.addToTop(new WaitAction(0.1f));
        }
    }
}