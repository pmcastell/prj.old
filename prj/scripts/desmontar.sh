#!/bin/bash
if [ "$1" = "" ];
then
   DIR_MONTAJE=/virtualcd
else
   DIR_MONTAJE=$2
fi      
mount -u $DIR_MONTAJE
mdconfig -d -u 1

