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
cmu_us_awb_arctic-0.90-release.tar.bz2	
#for VOICE in aup awb bdl clb fem gka jmk ksp rms rxr slt; do
for VOICE in awb bdl clb jmk ksp rms slt; do
  descarga "$URL/festvox_cmu_us_${VOICE}_cg.tar.gz"
done
exit
#Unpack
for f in $(ls *.bz2); do
   tar xvf $f
done   
#Install
sudo mkdir -p /usr/share/festival/voices/us
sudo mv lib/voices/us/* /usr/share/festival/voices/us/
sudo mv lib/hts.scm /usr/share/festival/hts.scm
