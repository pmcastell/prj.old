#ping -c 4 checkip-ewr.dyndns.com
#inadyn -u franc2 -p basura -a franc.ath.cx --iterations 1
#inadyn -u franc2 -p basura -a franc.dvrdns.org --iterations 1
ping -c 1 172.16.1.14 &> /dev/null
if [ "$?" = "" ];
then
    ssh franc@iesinclan.dyndns.org /usr/sbin/inadyn -u franc2     -p basura68  -a  franc.dyndns.org --iterations 1
    ssh franc@iesinclan.dyndns.org /usr/sbin/inadyn -u cyane007  -p lavidaperra -a cristi007.ath.cx --iterations 1 
    #ssh franc@iesinclan.dyndns.org /usr/sbin/inadyn -u iesinclan  -p 654321 -a formacion.dyndns.org --iterations 1 
    #ssh franc@iesinclan.dyndns.org /usr/sbin/inadyn -u casapaqui6 -p basura -a casapaqui.dyndns.org --iterations 1
else
    if [ "$(ifconfig | grep 172.16.1.14 | grep -v grep)" != "" ];
    then
       /usr/sbin/inadyn -u franc2    -p basura68    -a franc.dyndns.org --iterations 1
       /usr/sbin/inadyn -u cyane007  -p lavidaperra -a cristi007.ath.cx --iterations 1 
       #/usr/sbin/inadyn -u iesinclan  -p 654321 -a formacion.dyndns.org --iterations 1 
       #/usr/sbin/inadyn -u casapaqui6 -p basura -a casapaqui.dyndns.org --iterations 1
    else
       ssh usuario@172.16.1.14 /m/Mios/prj/scripts/dynDns.sh
    fi
fi    
ping -c 2 www.criado.tk
ping -c 2 www.iesinclan.tk
        