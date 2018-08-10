MOD_THE_SPIRE_LATEST_RELEASE_URL=https://github.com/kiooeht/ModTheSpire/releases/latest
BASE_MOD_LATEST_RELEASE_URL=https://github.com/daviscook477/BaseMod/releases/latest
DESKTOP_JAR_LOCAL_PATH="$HOME/Library/Application Support/Steam/steamapps/common/SlayTheSpire/SlayTheSpire.app/Contents/Resources/desktop-1.0.jar"

# Add the lines below to dependency_overrides.properties, to override any of the dependency locations above
#   MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/<SOME VERSION>/ModTheSpire.zip
#   BASE_MOD_JAR_URL=https://github.com/daviscook477/BaseMod/releases/download/<SOME VERSION>/BaseMod.jar
#   DESKTOP_JAR_LOCAL_PATH=<SOME DIFFERENT LOCAL PATH>
#
# For a CI environment where desktop-1.0.jar is not installed
#   Create a passphrase to encrypt your jar, and save as GPG_PASSPHRASE
#   Use ./bin/encrypt.sh to encrypt your copy of the jar using your GPG_PASSPHRASE.
#   Create an application in DropBox, and provided that application with a BearerToken - save that token in DROPBOX_BEARER_TOKEN
#   Upload your encrypted jar in your DropBox Application's folder.  Save the path in DROPBOX_ENCRYPTED_DESKTOP_JAR_PATH
#   Test this on your local machine by clearning out DESKTOP_JAR_LOCAL_PATH (ie: DESKTOP_JAR_LOCAL_PATH='')
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
  elif [ ! -z $DROPBOX_ENCRYPTED_DESKTOP_JAR_PATH ]; then
    echo "Found DropBox URL to encrypted desktop-1.0.jar - downloading"
    getDesktopJarFromDropBox
  else
    echo "Unable to find a copy of desktop-1.0.jar. Is the game installed on your machine?"
  fi
}

getDesktopJarFromDropBox() {
  curl -X POST https://content.dropboxapi.com/2/files/download \
    --header "Authorization: Bearer ${DROPBOX_BEARER_TOKEN}" \
    --header "Dropbox-API-Arg: {\"path\":\"$DROPBOX_ENCRYPTED_DESKTOP_JAR_PATH\"}" > desktop-1.0.jar.gpg
  ./bin/decrypt.sh desktop-1.0.jar.gpg
  rm desktop-1.0.jar.gpg
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

getModTheSpireJar
getDesktopJar
makeModDirectory
getBaseModJar
getFruityModJar
