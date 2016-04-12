<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
xmlns="http://www.w3.org/1999/xhtml">
<xsl:output method="xml" indent="yes" encoding="UTF-8"/>
	<xsl:template match="/">
	
		<!-- TODO: Auto-generated template -->
		<html>
		  <head>
		     <title>Mis Películas Favoritas</title>
		     <script type="text/javascript">
		        var ventana;
		        function abreFoto(foto) {
		          ventana=window.open(foto.src,'caratulas','width=210,height=317');
		        }
		        function cierraFoto(foto) {
		           //window.close('caratulas');
		           ventana.close();
		        }
		     </script>
		     <style>
		        table {
		           margin: auto;
		        }
		        th {
		          background-color: yellow;
		          border: none;
		          border-bottom: 1px solid black;
		          border-left: 1px solid black;
		        }
		        td {
		           background-color: lightblue;
		           border: none;
		           padding-left: 5px;
		           padding-right: 5px;
		        }
		        .derecha {
		           text-align: right;
		        }
		        .centro {
		           text-align: center;
		        }
		     </style>
		  </head>
		  <body>
		      <table border="1">
		          <tr><th>Id</th><th>Título</th><th>Nacionalidad</th><th>Productor</th><th>Director</th><th>Año</th><th>Duración</th>
		          <th>Género</th><th>Foto</th></tr>
		        
                   <xsl:for-each select="videoclub/pelicula[nacionalidad='ES']">
                      <tr>
                          <td class="derecha">
                            <xsl:value-of select="substring(@id,2)"/>
                          </td>
                          <td>
                             <xsl:attribute name="title"><xsl:value-of select="sinopsis"/></xsl:attribute>
                             <xsl:value-of select="titulo"/>
                          </td>
                          <td><xsl:value-of select="nacionalidad"/></td>
                          <td><xsl:value-of select="productor"/></td>
                          <td><xsl:value-of select="director"/></td>
                          <td class="derecha"><xsl:value-of select="año"/></td>
                          <td class="derecha"><xsl:value-of select="duracion"/></td>
                          <td>
                             <table>
                                <xsl:for-each select="./genero">
                                   <tr><td><xsl:value-of select="."/></td></tr>
                                </xsl:for-each>
                             </table>
                          </td>
		                  <td class="centro">
		                      <a>
		                          <xsl:attribute name="href">
		                              <xsl:value-of select="url"/>
		                          </xsl:attribute>
			                      <img>
			                         <xsl:attribute name="src">fotos/<xsl:value-of select='foto'/></xsl:attribute>
			                         <xsl:attribute name="onmouseover">abreFoto(this);</xsl:attribute>
			                         <xsl:attribute name="onmouseout">cierraFoto(this);</xsl:attribute>
			                         <xsl:attribute name="title"><xsl:value-of select="titulo"/></xsl:attribute>
			                         <xsl:attribute name="width">10%</xsl:attribute>
			                         <xsl:attribute name="height">10%</xsl:attribute>
			                      </img>
		                      </a>
		                  </td>
		              </tr>
		           </xsl:for-each>
		      </table>
		  </body>
		</html>
	</xsl:template>
</xsl:stylesheet>