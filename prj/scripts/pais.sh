if [ "$1" != "" ]; then
   ENTITIES=('&ntilde;/ñ' '&Ntilde;/Ñ' '&acute;/á' '&eacute;/é' '&iacute;/í' '&oacute;/ó' '&uacute;/ú' '&Acute;/Á' '&Eacute;/É' '&Iacute;/Í' '&Oacute;/Ó' '&Uacute;/Ú' )
   for((i=1;i<=5;i++)); do
      #<h1 align="center">Tu IP es</h1><h1 align="center">88.206.169.13</h1> <h2 align="center">Tu pais es: Suecia (SE)</h2>  </td>
      INFO=$(curl http://www.cualesmiip.es 2> /dev/null | strings | grep -i "Tu ip es")
      IP=$(echo $INFO | grep -oE '([0-9]{1,3}\.?){4}')
      PAIS=$(echo $INFO | grep -oE 'Tu pais es: [a-zA-Z\&\; \(\)]+')
      if [ "$PAIS" != "" ]; then break; fi
   done
   if [ "$PAIS" != "" ]; then
      for((i=0;i<${#ENTITIES[@]};i++)); do
         entity=${ENTITIES[$i]}
         PAIS=$(echo "$PAIS" | sed -e s/$entity/g)
      done  
   fi
   echo "IP: $IP"
   echo $PAIS 
else 
   for((i=1;i<=5;i++)); do
      INFO=$(wget --no-check-certificate -O - http://geoiptool.com/ 2> /dev/null)
      if [ "$INFO" != "" ]; then break; fi
   done
   #echo $INFO | grep -i country | grep '<div>' | awk -F'<div>' '{print $2;}' | sed -e 's/Country/Pais/g' | sed -e 's/<\/div>";//g'
   echo IP: $(echo $INFO | grep -oE '([0-9]{1,3}\.){3}[0-9]{1,3} | head -1')
   echo $INFO | grep -i "ip address" | grep '<div>' | awk -F'<div>' '{print $2;}' | awk -F '</div>' '{print $1;}' | sed -e 's/Country/Pais/g' | sed -e 's/<\/div>";//g'
fi

