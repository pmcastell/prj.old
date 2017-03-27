if [ "$1" = "DAM1" ];
then
    RES=1280x768
elif [ "$1" = "DAM2" ];
then
    RES=1024X768
elif [ "$1" = "SMR1" ];
then
    RES=1024x768 
elif [ "$1" = "SMR2" ];
then
    RES=1024x768
else                   
    RES=$1
fi    
xrandr -s $RES
