<?xml version="1.0" encoding="utf-8"?>
<xsl:transform version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml">
	 <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
 
	<xsl:template match="/">
	   
		<html>
			<head>
				<title>Lecturas Recomendadas para el Primer Curso</title>
				<style>
					table {
	                    background-color:#CCDDEE;
	                }

					.azulClaro {
					    background-color:lightblue;
					}
				</style>
			</head>
			<body>
				<h1>Lecturas recomendadas para final de curso</h1>
				<table border="1">
					<tr bgcolor="azulClaro">
					    <th>Isbn</th>
						<th>Título</th>
						<th>Autor</th>
						<th>Precio</th>
						<th>Iva</th>
						<th>Precio Total</th>
					</tr>
					<xsl:for-each select="libreria/libro">
						<xsl:sort select="titulo" order="descending" />
						<tr>
                            <td><xsl:value-of select="isbn" /></td>						
							<td><xsl:value-of select="titulo" /></td>
                            <td><xsl:value-of select="autor" /></td>  
                            <td><xsl:value-of select="precio" /></td>
                            <td><xsl:value-of select="precio * 0.18" /></td>
                            <td><xsl:value-of select="precio * 1.18" /></td>
                             
						</tr>
					</xsl:for-each>
				</table>
			</body>
		</html>
	</xsl:template>
</xsl:transform>
                        <!--
                            <xsl:for-each select="./*"> <!- - . se refiere al elemento actual seleccionado, * se refiere a los hijos 
                              <xsl:choose> 
                                  <xsl:when test="*"> <!- - test="*" ¿El elemento actual tienes hijos?
                                      <xsl:for-each select="./*"> 
                                          <td><xsl:value-of select="."/></td>
                                      </xsl:for-each>
                                  </xsl:when>
                                  <xsl:otherwise> <!- - como no hay <xsl:else utilizamos un choose equivalente a un if con varias ramas
                                      <td><xsl:value-of select="."/></td>
                                  </xsl:otherwise>
                              </xsl:choose>
                              <td><xsl:value-of select="position()"/></td> <!- - añade el número del nodo actual 
                            </xsl:for-each>
                            -->
                            <!-- <xsl:for-each select="otro/*">
                                <td><xsl:value-of select="."/></td>
                            </xsl:for-each>
                            recorre los elementos hijos del nodo otro
                            -->