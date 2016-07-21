# Generate local cert and keystore
keytool -genkey -keystore keystore -keyalg rsa -dname "CN=ECMO Project, OU=Telehealth Research Institute, O=John A. Burns School of Medicine, L=Honolulu, ST=HI,C=US" -alias kalias -validity 3650 -keypass kpassword -storepass kpassword

# Generate developer cert and keystore renew every 2y
keytool -genkey -keystore %keystore%.jks -keyalg EC -dname "CN=ECMOjo Developer, OU=Telehealth Research Institute, O=John A. Burns School of Medicine, L=Honolulu, ST=HI,C=US" -alias ecmojo_dev -validity 730

# Export self-signed certificate for import into trust store
keytool -export -alias ecmojo_dev -sigalg EC -file ecmojo_app.cer -keystore %keystore%.jks
