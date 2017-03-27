if [ "$1" = "stop" ];
then
   exit 0;
fi
echo selecciona opcion:
echo 1\) casa
echo 2\) insti
echo 3\) vmware
echo 4\) portatil
echo 5\) Otros
read RESP
#rm /etc/rc2.d/S99stopMio > /dev/null
case "$RESP" in
   1)
      echo Has elegido Opcion 1: Casa
      /bin/cp /etc/X11/xorg.conf.casa /etc/X11/xorg.conf
      
      /bin/cp /etc/network/interfaces.casa /etc/network/interfaces
      /bin/cp /etc/resolv.conf.casa /etc/resolv.conf
#      /bin/ln /etc/init.d/stop.sh /etc/rc2.d/S99stopMio
      ;;
   2)
      echo Has elegido Opcion 2: Instituto
      /bin/cp /etc/X11/xorg.conf.insti /etc/X11/xorg.conf
      /bin/cp /etc/network/interfaces.insti /etc/network/interfaces
      /bin/cp /etc/resolv.conf.insti /etc/resolv.conf
      ;;
   3)
      echo Has elegido Opcion 3: vmware
      /bin/cp /etc/X11/xorg.conf.vmware /etc/X11/xorg.conf
      /bin/cp /etc/network/interfaces.vmware /etc/network/interfaces
      /bin/cp /etc/resolv.conf.vmware /etc/resolv.conf
      ;;
   4) 
      echo Has elegido Opcion 4: portatil
      /bin/cp /etc/X11/xorg.conf.portatil /etc/X11/xorg.conf
      /bin/cp /etc/network/interfaces.insti /etc/network/interfaces
      /bin/cp /etc/resolv.conf.casa /etc/resolv.conf
      ;;
   5)
      echo Has elegido Opcion 4: Otros
      /bin/rm -f /etc/X11/xorg.conf
      /bin/cp /etc/resolv.conf.original /etc/resolv.conf
      ;;
esac
sleep 2
/etc/init.d/networking restart
exit 0

