uso() {
    echo 
mpg321 $1 -w - | oggenc -o $1.ogg -
