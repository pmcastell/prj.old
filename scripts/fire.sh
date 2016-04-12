#FICH_PREFS=/home/usuario/.mozilla/firefox/7f5z8yc7.default/prefs.js

#echo 'user_pref("general.appname.override", "Netscape");' >> $FICH_PREFS
#echo 'user_pref("general.appversion.override", "5.0 (Windows; en-US)");' >> $FICH_PREFS
#echo 'user_pref("general.platform.override", "Win32");' >> $FICH_PREFS
#echo 'user_pref("general.useragent.appName", "Mozilla");' >> $FICH_PREFS
#echo 'user_pref("general.useragent.extra.firefox", "Firefox/4.0");' >> $FICH_PREFS
#echo 'user_pref("general.useragent.override", "Mozilla/4.0 (Windows NT 6.1; rv:2.0b7pre) Gecko/20100921 Firefox/4.0b7pre");' >> $FICH_PREFS
#echo 'user_pref("general.useragent.vendor", "");' >> $FICH_PREFS
#echo 'user_pref("general.useragent.vendorSub", "");' >> $FICH_PREFS

firefox http://systemdetails.com/browser.php $1 $2 $3 $4 $5 $6 $7 $8 $9 &


