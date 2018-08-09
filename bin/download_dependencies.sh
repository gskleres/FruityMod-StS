MOD_THE_SPIRE_ZIP_URL=https://github.com/kiooeht/ModTheSpire/releases/download/v2.9.1/ModTheSpire.zip

getModTheSpireJar() {
  curl -L https://github.com/kiooeht/ModTheSpire/releases/download/v2.9.1/ModTheSpire.zip > ModTheSpire.zip
  unzip -d . ModTheSpire.zip ModTheSpire.jar
  rm ModTheSpire.zip
}

set -x
set -e

getModTheSpireJar
