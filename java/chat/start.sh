#  java -Djava.security.main 
#  -DJava.security.policy=polfile  FileIO

#To run an applet in appletviewer with a policy file named polfile in the user's home directory, type:
if [ "$1" = "" ];
then
   HTML=index.html
else
   HTML=$1
fi      
appletviewer -J-Djava.security.policy=permisos.pol $HTML

