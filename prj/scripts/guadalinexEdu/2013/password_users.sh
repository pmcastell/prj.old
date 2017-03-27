#!/bin/bash

TMP=$(mktemp)
SHADOW="/etc/shadow"
PASSWD="/etc/passwd"
GROUP="/etc/group"

awk -F: '{if ($1 == "root") $2="!*!"; print $1":"$2":"$3":"$4":"$5":"$6":"$7"::"}' $SHADOW > $TMP
cp $TMP $SHADOW

rm -f $TMP

/usr/sbin/useradd --create-home --shell /bin/bash profesor

# Resetea la clave del usuario 'usuario'
echo "root:francisco" | /usr/sbin/chpasswd
echo "profesor:francisco" | /usr/bin/chpasswd
echo "usuario:usuario" |  /usr/sbin/chpasswd

awk -F: '{ if($1=="sudo") $4=""; print $1":"$2":"$3":"$4}' $GROUP > $TMP
cp $TMP $GROUP
rm -f $TMP

exit 0

