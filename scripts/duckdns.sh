#!/bin/bash
duckdnsMain() {
    #https://www.duckdns.org/update?domains={YOURVALUE}&token={YOURVALUE}[&ip={YOURVALUE}][&ipv6={YOURVALUE}][&verbose=true][&clear=true]
    [ "$1" = "" ] && DOMAIN="paquita58" || DOMAIN="$1"
    [ "$2" = "" ] && IP=$(realIp) || IP="$2"
    [ "$3" = "" ] && TOKEN="$(tokens $DOMAIN)" || TOKEN="$3"
    TOUT="--connect-timeout 30"
    TOUT=""
    ###eecho wget ${TOUT} -O - "https://www.duckdns.org/update?domains=${DOMAIN}&token=${TOKEN}&ip=${IP}&verbose=true" 2>/dev/null
    curl ${TOUT} "https://www.duckdns.org/update?domains=${DOMAIN}&token=${TOKEN}&ip=${IP}&verbose=true"
}

tokens() {
echo "03302f67-1c64-40be-8554-d35028f5f3af reg6543@gmail.com paquita ubuin
3a115b52-3c62-42ac-93b4-47ed6ea18423 correo6543@gmail.com ceuta6543 ceutaavm12 micasa6543 pruebas8765 raspjavi" \
| grep $1 | awk '{print $1;}'
}

(return 2>/dev/null) || duckdnsMain "$@"
