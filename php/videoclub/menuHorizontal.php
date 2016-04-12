<?php
   function escribeOpcion($param,$destino,$style,$titulo) {
       if (!isset($_GET['menu'])) {
           $_GET['menu']="consultas";
       }
       if ($parametro="menu" && $destino==$_GET['menu']) {
           /*echo '<a href="?',$param,'=',$destino,'" style="background: #99CCDD;
                 color: blue; border-bottom: 1px solid #A98; border-top: 1px solid #DDC;
                 border-left: 1px solid #DDC; border-right: 1px solid #A98; text-decoration: underline;
                 font-style: italic; font-weight: bold;','">',$titulo,'</a>';*/
          echo '<a class="bajado" ';

       } else {
           echo '<a class="levantado" ';
           
       }   
       echo 'href="?',$param,'=',$destino,'" style="',$style,'">',$titulo,'</a>&nbsp;';
   }
   escribeOpcion("menu","consultas","width: 125px","Consultas");
   if (esUsuario("admin")) {
      //<!--&nbsp&nbsp | &nbsp&nbsp  -->
      escribeOpcion("menu","altas","width: 100px","Altas");
      //<a href="?menu=altas" style="width: 100px">Altas</a>
      escribeOpcion("menu","modif","width: 150px","Bajas/modificaciones");
      //<a href="?menu=modif" style="width: 150px">Bajas/Modificaciones</a>
      escribeOpcion("menu","informes","width: 120px","Informes");
   }
   if (esUsuario("admin") || esUsuario("socio")) { 
       escribeOpcion("pag","salir","width: 100px","Salir");
       //<a href="index.php?pag=salir" style="width: 100px">Salir</a>
   } else { 
       escribeOpcion("pag","ident","width: 150px","Identificarse");
       //<a href="index.php?pag=ident" style="width: 150px">Identificarse</a>
   }    
?>