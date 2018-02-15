# FruityMod-StS
Fruity Mod for Slay The Spire

## Requirements ##
#### General Use ####
* BaseMod v.1.5.2+ (https://github.com/t-larson/BaseMod/releases)
* ModTheSpire v2.2.1+ (https://github.com/kiooeht/ModTheSpire/releases)

#### Development ####
* Java 8
* Maven
* CFR 124 (run this with Java 8, doesn't work well with 9)
* BaseMod v1.5.2+
* ModTheSpire v2.2.1+
* ModTheSpireLib (whatever version is associated with your version of ModTheSpire)

## Building ##
1. Modify `pom.xml` to point to the location you've placed the dependencies
2. Copy `desktop-1.0.jar` from your Slay the Spire folder into `../_lib` relative to the repo.
3. Decompile `desktop-1.0.jar` with `java -jar "cfr_0_124.jar" --comments false --showversion false --caseinsensitivefs true --outputdir "decompiled" --jarfilter com.megacrit.cardcrawl.* "desktop-1.0.jar"`
4. Run `mvn package` to make the jar `FruityMod.jar` in the `targets` directory

### Building in Eclipse ###
1. Right click on the project in eclipse then go to `configure` and `convert to maven project`
2. Then to build the project use `Run as` and select `Maven build` and specify `package` as the `Goal` for the build
3. If you get an error about lacking a compiler change the default `jre` for Eclipse to point to a `jdk` instead. The Eclipse Maven plugin is weird like that.

## Installation ##
1. Copy `BaseMod.jar` to your **ModTheSpire** `mods` directory.
2. Make a directory `fruity_mod_assets` in your `mods` directory.
3. Copy the contents of `images` into `fruity_mod_assets`.

## Progress ##
Cards are up to date through FluxBolt (alphabetical order); i.e. from FluxShield onward things aren't up to date yet. Exceptions are that  VoidBolt, VoidBarrier, UmbralWave, Shimmer, Power Spike, and VoidRipple **ARE** up to date.

Most uncommons have to be added and all rares

Until rares are added we crash on elite fights, shops, or boss fights

## Some notes about adding custom cards ##
1. With cards, you need to set `this.baseDamage` and `this.baseBlock` when assigning damage values or block values to a card. This is because the game will compute `this.damage` and `this.block` from those values before doing any damage or block actions. **HOWEVER** when setting magic numbers on cards you must set `this.baseMagicNumber` **AND** `this.magicNumber` otherwise the first time the card is used its magic number will be wrong because the game **does not** compute `this.magicNumber` before it is used (it defaults to -1).
2. Try to keep the damage values, block values, etc... to `static final` constants at the top of the file so it's easier to make edits to the cards for balancing.
3. If you have an `ethereal` card it needs to override the `triggerOnEndOfPlayerTurn` hook and add in code that looks like this:
```java
public void triggerOnEndOfPlayerTurn() {
	AbstractDungeon.actionManager.addToTop(new ExhaustSpecificCardAction(this, AbstractDungeon.player.hand));
}
```
