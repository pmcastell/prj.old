wget http://checkip.dyndns.org -O - 2> /dev/null | strings | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}'   
