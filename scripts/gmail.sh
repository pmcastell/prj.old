#!/bin/bash

username="fcriado"
password="$(cat /m/Mios/prj/scripts/passBas.txt | desencripta -k 10)"

curl -u $username:$password --silent "https://mail.google.com/mail/feed/atom" |  grep -oPm1 "(?<=<title>)[^<]+" | sed '1d'
