// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MarcoVentanaIrcCanal.java

package nat.irc;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Choice;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.StringTokenizer;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            VentanaIrcCanal, CreaCanal, PropUsu, Registro, 
//            hiloRecibir, Principal, CambiaNick, Busqueda

public class MarcoVentanaIrcCanal extends Frame
    implements WindowListener, MouseListener, ItemListener, KeyListener, ActionListener, ComponentListener
{

    public MarcoVentanaIrcCanal(hiloRecibir hilorecibir)
    {
        super((String)hilorecibir.padre.tablaIdioma.get("nueCan"));
        creado = false;
        padre = hilorecibir.padre;
        hiloReceptor = hilorecibir;
        initialize();
        if(System.getProperty("java.version").compareTo("1.1") == 0)
        {
            System.out.println("Recomendado actualizar m\341quina virtual java");
            setResizable(false);
        }
    }

    public MarcoVentanaIrcCanal()
    {
        creado = false;
    }

    private void initialize()
    {
        setSize(padre.getSize().width + 6, padre.getSize().height + 40);
        setLayout(null);
        initConnections();
        add(getMarcoIrcCanal(), getMarcoIrcCanal().getName());
        ponIconos();
        for(int i = 0; i < padre.itemsAcciones.length; i++)
            getMarcoIrcCanal().getExpresiones().add(padre.itemsAcciones[i]);

        setBackground(padre.colorFondo);
        getMarcoIrcCanal().getTextoConv().setVisible(false);
        getMarcoIrcCanal().getTexto().setVisible(true);
        validate();
    }

    public void ponIconos()
    {
        try
        {
            ivjMarcoIrc.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("antifaz_t.gif")));
            ivjMarcoIrc.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
            ivjMarcoIrc.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("lista_t.gif")));
            ivjMarcoIrc.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("entra_t.gif")));
            ivjMarcoIrc.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("quien_t.gif")));
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

    public VentanaIrcCanal getMarcoIrcCanal()
    {
        if(ivjMarcoIrc == null)
            try
            {
                ivjMarcoIrc = new VentanaIrcCanal(padre);
                ivjMarcoIrc.setLocation(3, 20);
            }
            catch(Throwable throwable) { }
        return ivjMarcoIrc;
    }

    private void initConnections()
    {
        addWindowListener(this);
        addComponentListener(this);
        getMarcoIrcCanal().addMouseListener(this);
        ivjMarcoIrc.getComando().addKeyListener(this);
        ivjMarcoIrc.getComando().addMouseListener(this);
        ivjMarcoIrc.getCambiaNick().addMouseListener(this);
        ivjMarcoIrc.getUnir().addMouseListener(this);
        ivjMarcoIrc.getQuien().addMouseListener(this);
        ivjMarcoIrc.getAyuda().addMouseListener(this);
        ivjMarcoIrc.getSalir().addMouseListener(this);
        ivjMarcoIrc.getUsuarios().addActionListener(this);
        ivjMarcoIrc.getCanales().addActionListener(this);
        ivjMarcoIrc.getCanales().addMouseListener(this);
        ivjMarcoIrc.getAreas().addMouseListener(this);
        ivjMarcoIrc.getAreas().addItemListener(this);
        ivjMarcoIrc.getExpresiones().addItemListener(this);
    }

    public void mouseClicked(MouseEvent mouseevent1)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        hiloReceptor.asignaVentanaRealUsuario(ivjMarcoIrc.getLabel4().getText());
        try
        {
            if(mouseevent.getSource() == ivjMarcoIrc.getUnir())
            {
                ivjMarcoIrc.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("entra_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjMarcoIrc.getCambiaNick())
            {
                ivjMarcoIrc.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("antifaz_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjMarcoIrc.getAyuda())
            {
                ivjMarcoIrc.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("lista_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjMarcoIrc.getSalir())
            {
                ivjMarcoIrc.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("sal_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjMarcoIrc.getQuien())
            {
                ivjMarcoIrc.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("quien_t2.gif")));
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Excepcion al traer unicono");
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        if(mouseevent.getSource() == ivjMarcoIrc.getAyuda())
        {
            try
            {
                ivjMarcoIrc.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("lista_t.gif")));
                padre.getAppletContext().showDocument(new URL(padre.URLAyuda), "Ayuda");
                return;
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            return;
        }
        if(mouseevent.getSource() == ivjMarcoIrc.getCambiaNick())
        {
            try
            {
                ivjMarcoIrc.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("antifaz_t.gif")));
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            int i = getLocationOnScreen().x;
            int k = getLocationOnScreen().y;
            padre.h.setBounds(i > 0 ? i : 0, k > 0 ? k : 0, 224, 180);
            padre.h.show();
            padre.h.getNick().requestFocus();
            return;
        }
        if(mouseevent.getSource() == ivjMarcoIrc.getUnir())
        {
            try
            {
                ivjMarcoIrc.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("entra_t.gif")));
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            if(padre.ventanaCanal != null)
                padre.ventanaCanal.dispose();
            int j = getLocationOnScreen().x;
            int l = getLocationOnScreen().y;
            padre.ventanaCanal = new CreaCanal(padre);
            padre.ventanaCanal.setBounds(j > 0 ? j : 0, l > 0 ? l : 0, 280, 145);
            padre.ventanaCanal.show();
            padre.ventanaCanal.textField1.requestFocus();
            return;
        }
        if(mouseevent.getSource() == ivjMarcoIrc.getSalir())
        {
            try
            {
                ivjMarcoIrc.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
            ivjMarcoIrc.getComando().setText("/PART ".concat(String.valueOf(String.valueOf(ivjMarcoIrc.getLabel4().getText().trim()))));
            hiloReceptor.lanzaComando();
            dispose();
            return;
        }
        if(mouseevent.getSource() == ivjMarcoIrc.getQuien())
        {
            try
            {
                ivjMarcoIrc.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("quien_t.gif")));
            }
            catch(Exception _ex)
            {
                System.out.println("Error en URL de iconos");
            }
            ivjMarcoIrc.getComando().setText("/LUSERS");
            hiloReceptor.lanzaComando();
            padre.ventanaBuscar.setVisible(true);
            ivjMarcoIrc.getComando().setText("");
            padre.ventanaBuscar.textField1.requestFocus();
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
        hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
    }

    public void windowClosed(WindowEvent windowevent1)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
        ivjMarcoIrc.getComando().setText("/PART ".concat(String.valueOf(String.valueOf(ivjMarcoIrc.getLabel4().getText().trim()))));
        hiloReceptor.lanzaComando();
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

    public void actionPerformed(ActionEvent actionevent)
    {
        hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
        if(actionevent.getSource() == ivjMarcoIrc.getUsuarios())
        {
            if(ventanaProp != null)
                ventanaProp.dispose();
            hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
            String s = null;
            if(ivjMarcoIrc.getUsuarios().getSelectedItem().startsWith("*"))
                s = ivjMarcoIrc.getUsuarios().getSelectedItem().substring(1);
            else
            if(ivjMarcoIrc.getUsuarios().getSelectedItem().startsWith("@*"))
                s = ivjMarcoIrc.getUsuarios().getSelectedItem().substring(2);
            else
                s = ivjMarcoIrc.getUsuarios().getSelectedItem();
            ventanaProp = new PropUsu(padre, s);
            hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
            hiloReceptor.lanzaComando();
            Rectangle rectangle = ivjMarcoIrc.getUsuarios().getBounds();
            int j = ivjMarcoIrc.getUsuarios().getLocationOnScreen().x;
            int k = ivjMarcoIrc.getUsuarios().getLocationOnScreen().y;
            ivjMarcoIrc.getUsuarios().getSelectedIndex();
            ventanaProp.setBounds(j > 0 ? j + rectangle.width : rectangle.x + rectangle.width, k > 0 ? k : rectangle.y, 91, 65);
            ventanaProp.setVisible(true);
        }
        if(actionevent.getSource() == ivjMarcoIrc.getCanales())
        {
            String s1 = ivjMarcoIrc.getCanales().getSelectedItem();
            int i = s1.indexOf(" ");
            if(!estoyEnCanal(s1.substring(0, i)))
            {
                ivjMarcoIrc.getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(s1.substring(0, i)))));
            } else
            {
                Registro registro = new Registro((Registro)hiloReceptor.ventanas.get(s1.substring(0, i).toUpperCase()));
                int l = registro.posicion;
                if(l != 0)
                    hiloReceptor.Chats[l].toFront();
            }
            hiloReceptor.lanzaComando();
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
        hiloReceptor.asignaVentanaRealUsuario(ivjMarcoIrc.getLabel4().getText());
        if(keyevent.getSource() == ivjMarcoIrc.getComando() && keyevent.getKeyChar() == '\n' && padre.conectado && ivjMarcoIrc.getComando().getText().trim() != "")
        {
            hiloReceptor.asignaVentanaActual(ivjMarcoIrc.getLabel4().getText());
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
        if(itemevent.getSource() == ivjMarcoIrc.getExpresiones() && ivjMarcoIrc.getExpresiones().getSelectedIndex() != 0)
        {
            int i = ivjMarcoIrc.getExpresiones().getSelectedIndex();
            i--;
            ivjMarcoIrc.getComando().append(String.valueOf(String.valueOf((new StringBuffer(" ")).append(padre.Acciones[i]).append(" "))));
            ivjMarcoIrc.getComando().requestFocus();
        }
        if(itemevent.getSource() == ivjMarcoIrc.getAreas())
        {
            int j;
            try
            {
                j = ivjMarcoIrc.getAreas().getSelectedIndex();
                ivjMarcoIrc.getCanales().removeAll();
            }
            catch(Exception _ex)
            {
                j = -1;
            }
            if(j >= 0)
            {
                for(int k = 1; k < hiloReceptor.AreasyCanales[j].length; k++)
                    if(hiloReceptor.AreasyCanales[j][k].compareTo("") != 0)
                    {
                        StringTokenizer stringtokenizer = new StringTokenizer(hiloReceptor.AreasyCanales[j][k]);
                        String s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(stringtokenizer.nextToken())))).append("   ").append(stringtokenizer.nextToken())));
                        ivjMarcoIrc.getCanales().add(s);
                    }

            }
        }
    }

    public void addNotify()
    {
        super.addNotify();
        creado = true;
        ivjMarcoIrc.cambiaIpad(false);
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
        if(componentevent.getSource() == this && isResizable())
        {
            int i = padre.minimoAnchoCanal + 6;
            int j = padre.minimoAltoCanal + 40;
            int k = 0;
            int l = 0;
            if(getSize().width < i)
                k = i;
            else
                k = getSize().width - 6;
            if(getSize().height < j)
                l = j;
            else
                l = getSize().height - 40;
            do
            {
                ivjMarcoIrc.setSize(k, l);
                if(creado)
                    ivjMarcoIrc.cambiaIpad(false);
                validate();
                l++;
            } while(ivjMarcoIrc.getTexto().getSize().height > ivjMarcoIrc.getSize().height);
        }
    }

    public boolean estoyEnCanal(String s)
    {
        return hiloReceptor.ventanas.containsKey(s.toUpperCase());
    }

    public PropUsu ventanaProp;
    public VentanaIrcCanal ivjMarcoIrc;
    public Principal padre;
    public hiloRecibir hiloReceptor;
    private boolean creado;
}
