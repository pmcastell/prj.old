IP_ACTUAL=$(cat /etc/hosts | grep -i squid | awk '{ print $1;}')
if [ "$1" != "" ];
then
   IP_NUEVA=$1
else
   IP_NUEVA=127.0.0.1
fi   
if [ "$IP_ACTUAL" != "$IP_NUEVA" ];
then
   cat /etc/hosts | grep -v squid  > /tmp/hosts
   mv /tmp/hosts /etc/hosts
   echo $IP_NUEVA squid >> /etc/hosts
fi   

