#!/bin/bash

#descarga http://releases.lliurex.net/isos/15.05/lliurex-client_1505_20151126.iso
#descarga http://ftp.fau.de/mint/iso/linuxmint.com//stable/17.3/linuxmint-17.3-cinnamon-64bit.iso
#descarga http://ftp.freebsd.org/pub/FreeBSD/releases/amd64/amd64/ISO-IMAGES/10.2/FreeBSD-10.2-RELEASE-amd64-memstick.img.xz
uso() {
   cat<<EOF
   Uso: $0 [<fichero-con-lista-a-descargar.txt>]
   si existe un fichero llamado $FICHERO_CLAVE toma este fichero como lista a descargar  
EOF
   exit 1
}      
descargas() {
echo '
#http://download.avg.com/filedir/inst/avg_arl_cdi_all_120_160420a12074.iso
http://cznic.dl.osdn.jp/drbl/67430/drbl-live-xfce-2.5.1-16-amd64.iso
http://cznic.dl.osdn.jp/clonezilla/67139/clonezilla-live-2.5.0-25-amd64.iso
http://cdimage.kali.org/kali-2016.2/kali-linux-2016.2-amd64.iso
https://install.avira-update.com/package/rs_avira/unix/int/rescue-system.iso
http://www.downloadwireless.net/isos/wifislax64-1.0-final.iso
'
}
FICHERO_CLAVE="listaFicheros.txt"
LIMIT="400"
([ "$1" = "-h" ] || [ "$1" = "--help" ]) && uso
if [ -f $FICHERO_CLAVE ]; then DESCARGAR="$FICHERO_CLAVE";
else
   DESCARGAR=$(tempfile)
   [ "$1" = "" ] &&   descargas > $DESCARGAR || DESCARGAR="$1"
fi   
while read d; do
   [ "$d" = "" ] || [ "${d:0:1}" = "#" ] && continue
   MAQUINA=$(echo $d | awk -F'/' '{print $3;}')
   sudo route add -host $MAQUINA gw r1
   descarga "$d" "$LIMIT"
   sudo route del -host $MAQUINA
done < $DESCARGAR
