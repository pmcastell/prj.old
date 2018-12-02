#!/bin/bash

#echo $(( RANDOM % 90000 ))
n=$1
LIMITE="1"
for((i=1;i<=$n;i++)); do
   LIMITE="${LIMITE}0"
done
R=""
while [ ${#R} -lt $n ]; do
   R=$(( RANDOM % $LIMITE ))
done
echo $R
