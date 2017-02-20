uso() {
   echo uso: (basename $0) '<fichero-7z-a-probar-passwords>'
   exit 1
}

if [ "$1" = "" ]; then uso;

for((i=1;i<=${#PASS};i++)); do
   NEW_PASS=${PASS:0:$((i-1))}${PASS:$((i))}
   echo $NEW_PASS
   eecho 7za x -p$NEW_PASS /tmp/TutorialInkscape.7z
   if [ $? -eq 0 ]; then echo PASS ENCONTRADA: $NEW_PASS; break; fi
done   
if [ $i -ge ${#PASS} ]; then echo PASS NO ENCONTRADA; fi
