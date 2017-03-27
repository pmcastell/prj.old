#!/bin/bash
imprime() {
   echo $1 > /dev/stderr
   echo $1
}
   
#echo "soy hola" > /dev/stderr
   
imprime "hola"

##sleep 2 && read resp
##if [ "$resp" = "que tal" ]; then
##   imprime "bien"
##   #imprime "y tu"
##fi
##imprime "hasta luego"   

while true; do
   read resp
   #echo leido hola: $resp > /dev/stderr
   if [ "$resp" = "que tal" ]; then
      imprime "bien"
   elif [ "$resp" = "me alegro" ]; then
      imprime "y tu"
   elif [ "$resp" = "bien, gracias. Hasta luego" ]; then
      imprime "hasta luego"
      break
   fi
done   
