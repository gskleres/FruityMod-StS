MOD_THE_SPIRE_LATEST_RELEASE_URL=https://github.com/kiooeht/ModTheSpire/releases/latest
BASE_MOD_LATEST_RELEASE_URL=https://github.com/daviscook477/BaseMod/releases/latest
DESKTOP_JAR_LOCAL_PATH="$HOME/Library/Application Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/Resources/desktop-1.0.jar"

# Add the lines below to dependency_overrides.properties, to override any of the dependency locations above
#   MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/<SOME VERSION>/ModTheSpire.zip
#   BASE_MOD_JAR_URL=https://github.com/daviscook477/BaseMod/releases/download/<SOME VERSION>/BaseMod.jar
#   DESKTOP_JAR_LOCAL_PATH=<SOME DIFFERENT LOCAL PATH>
if [ -f dependency_overrides.properties ]; then
  source dependency_overrides.properties
fi

getModTheSpireJar() {
  if [ -f ./ModTheSpire.jar ]; then
    echo "ModTheSpire.jar already present - skipping."
    return
  fi

  if [ -z $MOD_THE_SPIRE_ZIP_URL ]; then
    MOD_THE_SPIRE_ZIP_URL=`curl $MOD_THE_SPIRE_LATEST_RELEASE_URL 2> /dev/null | sed -E "s/.*href=\"([^\"]*)\".*/\1\/ModTheSpire.zip/g; s/tag/download/g"`
  fi

  if [ ! -z $MOD_THE_SPIRE_ZIP_URL ]; then
    echo "Downloading ModTheSpire.jar from $MOD_THE_SPIRE_ZIP_URL"
    curl -L $MOD_THE_SPIRE_ZIP_URL > ModTheSpire.zip
    unzip -d . ModTheSpire.zip ModTheSpire.jar
    rm ModTheSpire.zip
  else
    echo "Could not download ModTheSpire.jar - try downloading it manually from $MOD_THE_SPIRE_LATEST_RELEASE_URL"
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
    return
  fi

  if [ -z $BASE_MOD_JAR_URL ]; then
    BASE_MOD_JAR_URL=`curl $BASE_MOD_LATEST_RELEASE_URL 2> /dev/null | sed -E "s/.*href=\"([^\"]*)\".*/\1\/BaseMod.jar/g; s/tag/download/g"`
  fi

  if [ ! -z $BASE_MOD_JAR_URL ]; then
    echo "Downloading BaseMod.jar from $BASE_MOD_JAR_URL"
    curl -L $BASE_MOD_JAR_URL > ./mods/BaseMod.jar
  else
    echo "Could not download BaseMod.jar - try downloading it manually from $BASE_MOD_LATEST_RELEASE_URL"
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
