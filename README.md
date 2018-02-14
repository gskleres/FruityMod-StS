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

## Installation ##
1. Copy `BaseMod.jar` to your **ModTheSpire** `mods` directory.
2. Make a directory `fruity_mod_assets` in your `mods` directory.
3. Copy the contents of `images` into `fruity_mod_assets`.

## Progress ##
Cards are up to date through FluxBolt (alphabetical order); i.e. from FluxShield onward things aren't up to date yet. Exceptions are that  VoidBolt, VoidBarrier, UmbralWave, Shimmer, Power Spike, and VoidRipple **ARE** up to date.

Still need to add Surge and Starfall for common cards
Most uncommons have to be added and all rares

Until rares are added we crash on elite fights, shops, or boss fights