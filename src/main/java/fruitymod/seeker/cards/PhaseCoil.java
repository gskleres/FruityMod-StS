package fruitymod.seeker.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ModifyDamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.SeekerMod;
import fruitymod.seeker.patches.AbstractCardEnum;

public class PhaseCoil extends CustomCard {
	public static final String ID = "PhaseCoil";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 10;
	private static final int ATTACK_DMG_UPGRADE = 4;
	private static final int DMG_BONUS_WHEN_DRAWN = 2;
	private static final int UPGRADE_PLUS_DMG_BONUS_WHEN_DRAWN = 1;

	public PhaseCoil() {
		super(ID, NAME, SeekerMod.makeCardImagePath(ID), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.SEEKER_PURPLE,
				AbstractCard.CardRarity.COMMON,
				AbstractCard.CardTarget.ENEMY);

		this.damage = this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = DMG_BONUS_WHEN_DRAWN;
	}
	
	@Override
	public void triggerWhenDrawn(){
        super.triggerWhenDrawn();
		AbstractDungeon.actionManager.addToBottom(new ModifyDamageAction(this.uuid, this.magicNumber));
    }
    
	public void use(com.megacrit.cardcrawl.characters.AbstractPlayer p, AbstractMonster m) {
			AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
	}

	public AbstractCard makeCopy() {
		return new PhaseCoil();
	}

	public void upgrade() {
		if (!this.upgraded) {
			upgradeName();
			upgradeDamage(ATTACK_DMG_UPGRADE);
			this.upgradeMagicNumber(UPGRADE_PLUS_DMG_BONUS_WHEN_DRAWN);
		}
	}
}