MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/v2.9.1/ModTheSpire.zip
BASE_MOD_JAR_URL=https://github.com/daviscook477/BaseMod/releases/download/v2.18.0/BaseMod.jar

getModTheSpireJar() {
  if [ -f ./ModTheSpire.jar ]; then
    echo "ModTheSpire.jar found - skipping."
  else
    echo "Downloading ModTheSpire.jar"
    curl -L $MOD_THE_SPIRE_ZIP_URL > ModTheSpire.zip
    unzip -d . ModTheSpire.zip ModTheSpire.jar
    rm ModTheSpire.zip
  fi
}

getDesktopJar() {
  if [ -f ./desktop-1.0.jar ]; then
    echo "desktop-1.0.jar found - skipping."
  else
    echo "UNIMPLEMENTED!!!: Download desktop-1.0.jar"
  fi
}

makeModDirectory() {
  mkdir -p mods
}

getBaseModJar() {
  if [ -f ./mods/BaseMod.jar ]; then
    echo "./mods/BaseMod.jar found - skipping."
  else
    echo "Downloading BaseMod.jar"
    curl -L $BASE_MOD_JAR_URL > ./mods/BaseMod.jar
  fi
}

set -e

getModTheSpireJar
getDesktopJar
makeModDirectory
getBaseModJar
