<?php
   if (esUsuario("admin")) {
?>      
   <ul>
      <li><font color="blue">Altas</font></li>
          <ul>
             <li><a href="index.php?pag=altaSocios">Socios</a></li>
             <li><a href="index.php?pag=altaPelis">Pel&iacute;culas</a></li>
             <li><a href="index.php?pag=altaEjemplares">Ejemplares</a></li>
             <li><a href="index.php?pag=altaAlquileres">Alquileres</a></li>
          </ul>   
      <li><h1><font color="blue">Bajas/Modificaciones</font></h1></li> 
          <ul> 
             <li><a href="index.php?pag=modifSocios">Socios</a></li>
             <li><a href="index.php?pag=modifPelis">Pel&iacute;culas</a></li>
             <li><a href="index.php?pag=modifEjemplares">Ejemplares</a></li>
             <li><a href="index.php?pag=modifAlquileres">Alquileres</a></li>
          </ul>   
<?php }  ?>         
      <li><font color="blue">Consultas</font></li> 
          <ul>   
<?php
   if (esUsuario("admin")) {
?>                      
             <li><a href="index.php?pag=consultas&destino=socios">Socios</a></li>
<?php }  
   if (esUsuario("socio")) { 
?>
             <li><a href="index.php?pag=consultas&destino=datosPersonales">Datos Personales</a></li>
<?php } ?>                           
             <li><a href="index.php?pag=consultas&destino=peliculas">Pel&iacute;culas</a></li>
<?php if (esUsuario("admin")) { ?>             
             <li><a href="index.php?pag=consultas&destino=ejemplares">Ejemplares</a></li>
             <li><a href="index.php?pag=consultas&destino=alquileres">Alquileres</a></li>
<?php } ?>             
          </ul> 
<?php if (esUsuario("admin") || esUsuario("socio")) { ?>                       
      <li><a href="index.php?pag=salir">Salir</a></li>    
<?php } else { ?>         
      <li><a href="index.php?pag=ident">Identificarse</a></li>    
<?php } ?>      
   </ul>                         
