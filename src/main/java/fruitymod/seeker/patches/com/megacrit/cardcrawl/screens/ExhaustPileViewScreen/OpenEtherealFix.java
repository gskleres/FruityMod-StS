package fruitymod.seeker.patches.com.megacrit.cardcrawl.screens.ExhaustPileViewScreen;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch(cls = "com.megacrit.cardcrawl.screens.ExhaustPileViewScreen", method = "open")
public class OpenEtherealFix {
	@SpireInsertPatch(rloc=17, localvars={"c", "toAdd"})
	public static void Insert(Object __obj_instance, AbstractCard c, AbstractCard toAdd) {
		if (!toAdd.isEthereal && c.isEthereal) {
			toAdd.isEthereal = true;
			toAdd.rawDescription = "Ethereal. " + toAdd.rawDescription;
			toAdd.initializeDescription();
		}
	}
}
