if [ "$1" = "" ];
then
   echo uso: $0 '<mac-address>'
   exit 1
fi
BYTE2=$(echo $1 | awk -F':' '{print $2;}')
if [ "$BYTE2" = "" ];
then 
   BYTE1=$(echo $1 | awk -F'-' '{print $1;}')
   BYTE2=$(echo $1 | awk -F'-' '{print $2;}')
   BYTE3=$(echo $1 | awk -F'-' '{print $3;}')
else
   BYTE1=$(echo $1 | awk -F':' '{print $1;}')
   BYTE3=$(echo $1 | awk -F':' '{print $3;}')  
fi   
wget http://standards.ieee.org/cgi-bin/ouisearch?$BYTE1-$BYTE2-$BYTE3 -O - 2> /dev/null | grep -i '<pre>' -A 5
