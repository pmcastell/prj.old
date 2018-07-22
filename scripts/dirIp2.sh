#!/bin/bash

#wget http://www.cualesmiip.es -O - 2>/dev/null | strings | grep -oE '([0-9]{1,3}\.){3}([0-9]{1,3})'
[ "$1" != "" ] && TIMEOUT="--connect-timeout $1"
curl $TIMEOUT http://www.cualesmiip.es  2>/dev/null | strings | grep -oE '([0-9]{1,3}\.){3}([0-9]{1,3})'
