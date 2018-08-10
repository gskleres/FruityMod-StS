source ./dependency_overrides.properties

echo "Encrypting $1 and saving as $1.gpg"
gpg --symmetric --batch --no-symkey-cache -c --cipher-algo AES256 --passphrase $GPG_PASSPHRASE $1
