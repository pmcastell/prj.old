#!/bin/bash
PREFS="/home/usuario/.mozilla/firefox/7f5z8yc7.default/prefs.js"
cp "${PREFS}" "${PREFS}.old"
TMP=$(tempfile)
cat $PREFS | egrep -v "user.gent.override" > $TMP
mv $TMP $PREFS
/home/usuario/programas/firefox-52.5.2esr/firefox
cp "$PREFS.old" "$PREFS"
