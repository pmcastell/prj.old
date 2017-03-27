for NIC in $(ls /drivers | awk -F/ '{print $1;}'); 
do
   if [ "$(lspci | grep -i $NIC)" != "" ];
   then
      echo $NIC
      exit 0
   fi
done
exit -1
   

