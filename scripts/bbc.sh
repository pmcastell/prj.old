#!/bin/bash
main() {
    uso $1
    [ "$1" = "-p" ] && shift && PROXY="$1" && shift || PROXY="$(getProxy)"
    while [ "$1" != "" ]; do
        eecho get_iplayer --subtitles --proxy  $PROXY $1
        shift
    done
}    

uso() {
    if [ "$1" = "" ]; then
       echo usamos get-iplayer para bajar vídeos de BBC
       echo uso $0 '[ -p proxy ] <urls> '
       echo Ejemplo: $0 http://62.254.14.227:61399 http://www.bbc.co.uk/iplayer/episode/b00794k1/The_Nightmare_Before_Christmas/
       exit 1
    fi
}
getProxy() {
###   #PROXY=http://82.198.225.82:80
###   #PROXY=http://82.198.228.92:80
###   #PROXY=http://31.3.229.99:9090
###   #PROXY=http://81.145.129.116:3128
###   PROXY=80.194.50.123:8080
###   PROXY=178.62.193.19:3128
###   PROXY=51.38.71.101:8080
    echo "http://62.254.14.227:61399"
}    

(return 2>/dev/null) || main "$@"

###if [ "$2" = "" ]; then

###else
###   PROXY=$2
###fi   

#get-iplayer --subtitles --proxy http://82.198.225.82:80 http://www.bbc.co.uk/iplayer/episode/b00794k1/The_Nightmare_Before_Christmas/
