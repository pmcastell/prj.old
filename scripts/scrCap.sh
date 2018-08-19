#!/bin/bash

DATE=`date +%s`
DATE=$(date  +'%Y-%m-%d_%H:%M:%S')
BASE=$(dirname $0)
scrot "$HOME/Imágenes/scr-$DATE.png"
mplayer $BASE/camera_click.ogg

