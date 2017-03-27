uso() {
   echo $0 usuario contrasenia
   exit 1
}
if [ "$1" = "" -o "$2" = "" ];
then 
   uso
fi   


perl -MMIME::Base64 -e 'print encode_base64("\000'$1'\000'$2'")'
