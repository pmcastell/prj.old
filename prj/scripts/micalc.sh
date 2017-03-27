#!/bin/bash

if [ -f /m/Mios/Personal/calculadora/calculadora.jar ]; then
   cd /m/Mios/Personal/calculadora
   java -jar calculadora.jar &> /dev/null &
elif [ -f /net/server-sync/home/teachers/franav/Desktop/Dropbox/Instituto/prj/scripts/aula/calculadora.jar ]; then
   java -jar /net/server-sync/home/teachers/franav/Desktop/Dropbox/Instituto/prj/scripts/aula/calculadora.jar &> /dev/null &
fi   
