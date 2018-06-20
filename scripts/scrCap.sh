#!/bin/bash

DATE=`date +%s`
BASE=$(dirname $0)
scrot "$HOME/Imágenes/scr-$DATE.png"
mplayer $BASE/camera_click.ogg

