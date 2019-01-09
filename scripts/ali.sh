#!/bin/bash -i

#shopt -s expand_aliases
#cat aliasMios.sh | sed -e 's/alias/export/g')
#while read alia; do
#   alia="$(echo ${alia} | sed -e 's/alias/export/')"
#   $($alia)
#done < aliasMios.sh
#echo '----------------------------------------------' 
#echo "Ejecutando: $(eval $@)"
#CMD=$(alias | grep $1 | head -1 | awk -F"='" '{print $2;}' |awk -F"'" '{print $1;}')
#shift
#while [ "$1" != "" ]; do PARAMS="$PARAMS $1"; shift; done
eval $@
#echo $CMD "$PARAMS"
#$CMD "$PARAMS"
#PARAMS="$1"; shift
#eval $CMD $PARAMS "\"$@\""
