if [ "$1" = "" ];
then
   echo usamos get-iplayer para bajar vídeos de BBC
   echo uso $0 '<url> [<proxy>]'
fi
if [ "$2" = "" ];
then
   #PROXY=http://82.198.225.82:80
   #PROXY=http://82.198.228.92:80
   #PROXY=http://31.3.229.99:9090
   #PROXY=http://81.145.129.116:3128
   PROXY=80.194.50.123:8080
else
   PROXY=$2
fi      
#get-iplayer --subtitles --proxy http://82.198.225.82:80 http://www.bbc.co.uk/iplayer/episode/b00794k1/The_Nightmare_Before_Christmas/

#eecho get-iplayer --subtitles --proxy  http://$PROXY $1
eecho get-iplayer --subtitles $1
