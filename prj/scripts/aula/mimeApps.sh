#!/bin/bash

uso() {
   echo $0 alumno
   echo añade vlc y scratch como aplicaciones por defecto
   exit 1
}

if [ "$1" = "" ]; then uso; fi
ALUM="$1"
if [ ! -d /net/server-sync/home/students/$ALUM/Documents/.moving_profiles/.local/share/applications ]; then
   sudo mkdir -p /net/server-sync/home/students/$ALUM/Documents/.moving_profiles/.local/share/applications;
   sudo chown -R $ALUM:students /net/server-sync/home/students/$ALUM/Documents; 
fi
sudo bash -c "echo '[Added Associations]
video/mp4=vlc.desktop;
application/x-s4a-project=scratch.desktop;

[Default Applications]
video/mp4=vlc.desktop;
application/x-s4a-project=scratch.desktop;
' > /net/server-sync/home/students/$ALUM/Documents/.moving_profiles/.local/share/applications/mimeapps.list"
sudo chown -R $ALUM:students /net/server-sync/home/students/$ALUM
sudo chmod 660 /net/server-sync/home/students/$ALUM/Documents/.moving_profiles/.local/share/applications/mimeapps.list
