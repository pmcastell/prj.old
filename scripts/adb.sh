#https://stackoverflow.com/questions/7789826/adb-shell-input-events
#Desbloquear móvil
adb shell input keyevent 26
#Enviar contraseña
adb shell input text 3009
#Enviar enter
adb shell input keyevent KEYCODE_ENTER
#Copiar fichero del móvil al ordenador
adb pull /storage/7D91-87D5/Mios/PELIS/peli.mp4 [<-dir-local>]
