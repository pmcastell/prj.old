#!/bin/bash
URL="http://checkip.dyndns.org"
URL="http://www.vermiip.es"
wget -O - $URL  2> /dev/null | strings | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3}' | head -1 
