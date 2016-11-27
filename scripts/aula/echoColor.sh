#!/bin/bash
uso() {
   echo $0 '<color>' '<texto-a-imprimir>'
   echo Colores Disponibles:
   echo "   Black  
   Red
   Green  
   Orange
   Blue   
   Purple 
   Cyan
   LightGray
   DarkGray
   LightRed
   LightGreen
   Yellow
   LightBlue
   LightPurple
   LightCyan
   White
"
   exit 1
}
if [ "$1" = "" ]; then uso; fi
NC='\033[0m' # No Color

Black='\033[0;30m'   
Red='\033[0;31m'     
Green='\033[0;32m'     
Orange='\033[0;33m'     
Blue='\033[0;34m'     
Purple='\033[0;35m'     
Cyan='\033[0;36m'     
LightGray='\033[0;37m'     
DarkGray='\033[1;30m'
LightRed='\033[1;31m'
LightGreen='\033[1;32m'
Yellow='\033[1;33m'
LightBlue='\033[1;34m'
LightPurple='\033[1;35m'
LightCyan='\033[1;36m'
White='\033[1;37m'
COLOR=$1; shift
echo -e "${!COLOR}$@${NC}"
