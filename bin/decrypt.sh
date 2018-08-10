source ./dependency_overrides.properties

DESTINATION=`echo $1 | sed -E 's/(.*).gpg/\1/g'`

echo "Decrypting $1, and saving as $DESTINATION"
gpg --batch --passphrase $GPG_PASSPHRASE -d $1 > $DESTINATION
