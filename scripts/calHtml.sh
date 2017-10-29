#!/bin/bash

lineaCal() {
    L="$@"
    [ "$(echo $L | grep -E 'lu *ma *mi *ju *vi')" != "" ] && CELL="th" || CELL="td"
    for d in $L; do 
        if [ "$(echo $d | grep '_')" != "" ]; then
            CELL="$CELL style='background-color: red;'"
            for c in $(echo -n $d | grep -Eo '[a-zA-Z0-9]+'); do
               DIA="${DIA}${c}"
            done
            d="$DIA"
        fi
        echo -n "<$CELL class='right'>$d</td>"
    done
}
celdasVacias() {
    NCELDAS=$1
    for((i=1;i<=$NCELDAS;i++)); do
        echo -n "<td></td>"
    done
}    

calMes() {
    ncal -C $@  | 
    (
    read; 
    echo "<table border='1'>"
    echo "<tr><th colspan='7'>$REPLY</th></tr>"; 
    while read; do
        [ "$(echo $REPLY | wc -w)" = "0" ] && continue
        echo -n "<tr>" 
        if [ "$(echo $REPLY | wc -w)" = "7" ]; then
            lineaCal $REPLY
        else 
           NDIAS=$((7 - $(echo $REPLY | wc -w)))
           [ "${REPLY:0:1}" = " " ] && celdasVacias $NDIAS
           lineaCal $REPLY
           [ "${REPLY:0:1}" != " " ] && celdasVacias $NDIAS
        fi
        echo "</tr>"
    done
    echo "</table>"
    )
}    


calMeses() {
    ANIO="$1"; PRIM="$2"; ULT="$3"
    [ "$ANIO" = "" ] && ANIO=$(date "+%Y"); [ "$PRIM" = "" ] && PRIM=1; [ "$ULT" = "" ] && ULT=12
    echo "<table border='1'>"
    for((m=$PRIM;m<=$ULT;m+=4)); do
       echo "<tr>"
       for((mm=$m;mm<=$(($m+3));mm++)); do
          [ $mm -le $ULT ] && { echo "<td>"; calMes $mm $1; echo  "</td>"; }
       done
       echo  "</tr>"
    done
    echo "</table>"
}
fin() {
    $DIR_BASE/pieHtml.sh 
    exit 0
}
#for((m=1;m<=1;m++)); do /scripts/calHtml.sh $m 2018; done 
DIR_BASE=$(dirname $0)
$DIR_BASE/cabeceraHtml5.sh "$1" '.right { text-align: right; } th { text-align: center; font-weight: bold; color: black; } td { vertical-align: top; }'
#mes y año actual
[ "$1" = "" ] && calMes && fin
#todo el año ($1)
[ "$2" = "" ] && calMeses $1  && fin
#mes ($2) del año ($1)
[ "$3" = "" ] && calMes $2 $1 && fin
#meses desde ($2) hasta ($3) del año ($1)
calMeses $1 $2 $3 && fin
   
