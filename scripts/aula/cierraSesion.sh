uso() {
   echo uso $0 pcxxx usuario
   echo cierra la sesión del usuario usuario en el pcxxx
   echo pcxxx xxx entre 101 y 127 para el aula grande y 201 y 222 para el aula pequeña
   echo .
   
   exit 1
}   
if [ $# -lt 2 ]; then uso; fi
ssh lliurex@$1 sudo pkill -u $2
