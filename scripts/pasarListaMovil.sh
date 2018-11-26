#!/bin/bash
main() {
    #[ "$1" != "c1" ] && [ "$1" != "c2"] && uso
    [ "$1" != "c1" ] && [ "$1" != "c2" ] && uso
    #alu${1} | while read a; do hf -n $a; echo $a; read -rsn1; done
    #TEMP=$(tempfile)
    #alu${1} > $TEMP
    #hf -n -l $TEMP
    #rm $TEMP
    [ "$2" = "1" ] && ENGINE="com.samsung.SMT" || ENGINE="com.google.android.tts"
    echo Buenos dias voy a pasar lista | termux-tts-speak -l es -e $ENGINE
    read </dev/tty
    read </dev/tty
    alu${1} | while read alu; do
        RESP="R"
        while [ "$RESP" = "R" ] || [ "$RESP" = "r" ]; do
            echo $alu | termux-tts-speak -l es -e $ENGINE
            read -rsn1 RESP < /dev/tty
        done
        #echo "RESP: $RESP"
    done
    
}
uso() {
    echo uso: $0 '<c1 | c2>'
    echo pasa lista c1 o c2
    exit 1
}
aluc1() {
echo "MustafaAbdelatif Ahmed
Carlos Bermejo del Valle
Rául Botella Chaves
José Manuel Chenard Gaona
Marina Contreras Oliva
Abderrahman Damoun Laarbi
Abdeslam El Kadouri Ahmed
Jonathan  Gómez Bouselham 
Jorge Guiérrez Moreno
Rafael Hernández Cano
Fernando Luzón Orduña
Carlos Maldonado Navas
Sául Lecabel Martón Bermúdez
Yarell Jesús Martón Bermúdez
Ali Mekki Abdelkader
Inmaculada Menjíbar Jíménez
Brahim Mohamed Fadel
Omar Yannati Mohamed Mohamed
Naufel Mohamed Mojtar
Mohamed Mustafa Mohamed
David Osuna Villena 
Juan Manuel Pérez Cárdenas
José Manuel Pérez del Río
José Miguel Pérez Espinosa
Jesús Rivas Vázquez
Santiago Romero Miranda
Jesús Ruiz Rodríguez
Juan Manuel Serrano Alcaide
Iván Sierra Mérida"
}
aluc2() {
echo "Yasser Abdeselam Ferrer
Abdunásar Ahmed Mohamed
Alejandro Alba Barrientos	
Mohamed (Lango) Ali Abdelkader
Raúl Baro Guerrero	
Brayan José Barrero Pérez
Javier González Raya
Adrián Hermosilla Ruiz
Jesús Hesles Morilla
Omar Kanyaa Lahasen
Sergio Lara Vega
Cynthia Mohamed Maese
Rául Ríos Enrique
Lucía Sorroche de la Rubia"
}
main "$@"
