package fruitymod.cards;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;

import basemod.abstracts.CustomCard;
import fruitymod.FruityMod;
import fruitymod.patches.AbstractCardEnum;

public class Starburst extends CustomCard {
	public static final String ID = "Starburst";
	private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
	public static final String NAME = cardStrings.NAME;
	public static final String DESCRIPTION = cardStrings.DESCRIPTION;
	private static final int COST = 2;
	private static final int ATTACK_DMG = 14;
	private static final int UPGRADE_PLUS_DMG = 4;
	private static final int ALL_ATTACK_DMG = 8;
	private static final int UPGRADE_PLUS_ALL_DMG = 2;
	private static final int POOL = 1;

	public Starburst() {
		super(ID, NAME, FruityMod.makePath(FruityMod.ARCANE_BARRAGE), COST, DESCRIPTION, AbstractCard.CardType.ATTACK,
				AbstractCardEnum.PURPLE, AbstractCard.CardRarity.UNCOMMON, AbstractCard.CardTarget.ENEMY, POOL);
		this.magicNumber = this.baseMagicNumber = ALL_ATTACK_DMG;
		this.baseDamage = ATTACK_DMG;
	}

	@Override
	public void use(AbstractPlayer p, AbstractMonster m) {
		AbstractDungeon.actionManager.addToBottom(new VFXAction(new MindblastEffect(p.dialogX, p.dialogY)));
		AbstractDungeon.actionManager.addToBottom(new DamageAction((AbstractCreature) m,
				new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.NONE));

		MonsterRoom room = ((MonsterRoom) AbstractDungeon.currMapNode.room);
		int numMonsters = room.monsters.monsters.size();

		float[] tmp = new float[numMonsters];
		for (int i = 0; i < tmp.length; i++) {
			// don't hit the main target twice
			if (room.monsters.monsters.get(i) == m) {
				tmp[i] = 0.0f;
			} else {
				tmp[i] = this.magicNumber;
			}
		}
		
		this.multiDamage = new int[numMonsters];
		for (int i = 0; i < tmp.length; i++) {
			this.multiDamage[i] = MathUtils.floor(tmp[i]);
		}
		
		AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn,
				AbstractGameAction.AttackEffect.NONE));
	}

	@Override
	public AbstractCard makeCopy() {
		return new Starburst();
	}

	@Override
	public void upgrade() {
		if (!this.upgraded) {
			this.upgradeName();
			this.upgradeDamage(UPGRADE_PLUS_DMG);
			this.upgradeMagicNumber(UPGRADE_PLUS_ALL_DMG);
		}
	}
}
