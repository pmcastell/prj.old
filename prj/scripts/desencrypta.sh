uso () {
    echo uso: $0 '<palabra-a-encriptar>'
    exit 0
}
desencriptaLinea() {
   CHA=$1
   i=1
   while [ $i -le $KEY ];
   do
      CHA=$(echo -n $CHA | base64 -d)
      i=$(expr $i + 1)
   done
   echo $CHA
}
if [ "$1" = "-k" ]; then shift; KEY=$1; shift; else KEY=16; fi
if [ "$1" = "" ]; then ENTRADA="/dev/stdin"; else ENTRADA="$1"; fi
if [ -f "$ENTRADA" -o "$ENTRADA" = "/dev/stdin" ]; then
   while read LINEA; do
      echo $(desencriptaLinea "$LINEA")
   done < $ENTRADA
else
   echo $(desencriptaLinea "$1")
fi


