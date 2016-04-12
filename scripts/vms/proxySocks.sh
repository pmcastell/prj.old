#!/bin/bash
if [ "$(/bin/netstat -onatup 2>&1 | /bin/grep 8888)" = "" ];
then
   /usr/bin/ssh -ND 172.16.1.9:8888 profe@localhost &> /dev/null &
fi
if [ "$(/bin/netstat -onatup 2>&1 | /bin/grep 5999)" = "" ];
then
   /usr/bin/ssh -L 172.16.1.9:5999:cvsup.de.freebsd.org:5999 -N profe@localhost &> /dev/null &
fi
if [ "$(/bin/netstat -onatup 2>&1 | /bin/grep 6881)" = "" ];
then
#    /usr/bin/ssh -L 172.16.1.9:6881:10.1.0.22:6881 -A -N profe@localhost &> /dev/null &
fi
if [ "$(/bin/netstat -onatup 2>&1 | /bin/grep 46005)" = "" ];
then
#    /usr/bin/ssh -L 172.16.1.9:46005:10.1.0.22:46005 -A -N profe@localhost &> /dev/null &
fi

