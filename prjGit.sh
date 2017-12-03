#!/bin/bash
DEST=/l/gitPrj
[ ! -d $DEST ] && echo No está montada l && exit
if [ ! -d $DEST ]; then
   mkdir $DEST
   cd $DEST
   git clone https://github.com/javier-iesn/prj
fi  
#rsync -rv /m/Mios/Instituto/prj $DEST/
cp -ruv /m/Mios/Instituto/prj $DEST/
cd $DEST/prj
/m/Mios/prj/scripts/git.sh commit
