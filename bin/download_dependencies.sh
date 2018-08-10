MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/v2.9.1/ModTheSpire.zip
BASE_MOD_JAR_URL=https://github.com/daviscook477/BaseMod/releases/download/v2.18.0/BaseMod.jar
DESKTOP_JAR_LOCAL_PATH="$HOME/Library/Application Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/Resources/desktop-1.0.jar"

if [ -f dependency_overrides.properties ]; then
  source dependency_overrides.properties
fi

getModTheSpireJar() {
  if [ -f ./ModTheSpire.jar ]; then
    echo "ModTheSpire.jar already present - skipping."
  else
    echo "Downloading ModTheSpire.jar"
    curl -L $MOD_THE_SPIRE_ZIP_URL > ModTheSpire.zip
    unzip -d . ModTheSpire.zip ModTheSpire.jar
    rm ModTheSpire.zip
  fi
}

getDesktopJar() {
  if [ -f ./desktop-1.0.jar ]; then
    echo "desktop-1.0.jar already present - skipping."
  elif [ -f "$DESKTOP_JAR_LOCAL_PATH" ]; then
    echo "Found desktop-1.0.jar in $DESKTOP_JAR_LOCAL_PATH - making a copy"
    cp "$DESKTOP_JAR_LOCAL_PATH" ./
  else
    echo "UNIMPLEMENTED!!!: Download desktop-1.0.jar"
  fi
}

makeModDirectory() {
  mkdir -p mods
}

getBaseModJar() {
  if [ -f ./mods/BaseMod.jar ]; then
    echo "./mods/BaseMod.jar already present - skipping."
  else
    echo "Downloading BaseMod.jar"
    curl -L $BASE_MOD_JAR_URL > ./mods/BaseMod.jar
  fi
}

getFruityModJar() {
  if [ -f ./mods/FruityMod.jar ]; then
    echo "./mods/FruityMod.jar already present - skipping."
  else
    echo "Building FruityMod.jar"
    mvn package
  fi
}

set -e

getModTheSpireJar
getDesktopJar
makeModDirectory
getBaseModJar
getFruityModJar
