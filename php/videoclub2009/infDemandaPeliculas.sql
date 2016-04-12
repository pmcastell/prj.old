-- 
-- INFORME DE DEMANDA DE PELÍCULAS
--
SELECT id_PELICULA,titulo,n_ejemplares,n_alquileres,num_total_alquileres,(n_alquileres/n_ejemplares/num_total_alquileres)*100 as porcentaje,(n_alquileres/num_total_alquileres)*100 as porcentaje_global FROM
(
   ((SELECT id_EJEMPLAR,count(id_EJEMPLAR) as n_alquileres FROM detalle_alquileres  GROUP BY id_EJEMPLAR) as NUM_ALQUILERES)
      JOIN
   ((SELECT id_PELICULA,titulo,count(*) as n_ejemplares FROM (EJEMPLAR JOIN PELICULA ON EJEMPLAR.id_PELICULA=PELICULA.id) 
         GROUP BY id_PELICULA) as NUM_EJEMPLARES)
   ON NUM_ALQUILERES.id_EJEMPLAR=NUM_EJEMPLARES.id_PELICULA
) 
JOIN
((SELECT count(*) as num_total_alquileres FROM detalle_alquileres)  AS NUM_TOTAL_ALQUILERES);

--
-- INGRESOS BRUTOS, agrupados por clientes [Esto no se pedía]
--
SELECT num_cli,CONCAT(apellido1,' ',IF (apellido2 IS NULL,'',apellido2),' ',nombre) as nombre_completo,sum(cantidad) FROM CLIENTE JOIN INGRESO on num_cli=num_cli_CLIENTE
GROUP BY num_cli WHERE fecha between '2008-01-01' and '2009-12-31';

--
-- INGRESOS BRUTOS DETALLADOS
--
SELECT num_cli,CONCAT(apellido1,' ',IF (apellido2 IS NULL,'',apellido2),' ',nombre) as nombre_completo,fecha,cantidad FROM CLIENTE JOIN INGRESO on num_cli=num_cli_CLIENTE
WHERE fecha between '2008-01-01' and '2009-12-31';

--
-- INGRESOS POR ALQUILERES EN BRUTO
--
SELECT num_cli,CONCAT(apellido1,' ',IF (apellido2 IS NULL,'',apellido2),' ',nombre) as nombre_completo, titulo,ALQUILER.fecha,fecha_devolucion,importe
FROM ALQUILER JOIN detalle_alquileres ON ALQUILER.id=id_ALQUILER JOIN CLIENTE on num_cli=num_cli_CLIENTE JOIN PELICULA on id_EJEMPLAR=PELICULA.id WHERE fecha BETWEEN '2008-01-01' and '2009-12-31' AND fecha_devolucion IS NOT NULL;

--
-- ALQUILERES PENDIENTES DE DEVOLUCION
--
SELECT num_cli,CONCAT(apellido1,' ',IF (apellido2 IS NULL,'',apellido2),' ',nombre) as nombre_completo, titulo,ALQUILER.fecha
FROM ALQUILER JOIN detalle_alquileres ON ALQUILER.id=id_ALQUILER JOIN CLIENTE on num_cli=num_cli_CLIENTE JOIN PELICULA on id_EJEMPLAR=PELICULA.id WHERE fecha BETWEEN '2008-01-01' and '2009-12-31' AND fecha_devolucion IS NULL;
