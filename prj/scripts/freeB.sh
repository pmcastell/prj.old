uso() {
    echo uso: $0 'start|stop'
    exit 1
}    
if [ "$1" = "start" ];
then
    if [ ! -d /l/virtualbox-enc/Free ];
    then
        sudo encfs --public /l/.virtualbox.enc /l/virtualbox-enc/
    fi
    #sudo /etc/init.d/vboxdrv setup
    sudo VBoxManage startvm Free --type headless &
    sleep 3
    rdesktop localhost
elif [ "$1" = "stop" ];
then
    sudo VBoxManage controlvm Free savestate
else
    uso
fi    
