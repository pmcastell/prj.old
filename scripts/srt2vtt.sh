#!/bin/bash
uso() {
   echo uso: $0 '<fichero-srt-sin-extension>'
   exit 1
}   
if [ -f $1.srt ]; then uso; fi
nodejs /home/usuario/programas/srt2vtt/node_modules/@naholyr/srt2vtt/bin/convert.js < $1.srt > $1.vtt
