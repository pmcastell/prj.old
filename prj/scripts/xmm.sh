#for i in $(ls /usr/lib64/python2.6/); 
#do
#   echo $i
#   if [ -d $i ];
#   then
#      PYPATH="$PYPATH:$i"
#      echo $i
#   fi
#done
#export PYTHONPATH=$PYPATH   
export PYTHONPATH=/usr/lib64/python2.6/site-packages
/usr/sbin/xm $*
