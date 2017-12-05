#!/bin/bash 

passAlu() {
echo '"Ahmed Mohamed, Bilal"		    8786	19980321
"Ali Bouhdid, Aiman"				    8030	19980724
"Cantero López, Jesús José"				8714	19990331
"Conde Silva, Francisco Javier"			8711	19880512
"Damasceno Grimaldi, Joao Víctor"		8370	19981226
"Fernández de Córdoba Ruiz, Guillermo"	8471	19990602
"García Morales, Samuel"				8890	19990414
"Hossain Badri, Sufian"				    8667	19981018
"Houmisa Taieb, Mohamed"				8713	19991126
"Jaén Muñoz, Aarón"				        8192	19981021
"León Reina, Christian"				    6261	19870202
"Makran, Ayoub"				            8231	19980410
"Hodda Herrezuelo, Yusef"				8031	19981214
"Ramos Castillo, Domingo"				8910	19980929
"Romero Calvillo, Cristian"				8708	19981025
"Salah Bunuar, Ismail"				    8464	19950731
"Salvador Berenguer, Juan Sebastián"	6914	19970715
"Sánchez Ros, Francisco"				8902	20001002
"Santapau Zurita, Ezequiel" 			7352	20000727'
}
abreAlu() {
   USER="$1"
   PASS="$2"
   echo "<form id='formLogin' method='post' action='http://server/moodle/login/index.php'>
         <input type='text' name='username' value='$USER'>
         <input type='text' name='password' value='$PASS'>
         </form>
         <script>document.getElementById('formLogin').submit();</script>
         " > /tmp/prueba.html
   firefox -private /tmp/prueba.html
}
if [ "$1" != "" ]; then
   USER=$(passAlu | grep -i $1 | awk '{ print $(NF-1);}')
   PASS=$(passAlu | grep -i $1 | awk '{ print $NF;}')
   abreAlu $USER $PASS
   exit 0
fi      
passAlu | while read a; do
   USER=$(echo $a | awk '{ print $(NF-1);}')
   PASS=$(echo $a | awk '{ print $NF;}')
   abreAlu $USER $PASS
   read l < /dev/tty
done   
