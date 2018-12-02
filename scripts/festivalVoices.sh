#!/bin/bash
#Setup

dir=nitech_us
mkdir $dir
cd $dir
#Download the voices
#festvox_nitech_us_"$voice"_arctic_hts-2.0.1.tar.bz2
#cmu_us_awb_arctic-0.95-release.tar.bz2	
URL="http://www.speech.cs.cmu.edu/cmu_arctic/packed/"
#cmu_us_${voice}_arctic-0.95-release.tar.bz2
URL="http://festvox.org/packed/festival/2.4/voices/"
URL="http://www.speech.cs.cmu.edu/cmu_arctic/packed/"
#cmu_us_awb_arctic-0.90-release.tar.bz2	
#for VOICE in aup awb bdl clb fem gka jmk ksp rms rxr slt; do
VOICES=$(wget -O - $URL 2>/dev/null | egrep cmu_us.*bz2 | egrep -o '<a href.*</a>' | egrep -o '".*"' | sed -e 's/"//g' | tail -7)
for VOICE in $VOICES; do
  descarga "$URL/$VOICE"
done
exit
#Unpack
for f in $(ls *.bz2); do
   tar xvf $f
done   
#Install
#sudo mkdir -p /usr/share/festival/voices/us
#sudo mv lib/voices/us/* /usr/share/festival/voices/us/
#sudo mv lib/hts.scm /usr/share/festival/hts.scm
for d in $(ls * | egrep -v bz2$); do
    mv $d /usr/share/festival/voices/us/${d}_clunits
done    
