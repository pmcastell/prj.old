javac mieval/evaluador.java
if [ $? == 0 ];
then
   java -jar /m/Mios/utils/deploy.jar -jar evaluador.jar mieval.evaluador
fi

