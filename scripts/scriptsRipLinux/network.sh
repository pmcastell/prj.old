DRIVER=""
#NIC=$(lspci | grep -i acx)
#if [ "$NIC" != "" ];
#then
#   DRIVER=acx
#else
#   NIC=$(lspci | grep -i rt2500)
#   if [ "$NIC" != "" ];
#   then
#      DRIVER=rt2500
#   else
#      NIC=$(lspci | grep -i atheros)
#      if [ "$NIC" != "" ];
#      then
#         DRIVER=atheros
#      fi
#   fi   
#fi
DRIVER=$(/scripts/nic.sh)
if [ "$DRIVER" = "" ];
then
   /sbin/dhcpcd -t 10
else
   /usr/sbin/ndiswrapper -i $(ls /drivers/$DRIVER/* | grep -i inf)
   /sbin/modprobe ndiswrapper
   ndiswrapper -m
   iwlist wlan0 scan > /tmp/scan.txt
   AP=$(cat /tmp/scan.txt | grep "No scan results")
   if [ "$AP" = "" ];
   then
      AP=$(cat /tmp/scan.txt | grep ZULO)
      if [ "$AP" != "" ];
      then 
         iwconfig wlan0 mode Managed channel 1 essid ZULO key FAA1234567
      else 
         iwconfig wlan0 mode Managed channel 4 essid Angela
      fi   
      if [ $? = 0 ];
      then
         conexion=falso
         intentos=0
         while [ "$conexion" = "falso" -a "$intentos" != "3" ];
         do
            sleep 1
            /sbin/dhcpcd wlan0 -t 5
            if [ $? = 0 ];
            then
               conexion=cierto
            fi
	    intentos=$(expr $intentos + 1)
         done   
         if [ "$conexion" = "falso" ];
         then
            echo No he podido utilizar la conexión inlámbrica
            echo Inténtalo manualmente
         fi
      else
	     echo No se detecta ningún punto de Acceso Inalámbrico
		 echo Comprueba que la tarjeta está funcionando bien y que la
		 echo antena está bien conectada
      fi
   fi	  
fi   
