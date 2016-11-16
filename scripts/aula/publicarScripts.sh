#!/bin/bash
if [ "$(whoami)" != "root" ]; then
   sudo $0 $*
   exit
fi
for s in bloquear hs encenderAula apagarAula pantalla srv1On srv2On activarTor micalc vaciarArp cerrarSesion ; do
   chmod +x $s.sh
   sudo cp /home/franav/aula/$s.sh /usr/local/bin/
   sudo ln -s /usr/local/bin/$s.sh /usr/local/bin/$s
done
   
