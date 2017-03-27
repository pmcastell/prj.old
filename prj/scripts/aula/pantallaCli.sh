sudo x11vnc -noshm -24to32 -connect_or_exit 10.2.1.254:$1 -auth /var/run/lightdm/root/:0 -display :0
