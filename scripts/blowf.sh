#!/bin/bash


#php -r 'echo password_hash("15Galileo64*",PASSWORD_DEFAULT);'
[ "$1" != "" ] && PASS="$1" || read PASS < /dev/stdin
php -r "echo password_hash('$1',PASSWORD_DEFAULT); echo \"\n\";"
