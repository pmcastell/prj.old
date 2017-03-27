#!/bin/bash
publicarScripts() {
   for s in $2; do
      chmod +x,+r $s.$1
      sudo cp /home/franav/aula/$s.$1 /usr/local/bin/$s
      sudo cp /home/franav/aula/$s.$1 /usr/local/bin/$s.$1
      sudo chmod +x,+r /usr/local/bin/$s
      #sudo ln -s /usr/local/bin/$s.$1 /usr/local/bin/$s
   done
}

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
despiertaEpoptes
ayuda"
publicarScripts sh "$SCRIPTS"
PYTHONS="aluPass"
publicarScripts py "$PYTHONS"   
