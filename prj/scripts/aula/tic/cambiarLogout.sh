#!/bin/sh
uso() {
   echo Uso: $0 '<tiempo-en-segundos-para-cerrar-la-sesión>'
   exit 1
}


if [ "$1" = "" ]; then uso; fi
sleep 60
gsettings set org.gnome.settings-daemon.plugins.power sleep-inactive-ac-timeout  $1
gsettings set org.gnome.settings-daemon.plugins.power sleep-inactive-ac-type logout
echo $USER > /tmp/usuario.txt
