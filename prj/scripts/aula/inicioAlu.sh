#esto son comentarios
USER=$(whoami)
chmod o+r,o+x /home/$USER
chmod o+r,o+x /home/$USER/Escritorio
chmod o+r,o+x /home/$USER/Escritorio/INFORMATICA
chmod o+r,o+x /home/$USER/Escritorio/INFORMATICA/php
chmod -R +r /home/$USER/Escritorio/INFORMATICA/php/*
TEMP=$(tempfile)
find /home/$USER/Escritorio/INFORMATICA/php -type d > $TEMP
while read DIR; do
   #echo "DIR: $DIR"
   chmod +r,+x "$DIR"
done < $TEMP
   
#for DIR in $(find /home/$USER/Escritorio/INFORMATICA/php -type d); do
#   echo "DIR: $DIR"
#   chmod +r,+x "$DIR"
#done   
