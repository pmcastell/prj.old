#!/bin/bash -i
main() {
BASE_DIR=$(dirname $0)
[ -e $BASE_DIR/funcionesAula.sh ] && . $BASE_DIR/funcionesAula.sh
[ -e $BASE_DIR/scriptsCeuta/funcionesAula.sh ] && . $BASE_DIR/scriptsCeuta/funcionesAula.sh
    #[ "$1" != "ciclo1" ] && [ "$1" != "ciclo2"] && uso
    [ "$1" != "ciclo1" ] && [ "$1" != "ciclo2" ] && uso
    [ "$2" = "" ] && HABLA="a gttss " || HABLA="$2 "
    alu${1} | while read a; do
        RESP="R"
        while [ "$RESP" = "R" ] || [ "$RESP" = "r" ]; do echo alumno: $a; eval $HABLA "'$a'"; read -rsn1 RESP </dev/tty; done
    done
    #TEMP=$(tempfile)
    #alu${1} > $TEMP
    #hf -n -l $TEMP
    #rm $TEMP
}
uso() {
    echo uso: $0 '<ciclo1 | ciclo2>'
    echo pasa lista ciclo1 o ciclo2
    exit 1
}

#[[ $_ != $0 ]] && echo "Script is being sourced" || echo "Script is a subshell"
#[[ $_ = $0 ]] && main "$@"
(return 2>/dev/null) || main "$@"
