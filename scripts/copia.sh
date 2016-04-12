USER=$(ps aux | grep $0 | grep -v sudo | grep -v grep | awk '{print $1;}')
echo $USER
read pepe
if [ "$USER" != "root" ];
then
   $0 $*
   exit 0
fi
TEMP=/tmp/listaFicherosOdtDocx.txt
ORIGEN=/net/server-sync/home/students/
ORIGEN=/m/Mios/Instituto/
sudo find $ORIGEN | grep -iE '(\.odt$|\.docx$)' > $TEMP
while read f; do
   echo $f
done
rm $TEMP      
