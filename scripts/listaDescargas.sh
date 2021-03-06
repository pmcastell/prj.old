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
#http://cznic.dl.osdn.jp/drbl/67430/drbl-live-xfce-2.5.1-16-amd64.iso
#http://cznic.dl.osdn.jp/clonezilla/67139/clonezilla-live-2.5.0-25-amd64.iso
#http://cdimage.kali.org/kali-2016.2/kali-linux-2016.2-amd64.iso
#https://install.avira-update.com/package/rs_avira/unix/int/rescue-system.iso
#http://www.downloadwireless.net/isos/wifislax64-1.0-final.iso
#http://ftp.cica.es/ubuntu/releases/17.04/ubuntu-17.04-desktop-amd64.iso
#http://releases.lliurex.net/isos/15.05_64bits/lliurex-servidor_amd64_1505_20170223_kernel4.iso
#http://downloadcenter.samsung.com/content/FM/201704/20170427160256419/T-JZL6DEUC_1169.2.exe
#https://netix.dl.sourceforge.net/project/systemrescuecd/sysresccd-x86/5.0.2/systemrescuecd-x86-5.0.2.iso
#http://cdimage.kali.org/kali-2017.1/kali-linux-2017.1-amd64.iso
#http://www.mediafire.com/file/5yndrs88pie88nz/wifislax64-1.1-final.iso
#http://www.downloadwireless.net/isos/wifislax64-1.1-final.iso
#https://az792536.vo.msecnd.net/vms/VMBuild_20170320/VirtualBox/MSEdge/MSEdge.Win10.RS2.VirtualBox.zip
#ftp://ftp.cica.es/ubuntu/cdimage/releases/17.10/release/ubuntu-17.10-live-server-amd64.iso
#ftp://ftp.cica.es/mirrors/Linux/ubuntu/releases/17.10/ubuntu-17.10-desktop-amd64.iso
ftp://ghostbsd.org/pub/GhostBSD/releases/amd64/ISO-IMAGES/10.3-RELEASE/GhostBSD10.3-RELEASE-20160829-214135-mate-amd64.iso
'
}
FICHERO_CLAVE="listaFicheros.txt"
LIMIT="400"
LIMIT="0"
([ "$1" = "-h" ] || [ "$1" = "--help" ]) && uso
if [ -f $FICHERO_CLAVE ]; then DESCARGAR="$FICHERO_CLAVE"; 
else
   DESCARGAR=$(tempfile)
   [ "$1" = "" ] &&   descargas > $DESCARGAR || echo "$1" > $DESCARGAR
fi   
while read d; do
   [ "$d" = "" ] || [ "${d:0:1}" = "#" ] && continue
   MAQUINA=$(echo $d | awk -F'/' '{print $3;}')
   #echo "MAQUINA: $MAQUINA"
   echo hola
   eecho sudo route add -host $MAQUINA gw r1

   eecho descarga "--limit-rate $LIMIT" "$d"
   eecho sudo route del -host $MAQUINA
done < $DESCARGAR
