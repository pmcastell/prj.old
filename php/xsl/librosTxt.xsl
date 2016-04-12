<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:output method='text'/>
	
	<xsl:variable name='iva'><xsl:text>18%</xsl:text></xsl:variable>
	<xsl:template match="/">
		<xsl:for-each select="libreria/libro">
			<xsl:value-of select="titulo" />;<xsl:value-of select="autor" />;<xsl:value-of select="editor" />;<xsl:value-of select="isbn" />;<xsl:value-of select="precio" />;<xsl:text>&#13;&#10;</xsl:text>
			<!-- <xsl:value-of select="$iva"/><xsl:text>&#xa;</xsl:text> -->
		</xsl:for-each>
	</xsl:template>
</xsl:stylesheet>