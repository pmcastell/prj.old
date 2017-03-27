<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
    xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:template match="/">
        <!-- TODO: Auto-generated template -->
        <html>
            <head>
                <title>Lecturas Recomendadas para el Primer Curso</title>
            </head>
            <body>
                <h1>Lecturas Recomendadas</h1>
                <table>
                    <tr bgcolor="lightgray">
                        <th>Título</th>
                        <th>Autor</th>
                    </tr>
                    <xsl:for-each select="libreria/libro">
                        <tr>
                            <td>
                                <xsl:value-of select="titulo" />
                            </td>
                            <td>
                                <xsl:value-of select="autor" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </table>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>