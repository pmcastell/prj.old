cd /m/Mios/Personal/calculadora/src
if [ ! -f mieval/evaluador.class ];
then
   javac mieval/evaluador.java
fi   
java mieval.evaluador $*
