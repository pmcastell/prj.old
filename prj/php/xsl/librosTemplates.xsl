<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns="http://www.w3.org/1999/xhtml">
	 <xsl:output method="xml" indent="yes" encoding="UTF-8"/>
	<xsl:template match="/">
		<html>
			<head>
				<title>Ejemplo xsl de uso de plantillas</title>
			</head>
			<body>
				<h1>Librería Gamma</h1>
				<xsl:apply-templates />
			</body>
		</html>
	</xsl:template>
	<xsl:template match="libreria">
		<h2>Lecturas Recomendadas</h2>
		<table>
			<tr bgcolor="#887788">
				<th>Título</th>
				<th>Autor</th>
			</tr>
            <xsl:apply-templates select="libro" />
		</table>
	</xsl:template>
	<xsl:template match="libro">

		<tr>
			<td>
				<xsl:apply-templates select="titulo" />
			</td>
			<td>
				<xsl:apply-templates select="autor" />
			</td>
			<td>
                <xsl:apply-templates select="precio" />
            </td>
		</tr>
	
	</xsl:template>
	<xsl:template match="titulo">
		<td bgcolor="#DDEEDD">
			<xsl:value-of select="../autor" />
		</td>
	</xsl:template>
	<xsl:template match="autor">
		<td bgcolor="#AABBAA">
			<xsl:value-of select="." />
		</td>
	</xsl:template>
	<xsl:template match="precio">
	   <xsl:choose>
	       <xsl:when test=". &gt; 100">
                <td bgcolor="red">
                    <xsl:value-of select="." />
                </td>
           </xsl:when>
           <xsl:otherwise>
                <td bgcolor="skyblue">
                    <xsl:value-of select="." />
                </td>           
           </xsl:otherwise> 
        </xsl:choose>
    </xsl:template>
</xsl:stylesheet>