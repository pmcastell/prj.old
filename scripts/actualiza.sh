ACTUALIZADOR="apt-get -y "
if [ "$(uname)" = "Linux" ];
then
   sudo $ACTUALIZADOR -f install
   sudo $ACTUALIZADOR update
   sudo $ACTUALIZADOR autoclean
   sudo $ACTUALIZADOR clean
   sudo $ACTUALIZADOR autoremove
   sudo $ACTUALIZADOR upgrade
   sudo $ACTUALIZADOR dist-upgrade
fi   

