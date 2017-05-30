#!/bin/bash
[ "$2" = "" ] && LANG="en-US" || LANG="$2"
pico2wave -l=$LANG -w=/tmp/test.wav "$1"
aplay /tmp/test.wav
rm /tmp/test.wav
