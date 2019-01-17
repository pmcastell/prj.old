#!/bin/bash

echo "Name,Given Name,Additional Name,Family Name,Yomi Name,Given Name Yomi,Additional Name Yomi,Family Name Yomi,Name Prefix,Name Suffix,Initials,Nickname,Short Name,Maiden Name,Birthday,Gender,Location,Billing Information,Directory Server,Mileage,Occupation,Hobby,Sensitivity,Priority,Subject,Notes,Group Membership,E-mail 1 - Type,E-mail 1 - Value,Phone 1 - Type,Phone 1 - Value,Address 1 - Type,Address 1 - Formatted,Address 1 - Street,Address 1 - City,Address 1 - PO Box,Address 1 - Region,Address 1 - Postal Code,Address 1 - Country,Address 1 - Extended Address"

#ayoubmakran1998@gmail.com,ayoubmakran1998@gmail.com,,,,,,,,,,,,,,,,,,,,,,,,,* My Contacts ::: clase ::: * Starred

#Imprimir en pantalla Introduce ...
#leer y guardar en la variable GRUPO lo que teclee el usuario hasta que le de al INTRO
read -p "GRUPO: " GRUPO

#while repite una serie de instrucciones mientras se de una condición
while true; do
   read -p "NOMBRE: " NOMBRE
   if [ "$( echo $NOMBRE | awk '{print toupper($0);}')" = "FIN" ]; then break; fi
   read -p "APELL1: " AP1
   read -p "APELL2: " AP2
   read -p "EMAIL : " EMAIL
   [ "$(echo ${EMAIL} | grep '@')" = "" ] && EMAIL="${EMAIL}@gmail.com"
   echo "${NOMBRE} ${AP1} ${AP2},${NOMBRE},,${AP1} ${AP2},,,,,,,,,,,,,,,,,,,,,,,* My Contacts ::: ${GRUPO},* ,${EMAIL},,,,,,,,,,,"
done
