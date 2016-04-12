#!/bin/bash
if [ "$(/bin/netstat -onatup 2>&1 | /bin/grep 8888)" = "" ];
then
   /usr/bin/ssh -ND 8888 franc@localhost
fi

