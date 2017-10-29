#!/bin/bash

[ "$1" = "" ] && TARGET="192.168.1.1" || TARGET="$1"
sudo nmap --script=http-enum-telefonica-homestation.nse -p80,443 -sS $TARGET
sudo nmap --script=http-enum-telefonica-comtrend-vg-8050.nse -p80,443 -sS $TARGET
sudo nmap --script=http-enum-vodafone-hua253s.nse -p80,443 -sS $TARGET
sudo nmap --script smb-brute -sV -p 445 $TARGET

