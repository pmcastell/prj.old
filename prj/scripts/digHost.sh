uso () {
   echo 'uso(1):' $0 '<host-name>'
   echo 'uso(2):'  $0 '<dns-server> <host-name>'
   exit 1
}
if [ "$1" = "" ];
then
   uso
elif [ "$2" != "" ];
then
   HOST=$2
   DNS="@$1"
else
   HOST=$1   
fi
echo $(dig $DNS $HOST | grep -E "^$HOST" | head -1 | grep IN | grep A | awk '{print $NF;}')
