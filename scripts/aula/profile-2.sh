#!/bin/sh
if [ "$(ps aux | grep -i nautilus | grep -v grep)" = "" ]; then
   nautilus -n &
fi
sleep 120
/usr/bin/gsettings set com.canonical.indicator.sound visible true &
/usr/bin/setxkbmap es &
/usr/bin/gsettings set com.canonical.indicator.session suppress-restart-menuitem false & 
/usr/bin/gsettings set com.canonical.indicator.session suppress-shutdown-menuitem true &
/usr/bin/dconf write /com/canonical/indicator/session/suppress-restart-menuitem false &
/usr/bin/dconf write /com/canonical/indicator/session/suppress-shutdown-menuitem true &
dconf write /apps/indicator-session/suppress-shutdown-menuitem true &
dconf write /apps/indicator-session/user-show-menu false
