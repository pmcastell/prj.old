
socat udp4-listen:5060,reuseaddr,fork tcp:127.0.0.1:5060 &
echo socat udp4-listen:5060,reuseaddr,fork tcp:127.0.0.1:5060 
echo ssh
ssh -L 5060:127.0.0.1:5060 -x -p 443 usuario@shell.cjb.net \
'socat tcp4-listen:5060,reuseaddr,fork \
UDP:sip.ovh.es:5060'

#'socat -lf /dev/null tcp4-listen:1054,reuseaddr,fork \
#UDP:208.67.222.222:53'


