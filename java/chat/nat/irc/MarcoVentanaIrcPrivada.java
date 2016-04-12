// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarcoVentanaIrcPrivada.java

package nat.irc;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.Hashtable;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            VentanaIrcPrivada, hiloRecibir, Principal, CajaGraficaScroll

public class MarcoVentanaIrcPrivada extends Frame
    implements WindowListener, MouseListener, ItemListener, KeyListener, ComponentListener
{

    public MarcoVentanaIrcPrivada(hiloRecibir hilorecibir)
    {
        super((String)hilorecibir.padre.tablaIdioma.get("nuePri"));
        padre = hilorecibir.padre;
        hiloReceptor = hilorecibir;
        initialize();
    }

    private void initialize()
    {
        setSize(350, 350);
        setLayout(null);
        initConnections();
        add(getMarcoIrcPrivada(), getMarcoIrcPrivada().getName());
        ponIconos();
        for(int i = 0; i < padre.itemsAcciones.length; i++)
            getMarcoIrcPrivada().getExpresiones().add(padre.itemsAcciones[i]);

    }

    public void ponIconos()
    {
        try
        {
            ivjMarcoIrcPrivada.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("desconexion_t.gif")));
            ivjMarcoIrcPrivada.getTituloGrafico().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("charla_privada.gif")));
            return;
        }
        catch(MalformedURLException _ex)
        {
            return;
        }
        catch(PropertyVetoException _ex)
        {
            return;
        }
    }

    public VentanaIrcPrivada getMarcoIrcPrivada()
    {
        if(ivjMarcoIrcPrivada == null)
            try
            {
                ivjMarcoIrcPrivada = new VentanaIrcPrivada(padre);
                ivjMarcoIrcPrivada.setLocation(0, 20);
            }
            catch(Throwable throwable) { }
        return ivjMarcoIrcPrivada;
    }

    private void initConnections()
    {
        addWindowListener(this);
        getMarcoIrcPrivada().addMouseListener(this);
        ivjMarcoIrcPrivada.getComando().addKeyListener(this);
        ivjMarcoIrcPrivada.getComando().addMouseListener(this);
        ivjMarcoIrcPrivada.getSalir().addMouseListener(this);
        ivjMarcoIrcPrivada.getExpresiones().addItemListener(this);
        addComponentListener(this);
    }

    public void mouseClicked(MouseEvent mouseevent1)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        hiloReceptor.asignaVentanaRealUsuario(ivjMarcoIrcPrivada.getLabel4().getText());
        if(mouseevent.getSource() == ivjMarcoIrcPrivada.getSalir())
        {
            try
            {
                ivjMarcoIrcPrivada.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("sal_t2.gif")));
                return;
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            return;
        } else
        {
            return;
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        if(mouseevent.getSource() == ivjMarcoIrcPrivada.getSalir())
        {
            try
            {
                ivjMarcoIrcPrivada.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            hiloReceptor.cierraVentana(ivjMarcoIrcPrivada.getLabel4().getText());
        }
    }

    public void mouseEntered(MouseEvent mouseevent1)
    {
    }

    public void mouseExited(MouseEvent mouseevent1)
    {
    }

    public void windowActivated(WindowEvent windowevent)
    {
        System.out.print(" ");
    }

    public void windowClosed(WindowEvent windowevent1)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        hiloReceptor.cierraVentana(ivjMarcoIrcPrivada.getLabel4().getText());
    }

    public void windowDeactivated(WindowEvent windowevent1)
    {
    }

    public void windowDeiconified(WindowEvent windowevent1)
    {
    }

    public void windowIconified(WindowEvent windowevent1)
    {
    }

    public void windowOpened(WindowEvent windowevent1)
    {
    }

    public void actionPerformed(ActionEvent actionevent1)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
        hiloReceptor.asignaVentanaRealUsuario(ivjMarcoIrcPrivada.getLabel4().getText());
        if(keyevent.getSource() == ivjMarcoIrcPrivada.getComando() && keyevent.getKeyChar() == '\n' && padre.conectado && ivjMarcoIrcPrivada.getComando().getText().trim() != "")
        {
            hiloReceptor.asignaVentanaActual(ivjMarcoIrcPrivada.getLabel4().getText());
            hiloReceptor.lanzaComando();
        }
    }

    public void keyPressed(KeyEvent keyevent1)
    {
    }

    public void keyTyped(KeyEvent keyevent1)
    {
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        if(itemevent.getSource() == ivjMarcoIrcPrivada.getExpresiones() && ivjMarcoIrcPrivada.getExpresiones().getSelectedIndex() != 0)
        {
            int i = ivjMarcoIrcPrivada.getExpresiones().getSelectedIndex();
            i--;
            ivjMarcoIrcPrivada.getComando().append(String.valueOf(String.valueOf((new StringBuffer(" ")).append(padre.Acciones[i]).append(" "))));
            ivjMarcoIrcPrivada.getComando().requestFocus();
        }
    }

    public void componentHidden(ComponentEvent componentevent1)
    {
    }

    public void componentMoved(ComponentEvent componentevent1)
    {
    }

    public void componentShown(ComponentEvent componentevent1)
    {
    }

    public void componentResized(ComponentEvent componentevent)
    {
        if(componentevent.getSource() == this)
        {
            int i = getSize().width;
            int j = getSize().height;
            int k = i - 350;
            int l = j - 332;
            ivjMarcoIrcPrivada.setSize(i, j);
            ivjMarcoIrcPrivada.getSalir().setBounds(k < 0 ? 260 : 260 + k, 0, 75, 36);
            ivjMarcoIrcPrivada.getLabel2().setBounds(84, 48, k < 0 ? 84 : 84 + k, 12);
            ivjMarcoIrcPrivada.getLabel3().setBounds(k < 0 ? 168 : 168 + k, 48, 62, 12);
            ivjMarcoIrcPrivada.getLabel4().setBounds(k < 0 ? 230 : 230 + k, 48, 94, 12);
            ivjMarcoIrcPrivada.texto.setBounds(12, 60, k < 0 ? 312 : 312 + k, l < 0 ? 156 : 156 + l);
            ivjMarcoIrcPrivada.getComando().setBounds(12, l < 0 ? 228 : 228 + l, k < 0 ? 240 : 240 + k, 24);
            ivjMarcoIrcPrivada.getExpresiones().setBounds(12, l < 0 ? 260 : 260 + l, k < 0 ? 159 : 159 + k, 24);
            validate();
        }
    }

    public VentanaIrcPrivada ivjMarcoIrcPrivada;
    public Principal padre;
    public hiloRecibir hiloReceptor;
}
