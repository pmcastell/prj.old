#!/bin/bash
uso() {
   echo uso: $0 '<nombre-grupo-a-añadir> <dominio-correo> [<fichero-a-crear>]'
   exit 1
}
[ "$#" -lt 2 ] && uso
GRUPO="$1"
DOMINIO_CORREO="$2"
[ "$3" = "" ] && FICH="/dev/stdout" || FICH="$3"   
#echo "Name,Given Name,Additional Name,Family Name,Yomi Name,Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Group Membership" > $FICH
echo "Name,Given Name,Additional Name,Family Name,Yomi Name,Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Group Membership,E-mail 1 - Type,E-mail 1 - Value" > $FICH
#ayoubmakran1998@gmail.com,ayoubmakran1998@gmail.com,,,,,,,,,,,,,,,,,,,,,,,,,* My Contacts ::: clase ::: * Starred

#Imprimir en pantalla Introduce ...
###echo "Introduce el nombre del grupo al que añadir las direcciones: " > /dev/stderr
#leer y guardar en la variable GRUPO lo que teclee el usuario hasta que le de al INTRO
###read GRUPO

#while repite una serie de instrucciones mientras se de una condición
USUARIO_CORREO=""
while [ "$USUARIO_CORREO" != "FIN" ]; do
   echo "Introduce nombre del usuario (ej.: Pepe Pérez):" > /dev/stderr
   read  NOMBRE
   if [ "$NOMBRE" = "FIN" ]; then break; fi
   echo "Introduce dirección de USUARIO_CORREO: " > /dev/stderr
   read USUARIO_CORREO
   #echo "${NOMBRE},${USUARIO_CORREO}@${DOMINIO_CORREO},,,,,,,,,,,,,,,,,,,,,,,,,* My Contacts ::: $GRUPO ::: * Starred" >> $FICH
   DIR_CORREO="${USUARIO_CORREO}@${DOMINIO_CORREO}"
   NOMBREP=$(echo $NOMBRE | awk '{print $1;}')
   AP1=$(echo $NOMBRE | awk '{print $2;}')
   AP2=$(echo $NOMBRE | awk '{print $3;}')
   echo "${NOMBRE},${NOMBREP},${AP1},${AP2},,,,,,,,,,,,,,,,,,,,,,,${GRUPO} ::: * My Contacts ::: * Starred,* Work,${DIR_CORREO}" >> $FICH
done

