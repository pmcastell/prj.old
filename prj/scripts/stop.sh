for servicio in squid3 apache2 samba mysql tor ssh cupsys;
do
   /etc/init.d/$servicio stop
done
VINO_PID=$(ps ax | grep -i vino | grep -v grep | gawk '{print $1;}')
if [ "$VINO_PID" != "" ];
then
   gconftool-2 -s -t bool /desktop/gnome/remote_access/enabled false
   gconftool-2 -s -t bool /desktop/gnome/remote_access/disabled true
   kill -TERM $(ps ax | grep -i vino | grep -v grep | gawk '{print $1;}')
fi



