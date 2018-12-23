#!/bin/bash
main() {
    URL="ftp://ftp.nai.com:21/pub/datfiles/english/"
    while true; do 
        INFO=$(wget $URL -S --spider 2>&1 | grep tar | tail -1)
        NOMBRE=$(echo $INFO | awk '{ print $NF;}')
        TAM_FICHERO=$(echo $INFO | awk '{ print $5;}')
        [ "$NOMBRE" != "" ] && [ "$TAM_FICHERO" != "" ] && break
    done
    TAM_NUEVO=0
    info
    while true; do 
        descarga $1 "$URL/$NOMBRE"
        [ "$(ls -l $NOMBRE | awk '{print $5;}')" = "$TAM_FICHERO" ] && break
    done
    tar xvf $NOMBRE
    sudo mv avv*.dat /usr/local/uvscan/
    read -p "Borrar $NOMBRE?" RESP
    [ "$RESP" = "s" ] || [ "$RESP" = "S" ] && rm $NOMBRE && rm legal.txt    
}
info() {
    echo Tam.: $TAM_FICHERO
    echo nombre: $NOMBRE
    echo URL: $URL
    echo http_proxy: $http_proxy
    echo ftp_proxy : $ftp_proxy
    echo ..........................................................
    echo Iniciando Descarga
    echo ..........................................................
}
main "$@"
exit 0


