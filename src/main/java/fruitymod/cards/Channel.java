package fruitymod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.common.DiscardWithCallbackAction;
import fruitymod.actions.common.IDiscardCallback;
import fruitymod.patches.AbstractCardEnum;

public class Channel extends CustomCard {
	public static final String ID = "Channel";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 7;
	private static final int UPGRADED_DMG_AMT = 6;
	private static final int DISCARD_AMT = 1;
	private static final int POOL = 1;
	
	public Channel() {
		super(ID, NAME, FruityMod.makePath(FruityMod.CHANNEL), COST, DESCRIPTION,
				AbstractCard.CardType.ATTACK, AbstractCardEnum.PURPLE,
				CardRarity.RARE, AbstractCard.CardTarget.ENEMY, POOL);
		this.baseDamage = ATTACK_DMG;
		this.magicNumber = this.baseMagicNumber = ATTACK_DMG;
	}
	
	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom((new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY)));
		Channel that = this; // funny little naming convention for providing this to inner class
		AbstractDungeon.actionManager.addToBottom(new DiscardWithCallbackAction(
				p, p, DISCARD_AMT, false, false, false, false, new IDiscardCallback() {
					@Override
					public void processCard(AbstractCard c) {
						if (c.isEthereal) {
							AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
									new DamageInfo(p, that.magicNumber, that.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
						}
					}
				}));
	}
	
	@Override
	public AbstractCard makeCopy() {
		return new Channel();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeMagicNumber(UPGRADED_DMG_AMT);
		}
	}
}
