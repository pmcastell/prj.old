#!/bin/bash
TEMP=$(tempfile)
ls -l /usr/share/themes/ | grep -v total | awk '{print $NF;}' > $TEMP
ls -l /home/usuario/.themes/ | grep -v total | awk '{print $NF;}' >> $TEMP
TEMP2=$(tempfile)
cat $TEMP | sort > $TEMP2
while read T; do 
    TEMAS="$TEMAS $T"
done < $TEMP2
#TEMAS="$(ls -l /usr/share/themes/ | grep -v total | awk '{print $NF" | ";}') $(ls -l /home/usuario/.themes/ | grep -v total | awk '{print $NF" | ";}')"
#echo $TEMAS
#TEMAS="${TEMAS:0:${#TEMAS}-2}"
#TEMAS="${TEMAS:2}"

#echo $TEMAS && read
#TEMA=$(zenity --forms --title="Seleccionar Tema" --text="Temas:" --add-list "Temas Disponibles:" --list-values "$TEMAS" --width=1024 --height=300 --add-entry="    f        o        o        ")
TEMA=$(zenity --list --column "Seccionar Tema" --height 400 --width 1024 $TEMAS)
TEMA="$(echo $TEMA | awk '{print $1;}')"
echo $TEMA
[ "$TEMA" != "" ] && gsettings set org.gnome.desktop.interface gtk-theme \'$TEMA\'
