<?php
   
   include_once("funcionesAuxiliares.php");

   
   $a['Alvaro']=1.73;
   $a['Daniel']=1.79;
   $alturasTotales=0;
   $numVals=1;
   foreach($a as $clave => $valor) {
      $alturasTotales+=$valor;
      $numVals++;
   }
   echo "la media es: ",$alturasTotales/$numVals;
   // Operadores
   //Aritméticos: + - * / %
   // Lógicos (compración): == != (<>) < <= > >=
   //Lógicos (condiciones compuestas): && and || or ! 
   //Incremento (pre-post): ++ --
   //Asignaciones: = += -= *= /= %= ($a/=3; <==> $a=$a/3;)
   //3.- Funciones en PHP
   function suma($n1,$n2) {
      return $n1+$n2;
   }
   $f=factorial(5);
   
   $d=suma(25,33);
   $e=suma($d,3);
   $f=suma($d,$e);
   $g=suma(3,5);
   
    
?>