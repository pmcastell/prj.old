#PAIS=$(wget http://www.cualesmiip.es -O - 2>/dev/null | grep -oE 'Tu pais es: [a-zA-Z\&\; \(\)]+')
#for entity in '&acute;/á' '&eacute;/é' '&iacute;/í' '&oacute;/ó' '&uacute;/ú' '&Acute;/Á' '&Eacute;/É' '&Iacute;/Í' '&Oacute;/Ó' '&Uacute;/Ú' #'&ntilde;/ñ' '&Ntilde;/Ñ';
#do 
#   PAIS=$(echo "$PAIS" | sed -e s/$entity/g)
#done  
#echo $PAIS 
TEMP=/tmp/geopitool.temp
wget -O - http://geoiptool.com/ &> $TEMP
cat $TEMP | grep -i country | grep '<div>' | awk -F'<div>' '{print $2;}' | sed -e 's/Country/Pais/g' | sed -e 's/<\/div>";//g'
cat $TEMP | grep -i "ip address" | grep '<div>' | awk -F'<div>' '{print $2;}' | sed -e 's/Country/Pais/g' | sed -e 's/<\/div>";//g'
rm $TEMP

