MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/v2.9.1/ModTheSpire.zip

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

set -e

getModTheSpireJar
getDesktopJar
