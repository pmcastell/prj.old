#!/bin/bash
uso() {
   echo Uso: $0 '-g <usuario> | -c <usuario> <nuevapass>'
   echo '-g muestra la clave del alumno <usuario>'
   echo '-c cambiar la clave del alumno <usuario> a <nuevapass>'
   exit 1
}
if [ $# -lt 2 ]; then uso; fi
if [ "$1" = "-c" ]; then
    if [ "$2" != "" ] && [ "$3" != "" ]; then
        consultaLdap -c $2 $3
    else
        echo "Error faltan parámetros. No se cambia la clave."
    fi
else
    consultaLdap -g | awk -F'},' '{for(i=1;i<=NF;i++) { print $i"}," } }' | grep -i $2
fi    
