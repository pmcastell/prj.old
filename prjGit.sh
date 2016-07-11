#!/bin/bash
DEST=/l/miL/gitPrj
[ ! -f /l/pagefile.sys ] && echo No está montada l && exit
if [ ! -d $DEST ]; then
   mkdir $DEST
   cd $DEST
   git clone https://github.com/javier-iesn/prj
fi  
cp -ruv /m/Mios/Instituto/prj $DEST/
cd $DEST/prj
/m/Mios/prj/scripts/git.sh commit
