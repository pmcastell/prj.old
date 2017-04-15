halfRandomMAC() {
   HEX_DIGITS=(0 1 2 3 4 5 6 7 8 9 a b c d e f)
   MAC=""
   for((i=1;i<=3;i++)); do
      D1=${HEX_DIGITS[$(( RANDOM % 16 ))]};
      D2=${HEX_DIGITS[$(( RANDOM % 16 ))]};
      MAC="$MAC:${D1}${D2}";
   done
   echo ${MAC:1} 
}
FABRICANTES="/m/Mios/prj/scripts/fabricantesMAC.txt.bz2"
LIMITE=$(($(bzcat $FABRICANTES | grep '(hex)' | wc -l) + 1 ))
NLINEA=$(( RANDOM % $LIMITE ))
MAC1=$(bzcat $FABRICANTES | grep '(hex)' | awk NR==$NLINEA | awk '{print $1;}')
MAC2=$(halfRandomMAC)
echo "$MAC1:$MAC2" | sed  's/-/:/g'
