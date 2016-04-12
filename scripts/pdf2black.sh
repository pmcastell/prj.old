uso() {
   echo $0 '<fichero-entrada> <fichero-salida>'
   exit -1
}
if [ "$1" = "" -o "$2" = "" ];
then
   uso
fi      
gs -sOutputFile=$2.pdf -sDEVICE=pdfwrite -sColorConversionStrategy=Gray -dProcessColorModel=/DeviceGray -dCompatibilityLevel=1.4 $1.pdf < /dev/null

#$nconvert -binary nodither -keepcspace -colors 2 -out pdf -c 4 -o output.pdf input.pdf


