#!/bin/bash
uso() {
echo "uso: $0 <nombre-pregunta> <pregunta> <resp1>..<respn>
Ejemplo: 
/scripts/plantillaQuestionMoodleXml.sh P19 'Las listas <b>Robinson</b>:' '#Pretenden librarnos de publicidad no deseada' 'Pretenden librarnos de ataques de hackers' 'Identifican a servidores de correo maliciosos' 'Son de gran utilidad para previnir ataques de fuerza bruta' >> Examen-SIN-Temas-1-2-Parte-I-cuestionario-17-18.xml
"
}
[ $# -lt 6 ] && echo "Faltan Parámetros" && uso && exit 1

NAME="$1"
QUESTION="$2"
shift; shift;
echo "    <question type='multichoice'>
        <name><text>$NAME</text></name>
        <questiontext format='html'>
            <text>$QUESTION</text>
        </questiontext>"
	    RESPCORREC=0
	    while [ "$1" != "" ]; do
	       ANSWER="$1"; shift
	       if [ "${ANSWER:0:1}" = "#" ]; then 
	          RESPCORREC=$((RESPCORREC+1))
	          VALOR="100"
	          ANSWER=${ANSWER:1} 
	       else 
	          VALOR="0"
	       fi
	       echo "        <answer fraction='$VALOR'><text>${ANSWER}</text></answer>"
	    done
	    [ "$RESPCORREC" -gt 1 ] && SINGLE="false" || SINGLE="true"
echo "        <shuffleanswers>1</shuffleanswers><single>${SINGLE}</single>
    </question>"
