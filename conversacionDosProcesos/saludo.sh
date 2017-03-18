#!/bin/bash
imprime() {
   echo $1 > /dev/stderr
   echo $1
}
 
#echo "soy saludo" > /dev/stderr
 
##sleep 1 && read resp
##if [ "$resp" = "hola" ]; then
##   imprime "que tal"
##fi
##sleep 1 && read resp
##if [ "resp" = "bien" ]; then
##   imprime "me alegro"
##fi
##sleep 1 && read resp
##if [ "$resp" = "y tu" ]; then
##   imprime "bien, gracias. Hasta luego"
##fi         
while true; do
   read resp
   #echo leido saludo: $resp > /dev/stderr
   if [ "$resp" = "hola" ]; then
      imprime "que tal"
   elif [ "$resp" = "bien" ]; then
      imprime "me alegro"
   elif [ "$resp" = "y tu" ]; then
      imprime "bien, gracias. Hasta luego"
      break
   fi
done   


