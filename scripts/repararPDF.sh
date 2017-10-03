#!/bin/bash
uso() {
   echo Uso: $0 '<fichero-pdf-a-reparar>'
   exit 1
}
[ "$1" = "" ] && uso   
#gs -o $1.reparado.pdf -sDEVICE=pdfwrite -dPDFSETTINGS=/prepress $1.pdf
#pdftocairo -pdf $1 $1.reparado
mutool clean $1 $1.reparado
