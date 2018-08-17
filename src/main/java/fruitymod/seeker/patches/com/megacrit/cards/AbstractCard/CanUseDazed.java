package fruitymod.seeker.patches.com.megacrit.cards.AbstractCard;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.AbstractCard", method="canUse")
public class CanUseDazed {
	public static ExprEditor Instrument() {
		return new ExprEditor() {
			public void edit(MethodCall m) throws CannotCompileException {
				if (m.getMethodName().equals("hasRelic")) {
					// pass the original argument (relicID) + this ($0 which is the AbstractCard)
					m.replace("{ $_ = fruitymod.SeekerMod.hasRelicCustom($1, this); }");
				}
			}
		};
	}	
}
