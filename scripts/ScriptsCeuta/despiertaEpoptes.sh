#!/bin/bash


[ "$(ip a | grep 172.124.116)" ] && RED=172.124.116 || RED=172.124.117
IPS=$(sudo arp-scan -r 5 ${RED}.0/24 | grep $RED | awk '{print $1;}' | sort | uniq)

for IP in $IPS; do 
    echo ssh -p 22 profesor@$IP sudo "sudo -u ciclomedio bash -c 'export DISPLAY=:0 && epoptes-client'"
    ssh -p 22 profesor@$IP sudo "sudo -u ciclomedio bash -c 'export DISPLAY=:0 && epoptes-client&> /dev/null &'" &
done    
PIDS=$(ps aux | grep -i ssh | grep profesor | grep $RED | grep DISPLAY | grep 'epoptes-client' | grep -v grep | awk '{print $2;}')
for P in $PIDS; do 
   kill -term $P
done   
