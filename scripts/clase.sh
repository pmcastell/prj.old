mataClase() {
    PID=$(ps aux | grep -i x11vnc | grep 5900 | grep -v grep | awk '{print $2;}' | head -1)
    while [ "$PID" != "" ]; do
        kill -TERM $PID
        PID=$(ps aux | grep -i x11vnc | grep 5900 | grep -v grep | awk '{print $2;}' | head -1)
    done
}    
if [ "$1" = "stop" ]; then
    mataClase
    sudo firewall
else    
    echo Elige clase
    echo '1) DAM1'
    echo '2) DAM2'
    echo '3) SMR2'
    echo '4) Otros (sólo activa x11vnc no el firewall)'
    read clase
    clase="$(menu DAM1 DAM2 SMR2)"
    case $clase in
        1) sudo firewall DAM1
        ;;
        2) sudo firewall DAM2
        ;;
        3) sudo firewall SMR2
        ;;
    esac
    mataClase
    x11vnc -rfbport 5900 -reopen  -viewonly -shared  -forever -loop &
fi        
