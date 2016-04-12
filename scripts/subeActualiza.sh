uso() {
   echo uso: $0 fichero-a-subir
   exit 1
}

if [ "$1" = "" ];
then
   uso
fi  
if [ "$2" = "" ];
then 
   DEST=./www/franc/;
else
   DEST=$2
fi 
MD5=$(md5sum $1)
while [ 1 ]; 
do 
   if [ "$MD5" != "$(md5sum $1)" ]; 
   then 
      scp $1 profe@iesinclan.dyndns.org:$DEST; 
      MD5=$(md5sum $1); 
      sleep 1; 
   fi 
done
