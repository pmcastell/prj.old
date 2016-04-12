if [ "$1" = "" -o "$2" = "" ];
then
   echo uso $0 '<comando> <fichero-ips>'
   exit 1
fi
while read IP; do
   #COMANDO="gnome-terminal -e \"ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo $1\" "
   COMANDO="ssh -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no lliurex@$IP sudo $1"
   echo Ejecutando: $COMANDO 
   $COMANDO &
done < $2
