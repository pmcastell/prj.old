#!/bin/bash

lineaCal() {
    L="$@"
    for d in $L; do 
        echo -n "<td>$d</td>"
    done
}

calMes() {
    ncal -C $@  | 
    (
    read L; 
    echo "<table border='1'>"
    echo "<tr><th colspan='7'>$L</th></tr>"; 
    while read L; do
        [ "$L" = "" ] && continue
        echo -n "<tr>" 
        if [ "$(echo $L | wc -w)" = "7" ]; then
            lineaCal $L
        elif [ "${L:0:0}" = "" ]; then
           NDIAS=$((7 - $(echo $L | wc -w)))
           for((i=1;i<=$NDIAS;i++)); do
              echo -n "<td></td>"
           done
           lineaCal $L
        else
           lineaCal $L
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
          [ $mm -lt $ULT ] && { echo "<td>"; calMes $mm $1; echo  "</td>" };
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
$DIR_BASE/cabeceraHtml5.sh "$1 $2" 'body { background-color: red; } th,td { valign: top; }'
#mes y año actual
[ "$1" = "" ] && calMes && fin
#todo el año ($1)
[ "$2" = "" ] && calMeses $1  && fin
#mes ($2) del año ($1)
[ "$3" = "" ] && calMes $2 $1 && fin
#meses desde ($2) hasta ($3) del año ($1)
calMeses $1 $2 $3
   
