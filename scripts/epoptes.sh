#!/bin/bash

(epoptes &) ; sleep 1.5 && xdotool type franav && xdotool key Tab && xdotool type $(/home/franav/aula/claves.sh franav) && xdotool key KP_Enter

