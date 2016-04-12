uso() {
   echo uso: $0 '<ruta-archivos-ovpn>'
   exit 1
}   
if [ "$1" = "" ];
then
   uso
fi   
sed -i 's/auth-user-pass/auth-user-pass \/m\/Mios\/Personal\/openvpn\/freeVpns\/book\/pass.txt/g' $1/*.ovpn
sed -i 's/dev tun0/dev tun3/g' $1/*.ovpn
sed -i 's/dev tun1/dev tun3/g' $1/*.ovpn
sed -i 's/dev tun2/dev tun3/g' $1/*.ovpn

