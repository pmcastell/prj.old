#!/bin/bash
uso() {
   cat<<EOF
   uso: $0 <on|off>
   Activa o desactiva el proxy en los clientes
EOF
   exit 1
}

if [ "$1" = "" ]; then uso; fi
if [ "$(whoami)" != "root" ]; then sudo $0 "$@"; exit $?; fi

if [ "$1" = "on" ]; then
   echo 'function FindProxyForURL(url,host)
{
	if (isInNet(host, "10.2.1.0",  "255.255.255.0") ||
	    isInNet(host, "127.0.0.1", "255.255.255.255") ||
	    dnsDomainIs(host, "aula1") ||
	    (url.substring (0, 5) == "feed:"))
	{
	    return "DIRECT";
	}
	else{
	    return "PROXY proxy:3128";
	}
}
' > /var/www/proxy.pac
elif [ "$1" = "off" ]; then
   echo 'function FindProxyForURL(url,host)
{
	    return "DIRECT";
}
' > /var/www/proxy.pac
   sudo enrutamiento on
fi
      
