#!/bin/bash

killOpenvpn() {
    [ "$1" = "" ] && MAX="10" || MAX="$1"
    [ "$2" = "" ] && SENIAL="-SIGTERM" || SENIAL="$2"
    for((i=1;i<=$MAX;i++)); do
        PID=$(pgrep openvpn)
        [ "$PID" = "" ] && break
        kill $SENIAL $PID
    done
}    
redOpenvpn() {
    REDES=(euro1 euro2 fr1 de233 us1 us2 ca1)
    NUM_REDES=${#REDES[@]}
    DIR_BOOK="/home/usuario/freeVpns/book"
    if [ "$1" = "" ]; then
        fRedActual="/root/redActual.txt"
        [ ! -f $fRedActual ] && echo -1 > $fRedActual
        R=$(( ($(cat $fRedActual) + 1) % $NUM_REDES ))
        echo $R > $fRedActual
    else 
        R="$1"
    fi
    killOpenvpn 10 
    killOpenvpn 10 "-SIGKILL"
    RED=${REDES[$R]}
    [ "$(cat $DIR_BOOK/pass.txt | wc -l)" != "2" ] && /scripts/bookPass.sh
    openvpn --config ${DIR_BOOK}/${RED}/vpnbook-${RED}-udp25000.ovpn &
}

