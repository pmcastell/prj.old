#5.1. Converting subtitles to text format
#Very often you don't want to be forced to see the subtitles. This is not possible if you include the subtitles in the picture during encoding. You have #to extract the subtitles from the DVD into an external file/stream that the user can activate (or not). I will describe the process of converting the DVD #subtitles into a text format that is widely used. Text subtitles can be easily scaled by the player (by selecting an appropriate font) and they are #really small (most often below 100KB).
#
#For this process you must have transcode and its sources. You need tccat and tcextract from transcode itself and the files in transcode/contrib/subrip #from the transcode sources.
#5.1.1. Compiling the tools
#Unfortunately no binary package (RPM, deb) that I know of includes subrip so we have to compile and install it ourselves. But this is rather easy.
#
#   1. Make sure you have gocr and ispell installed on your system. They come with most modern distributions. But if yours does not contain gocr then head #over to its website.
#   2. Change into subrip's source directory (cd transcode/contrib/subrip) and invoke make.
#   3. Copy the three programs srttool, subtitle2pgm and pgm2txt to a directory in your PATH.
#   4. You may have to change the script pgm2txt if your gocr does not support the -p option: at the end there are two lines containing -p ${DBPATH}. #Simply remove it (after consultin gocr's manpage).
#
#5.1.2. Extracting the subtitle stream
#Here I assume that you've copied your DVD with vobcopy -m meaning that it has been completely mirrored including the .IFO files. If not then you'll have #to adjust the sources.
#
#First let's see which subtitles are available. We can use mplayer for this task:
#mplayer -dvd-device /space/st-tng/disc1/ -dvd 1 -vo null -ao null -frames 0 -v 2>&1 | grep sid
#This causes mplayer to just print a lot of information about the source and not to play anything at all. It should give you a list of subtitles:
#[open] subtitle ( sid ): 0 language: da
#[open] subtitle ( sid ): 1 language: de
#[open] subtitle ( sid ): 2 language: en
#[open] subtitle ( sid ): 3 language: es
#[open] subtitle ( sid ): 4 language: fr
#[open] subtitle ( sid ): 5 language: it
#[open] subtitle ( sid ): 6 language: nl
#[open] subtitle ( sid ): 7 language: no
#[open] subtitle ( sid ): 8 language: sv
#[open] subtitle ( sid ): 9 language: en
#
#Now that we have the sid (subtitle ID) for the language that we want we can fire up the transcode tools and let them extract the raw subtitle stream:
#tccat -i /space/st-tng/dic1/ -T 1 -L | tcextract -x ps1 -t vob -a 0x22 > subs-en
#The -a 0x21 is the subtitle stream's hexadecimal number: 0x20 + sid. Here I use the English subtitles.
#5.1.3. Converting the raw stream
#Ok, we have a raw subtitle stream - but what can we do with it? First we have to convert each subtitle entry into a picture. This can be easily done with
#subtitle2pgm -o english -c 255,255,0,255 < subs-en
#Here's a catch however. With -c you can specify the grey levels used in the conversion. The idea is to make the job for gocr as easy as possible. #Therefore you might have to experiment with the parameters - but this is easy, too. I've taken the following samples from my Star Trek - The Next #Generation DVD:
#
#    * -c 0,255,255,255 - this is obviously wrong.
#    * -c 255,0,255,255 - this looks good.
#    * -c 255,255,0,255 - we don't want this one.
#    * -c 255,255,255,0 - we don't want this either.
#
#As you can see you need a picture that does not contain outlined characters.
#
#subtitle2pgm creates a lot of images - one for each subtitle - and a control file, called english.srtx in my case, that contains the duration for each #subtitle. The next step is to let gocr recognize the text:
#pgm2txt english
#Be warned - gocr will ask you often about charcters that it can't recognize. This is normal. Once you're done you should run ispell over all the newly #created text files:
#ispell -d american english*txt
#Adjust the languange to your needs, of course.
#
#The last step is to let srttool include the actual text into the .srtx file:
#srttool -s -w < english.srtx > english.srt
#
#Voila, you have a working subtitle file. You can watch them with e.g.
#mplayer -sub english.srt mymovie.avi
uso() {
   echo $0 '<carpeta-origen> <titulo> <0x22=ingles> <fichero-salida>'
   exit 1
}
if [ "$1" = "" -o "$2" = "" -o "$3" = "" -o "$4" = "" ];
then
   uso
fi    
IDIOMA=english
if [ "$2" = "0x22" ];
then
   IDIOMA=english
fi     
#tccat -i $CARPETA_ORIGEN -T 1 -L | tcextract -x ps1 -t vob -a 0x22 > subs-en

tccat -i $1 -T $2  | tcextract -x ps1 -t vob -a $3 > $4
subtitle2pgm -o $IDIOMA -c 255,255,0,255 < $4
pgm2txt $IDIOMA
ispell -d american $IDIOMA*txt
srttool -s -w < $IDIOMA.srtx > $IDIOMA.srt

