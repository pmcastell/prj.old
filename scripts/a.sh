#!/bin/bash -i

#shopt -s expand_aliases
#cat aliasMios.sh | sed -e 's/alias/export/g')
#while read alia; do
#   alia="$(echo ${alia} | sed -e 's/alias/export/')"
#   $($alia)
#done < aliasMios.sh
#echo '----------------------------------------------' 
#echo "Ejecutando: $(eval $@)"
eval "$@"
