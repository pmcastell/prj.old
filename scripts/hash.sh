#!/bin/bash
uso() {
   echo uso: $0 '<salt> [<pass>]'
   exit 1
}
#mkpasswd se encuentra en el paquete whois
#mkpasswd  -m sha-512 -S salt pass
#[ "$1" = "" ] && uso
[ "$1" != "" ] && SALT="$1"
[ "$2" = "" ] && read -s -p "Introduce la contraseña:" PASS || PASS="$2"
echo -ne "\n"
python -c "import crypt,getpass;print(crypt.crypt('"$PASS"','\$6\$"$SALT"'));"
mkp $PASS $SALT
[ "$SALT" != "" ] && SALT="-S $SALT"
mkpasswd  -m sha-512 $SALT $PASS
exit 0

#!/bin/bash
read -p "Enter username: " username
read -s -p "Enter password: " mypassword
echo
echo -n $username:$mypassword | chpasswd -S -c SHA512
