package fruitymod.patches;

import java.lang.reflect.Field;
import java.util.Iterator;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.PandorasBox;

@SpirePatch(cls="com.megacrit.cardcrawl.relics.PandorasBox", method="onEquip")
public class PandorasBoxPatch {
	@SpireInsertPatch(rloc=12)
	public static void Insert(Object __obj_instance) {
		Field count;
		try {
			PandorasBox box = (PandorasBox) __obj_instance;
			count = box.getClass().getDeclaredField("count");
			count.setAccessible(true);
			
			for (Iterator<AbstractCard> i = AbstractDungeon.player.masterDeck.group.iterator(); i.hasNext();) {
				AbstractCard e = (AbstractCard) i.next();
				if (e.cardID.equals("Strike_P") || e.cardID.equals("Defend_P")) {
					i.remove();
					count.set(box, ((Integer) count.get(box)) + 1);
				}
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}