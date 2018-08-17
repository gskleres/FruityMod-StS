package fruitymod.seeker.patches.com.megacrit.cardcrawl.cards.status.Dazed;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

@SpirePatch(cls="com.megacrit.cardcrawl.cards.status.Dazed", method="use")
public class UseDazed {
	public static ExprEditor Instrument() {
		return new ExprEditor() {
			public void edit(MethodCall m) throws CannotCompileException {
				if (m.getMethodName().equals("addToTop")) {
					// pass the original argument (relicID) + this ($0 which is the AbstractCard)
					m.replace("{ fruitymod.SeekerMod.maybeUseDazed(this); }");
				}
			}
		};
	}	
}
