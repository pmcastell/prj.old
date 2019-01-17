#!/bin/bash

for((i=$1;i>=$2;i--)); do 
   echo '(voice_JuntaDeAndalucia_es_pa_diphone) (SayText "'$i'")' | festival --pipe &> /dev/null; 
done
