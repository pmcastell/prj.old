#!/bin/bash

uso() {
   echo uso: $0 '<remitente> <dest> <subject> <texto> [<ficheros-a-adjuntar>]'
   exit 1
}
boundary() {
   #89035b44283f1016fe91c5c5ad4a
   DIG=( 1 2 3 4 5 6 7 8 9 0 a b c d e f )
   BND=""
   for((i=1;i<=28;i++)); do
     BND=$BND${DIG[$(( RANDOM % 16 ))]};
   done
   echo $BND
}   
if [ $# -lt 4 ]; then uso; fi
FROM="<$1>"  
DEST="<$2>"
SUBJECT="$3"
TEXTO="$4"
POSICION_ADJUNTOS=5
CABECERAS="From: $FROM\nTo: $DEST\nSubject: $SUBJECT\n"
#echo -e $CABECERAS | (cat - && uuencode /path/to/attachment attachment.name) | ssmtp sender@gmail.com"
if [ "$5" != "" ]; then
   FICHEROS_ADJUNTOS=$(tempfile)
   BOUNDARY=$(boundary)
   for((i=1;i<$POSICION_ADJUNTOS;i++)); do
      shift;
   done
   CABECERAS="${CABECERAS}Content-Type: multipart/mixed; boundary=\"$BOUNDARY\"\n\n" 
   CABECERAS="${CABECERAS}--${BOUNDARY}"
   CABECERAS="${CABECERAS}\nContent-Type: text/plain; charset=UTF-8\nContent-Transfer-Encoding: quoted-printable\n"
   while [ "$1" != "" ]; do
      #--001a11440fac243a3b05304e6dfb
      #Content-Type: application/pdf; name="archivo.pdf"
      #Content-Disposition: attachment; filename="archivo.pdf"
      #Content-Transfer-Encoding: base64
      echo -e "--${BOUNDARY}" >> $FICHEROS_ADJUNTOS
      echo Content-Type: $(mimetype "$1" | awk '{print $2;}')";" name=\"$(basename "$1")\" >> $FICHEROS_ADJUNTOS
      echo Content-Disposition: attachment";" filename=\"$(basename "$1")\" >> $FICHEROS_ADJUNTOS
      echo -e Content-Transfer-Encoding: base64"\n" >> $FICHEROS_ADJUNTOS
      #uuencode -m "$1" - >> $FICHEROS_ADJUNTOS
      cat "$1" | base64 >> $FICHEROS_ADJUNTOS
      shift
   done;
   echo -e "--${BOUNDARY}--\n" >> $FICHEROS_ADJUNTOS
   #echo -e "$CABECERAS\n$TEXTO\n" | (cat - && cat $FICHEROS_ADJUNTOS) | ssmtp $DEST
   echo -e "$CABECERAS\n$TEXTO\n" | (cat - && cat $FICHEROS_ADJUNTOS) 
   rm $FICHEROS_ADJUNTOS
else
   #echo -ne "$CABECERAS\n$TEXTO" | ssmtp $DEST
   echo -e "$CABECERAS\n$TEXTO\n" 
fi   
   
