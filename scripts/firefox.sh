#/usr/bin/firefox --new-window &
#sleep 5
FIREFOX="/opt/firefox/firefox"
FIREFOX="/usr/bin/firefox "
#$FIREFOX -private &
$FIREFOX -private -new-tab file:///m/Mios/Instituto/horarios/html/agenda.html "$@"  &
#/usr/bin/firefox -private -new-window http://sepie.es/iniciativas/europass/concursos.html &
[ -e /tmp/keypass2.lock ] && echo salgo && exit 0
[ "$(pgrep keypass2)" = "" ] && [ ! -e /tmp/keypass2.lock ] && touch /tmp/keypass2.lock && /usr/bin/keepass2 
rm /tmp/keypass2.lock
