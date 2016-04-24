#!/bin/bash
DEST=/l/miL/gitPrj
if [ ! -d $DEST ]; then
   mkdir $DEST
   cd $DEST
   git clone https://github.com/javier-iesn/prj
fi   
cp -ruv /m/Mios/Instituto/prj $DEST/
#/m/Mios/prj/scripts/git.sh commit
