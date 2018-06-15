#!/bin/bash
[ -d /scripts/ ] && BASE_DIR="/scripts"  || BASE_DIR="$(dirname $0)"
PREFS="/home/usuario/.mozilla/firefox/7f5z8yc7.default/prefs.js"
STARTUP="user_pref('browser.startup.homepage', 'https://start.ubuntu-mate.org');"
STARTUP_MIO="user_pref('browser.startup.homepage', '/m/Mios/Personal/Privado/agenda.html');"
$BASE_DIR/quitarLinea.sh $PREFS "browser.startup.homepage"
echo $STARTUP_MIO >> $PREFS
cp "${PREFS}" "${PREFS}.old"
TMP=$(tempfile)
cat $PREFS | egrep -v "user.gent.override" > $TMP
mv $TMP $PREFS
/home/usuario/programas/firefox-52.5.2esr/firefox -new-tab $@
cp "$PREFS.old" "$PREFS"
