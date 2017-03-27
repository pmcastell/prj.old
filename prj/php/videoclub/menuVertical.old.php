<?php
   function menu($opciones,$menu,$pag) {
      echo '<ul class="levantado">';
      for($i=0; $i<count($opciones); $i++) {
         echo '<a class="levantado" style="display:block" href="?menu=',$menu,'&pag=',$pag[$i],'">',$opciones[$i],'</a>';
      }
      echo '</ul>';
   }         
         
         
   if (esUsuario("admin") && isset($_GET['menu']) && $_GET['menu']=="altas") {
      menu(array("Socios","Tarifas","Pel&iacute;culas","Ejemplares","Alquileres"),
           "altas",
           array("altaSocios","altaTarifas","altaPelis","altaEjemplares","altaAlquileres")
          );
?>      
   
          <ul class="levantado">
             <a class="levantado" style="display: block"   href="?menu=altas&pag=altaSocios">Socios</a>
             <a class="levantado" style="display: block"   href="?menu=altas&pag=altaPelis">Pel&iacute;culas</a>
             <a class="levantado" style="display: block"   href="?menu=altas&pag=altaEjemplares">Ejemplares</a>
             <a class="levantado" style="display: block"   href="?menu=altas&pag=altaAlquileres">Alquileres</a>
             <a class="levantado" style="display: block"   href="?menu=altas&pag=tarifas">Tarifas</a>
          </ul>   
<?php
   } elseif (esUsuario("admin") && isset($_GET['menu']) && $_GET['menu']=="modif") {                
?>
          <ul class="levantado"> 

             <a class="levantado" style="display: block"   href="?menu=modif&pag=modifSocios">Socios</a>
             <a class="levantado" style="display: block"   href="?menu=modif&pag=modifPelis">Pel&iacute;culas</a>
             <a class="levantado" style="display: block"   href="?menu=modif&pag=modifEjemplares">Ejemplares</a>
             <a class="levantado" style="display: block"   href="?menu=modif&pag=modifAlquileres">Alquileres</a>

          </ul>   
<?php 
   } else {
?>         

          <ul class="levantado">   
    <?php
       if (esUsuario("admin")) {
    ?>                      
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=socios">Socios</a>
    <?php }  
       if (esUsuario("socio")) { 
    ?>
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=datosPersonales">Datos Personales</a>
    <?php } ?>                           
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=peliculas">Pel&iacute;culas</a>
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=tarifas">Tarifas</a>
    <?php if (esUsuario("admin")) { ?>             
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=ejemplares">Ejemplares</a>
                 <a class="levantado" style="display: block"   href="?menu=consultas&pag=consultas&destino=alquileres">Alquileres</a>
    <?php } ?>             
              </ul> 
<?php
   } 
?>      
   
