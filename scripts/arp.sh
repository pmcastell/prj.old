IF=$(interfaces | grep -i eth1 | grep -v grep | head -1)
arping -I $IF $1
