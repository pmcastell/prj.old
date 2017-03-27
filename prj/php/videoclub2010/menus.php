<?php
   function menuPrincipalBasico() {
      echo '<a href="?menup=Consultas">Consultas</a>';
      echo '&nbsp&nbsp'; // espacios
      echo '<a href="?menup=Identificarse">Identificarse</a>';
   }      	         
   function menuPrincipalUsuario() {
      echo '<a href="?menup=Consultas">Consultas</a>';
      echo '&nbsp&nbsp'; // espacios
      echo '<a href="?menup=Salir>Salir</a>';
   }
   function menuPrincipalAdmin() {
      echo '<a href="?menup=Consultas">Consultas</a>';
      echo '&nbsp&nbsp'; // espacios
      echo '<a href="?menup=Altas">Altas</a>';
      echo '&nbsp&nbsp'; // espacios
      echo '<a href="?menup=Bajas">Bajas/Modificaciones</a>';
      echo '&nbsp&nbsp';
      echo '<a href="?menup=Informes">Informes</a>';
      echo '&nbsp&nbsp';
      echo '<a href="?menup=Salir">Salir</a>';
   }
   function menuSecundarioBasico() {
      if (isset($_GET['menup'])) {
         $menup=$_GET['menup'];
      } else {
         $menup="Consultas";
      }
      echo '<a href="?menup=',$menup,'&menus=Tarifas">Tarifas</a>';
      echo '<br />';
      echo '<a href="?menup=',$menup,'&menus=Peliculas">Películas</a>';
      echo '<br />';
   }
   function menuSecundarioUsuario() {
      
   }
   function menuSecundarioAdmin() {
      if (isset($_GET['menup'])) {
         $menup=$_GET['menup'];
      } else {
         $menup="";
      }
      if ($menup=='Altas') {
         menuAltas();
      } elseif ($menup=='Bajas') {
         menuBajas();
      } elseif ($menup=='Informes') {
         menuInformes();
      } elseif ($menup=='Salir') {
         //No hacer nada
      } else {
         menuConsultas();
      }
   }
?>