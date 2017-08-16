#!/bin/bash

uso() {
   cat<<EOF
   Uso: $1
EOF
   exit 1
}
appendSiNoEsta() {
   CAD="$1"
   FICH="$2"
   [ ! -f "$FICH" ] && touch $FICH
   if [ "$(cat "$FICH" | grep -F "$CAD")" = "" ]; then
      echo "$CAD" >> "$FICH"
   fi
}
crontab_encabezado() {
echo "# Edit this file to introduce tasks to be run by cron.
# 
# Each task to run has to be defined through a single line
# indicating with different fields when the task will be run
# and what command to run for the task
# 
# To define the time you can provide concrete values for
# minute (m), hour (h), day of month (dom), month (mon),
# and day of week (dow) or use '*' in these fields (for 'any').# 
# Notice that tasks will be started based on the cron's system
# daemon's notion of time and timezones.
# 
# Output of the crontab jobs (including errors) is sent through
# email to the user the crontab file belongs to (unless redirected).
# 
# For example, you can run a backup of all your user accounts
# at 5 a.m every week with:
# 0 5 * * 1 tar -zcf /var/backups/home.tgz /home/
# 
# For more information see the manual pages of crontab(5) and cron(8)
# 
# m h  dom mon dow   command"
}
appendCrontab() {
   USUARIO=$1
   NEWLINE=$2
   TEMP=$(tempfile)
   if [ "$(sudo -u $USUARIO crontab -l 2>&1 | grep -i 'no crontab')" != "" ] || [ "$(sudo -u $USUARIO crontab -l 2>&1)" = "" ]; then crontab_encabezado > $TEMP; fi 
   sudo -u $USUARIO crontab -l 2>&1 | grep -v 'no crontab' >> $TEMP
   appendSiNoEsta "$NEWLINE" $TEMP
   cat $TEMP | sudo -u $USUARIO crontab -
   #(cat $TEMP; sudo crontab -l 2>&1 | grep -v tunelSsh6 ; echo '*/5 *   *   *   *    /root/tunelSsh6.sh &> /dev/null' ) | sudo crontab -
   rm $TEMP
}   
uuid() {
   #9f0f0692-5f52-11e7-bd87-73727a1c258e
   HEX_DIGITS=(0 1 2 3 4 5 6 7 8 9 a b c d e f)
   UUID=""
   for((i=1;i<=8;i++)); do
      R=${HEX_DIGITS[$(( RANDOM % 16 ))]};
      UUID="${UUID}${R}"
   done
   UUID="${UUID}-"
   for((i=1;i<=3;i++)); do
      for((j=1;j<=4;j++)); do
         R=${HEX_DIGITS[$(( RANDOM % 16 ))]};
         UUID="${UUID}${R}"
      done
      UUID="${UUID}-"
   done
   for((i=1;i<=12;i++)); do
      R=${HEX_DIGITS[$(( RANDOM % 16 ))]};
      UUID="${UUID}${R}"
   done
   echo $UUID
}   
espacios() {
   CAD=$1
   n=$2
   ns=$(( $n - ${#CAD}))
   for((i=0;i<$ns;i++)); do
      CAD="$CAD "
   done
   echo "$CAD"
}
codPais() {
   PAIS="$1";shift
   while [ "$1" != "" ]; do  
      PAIS="${PAIS} $1"; shift; 
   done
   while read L; do
      P=$(echo $L | awk '{for(i=1;i<=(NF-4);i++) printf "%s ",$i;}')
      if [ "$P" = "$PAIS " ]; then
         echo $L | awk '{ print tolower($(NF-3));}'
         return
      fi
   done < /scripts/codigosPaises.txt
}  

