<?php
  /*mysql> select distinct ejemplares.id as Cod,titulo as Ttulo,tema as Gnero, fecha_estreno as Estreno
,director as Director,actores as Actores,if(fecha_devolucion is null and fecha_alquiler is not null
,0,1) as Disp from (peliculas join ejemplares on peliculas.id=id_peliculas) left join (detalle_alqu
ileres join alquileres on id_alquileres=alquileres.id) on ejemplares.id=id_ejemplares where fecha_d
evolucion is null or (fecha_alquiler is not null and fecha_devolucion is not null);
*/
?>
