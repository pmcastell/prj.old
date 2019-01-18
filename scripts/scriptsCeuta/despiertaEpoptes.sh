#!/bin/bash
[ -e $(dirname $0)/funcionesAula.sh ] && . /funcionesAula.sh
[ -e /scripts/scriptsCeuta/funcionesAula.sh ] && . /scripts/scriptsCeuta/funcionesAula.sh

main() {
    [ "$(ip a | grep 172.124.116)" ] && RED=172.124.116 && MACS="macsCiclo1"
    [ "$(ip a | grep 172.124.117)" ] && RED=172.124.117 && MACS="macsCiclo2"
    [ "$1" = "ciclo1" ] && RED=172.124.116 && MACS="macsCiclo1"
    [ "$1" = "ciclo2" ] && RED=172.124.117 && MACS="macsCiclo2"
    [ "$1" != "" ] && reiniciaEpoptes $1 && terminarSsh && exit 0
    #IPS=$(sudo arp-scan -r 5 ${RED}.0/24 | grep $RED | awk '{print $1;}' | sort | uniq)
    for IP in $($MACS  | awk '{print $1;}'); do 
        reiniciaEpoptes $IP &
    done    
    terminarSsh
}

reiniciaEpoptes() {
    IP="$1"
    while true; do 
        ([ "$IP" = "172.124.117.100" ] || [ "$IP" = "server" ]) && continue
        PID=$(ssh -p 22 profesor@${IP} ps aux 2>/dev/null  | grep -i epoptes | grep -v root | grep -v profesor | awk '{print $2;}')
        [ "$PID" = "" ] && break
        echo ssh -p 22 profesor@${IP} sudo kill -9 $PID
        ssh -p 22 profesor@${IP} sudo kill -9 $PID 2>/dev/null
    done
    WHO=$(ssh $IP who 2>/dev/null | awk -F':' '{print $1;}')
###    echo "IP: $IP, WHO: $WHO"
    [ "$WHO" != "" ] && echo ssh -p 22 profesor@$IP sudo "sudo -u $WHO bash -c 'export DISPLAY=:0 && epoptes-client'"
    [ "$WHO" != "" ] && ssh -p 22 profesor@$IP sudo "sudo -u $WHO bash -c 'export DISPLAY=:0 && epoptes-client&> /dev/null &'" 2>/dev/null &
}    

terminarSsh() {
    PIDS=$(ps aux | grep -i ssh | grep profesor | grep $RED | grep DISPLAY | grep 'epoptes-client' | grep -v grep | awk '{print $2;}')
    for P in $PIDS; do 
       kill -term $P
    done   
}    
(return 2>/dev/null) || main "$@"
