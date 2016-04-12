ARCHIVO1=7.0-RELEASE-amd64-disc1.iso
ARCHIVO2=7.0-RELEASE-amd64-disc2.iso
ARCHIVO3=7.0-RELEASE-amd64-disc3.iso
ARCHIVO4=guadalinex-v5.iso
ARCHIVO5=7.1-RELEASE-amd64-dvd1.iso.gz
rm $ARCHIVO1
rm $ARCHIVO2
rm $ARCHIVO3
rm $ARCHIVO4
rm $ARCHIVO5
SITIO=ftp://ftp.de.freebsd.org/pub/FreeBSD/ISO-IMAGES-amd64/7.0/
SITIO=ftp://ftp.freebsd.org/pub/FreeBSD/releases/amd64/ISO-IMAGES/7.1/
SITIO=http://sunsite.rediris.es/mirror/guadalinex/guadalinex-descargas/imagenes_iso/v5/
cd /tmp
# wget -c --timeout=10 ftp://ftp2.es.freebsd.org/pub/FreeBSD/ISO-IMAGES-i386/7.1/7.1-RELEASE-i386-dvd1.iso.gz
# wget -c --timeout=10 ftp://ftp.freebsd.org/pub/FreeBSD/releases/amd64/ISO-IMAGES/7.1/7.1-RELEASE-amd64-dvd1.iso.gz
wget -c --timeout=5 $SITIO/$ARCHIVO4  $1 $2 $3 $4 $5 $6 $7 $8 $9

