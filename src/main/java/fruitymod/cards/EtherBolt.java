package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class EtherBolt extends CustomCard {
	public static final String ID = "EtherBolt";
	public static final String NAME = "Ether Bolt";
	public static final String DESCRIPTION = "Can only be played if all other cards in your hand are Ethereal. NL Deal !D! damage.";
	private static final int COST = 0;
	private static final int ATTACK_DMG = 15;
	private static final int UPGRADE_DMG_AMT = 5;
	private static final int POOL = 1;

	public EtherBolt() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ETHER_BOLT), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.COMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
	}

	@Override
	public boolean canUse(AbstractPlayer p, AbstractMonster m) {
		boolean canUse = super.canUse(p, m);
		if (!canUse) {
			return false;
		}
		for (AbstractCard c : p.hand.group) {
			if (c.isEthereal || c.cardID == ID)
				continue;
			canUse = false;
			this.cantUseMessage = "I have cards in my hand that are not Ethereal.";
		}
		return canUse;
	}

	@Override
	public AbstractCard makeCopy() {
		return new EtherBolt();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_DMG_AMT);
		}
	}
}