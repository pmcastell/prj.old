#!/bin/bash

#https://www.duckdns.org/update?domains={YOURVALUE}&token={YOURVALUE}[&ip={YOURVALUE}][&ipv6={YOURVALUE}][&verbose=true][&clear=true]
[ "$1" = "" ] && DOMAIN="paquita58" || DOMAIN="$1"
[ "$2" = "" ] && IP=$(realIp) || IP="$2"
[ "$3" = "" ] && TOKEN="03302f67-1c64-40be-8554-d35028f5f3af" || TOKEN="$3"
eecho wget --timeout=20 -O - "https://www.duckdns.org/update?domains=${DOMAIN}&token=${TOKEN}&ip=${IP}&verbose=true" 2>/dev/null
