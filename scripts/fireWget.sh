#!/bin/bash
uso() {
   cat<<EOF
   Uso: $0 <url> [<agent>] 
   Si no se especifica <agent> será: Mozilla/5.0 (X11; U; Linux x86_64; es-MX; rv:1.9.2.17) Gecko/20110422 Ubuntu/10.10 (maverick) Firefox/3.6.17
EOF
   exit 1
}      
([ "$2" = "--help" ] || [ "$2" = "-h" ] ) && uso
AGENT="Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:53.0) Gecko/20100101 Firefox/53.0"
AGENT="Mozilla/5.0 (Windows 15; Windows x86_64; rv:58.0) Gecko/20100101 Firefox/58.0"
[ "$2" ] && AGENT="$2"
wget -U "$AGENT" -O - "$1"  2>/dev/null
