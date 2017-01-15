#!/bin/bash
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
         ALL \(TODOS\)"
   exit 1
}
todas() {
   MACS="00:24:01:ee:22:59 00:24:01:ed:30:c1 00:19:99:88:6a:f8 00:19:99:80:53:e1 00:0f:ea:40:57:3f 00:24:01:ee:21:c7 44:8a:5b:c2:77:fc 44:8a:5b:c2:71:e6 8c:89:a5:2e:88:ba"
   for MAC in $MACS; do 
      echo sudo etherwake -i eth1 $MAC;
      sudo etherwake -i eth1 $MAC;
   done
}   
if [ "$1" = "" ]; then uso; fi
if [ "$(whoami)" != "root" ]; then sudo $0 $@; exit $?; fi
if [ "$1" = "srvSalaProfes" ];   then MAC="00:24:01:ee:22:59";
elif [ "$1" = "aula2srv" ];      then MAC="00:24:01:ed:30:c1";
elif [ "$1" = "aula1srv" ];      then MAC="00:19:99:88:6a:f8";
elif [ "$1" = "tecnologia" ];    then MAC="00:19:99:80:53:e1";
elif [ "$1" = "biblioSrv1" ];    then MAC="00:24:01:ee:21:c7";
elif [ "$1" = "DEPMAT2" ];       then MAC="44:8a:5b:c2:77:fc";
elif [ "$1" = "PT1" ];           then MAC="44:8a:5b:c2:71:e6";
elif [ "$1" = "Salaprofes2" ];   then MAC="00:0f:ea:40:57:3f";
elif [ "$1" = "DeptSociales1" ]; then MAC="8c:89:a5:2e:88:ba";
elif [ "$1" = "ALL" ];         then todas; exit 0;
fi
if [ "$MAC" != "" ]; then echo sudo etherwake -i eth1 $MAC; sudo etherwake -i eth1 $MAC;
else echo Error servidor no encontrado; fi

   
