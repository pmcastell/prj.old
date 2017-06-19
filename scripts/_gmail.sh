#!/bin/bash
. /scripts/uso.sh
. /scripts/debug.sh

DEBUG=true
[ "$1" = "" ] && uso "Uso: $0 <usuario>"
USERNAME="$1"
FICHERO_LOCAL_CLAVES="/m/Mios/Personal/Privado/AgendasClaves/genClavesIndicePass.sh"
[ -f $FICHERO_LOCAL_CLAVES ] && PASSWORD="$(cat $FICHERO_LOCAL_CLAVES | grep $USERNAME | awk '{print $2;}')" 
[ "$PASSWORD" = "" ] && [ -f $FICHERO_LOCAL_CLAVES ] && PASSWORD="$(cat $FICHERO_LOCAL_CLAVES | grep mail | awk '{print $2;}')" 
[ "$PASSWORD" = "" ] && PASSWORD="$(/scripts/getMyPass.sh $USERNAME)"
[ "$PASSWORD" = "" ] && PASSWORD="$(/scripts/getMyPass.sh mail)"
debug "USERNAME: ${USERNAME}  PASSWORD: $PASSWORD"
debug "curl -u $USERNAME:$PASSWORD --silent \"https://mail.google.com/mail/feed/atom\" "

curl -u $USERNAME:$PASSWORD --silent "https://mail.google.com/mail/feed/atom" | xmllint --format - | sed -e 's/xmlns=".*" //g'  | xmllint --xpath "//entry//*[self::title or self::email]" - | /scripts/unescapeHtml.py | sed -e 's/<\/email>/\n/g' | sed -e 's/<\/title><email>/ | REMITENTE: |  /g' | sed -e 's/<title>/ | ASUNTO: | /g'

exit 0

--xpath "//table[@id='vg_hosts_table_id']/tr[contains(td,'$1')]/td//span[contains(.,'session')]" --format - 2>/dev/null | sed -e 's/<\/span>/\n/g' | sed -e 's/<span .*>//g' |
/scripts/vpngate.sh~:URLS=$(cat $TEMP | xmllint -- --xpath "//table[@id='vg_hosts_table_id']/t
