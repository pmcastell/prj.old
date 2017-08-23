uso() {
   
   echo uso: $0 '<actualizar|clonar|commit|borrar|descargardir>'
   echo uso: $0 borrar '<carpeta-a-borrar>'
   echo uso: $0 clonar '<git-a-clonar>'
   exit 1
}   
if [ "$1" = "" ]; then uso; fi
case $1 in
   commit) 
      git add *
      git commit -m  "Actualización $(date -u +'%Y-%m-%d:%H:%M')"
      git push
      ;;
   clonar)
      if [ "$2" = "" ]; then uso; fi
      #git clone https://github.com/reg6543/ejercicios-php-2015-2016
      git clone $2
      ;;
   actualizar)
      git pull
      ;;
   borrar)
      if [ "$2" = "" ]; then uso ; fi
      git rm -r $2
      git commit -m "Actualización Borrado: '$2' $(date -u +'%Y-%m-%d:%H:%M')"
      git push 
      ;;
   borrarCache) 
      gir rm -r --cache $2
      git commit -m "Actualización Borrado: '$2' $(date -u +'%Y-%m-%d:%H:%M')"
      git push 
      ;;
   descargardir)
      svn checkout https://github.com/javier-iesn/prj/trunk/python/scripts/src/aula
      ;;
esac      
