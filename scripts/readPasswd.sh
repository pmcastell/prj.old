MODO_ANT=$(stty -g)
while true; do
   echo  "Introduzca la Contraseña y Pulse Intro: " 1>&2
   stty -echo
   read PASSWORD1
   #stty $MODO_ANT
   echo "Vuelva a introducir la Contraseña y Pulse Intro: " 1>&2
   stty -echo
   read PASSWORD2
   if [ "$PASSWORD1" = "$PASSWORD2" -a "$PASSWORD1" != "" ]; then break; fi
done   
stty $MODO_ANT
echo $PASSWORD1
