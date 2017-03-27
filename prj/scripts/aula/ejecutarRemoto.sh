if [ "$#" -lt 2 ];
then
   echo uso $0 '<comando> <dir-ip-inicial> <num-ips>'
   exit 1
fi
#while read IP; do
#   #COMANDO="gnome-terminal -e \"ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo $1\" "
#   COMANDO="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo $1"
#   echo Ejecutando: $COMANDO 
#   $COMANDO &
#done < $2
if [ "$3" = "" ]; then NUM=1; else NUM=$3; fi
RED=$(echo $2 | awk -F'.' '{print $1"."$2"."$3;}')
INI=$(echo $2 | awk -F'.' '{print $NF;}')
echo RED: $RED
echo INI: $INI

for((i=0;i<$NUM;i++)); do
   echo ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no ConnectTimeout=5 lliurex@${RED}.$(( $INI + $i )) sudo $1 > /dev/stderr
   ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no -o ConnectTimeout=5 lliurex@${RED}.$(( $INI + $i )) sudo $1 2>/dev/null &
done   
