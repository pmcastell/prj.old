<?php
   function menu($opciones,$menu,$pag) {
      echo '<ul class="levantado">';
      for($i=0; $i<count($opciones); $i++) {
         echo '<a class="levantado" style="display:block" href="?menu=',$menu,'&pag=',$pag[$i],'">',$opciones[$i],'</a>';
      }
      echo '</ul>';           
   }         
         
         
   if (esUsuario("admin") && isset($_GET['menu']) && $_GET['menu']=="altas") {
      menu(array("Socios","Tarifas","Pel&iacute;culas","Ejemplares","Alquileres","Detalle de Alquileres","Ingresos"),
           "altas",
           array("altas&destino=socios","altas&destino=tarifas","altas&destino=peliculas","altas&destino=ejemplares","altas&destino=alquileres","altas&destino=detalle_alquileres","altas&destino=ingresos")
          );
   } elseif (esUsuario("admin") && isset($_GET['menu']) && $_GET['menu']=="modif") {                
      menu(array("Socios","Tarifas","Pel&iacute;culas","Ejemplares","Alquileres","Detalle de Alquileres","Ingresos"),
           "modif",
           array("modif&destino=socios","modif&destino=tarifas","modif&destino=peliculas","modif&destino=ejemplares","modif&destino=alquileres","modif&destino=detalle_alquileres","modif&destino=ingresos")
          );      
      /*menu(array("Socios","Tarifas","Pel&iacute;culas","Ejemplares","Alquileres"),
           "modif",
           array("modifSocios","modifTarifas","modifPelis","modifEjemplares","modifAlquileres")
          );*/
   } elseif (esUsuario("admin") && isset($_GET['menu']) && $_GET['menu']=="informes") {
      menu(array("Demanda Películas","Ingresos Clientes","Ingresos Alquileres"),
           "informes",
           array("consultas&destino=informes&inf=pel","consultas&destino=informes&inf=cli",
           "consultas&destino=informes&inf=alq"));
   } else {
      
?>         

       <ul class="levantado">   
    <?php if (esUsuario("admin")) {  ?>                      
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=socios">Socios</a>
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=ejemplares">Ejemplares</a>
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=alquileres">Alquileres</a>          
    <?php } ?>
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=tarifas">Tarifas</a>  
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=peliculas">Pel&iacute;culas</a>
    <?php  if (esUsuario("socio")) { ?>
          <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=datosPersonales">Datos Personales</a>
    <?php } ?>                
       </ul> 
<?php
   } 
?>      
   
