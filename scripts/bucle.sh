if [ "$1" = "" ]; then 
   echo Uso: $0 comando-a-ejecutar parametros;
   exit 1;
fi
[ "$1" = "-c" ] && shift && LIMITE="$1" && shift || LIMITE="99999999"
while true; do 
    "$@"
    [ $? -gt 0 ] && habla -n algo salió mal
    habla -n bucle terminado &> /dev/null
    sleep 1;
    i=$(( $i + 1 ))
    [ $i -ge $LIMITE ] && break
done    



