#!/bin/sh
uso() {
   echo Uso: $0 '<tiempo-en-segundos-para-cerrar-la-sesión>'
   exit 1
}


if [ "$1" = "" ]; then uso; fi
sleep 60
gsettings set org.gnome.settings-daemon.plugins.power sleep-inactive-ac-timeout  $1
gsettings set org.gnome.settings-daemon.plugins.power sleep-inactive-ac-type logout

#gsettings set org.gnome.desktop.screensaver idle-activation-enabled false
#gsettings set org.gnome.desktop.screensaver lock-delay 0
#gsettings set org.gnome.desktop.screensaver lock-enabled false

#gsettings set org.gnome.desktop.screensaver logout-command gnome-session-quit
#gsettings set org.gnome.desktop.screensaver logout-delay 3600
#gsettings set org.gnome.desktop.screensaver logout-enabled true

#gsettings set org.gnome.desktop.screensaver logout-command gnome-session-quit
#gsettings set org.gnome.desktop.screensaver ubuntu-lock-on-suspend false


TEMP=$(tempfile)
rm $TEMP
TEMP="${TEMP}${USER}"
echo $USER > $TEMP
