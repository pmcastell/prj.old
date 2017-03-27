<?php
  include_once("sesion.php");
  include_once("funcionesAuxiliares.php");
?>
<html>
   <head>
      <title>Gesti&oacute;n Videoclub</title>
      <link rel="stylesheet" type="text/css"  href="temas/mio/mio.css">
      <!--<link rel="stylesheet" type="text/css" href="temas/snop/snop.css"  media="screen, tv, projection">
      <link rel="stylesheet" type="text/css" href="temas/snop/snop2.css" media="print"> -->
   </head>
   <body link="blue" vlink="blue" alink="blue">
  
      <table class="tabla" border="0" align="center" width="100%" height="95%" cellspacing="8">
         <tr height="5%">
            <th colspan="2">
               Gesti&oacute;n Videoclub Beta
         <tr height="6%">
               <td colspan="2">
               <div class="bajad">
                  <?php include("menuHorizontal.php"); ?>
               </div>  
         <tr>      
              <td width="5%">
              <div class="bajad">
              <?php include("menuVertical.php");?>
              </div>
         
              <td  id="celdaCentral" valign="middle"  align="center" height="450">
              <div  style="height: 100%; overflow: auto; width: 99%">
              <?php 
                 if (isset($_GET['pag'])) {
                    include($_GET['pag'].".php");
                    //echo 'Opción elegida: ',$_GET['pag'];                    
                 }

              ?>
              </div>



      </table>

   </body>
</html>                 
