#!/bin/bash

main() {
    DEST="/etc/grub.d"
    #sudo scriptHalt $DEST/60_mioHalt
    sudo scriptGrubPass usuario
    
}    



scriptHalt() {
echo "
echo '

menuentry \"Halt\" --unrestricted {
   halt
}



'
" > $1
}

scriptGrubPass() {
    USU="$1"
    echo "Introduzca la contraseña dos veces dándole al Intro cada vez."
    PASS="PASS=$(grub-mkpasswd-pbkdf2)"
    PASS=$(echo $PASS | awk '{print $NF;}')
    echo "
    echo '
set superusers=\"$USU\"
password_pbkdf2 $USU $PASS 
'
"
}

    

(return 2>/dev/null) || main "$@"
