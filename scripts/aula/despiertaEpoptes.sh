#!/bin/bash

uso() {
   cat<<EOF
   Uso: $0 [<pc-ip>|<pc-name>]
   Ejemplo: $0 10.2.1.103 (despierta el epoptes del pc103
   Ejemplo: $0 pc103      (Ídem)
EOF
   exit 1
}
if [ $# -lt 1 ]; then uso; fi      
#xdotool key --delay 0 --clearmodifiers ctrl+c | 
ssh lliurex@$1 sudo /etc/init.d/epoptes-client restart
