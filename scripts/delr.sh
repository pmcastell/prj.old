sudo route del -net $1 netmask $(route -n | grep $1 | grep -v gre | awk '{print $3}')
