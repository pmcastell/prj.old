#!/bin/bash
uso() {
   echo Uso: $0 '<dir-a-cambiar-permisos>'
   exit 1
}
[ "$1" = "" ] && uso
TEMP=$(tempfile)
sudo find $1 > $TEMP
while read f; do 
   if [ -d $f ]; then 
      chmod g+rwx $f; 
   fi; 
   if [ -f $f ]; then 
      chmod g+rw $f; 
   fi; 
done < $TEMP
rm $TEMP
