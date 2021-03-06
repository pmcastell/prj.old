SRC=/m/Mios/Personal/calculadora/src
UTILS=/m/Mios/utils
#UTILS=/m/Software
cd $SRC
rm -rf $SRC/hs_err_pid*
javac visualizador.java &> /dev/null
if [ $? -eq 0 ];
then
   rm calculadora.jar
   java -jar $UTILS/deploy.jar visualizador
   echo borrando $SRC/*.class
   rm -f $SRC/*.class
   echo borrando $SRC/mieval/*.class
   rm -f $SRC/mieval/*.class
   echo borrando $SRC/mieval/tipo/*.class
   rm -f $SRC/mieval/tipo/*.class
   echo borrando $SRC/layout/*.class
   rm -f $SRC/layout/*.class
   echo borrando calculadora.jar
   rm -f ../calculadora.jar
   mv visualizador.jar ../calculadora.jar
#   cat $UTILS/sirena
#   cat $UTILS/sirena
#   cat $UTILS/sirena
   #echo "pulsa una tecla para ejecutar el programa..."
   #read
   cd ..
   java -jar calculadora.jar &
else
   echo error compilando
fi
