#!/bin/bash 

dirIpMain() {
    URL="http://checkip.dyndns.org"
    URL="http://www.vermiip.es"
    [ "$1" = "-v" ] && VERBOSE="true" && shift
    [ "$1" != "" ] && TIMEOUT="--timeout $1"
    ULT=$(ipSites | wc -l)
    [ "$2" != "" ] && SITIO="$2" || SITIO=$(( RANDOM % $ULT + 1 ))
    URL="$(ipSitesN $SITIO)"
    [ "$VERBOSE" = "true" ] && echo $(ipSitesN $SITIO)
    USERAG="--user-agent 'Mozilla/5.0 (Windows 10; en-En; rv:57.0) Gecko/20170422 Firefox/57.0'"
    eval wget --no-check-certificate $USERAG $TIMEOUT -O - $URL  2> /dev/null | strings | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}' | head -1 
}

ipSites() {
    echo "http://checkip.dyndns.org
http://www.vermiip.es
http://ifconfig.me
http://www.cualesmiip.es
https://api.ipify.org?format=json
https://ipinfo.info/html/ip_checker.php
https://www.whatismyip.com
https://whatismyipaddress.com/
http://en.dnstools.ch/show-my-ip.html
https://www.whatismypublicip.com/
https://www.iplocation.net/
http://ipecho.net/plain" 
}
ipSitesN() {
    ipSites | head -n $1 | tail -1
}    
(return 2>/dev/null) || dirIpMain "$@"
