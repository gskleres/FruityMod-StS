package fruitymod.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import basemod.abstracts.CustomRelic;
import fruitymod.FruityMod;

public class MechanicalCore extends CustomRelic {
	private static final String ID = "MechanicalCore";
	private static final int ARTIFACT_AMT = 1;
	
	
	public MechanicalCore() {
		super(ID, FruityMod.getMechanicalCoreTexture(),
				RelicTier.COMMON, LandingSound.MAGICAL);
	}
	
	@Override
	public void atBattleStart() {
		flash();
		AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
				new ArtifactPower(AbstractDungeon.player, ARTIFACT_AMT), ARTIFACT_AMT));
        AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    
    @Override
    public AbstractRelic makeCopy() {
        return new MechanicalCore();
    }

}
