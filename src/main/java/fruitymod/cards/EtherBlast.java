package fruitymod.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.actions.unique.EtherBlastAction;
import fruitymod.patches.AbstractCardEnum;

// TODO: Flechettes adds additional information in play, shows how many attacks will be made, look into it to see if it should be added here.
public class EtherBlast extends CustomCard {
	public static final String ID = "EtherBlast";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 1;
	private static final int ATTACK_DMG = 5;
	private static final int ATTACK_UPGRADE = 3;
	private static final int POOL = 1;

	public EtherBlast() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ETHER_BLAST), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.damage = this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager
				.addToBottom(new EtherBlastAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
	}

	@Override
	public AbstractCard makeCopy() {
		return new EtherBlast();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(ATTACK_UPGRADE);
		}
	}
}