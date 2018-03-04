package fruitymod.patches.com.megacrit.cardcrawl.powers.HexPower;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;

@SpirePatch(cls="com.megacrit.cardcrawl.powers.HexPower", method="onUseCard")
public class OnUseCard {

	public static void Prefix(Object __obj_instance, Object cardObj, Object actionObj) {
		System.out.println("A card was used so we're going to add a bunch of Dazed");
	}
	
}
