if [ "$1" = "-b" ];
then
   mkdir /tmp/videoclub &> /dev/null
   if [ "$2" = "" ];
   then
      SESION=h5t0bh0qo9054nngk665kvhh52
   else
      SESION=$2
   fi   
   if [ "$3" != "" ];
   then
      INICIO=$3
   else
      INICIO=1
   fi
   if [ "$4" != "" ];
   then
      FIN=$4
   else
      FIN=21
   fi            
   for((page=$INICIO; page<$FIN; page++)); 
   do 
      if [ $page -lt 10 ];
      then
         PAGINA=00$page
      elif [ $page -lt 100 ];
      then
         PAGINA=0$page
      else
         PAGINA=$page
      fi
      if [ ! -f $PAGINA.htm ];
      then
         wget --keep-session "http://213.227.39.85/items.php?layout=list&page=$page" --post-data="PHPSESSID=$SESION" -O /tmp/videoclub/$PAGINA.htm; 
      fi
   done
fi   
cat /tmp/videoclub/*.htm  | grep -iE "item-new|item-stock" | grep -ioE "(<a.*</a>|No disponible|En Alquiler)" | grep -ioE '>.*<'

#cat /tmp/videoclub/*.htm  | grep -iE "item-new|item-stock" | gawk -F'id=' '{ print $2; }' | gawk -F'>' '{ print $2"-"$8; }' | gawk -F'</' '{ print $1$2; }' | grep -v '<div' | gawk -F'a-' '{ print $1"   --->   "$2; }'

#cat *.htm | grep -iE "item-new|item-stock" | gawk -F'item-' '{print $2; }'
#<tr class="big-menus"><td class="list-attributes-right" width="20%"><span class="item-stock">Catálogo</span></td><td class="separator"></td><td><a class="big-menus" href="/items.php?id=4C7DFABCA7CB4D29A3F53221C89BA732">2 DIAS EN PARIS</a></td><td class="separator"></td><td class="list-attributes-left" width="20%"><span class="item-rent">En Alquiler</span><br></td></tr>
#<tr class="big-menus"><td class="list-attributes-right" width="20%"><span class="item-stock">Catálogo</span></td><td class="separator"></td><td><a class="big-menus" href="/items.php?id=A0000Hz"><div class="soldout">2 FAST 2 FURIOUS (A TODO GAS 2)</div></a></td><td class="separator"></td><td class="list-attributes-left" width="20%"><span class="item-not-available">No disponible</span></td></tr>
#<tr class="big-menus"><td class="list-attributes-right" width="20%"><span class="item-new">Nuevo</span></td><td class="separator"></td><td><a class="big-menus" href="/items.php?id=26D4566AF7694FC996847CC340B1EFC8"><div class="soldout">2 LADRONES Y MEDIO</div></a></td><td class="separator"></td><td class="list-attributes-left" width="20%"><span class="item-not-available">No disponible</span></td></tr>
#<tr class="big-menus"><td class="list-attributes-right" width="20%"><span class="item-new">Nuevo</span><br><span class="item-seen">Ya vistas</span></td><td class="separator"></td><td><a class="big-menus" href="/items.php?id=6EC888DF8D7E43AE8B129882ADA8301A">21 BLACK JACK</a></td><td class="separator"></td><td class="list-attributes-left" width="20%"><span class="item-rent">En Alquiler</span><br></td></tr>


