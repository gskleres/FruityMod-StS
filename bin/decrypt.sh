if [ -f dependency_overrides.properties ]; then
  source dependency_overrides.properties
fi

DESTINATION=`echo $1 | sed -E 's/(.*).gpg/\1/g'`

echo "Decrypting $1, and saving as $DESTINATION"
gpg --batch --passphrase $GPG_PASSPHRASE -d $1 > $DESTINATION
