while (true); 
do
   echo ----------------------------------------------------
   echo ....................................................
   echo Utilidad para clonar discos duros a traves de la Red
   echo ....................................................
   echo ----------------------------------------------------
   echo "         1 clonar disco duro"
   echo "         2 restaurar disco duro"
   echo "         3 ejecutar partimage"
   echo "         4 prompt de linux (R.I.P.)"
   echo "         5 reiniciar sistema"
   echo "         6 apagar sistema"
   echo ----------------------------------------------------
   echo ....................................................
   echo ----------------------------------------------------
   read opc
   case $opc in
      1)
	     echo ----------------------------------------------
		 echo "          Has Elegido clonar disco duro"
		 echo ----------------------------------------------
		 echo -n "Introduce un nombre para la imagen: "
		 read imagen
		 /scripts/clonar.sh $imagen
		 ;;
	  2) echo -----------------------------------------------
	     echo "           Has Elegido restaurar disco duro"
		 echo -----------------------------------------------
		 echo -n "Introduce el nombre de la imagen: "
		 read imagen
		 /scripts/restaurar.sh $imagen
		 ;;
	  3)
	     partimage
		 ;;
          4)		 
	     exit 0
		 ;;
	  5) 
	     shutdown -r now
		 ;;
	  6)
	     shutdown -h now
		 ;;
   esac
done
