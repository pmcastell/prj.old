#!/bin/bash

DESCONOCIDO="192.168.1.1"
IESN_ORIENTA="192.168.1.20"
IESN_TALLER="192.168.1.21"

IESN_CONSERG="192.168.1.105"
IESN_ENGLISH_DEPARTAMENT="192.168.1.107"
IESN_DPTO_INFORMATICA="192.168.1.109"
wget --timeout=5 --tries=2 --user=1234 --password=1234 "http://192.168.1.102/Forms/DiagGeneral_2?IsReset=1"
for i in 107 109; do
   wget --timeout=5 --tries=2 --user=1234 --password=1234 -O - "http://192.168.1.$i/webconfig/commit_reboot/commit_reboot.html/CommitReboot?rebootfrom=RBF5&reboot=Reboot" 
done
#wget --timeout=5 --tries=2 --user=1234 --password=1234 "http://192.168.1.109/webconfig/commit_reboot/commit_reboot.html/CommitReboot?rebootfrom=RBF5&reboot=Reboot"
