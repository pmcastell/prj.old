#!/bin/bash
export PATH=/usr/local/sbin:/usr/local/bin:/usr/sbin:/usr/bin:/sbin:/bin:/scripts:/scripts/aula
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
    #REDES=(euro1 euro2 fr1 de233 us1 us2 ca1)
    REDES=(euro1 euro2 fr1 de233 us1 us2)
    REDES=(ca222 de4 fr1 pl226 us1 us2)

    NUM_REDES=${#REDES[@]}
    DIR_BOOK="/home/usuario/freeVpns/book"
    if [ "$1" = "" ]; then
        fRedActual="/root/redActual.txt"
        [ ! -f $fRedActual ] && echo -1 > $fRedActual
        R=$(( ($(cat $fRedActual) + 1) % $NUM_REDES ))
        echo $R > $fRedActual
    else 
        R=$(( $1 % $NUM_REDES ))
    fi
    killOpenvpn 10 
    killOpenvpn 10 "-SIGKILL"
    RED=${REDES[$R]}
    [ "$(cat $DIR_BOOK/pass.txt | wc -l)" != "2" ] && /scripts/bookPass.sh
    TMP=$(tempfile)
    while true; do
        echo openvpn --config ${DIR_BOOK}/${RED}/vpnbook-${RED}-udp25000.ovpn
        openvpn --daemon --log $TMP --config ${DIR_BOOK}/${RED}/vpnbook-${RED}-udp25000.ovpn
        while [ "$(cat $TMP | egrep '(AUTH_FAILED|Initialization Sequence Completed)')" = "" ]; do sleep 2; done
        [ "$(cat $TMP | grep AUTH_FAILED)" = "" ] && break
        rm $TMP
        /scripts/bookPass.sh
    done
}

