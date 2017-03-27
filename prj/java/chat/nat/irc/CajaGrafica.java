// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CajaGrafica.java

package nat.irc;

import java.awt.*;
import java.io.PrintStream;
import java.util.StringTokenizer;
import java.util.Vector;

// Referenced classes of package nat.irc:
//            BolaTexto, BolaGrafica, Bola, CajaGraficaScroll

public class CajaGrafica extends Panel
{

    public CajaGrafica(CajaGraficaScroll cajagraficascroll)
    {
        cortando = false;
        repintar = false;
        redibujando = false;
        ancho = 180;
        separacionIzquierda = 5;
        posicionY = 12;
        posicionX = 5;
        numeroMaximoLineas = 150;
        altoTipoLetra = 12;
        numeroLineasCorte = 1;
        distanciaSalto = 48;
        incrementoEntreLineas = altoTipoLetra + 3;
        alto = numeroMaximoLineas * incrementoEntreLineas;
        altoCorte = numeroLineasCorte * incrementoEntreLineas;
        padre = cajagraficascroll;
        datos = new Vector(numeroMaximoLineas, 0);
    }

    public void borrar()
    {
        datos.removeAllElements();
        numeroElementos = 0;
        buffer.getGraphics().clearRect(0, 0, buffer.getWidth(null), buffer.getHeight(null));
        posicionY = 12;
        posicionX = 5;
        repaint();
    }

    public void append(String s, Color color, boolean flag)
    {
        if(numeroElementos >= numeroMaximoLineas)
            datos.removeElementAt(0);
        else
            numeroElementos++;
        datos.addElement(new BolaTexto(s, color, flag, getGraphics().getFontMetrics()));
        if(posicionY + distanciaSalto > alto)
            corta();
        if(!redibujando && !cortando)
        {
            if(buffer == null)
                try
                {
                    buffer = createImage(ancho, alto);
                }
                catch(Exception _ex)
                {
                    System.out.println(String.valueOf(String.valueOf((new StringBuffer("Error al crear imagen buffer en CajaGrafica ")).append(alto).append(" ").append(ancho))));
                }
            dibujaTexto(s, color, buffer.getGraphics());
            if(flag)
            {
                posicionY += incrementoEntreLineas;
                posicionX = 5;
            }
        }
    }

    private void corta()
    {
        cortando = true;
        Graphics g = buffer.getGraphics();
        g.copyArea(0, altoCorte, ancho, alto - altoCorte, 0, -altoCorte);
        posicionY -= altoCorte;
        Color color = g.getColor();
        g.setColor(getBackground());
        g.fillRect(0, posicionY, ancho, posicionY);
        g.setColor(color);
        padre.validate();
        cortando = false;
        if(repintar)
            redibuja();
        repintar = false;
    }

    public void ponImagen(Image image)
    {
        if(numeroElementos >= numeroMaximoLineas)
            datos.removeElementAt(0);
        else
            numeroElementos++;
        datos.addElement(new BolaGrafica(image, true));
        update(getGraphics());
    }

    private void dibujaTexto(String s, Color color, Graphics g)
    {
        StringTokenizer stringtokenizer = new StringTokenizer(s);
        StringBuffer stringbuffer = new StringBuffer();
        int i = 0;
        while(stringtokenizer.hasMoreTokens()) 
        {
            String s1 = String.valueOf(String.valueOf(stringtokenizer.nextToken())).concat(" ");
            int j = g.getFontMetrics().stringWidth(s1);
            if(i + j < ancho)
            {
                stringbuffer.append(s1);
                i += j;
            } else
            {
                Color color1 = g.getColor();
                g.setColor(color);
                g.drawString(stringbuffer.toString(), separacionIzquierda, posicionY);
                g.setColor(color1);
                posicionY += incrementoEntreLineas;
                if(posicionY + distanciaSalto > alto)
                    corta();
                stringbuffer.setLength(0);
                stringbuffer.append(s1);
                i = j;
            }
        }
        Color color2 = g.getColor();
        g.setColor(color);
        g.drawString(stringbuffer.toString(), posicionX, posicionY);
        posicionX += g.getFontMetrics().stringWidth(stringbuffer.toString());
        g.setColor(color2);
    }

    private void dibujaBola(Bola bola)
    {
        int i = bola.ancho;
        int j = bola.alto;
        boolean flag = bola.esFinalLinea;
        try
        {
            BolaTexto bolatexto = (BolaTexto)bola;
            dibujaBolaTexto(bolatexto);
        }
        catch(ClassCastException _ex)
        {
            BolaGrafica bolagrafica = (BolaGrafica)bola;
            dibujaBolaGrafica(bolagrafica);
        }
        if(flag)
        {
            posicionX = 5;
            if(j > 0)
            {
                posicionY += j + 3;
                return;
            } else
            {
                posicionY += incrementoEntreLineas;
                return;
            }
        } else
        {
            posicionX += i;
            return;
        }
    }

    private void dibujaBolaTexto(BolaTexto bolatexto)
    {
        dibujaTexto(bolatexto.getTexto(), bolatexto.getColor(), buffer.getGraphics());
    }

    private void dibujaBolaGrafica(BolaGrafica bolagrafica)
    {
        buffer.getGraphics().drawImage(bolagrafica.getImagen(), posicionX, posicionY, null);
    }

    public void redibuja()
    {
        redibujando = true;
        Object obj = null;
        if(cortando)
        {
            repintar = true;
        } else
        {
            repintar = false;
            try
            {
                buffer = createImage(ancho, alto);
            }
            catch(Exception _ex)
            {
                System.out.println(String.valueOf(String.valueOf((new StringBuffer("Error al crear imagen buffer en CajaGrafica ")).append(alto).append(" ").append(ancho))));
            }
            if(buffer != null)
            {
                Graphics g = buffer.getGraphics();
                int i = 0;
                posicionY = 12;
                for(; i < numeroElementos; i++)
                {
                    if(posicionY + distanciaSalto > alto)
                        corta();
                    dibujaBola((Bola)datos.elementAt(i));
                }

            }
            update(getGraphics());
        }
        redibujando = false;
    }

    public void paint(Graphics g)
    {
        incrementoEntreLineas = altoTipoLetra + 3;
        alto = numeroMaximoLineas * incrementoEntreLineas;
        altoCorte = numeroLineasCorte * incrementoEntreLineas;
        if(buffer != null)
            g.drawImage(buffer, x, y, null);
    }

    public void draw(int i, int j)
    {
        x = -i;
        y = -j;
        update(getGraphics());
    }

    public void update(Graphics g)
    {
        try
        {
            if(buffer != null)
            {
                g.drawImage(buffer, x, y, null);
                return;
            }
        }
        catch(Exception exception) { }
    }

    public Dimension getPreferredSize()
    {
        return new Dimension(ancho, alto);
    }

    public int x;
    public int y;
    private boolean cortando;
    private boolean repintar;
    public boolean redibujando;
    private CajaGraficaScroll padre;
    private Image buffer;
    private Vector datos;
    private int numeroElementos;
    public int ancho;
    private int separacionIzquierda;
    public int posicionY;
    public int posicionX;
    public int incrementoEntreLineas;
    private int numeroMaximoLineas;
    public int altoTipoLetra;
    public int alto;
    public int altoCorte;
    public int numeroLineasCorte;
    public int distanciaSalto;
}
