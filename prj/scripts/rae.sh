#!/bin/bash

## rae - Consulta palabras usando el diccionario de la RAE online ##
##       v2.0 by tumorlike@gmai.com
##
## Uso: rae PALABRA [NUMERO] [NUMERO]...
##
## El primer número (opcional) representa el link de la referencia que aparece en 
## la definición principal. El segundo número (opcional) representa el link de la referencia
## que aparece en una referencia. Se pueden agregar vario números luego de la palabra, con
## la desventaja de enlentecer la ejecución del script.
## si en vez de números se agregan letras o palabras, el script las ignora.
##
## Instalación:
##
## - Otorgar permisos de ejecución: chmod 755 rae
## - Mover a la carpeta donde están los ejecutables: sudo mv rae /usr/bin/
## - (ArchLinux) Instalar dnsutils: pacman -S dnsutils
## - Instalar lynx, un navegador de internet en modo texto: sudo apt-get install lynx
## - Disfrutar de la RAE en tu consola: rae pecio

raeserver="www.rae.es"
raeip=$(nslookup $raeserver | tail -2 | head -1 | awk '{print $2}')
browser=$(which lynx)

#comprueba que el navegador web lynx esté instalado
if [ -z "$browser" ]; then
   echo -e "\a$(basename $0) error: el navegador 'lynx' no está instalado."
   exit 1
fi

#funciones de búsqueda
function find_query {
   $browser -dump -width 75 "$raeip/drae/srv/search?val=$query" > /tmp/rae.tmp 2> /dev/null
   if [ "$?" -ne "0" ]; then
      echo -e "\a$(basename $0) error: no se pudo conectar a $raeserver"
      exit 1      
   fi
}

function find_reference {
   reference=$(sed -n "s/[ ]*[0-9]*. http:\/\///p" /tmp/rae.tmp | \
               sed -n "$referencenumber"p 2> /dev/null)
   if [ -z "$reference" ]; then
      echo -e "\aerror: referencias inválida"
      exit 1
   fi
   $browser -dump -width 75 "$reference" > /tmp/rae.tmp 2> /dev/null
}

#funcion que muestra la definición y la "maquilla" para que se vea bonita en la consola
function show_definition {
   echo -e "\a"
   sed '/Referencias/,$d' /tmp/rae.tmp | sed -e :a -e '/^\n*$/{$d;N;ba' -e '}'
   echo ""
}

#se asocian los parámetros entregados al script con las variables query y referencenumber,
#se busca la definición y se muestra en la consola
case $# in
   0) echo -e "\aUso: $(basename $0) PALABRA [NUMERO]"
      exit 1;;
   1) query=$(echo $1 | iconv -f UTF-8 -t LATIN1)
      find_query
      show_definition;;
   *) query=$(echo $1 | iconv -f UTF-8 -t LATIN1)
      find_query
      for i in $@; do
         case $i in
            [0-9]* ) referencenumber=$(echo $i)
                     find_reference;;
         esac
      done
      show_definition;;
esac
