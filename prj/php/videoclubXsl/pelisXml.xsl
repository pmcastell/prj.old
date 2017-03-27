<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
    <xsl:output method="xml" indent="yes" encoding="utf-8"/> 
	<xsl:template match="/">
		<!-- TODO: Auto-generated template -->
		<videoclub>
		  <xsl:attribute name="xsi:noNamespaceSchemaLocation">videoclub.xsd</xsl:attribute>
		     <xsl:for-each select="videoclub/pelicula[nacionalidad='ES']">
		        <pelicula>
		           <xsl:attribute name="id">
                      <xsl:value-of select="substring(@id,2)"/>
                   </xsl:attribute>
                   <titulo>
                      <xsl:value-of select="titulo"/>
                   </titulo>
                   <nacionalidad>
                      <xsl:value-of select="nacionalidad"/>
                   </nacionalidad>
                   <director>
                      <xsl:value-of select="director"/>
                   </director>
                   <productor>
                      <xsl:value-of select="productor"/>
                   </productor>
                   <año>
                      <xsl:value-of select="año"/>
                   </año>
                   <xsl:for-each select="./genero">
                      <genero>
                         <xsl:value-of select="."/>
                      </genero>
                   </xsl:for-each>
                   <url>
                      <xsl:value-of select="url"/>
                   </url>
                </pelicula>
             </xsl:for-each>
		</videoclub>
	</xsl:template>
</xsl:stylesheet>