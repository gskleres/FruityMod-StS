package fruitymod.relics;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.SeekerMod;
import fruitymod.actions.common.DiscardWithCallbackAction;
import fruitymod.actions.common.IDiscardCallback;

public class RodOfNegation extends CustomRelic {
	private static final String ID = "RodOfNegation";
	private static final int DISCARD_AMT = 1;
	private static final int ARTIFACT_PER_DISCARD = 1;
	
	
	public RodOfNegation() {
		super(ID, new Texture(SeekerMod.makeRelicImagePath(ID)),
				RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void atBattleStart() {
		AbstractDungeon.actionManager.addToBottom(
				new RelicAboveCreatureAction(AbstractDungeon.player, this));
		this.flash();
		AbstractPlayer p = AbstractDungeon.player;
		AbstractDungeon.actionManager.addToBottom(new DiscardWithCallbackAction(
				p, p, DISCARD_AMT, false, true, false, false, new IDiscardCallback() {
					@Override
					public void processCard(AbstractCard c) {
						AbstractDungeon.actionManager.addToTop(
								new ApplyPowerAction(p, p, new ArtifactPower(p, ARTIFACT_PER_DISCARD), ARTIFACT_PER_DISCARD));
					}
				}));
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new RodOfNegation();
    }

}
