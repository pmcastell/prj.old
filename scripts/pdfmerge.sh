uso () {
   echo $0 salida.pdf fich-entrada-1.pdf ... fich-entrada-n.pdf
   exit -1
}   

if [ "$3" = "" ];
then
   uso
fi
#if [ "$9" != "" ]; then pdftk $9 burst output /tmp/%%04d____I.pdf; fi
#if [ "$8" != "" ]; then pdftk $8 burst output /tmp/%%04d____H.pdf; fi
#if [ "$7" != "" ]; then pdftk $7 burst output /tmp/%%04d____G.pdf; fi
#if [ "$6" != "" ]; then pdftk $6 burst output /tmp/%%04d____F.pdf; fi
#if [ "$5" != "" ]; then pdftk $5 burst output /tmp/%%04d____D.pdf; fi
#if [ "$4" != "" ]; then pdftk $4 burst output /tmp/%%04d____C.pdf; fi
#if [ "$3" != "" ]; then pdftk $3 burst output /tmp/%%04d____B.pdf; fi
#if [ "$2" != "" ]; then pdftk $2 burst output /tmp/%%04d____A.pdf; fi

#pdftk /tmp/*____?.pdf cat output $1
SALIDA=$1
shift
echo pdftk $* cat output $SALIDA
pdftk $* cat output $SALIDA

#rm -f /tmp/*____?.pdf

