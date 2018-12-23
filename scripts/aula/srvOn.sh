#!/bin/bash

main() {
    if [ "$1" = "" ]; then uso; fi
    while [ "$1" != "" ]; do
        [ "$1" = "ALL" ] && BUSCA="" || BUSCA="$1"
#        echo "BUSCA: $BUSCA"
        MACS="$(macsConocidas $BUSCA | awk '{print $2;}')"
#        echo "MACS: $MACS"
        IFACES="$(_ifaces)"
#        echo "IFACES: $IFACES"
        for MAC in $MACS; do
            for IFACE in $IFACES; do 
                echo sudo etherwake -i $IFACE $MAC
                sudo etherwake -i $IFACE $MAC
            done
        done
        shift
    done
}    
uso() {
   echo uso: $0 '<servidor-a-despertar>'
   echo Servidores disponibles: 
   echo "         srvSalaProfes
         aula1srv
         aula2srv
         tecnologia
         Salaprofes2
         biblioSrv1
         DEPMAT2
         PT1
         DeptSociales1
         paula1
         paula2
         dpto
         ALL \(TODOS\)"
   exit 1
}
macsConocidas() {
    __macsConocidas() {
          echo "srvSalaProfes   00:24:01:ee:22:59
aula2srv        00:24:01:ed:30:c1 
aula2srv        00:e0:4c:38:00:73 
aula1srv        00:19:99:88:6a:f8 
aula1srv        c4:6e:1f:05:fc:71 
tecnologia      00:19:99:80:53:e1 
biblioSrv1      00:24:01:ee:21:c7 
DEPMAT2         44:8a:5b:c2:77:fc 
PT1             44:8a:5b:c2:71:e6 
Salaprofes2     00:0f:ea:40:57:3f 
DeptSociales1   8c:89:a5:2e:88:ba
paula1          20:cf:30:90:dc:18
paula2          1c:1b:0d:0d:2d:71
dpto            20:cf:30:90:dc:5b" 
    }
    if   [ "$1" = "" ];   then  __macsConocidas
    elif [ "$1" = "-m" ]; then (__macsConocidas | awk '{print $2;}')
    elif [ "$1" = "-n" ]; then (__macsConocidas | awk '{print $1;}')
    elif [ "$1" != "" ];  then (__macsConocidas | grep "$1" )
    fi
#    | grep "$1" | awk '{print $2;}'
}
_ifaces() {
    ip -o l | awk '{print $2;}' | uniq | cut -d':' -f 1
}        
(return 2>/dev/null) || main "$@"
#exit 0

###if [ "$MAC" != "" ]; then echo sudo etherwake -i eth1 $MAC; sudo etherwake -i eth1 $MAC;
###else echo Error servidor no encontrado; fi

###todas() {
###    MACS="$(macsConocidas | awk '{print $2;}')"
###    IFACES="$(_ifaces)"
###    for MAC in $MACS; do 
###        for IFACE in $IFACES; do
###            echo sudo etherwake -i $IFACE $MAC;
###            sudo etherwake -i $IFACE $MAC;
###        done
###   done
###} 
