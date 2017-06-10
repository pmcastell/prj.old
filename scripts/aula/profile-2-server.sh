#!/bin/sh
#sleep 120
maxima_resolucion() {
/usr/bin/xrandr -q | egrep -vw "connected" | egrep -o '[0-9]{1,4}x[0-9]{1,4}'  | sort -r -n | ( read R1; while read R2; do
   [ "$R1" = "$R2" ] && echo $R1 && break
   R1=$R2
done)
}
while [ "$USER" = "" ]; do sleep 1; done
USUARIO="$USER"
/usr/bin/setxkbmap es &
/usr/bin/gsettings set com.canonical.indicator.sound visible true &> /dev/null &
/usr/bin/gsettings set com.canonical.indicator.session suppress-restart-menuitem true &> /dev/null & 
/usr/bin/gsettings set com.canonical.indicator.session suppress-shutdown-menuitem true &> /dev/null &
/usr/bin/gsettings set com.canonical.indicator.session user-show-menu false &> /dev/null &
/usr/bin/gsettings set com.canonical.notify-osd gravity 2 &> /dev/null &

/usr/bin/dconf write /com/canonical/indicator/sound visible true &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/session suppress-restart-menuitem true &> /dev/null & 
/usr/bin/dconf write /com/canonical/indicator/session suppress-shutdown-menuitem true &> /dev/null &
/usr/bin/dconf write /com/canonical/indicator/session user-show-menu false &> /dev/null &
/usr/bin/dconf write /com/canonical/notify-osd gravity 2 &> /dev/null &

MONITOR1="$(xrandr -q | grep -w connected | awk '{print $1;}' | head -1)"
MONITOR2="$(xrandr -q | grep -w connected | awk '{print $1;}' | tail -1)"
([ "$MONITOR1" = "" ] || [ "$MONITOR2" = "" ] || [ "$MONITOR1" = "$MONITOR2" ]) && exit 2
MAX_RES_COMUN=$(maxima_resolucion)
[ "$MAX_RES_COMUN" = "" ] && exit 2
###[ -f /tmp/profile2.txt ] && rm /tmp/profile2.txt
 
INTENTOS=0
MAX_INTENTOS=20
while true; do
   /usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES_COMUN --output $MONITOR2 --mode $MAX_RES_COMUN --same-as $MONITOR1 
   echo /usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES_COMUN --output $MONITOR2 --mode $MAX_RES_COMUN --same-as $MONITOR1 
   echo xrandr | grep '\*' | head -1  | awk '{print $1;}'   
   #[ "$(xrandr | grep '\*' | head -1  | awk '{print $1;}')" = "$MAX_RES_COMUN" ] && break
   RES1="$(xrandr | grep '\*' | head -1  | awk '{print $1;}')" 
   RES2="$(xrandr | grep '\*' | tail -1  | awk '{print $1;}')" 
   [ "$RES1" = "$MAX_RES_COMUN" ]  && [ "$RES2" = "$MAX_RES_COMUN" ]  && [ "$INTENTOS" -gt $MAX_INTENTOS ] && break
   INTENTOS=$(($INTENTOS + 1))
   [ "$USUARIO" != "$USER" ] && exit
   ###echo "INTENTOS: $INTENTOS. MAX_RES_COMUN: $MAX_RES_COMUN. USUARIO: $USER. RES1: $RES1. RES2: $RES2." >> /tmp/profile2.txt
   sleep 1
done   
exit 0

#[ "$MAX_RES_COMUN" != "" ] && echo "/usr/bin/xrandr --output $MONITOR1 --mode $MAX_RES_COMUN --output $MONITOR2 --mode $MAX_RES_COMUN --same-as $MONITOR1. USUARIO: $USER" >> /tmp/xrandr.txt &
#while [ "$(hostname)" = "" ]; do sleep 1; done
#rm /tmp/xrandr.txt
INTENTOS=0
if [ "$(hostname)" = "aula1srv" ]; then
   while true; do
      /usr/bin/xrandr --output VGA1 --mode 1440x900 --output HDMI1 --mode 1440x900 --same-as VGA1
      echo '/usr/bin/xrandr --output VGA1 --mode 1440x900 --output HDMI1 --mode 1440x900 --same-as VGA1' >> /tmp/xrandr.txt
      echo "USUARIO: $USER" >> /tmp/xrandr.txt
      xrandr | grep '\*' | head -1  | awk '{print $1;}' >> /tmp/xrandr.txt
      [ "$(xrandr | grep '\*' | head -1  | awk '{print $1;}')" = "1440x900" ] && [ "$(xrandr | grep '\*' | tail -1  | awk '{print $1;}')" = "1440x900" ]  && [ "$INTENTOS" -lt 60 ] && break
      INTENTOS=$(($INTENTOS + 1))
      sleep 1
   done
else
   /usr/bin/xrandr --output VGA-0 --mode 1920X1080 --output DVI-0 --mode 1920X1080 --same-as VGA-0
   echo '/usr/bin/xrandr --output VGA-0 --mode 1920X1080 --output DVI-0 --mode 1920X1080 --same-as VGA-0' >> /tmp/xrandr.txt
fi
echo acabo de ejecutar xrandr con el usuario $USER >> /tmp/xrandr.txt &

