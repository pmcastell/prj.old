if [ "$1" = "" ];
then
   echo uso: $0 nombre-procesos-a-matar
   exit 1
fi

while [ "$1" != "" ];
do
   echo hola
   PID=$(ps ax | grep -i $1  | grep -v grep | grep -v mata | tail -1 |  awk '{print $1;}')
   SIGNAL='-TERM'
   NUM_INTENTOS=10
   i=0
   while [ "$PID" != "" -a $i -le $NUM_INTENTOS ];
   do
      ##echo dolar 0: $0 - encontrado: $(ps ax | grep -i $1  | grep -v grep | grep -v mata | tail -1)
      eecho kill $SIGNAL $PID
      i=$(($i + 1))
      PID=$(ps ax | grep -i $1  | grep -v grep | grep -v mata | tail -1 |  awk '{print $1;}')
      if [ $i -eq 8 ];
      then
         SIGNAL='-9' # lo intentamos a la fuerza
      fi
   done
   if [ "$PID" != "" ];
   then
      echo no se pudo matar $1   
   fi
   shift
done   

