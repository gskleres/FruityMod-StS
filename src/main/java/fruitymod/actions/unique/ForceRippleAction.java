package fruitymod.actions.unique;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;

public class ForceRippleAction extends AbstractGameAction {
	public int damage;
	private boolean freeToPlayOnce = false;
	private DamageInfo.DamageType damageType;
	private AbstractPlayer p;
	private AbstractMonster m;
	private int energyOnUse = -1;
	
	public ForceRippleAction(AbstractPlayer p, AbstractMonster m, int damage, DamageInfo.DamageType damageType, 
			boolean freeToPlayOnce, int energyOnUse) {
		this.damage = damage;
		this.damageType = damageType;
		this.p = p;
		this.m = m;
		this.freeToPlayOnce = freeToPlayOnce;
		if (freeToPlayOnce) {
			System.out.println("FREE TO PLAY");
		}
		this.duration = Settings.ACTION_DUR_XFAST;
		this.actionType = AbstractGameAction.ActionType.SPECIAL;
		this.energyOnUse = energyOnUse;
	}
	
	@Override
	public void update() {
		int effect = EnergyPanel.totalCount;
		if (this.energyOnUse != -1) {
			effect = this.energyOnUse;
		}
		if (this.p.hasRelic("Chemical X")) {
			effect += 2;
			this.p.getRelic("Chemical X").flash();
		}
		if (effect > 0) {
			for (int i = 0; i < effect; i++) {
                AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY"));
                AbstractDungeon.actionManager.addToBottom(new VFXAction(this.p, new CleaveEffect(), 0.0f));
            	AbstractDungeon.actionManager.addToBottom(new DamageAction(this.m,
        				new DamageInfo(p, this.damage, this.damageType),
        				AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
            	AbstractDungeon.actionManager.addToBottom(
            			new ApplyPowerAction(p, p, new VulnerablePower(p, this.energyOnUse, false), this.energyOnUse, true, AbstractGameAction.AttackEffect.NONE));
            	AbstractDungeon.actionManager.addToBottom(
            			new ApplyPowerAction(p, p, new WeakPower(p, this.energyOnUse, false), this.energyOnUse, true, AbstractGameAction.AttackEffect.NONE));
            	AbstractDungeon.actionManager.addToBottom(
            			new ApplyPowerAction(p, p, new FrailPower(p, this.energyOnUse, false), this.energyOnUse, true, AbstractGameAction.AttackEffect.NONE));
			}
			if (!this.freeToPlayOnce) {
				this.p.energy.use(EnergyPanel.totalCount);
			}
		}
		this.isDone = true;
	}
	
}
