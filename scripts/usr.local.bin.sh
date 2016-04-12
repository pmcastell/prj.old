if [ "$1" = "" ];
then
   ORIGEN=/m/Mios/prj/scripts
else
   ORIGEN=$1
fi
for i in $(ls $ORIGEN/*.sh);
do
   SCRIPT=$(basename $i | awk -F'.sh' '{print $1;}')
   ln -s $ORIGEN/$SCRIPT.sh /usr/local/bin/$SCRIPT
done

