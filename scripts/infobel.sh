#cd cd /home/usuario/.wine/drive_c/Archivos\ de\ programa/Infobel/Infobel\ Espana\ Office\ v7.1/
if [ ! -f /cd-virtual/Setup.exe ];
then
   sudo mount -o loop /l/ImagenesCdDvd/Infobel.7.1.03407UN-61400080612.iso /cd-virtual/
fi   
wine "c:\Archivos de programa\Infobel\Infobel Espana Office v7.1\Infobel Espana Office v7.1.exe" &
