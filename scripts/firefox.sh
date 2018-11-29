#/usr/bin/firefox --new-window &
#sleep 5
/usr/bin/firefox --new-window --private file:///m/Mios/Instituto/horarios/html/agenda.html "$@"  &
[ -e /tmp/keypass2.lock ] && echo salgo && exit 0
[ "$(pgrep keypass2)" = "" ] && [ ! -e /tmp/keypass2.lock ] && touch /tmp/keypass2.lock && /usr/bin/keepass2 
rm /tmp/keypass2.lock
