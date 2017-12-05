#!/bin/bash
[ "$1" = "" ] && FICH_PASS="/m/Mios/Instituto/Ciclos/CGM-SMR/alumnosMoodle-17-18.txt" || FICH_PASS="$1"
while read l; do 
   USER=$(echo $l | cut -d' ' -f1); 
   PASS=$(echo $l | cut -d' ' -f2); 
   mysql -u root -pgalileo -e "update mdl_user set password='$(/scripts/blowf.sh $PASS)' where username='$USER'" moodle; 
done < $FICH_PASS 
