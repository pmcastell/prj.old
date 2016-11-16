#!/bin/bash

for ip in $(arp -n | awk '{print $1;}'); do 
   sudo arp -d $ip; 
done
