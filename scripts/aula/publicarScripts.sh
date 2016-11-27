#!/bin/bash
if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi
SCRIPTS="bloquear
hs
encenderAula
apagarAula
pantalla
srvOn
activarTor
micalc
vaciarArp
cerrarSesion
imprime
enrutamiento
echoColor
ayuda"
for s in $SCRIPTS; do
   chmod +x,+r $s.sh
   sudo cp /home/franav/aula/$s.sh /usr/local/bin/$s
   sudo chmod +x,+r /usr/local/bin/$s
   #sudo ln -s /usr/local/bin/$s.sh /usr/local/bin/$s
done
   
