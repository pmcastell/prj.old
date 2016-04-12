// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Principal.java

package nat.irc;

import java.applet.Applet;
import java.applet.AppletContext;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyVetoException;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.StringTokenizer;
import symantec.itools.multimedia.ImageViewer;

import javax.swing.*;
// Referenced classes of package nat.irc:
//            hiloRecibir, VentanaIrcCanal, leerVar, ListaCanales, 
//            CambiaNick, NickUsado, Busqueda, Registro, 
//            PropUsu, CreaCanal, ConexionIrc, ContenidoSencillo

public class Principal extends Applet
    implements ItemListener, KeyListener, MouseListener, ActionListener
{

    public void conectarDirecto()
    {
        conectadoDirecto = true;
        entrarChatDirecto();
        while(!conectado && !hayQueSeguir) 
            try
            {
                Thread.sleep(300L);
            }
            catch(InterruptedException interruptedexception) { }
        mostrarChat();
    }

    public void entrarChatDirecto()
    {
        String s = nickParam;
        int i = s.indexOf(" ");
        if(i != -1)
            s = s.substring(0, i).trim();
        if(s.length() > 9)
            s = s.substring(0, 9);
        hiloReceptor = new hiloRecibir(this, c, m, s, ncanalParam.trim());
        hiloReceptor.start();
    }

    public VentanaIrcCanal getMarcoChat()
    {
        if(ivjMarcoChat == null)
            try
            {
                ivjMarcoChat = new VentanaIrcCanal(this);
                ivjMarcoChat.setName("MarcoChat");
                ivjMarcoChat.setLocation(0, 0);
                ivjMarcoChat.setBackground(colorFondo);
                ivjMarcoChat.setVisible(false);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjMarcoChat;
    }

    private void handleException(Throwable throwable1)
    {
    }

    public int convierteHex(String n)
    {
        int i = 0;
        int r = 0;
        String s = n.toUpperCase();
        if(s.charAt(0) == '#')
            i = 1;
        for(; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if('0' <= c && c <= '9')
            {
                r = r * 16 + (c - 48);
                continue;
            }
            if('A' <= c && c <= 'F')
                r = r * 16 + ((c - 65) + 10);
        }

        return r;
    }
    public String getParameter(String param) {

        try {
            return super.getParameter(param);
        } catch (Exception ex) {
            if (param.equalsIgnoreCase("nick")) {
                return ("invitado");
            } else if (param.equalsIgnoreCase("idioma")) {
                return "es";
            } else if (param.indexOf("consola")>=0) {
                return "128";
            } else if (param.equalsIgnoreCase("PropiosR")) {
                return "29";
            } else if (param.equalsIgnoreCase("mPropiosG")) {
                return "163";
            } else if (param.equalsIgnoreCase("mPropiosB")) {
                return "248";
            } else if (param.indexOf("mOtros")>=0) { //mOtrosR mOtrosG mOtrosB
                return "0";
            } else if (param.equalsIgnoreCase("ncanal")) {
                return "iesinclan";
            } else if (param.equalsIgnoreCase("URLAyuda")) {
                return "http://iesinclan.dyndns.org";
            } else if (param.equalsIgnoreCase("URLSalida")) {
                return "http://iesinclan.dyndns.org";
            } else if (param.equalsIgnoreCase("filtro")) {
                return "3";
            } else if (param.equalsIgnoreCase("ctR")) {
                return "0";
            } else if (param.equalsIgnoreCase("colorTexto")) {
                return "#000000";
            } else if (param.equalsIgnoreCase("colorFondoTexto")) {
                return "#EE7700";
            } else if (param.equalsIgnoreCase("colorFondo")) {
                return "#EE7700";
            } else if (param.equalsIgnoreCase("wApplet")) {
                return "800";
            } else if (param.equalsIgnoreCase("hApplet")) {
                return "600";
            } else {
                return "";
            }
        }
    }

    public void init()
    {
        super.init();
        try
        {

            red = Integer.parseInt(getParameter("consolaR"));
            green = Integer.parseInt(getParameter("consolaG"));
            blue = Integer.parseInt(getParameter("consolaB"));
            colorConsola = new Color(red, green, blue);
            red = Integer.parseInt(getParameter("mPropiosR"));
            green = Integer.parseInt(getParameter("mPropiosG"));
            blue = Integer.parseInt(getParameter("mPropiosB"));
            colorMensajesPropios = new Color(red, green, blue);
            red = Integer.parseInt(getParameter("mOtrosR"));
            green = Integer.parseInt(getParameter("mOtrosG"));
            blue = Integer.parseInt(getParameter("mOtrosB"));
            colorMensajesOtros = new Color(red, green, blue);
        }
        catch(Exception exception) { }
        colorFondo = new Color(128, 0, 128);
        colorFondoTexto = new Color(128, 0, 128);
        colorTexto = new Color(0, 0, 0);
        try
        {
            red = convierteHex(getParameter("colorFondo"));
            colorFondo = new Color(red);
            red = convierteHex(getParameter("colorFondoTexto"));
            colorFondoTexto = new Color(red);
            red = convierteHex(getParameter("colorTexto"));
            colorTexto = new Color(red);
        }
        catch(Exception exception1) { }
        filtroBusq = 3;
        nickParam = getParameter("nick");
        ncanalParam = getParameter("ncanal");
        URLAyuda = getParameter("urlAyuda");
        URLSalida = getParameter("urlSalida");
        idioma = "es";
        setBackground(colorFondo);
        leerIdioma();
        String s = System.getProperty("os.name");
        if(s.toUpperCase().indexOf("WINDOWS") == -1)
            tamFuente = 14;
        label1.setFont(new Font("Arial", 1, tamFuente));
        label1.setBounds(228, 120, 250, 26);
        label1.setText((String)tablaIdioma.get("conect"));
        add(label1);
        label1.setBackground(colorFondo);
        try {
           UrlIconos = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getCodeBase())))).append(idioma).append("es/gifs/"))).replace('\\', '/');
        } catch (Exception ex) {
           UrlIconos = ".";
        }
        setName("Principal");
        setLayout(null);
        System.out.println("Versi\363n applet 3.1 ");
        System.out.println("Versi\363n Java: ".concat(String.valueOf(String.valueOf(System.getProperty("java.version")))));
    }

    public void start()
    {
        if(lNick == null)
            lNick = new leerVar("Chat I.E.S. Ramón Valle-Inclán - Lectura de Nick", true, colorFondo);
        if (nickParam==null || nickParam.equalsIgnoreCase("") || nickParam.equalsIgnoreCase("invitado")) {
            nickParam = lNick.leerCadena();
        }
    }

    public void seguir()
    {
        try
        {
            add(getMarcoChat(), getMarcoChat().getName());
            getMarcoChat().getUsuarios().setBackground(Color.white);
            getMarcoChat().getCanales().setBackground(Color.white);
            getMarcoChat().getAreas().setBackground(Color.white);
            initConnections();
            getMarcoChat().getLabel4().setText("Consola");
            getMarcoChat().getTextoConv().setVisible(true);
            setFont(new Font("dialog", 1, tamFuente));
            ivjMarcoChat.getComando().setEditable(true);
            f = new ListaCanales(this);
            f.setBackground(colorFondo);
            f.setResizable(false);
            h = new CambiaNick(this);
            h.setBackground(colorFondo);
            nickusado = new NickUsado(this);
            nickusado.setBackground(colorFondo);
            ventanaBuscar = new Busqueda(this);
            ponIconos();
            calculaAltoAnchoMinimos();
            leer();
            leerAcciones();
            if(!conectadoDirecto)
                conectarDirecto();
            remove(label1);
            getMarcoChat().cambiaIpad(true);
            validate();
            return;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public void paint(Graphics g)
    {
        if(!conectado && !nopinta)
        {
            nopinta = true;
            seguir();
        }
    }

    public void stop()
    {
        if(lNick != null)
        {
            lNick.hide();
            lNick.dispose();
        }
    }

    public void destroy()
    {
        hayQueSeguir = true;
        hiloReceptor.cerrarVentanas(true);
        hiloReceptor = null;
    }

    public static void main(String args[])
    {
        
        Principal applet = new Principal();
        applet.isStandalone = true;
        Frame frame = new Frame();
        frame.setTitle("Applet Frame");
        frame.add(applet, "Center");
        
        applet.init();
        applet.start();
        frame.setSize(800, 680);
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation((d.width - frame.getSize().width) / 2, (d.height - frame.getSize().height) / 2);
        frame.addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e)
            {
                System.exit(0);
            }

        }
);
        frame.setVisible(true);
    }

    private void initConnections()
    {
        ivjMarcoChat.getComando().addKeyListener(this);
        ivjMarcoChat.getComando().addMouseListener(this);
        ivjMarcoChat.getExpresiones().addItemListener(this);
        ivjMarcoChat.getCambiaNick().addMouseListener(this);
        ivjMarcoChat.getQuien().addMouseListener(this);
        ivjMarcoChat.getUnir().addMouseListener(this);
        ivjMarcoChat.getAyuda().addMouseListener(this);
        ivjMarcoChat.getSalir().addMouseListener(this);
        ivjMarcoChat.getCanales().addMouseListener(this);
        ivjMarcoChat.getAreas().addMouseListener(this);
        ivjMarcoChat.getAreas().addItemListener(this);
        ivjMarcoChat.getUsuarios().addActionListener(this);
        ivjMarcoChat.getCanales().addActionListener(this);
        ivjMarcoChat.getComando().addMouseListener(this);
        ivjMarcoChat.getExpresiones().addMouseListener(this);
        ivjMarcoChat.getAyuda().addMouseListener(this);
        ivjMarcoChat.getSalir().addMouseListener(this);
        ivjMarcoChat.getAreas().addMouseListener(this);
        ivjMarcoChat.getUsuarios().addMouseListener(this);
    }

    public void itemStateChanged(ItemEvent itemevent)
    {
        if(itemevent.getSource() == ivjMarcoChat.getExpresiones() && getMarcoChat().getExpresiones().getSelectedIndex() != 0)
        {
            int i = getMarcoChat().getExpresiones().getSelectedIndex();
            i--;
            getMarcoChat().getComando().append(String.valueOf(String.valueOf((new StringBuffer(" ")).append(Acciones[i]).append(" "))));
            getMarcoChat().getComando().requestFocus();
        }
        if(itemevent.getSource() == ivjMarcoChat.getAreas())
        {
            int j = -1;
            try
            {
                if(ivjMarcoChat.getAreas().getItemCount() > 0)
                    j = ivjMarcoChat.getAreas().getSelectedIndex();
            }
            catch(Exception exception) { }
            try
            {
                if(j >= 0)
                {
                    ivjMarcoChat.getCanales().removeAll();
                    for(int k = 1; k < hiloReceptor.AreasyCanales[j].length; k++)
                        if(hiloReceptor.AreasyCanales[j][k].compareTo("") != 0)
                        {
                            StringTokenizer stringtokenizer = new StringTokenizer(hiloReceptor.AreasyCanales[j][k]);
                            String s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(stringtokenizer.nextToken())))).append("   ").append(stringtokenizer.nextToken())));
                            ivjMarcoChat.getCanales().add(s);
                        }

                    return;
                }
            }
            catch(Exception _ex)
            {
                System.out.println("Error en Principal:itemStateChanged:Areas2");
                return;
            }
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        if(hiloReceptor != null)
            hiloReceptor.asignaVentanaActual("Consola");
        if(actionevent.getSource() == ivjMarcoChat.getCanales())
        {
            String s = ivjMarcoChat.getCanales().getSelectedItem();
            int i = s.indexOf(" ");
            if(!estoyEnCanal(s.substring(0, i)))
            {
                ivjMarcoChat.getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(s.substring(0, i)))));
                hiloReceptor.lanzaComando();
            } else
            {
                Registro registro = new Registro((Registro)hiloReceptor.ventanas.get(s.substring(0, i).toUpperCase()));
                int k = registro.posicion;
                if(k != 0)
                    hiloReceptor.Chats[k].toFront();
            }
        }
        if(actionevent.getSource() == ivjMarcoChat.getUsuarios())
        {
            if(ventanaProp != null)
                ventanaProp.dispose();
            String s1 = null;
            if(ivjMarcoChat.getUsuarios().getSelectedItem().startsWith("*"))
                s1 = ivjMarcoChat.getUsuarios().getSelectedItem().substring(1);
            else
            if(ivjMarcoChat.getUsuarios().getSelectedItem().startsWith("@*"))
                s1 = ivjMarcoChat.getUsuarios().getSelectedItem().substring(2);
            else
                s1 = ivjMarcoChat.getUsuarios().getSelectedItem();
            ventanaProp = new PropUsu(this, s1);
            Rectangle rectangle = ivjMarcoChat.getUsuarios().getBounds();
            int j = ivjMarcoChat.getUsuarios().getLocationOnScreen().x;
            int l = ivjMarcoChat.getUsuarios().getLocationOnScreen().y;
            ivjMarcoChat.getUsuarios().getSelectedIndex();
            ventanaProp.setBounds(j > 0 ? j + rectangle.width : rectangle.x + rectangle.width, l > 0 ? l : rectangle.y, 91, 65);
            ventanaProp.setVisible(true);
        }
    }

    public void keyPressed(KeyEvent keyevent1)
    {
    }

    public void keyReleased(KeyEvent keyevent)
    {
        hiloReceptor.asignaVentanaRealUsuario("Consola");
        if(keyevent.getSource() == ivjMarcoChat.getComando() && keyevent.getKeyChar() == '\n' && conectado && !expulsadoPrincipal && ivjMarcoChat.getComando().getText().trim() != "")
        {
            hiloReceptor.asignaVentanaActual("Consola");
            hiloReceptor.lanzaComando();
        }
    }

    public void KeyReleased(KeyEvent keyevent1)
    {
    }

    public void keyTyped(KeyEvent keyevent1)
    {
    }

    public void leer()
    {
        String s = "";
        String s1 = "";
        try
        {
            URL url = new URL(String.valueOf(String.valueOf(getCodeBase())).concat("config/config.cfg").replace('\\', '/'));
            System.out.println("url: "+url);
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoInput(true);
            urlconnection.setAllowUserInteraction(false);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
            String s2;
            while((s2 = bufferedreader.readLine()) != null) 
                s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s)))).append(s2).append("\n")));
            s = String.valueOf(String.valueOf(s)).concat("\n");
            bufferedreader.close();
            int i = s.indexOf("SerWWW=");
            int j = s.indexOf("\n", i + "SerWWW=".length());
            i += "SerWWW=".length();
            servidor = s.substring(i, j).trim();
            i = s.indexOf("Puerto=");
            j = s.indexOf("\n", i + "Puerto=".length());
            i += "Puerto=".length();
            puerto = (new Integer(s.substring(i, j).trim())).intValue();
            return;
        }
        catch(Exception exception)
        {
            System.out.println("Error en lectura de par\341metros iniciales.".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public void leerIdioma()
    {
        String s = new String("");
        try
        {
            URL url = new URL(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getCodeBase())))).append(idioma).append("/config/idioma.cfg"))).replace('\\', '/'));
            DataInputStream datainputstream = new DataInputStream(url.openConnection().getInputStream());
            InputStreamReader inputstreamreader = new InputStreamReader(datainputstream);
            BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
            do
            {
                String s1;
                if((s1 = bufferedreader.readLine()) == null)
                    break;
                if(s1.length() != 0 && s1.charAt(0) != '#')
                {
                    int i = s1.indexOf('=');
                    String s2 = s1.substring(0, i);
                    String s3 = s1.substring(i + 1);
                    tablaIdioma.put(s2, s3);
                }
            } while(true);
            divideMensaje("menTem", 42);
            divideMensaje("menUsa", 37);
            return;
        }
        catch(Exception exception)
        {
            System.out.println("Fichero de configuraci\363n del idioma no encontrado: ".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public void leerAcciones()
    {
        try
        {
            URL url = new URL(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getCodeBase())))).append(idioma).append("/config/acciones.cfg"))).replace('\\', '/'));
            URLConnection urlconnection = url.openConnection();
            urlconnection.setDoInput(true);
            urlconnection.setAllowUserInteraction(false);
            BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(urlconnection.getInputStream()));
            int i = 0;
            int j = 0;
            String s;
            String s1;
            for(s = new String(); (s1 = bufferedreader.readLine()) != null; s = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s)))).append(s1).append("\n"))));
            bufferedreader.close();
            for(int k = 0; k < s.length(); k++)
                if(s.charAt(k) == '\n')
                    j++;

            Acciones = new String[j];
            itemsAcciones = new String[j];
            while(i < j) 
            {
                int l = s.indexOf("=") + 1;
                int i1 = s.indexOf("\n");
                itemsAcciones[i] = new String(s.substring(0, l - 1));
                Acciones[i] = new String(s.substring(l, i1));
                ivjMarcoChat.getExpresiones().add(itemsAcciones[i]);
                i++;
                s = s.substring(i1 + 1, s.length());
            }
            return;
        }
        catch(Exception exception)
        {
            System.out.println("Error en lectura de par\341metros iniciales.".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public void mostrarCanales()
    {
        ivjMarcoChat.getComando().setText("/LIST >0");
        hiloReceptor.lanzaComando();
    }

    public void mostrarChat()
    {
        try
        {
            ivjMarcoChat.setVisible(true);
            return;
        }
        catch(Exception exception)
        {
            System.out.println("Se produjo la excepcion ".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public void mostrarUsuarios()
    {
        ivjMarcoChat.getComando().setText("/WHOIS *");
        hiloReceptor.lanzaComando();
    }

    public void mostrarUsuarioSeleccionado(String s)
    {
        ivjMarcoChat.getComando().setText("/WHOIS ".concat(String.valueOf(String.valueOf(s))));
        hiloReceptor.lanzaComando();
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
        if(hiloReceptor != null)
            hiloReceptor.asignaVentanaActual("Consola");
        if(mouseevent.getSource() == getMarcoChat().getAyuda())
        {
            try
            {
                getAppletContext().showDocument(new URL(URLAyuda), "Ayuda");
                return;
            }
            catch(MalformedURLException _ex)
            {
                System.out.println("Error en URL de la ayuda");
            }
            return;
        } else
        {
            return;
        }
    }

    public void mouseEntered(MouseEvent mouseevent1)
    {
    }

    public void mouseExited(MouseEvent mouseevent1)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        hiloReceptor.asignaVentanaRealUsuario("Consola");
        try
        {
            if(mouseevent.getSource() == getMarcoChat().getUnir())
            {
                ivjMarcoChat.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("entra_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getCambiaNick())
            {
                ivjMarcoChat.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("antifaz_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getAyuda())
            {
                ivjMarcoChat.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("lista_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getSalir())
            {
                ivjMarcoChat.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("sal_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getQuien())
            {
                ivjMarcoChat.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("quien_t2.gif")));
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Excepcion al traer un icono");
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        try
        {
            if(mouseevent.getSource() == getMarcoChat().getUnir())
            {
                ivjMarcoChat.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("entra_t.gif")));
                if(ventanaCanal != null)
                    ventanaCanal.dispose();
                int i = getLocationOnScreen().x;
                int k = getLocationOnScreen().y;
                ventanaCanal = new CreaCanal(this);
                ventanaCanal.setBounds(i > 0 ? i : 0, k > 0 ? k : 0, 280, 145);
                ventanaCanal.show();
                ventanaCanal.textField1.requestFocus();
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getCambiaNick())
            {
                ivjMarcoChat.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("antifaz_t.gif")));
                int j = getLocationOnScreen().x;
                int l = getLocationOnScreen().y;
                h.setBounds(j > 0 ? j : 0, l > 0 ? l : 0, 224, 180);
                h.show();
                h.getNick().requestFocus();
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getAyuda())
            {
                ivjMarcoChat.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("lista_t.gif")));
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getSalir())
            {
                ivjMarcoChat.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("sal_t.gif")));
                if(conectado)
                    hiloReceptor.cerrarVentanas(true);
                try
                {
                    getAppletContext().showDocument(new URL(URLSalida));
                    return;
                }
                catch(MalformedURLException _ex)
                {
                    System.out.println("Error en URL de la ayuda");
                }
                return;
            }
            if(mouseevent.getSource() == getMarcoChat().getQuien())
            {
                ivjMarcoChat.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("quien_t.gif")));
                ivjMarcoChat.getComando().setText("/LUSERS");
                hiloReceptor.VentanaActual = 0;
                hiloReceptor.lanzaComando();
                ventanaBuscar.setVisible(true);
                ivjMarcoChat.getComando().setText("");
                ventanaBuscar.textField1.requestFocus();
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Excepcion al traer un icono");
        }
    }

    public void ponIconos()
    {
        try
        {
            ivjMarcoChat.getCambiaNick().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("antifaz_t.gif")));
            ivjMarcoChat.getSalir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("sal_t.gif")));
            ivjMarcoChat.getAyuda().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("lista_t.gif")));
            ivjMarcoChat.getUnir().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("entra_t.gif")));
            ivjMarcoChat.getQuien().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconos)).concat("quien_t.gif")));
            return;
        }
        catch(MalformedURLException _ex)
        {
            System.out.println("Error en Principal:ponIconos");
            return;
        }
        catch(PropertyVetoException _ex)
        {
            System.out.println("Error en Principal:ponIconos");
        }
    }

    public boolean estoyEnCanal(String s)
    {
        return hiloReceptor.ventanas.containsKey(s.toUpperCase());
    }

    private void calculaAltoAnchoMinimos()
    {
        int i = 0;
        int j = 0;
        i += ivjMarcoChat.getCambiaNick().getImage().getWidth(null);
        i += ivjMarcoChat.getSalir().getImage().getWidth(null);
        i += ivjMarcoChat.getAyuda().getImage().getWidth(null);
        i += ivjMarcoChat.getUnir().getImage().getWidth(null);
        i += ivjMarcoChat.getQuien().getImage().getWidth(null);
        i += 180;
        j = getSize().height / 2;
        minimoAnchoCanal = i;
        minimoAltoCanal = j;
    }

    public void setSize(int i, int j)
    {
        if(System.getProperty("java.version").compareTo("1.1") == 0)
            sizeie4 = true;
        if(!sizeie4)
            try
            {
                if(i < minimoAnchoCanal)
                    i = minimoAnchoCanal;
                if(j < minimoAltoCanal)
                    j = minimoAltoCanal;
                super.setSize(i, j);
                if(ivjMarcoChat != null)
                {
                    ivjMarcoChat.setSize(i, j);
                    if(creado)
                        ivjMarcoChat.cambiaIpad(true);
                    else
                        creado = true;
                }
                validate();
                return;
            }
            catch(Exception _ex)
            {
                return;
            }
        else
            return;
    }

    public String reemplazarTexto(String s, String s1, String s2)
    {
        String s3 = "";
        int i = 0;
        boolean flag = false;
        for(int j = s.indexOf(s1, i); j != -1; j = s.indexOf(s1, i))
        {
            s3 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s3)))).append(s.substring(i, j)).append(s2)));
            i = j + s1.length();
            s3 = String.valueOf(s3) + String.valueOf(s.substring(i, s.length()));
            s = s3;
            i = 0;
            s3 = "";
        }

        return s;
    }

    public void divideMensaje(String s, int i)
    {
        String s1 = (String)tablaIdioma.get(s);
        StringTokenizer stringtokenizer = new StringTokenizer(s1);
        StringBuffer stringbuffer = new StringBuffer();
        int j = 1;
        while(stringtokenizer.hasMoreTokens()) 
        {
            String s2 = stringtokenizer.nextToken();
            if(stringbuffer.length() + s2.length() > i)
            {
                tablaIdioma.put(String.valueOf(s) + String.valueOf((new Integer(j)).toString()), stringbuffer.toString());
                j++;
                stringbuffer = new StringBuffer(String.valueOf(String.valueOf(s2)).concat(" "));
            } else
            {
                stringbuffer.append(String.valueOf(String.valueOf(s2)).concat(" "));
            }
        }
        tablaIdioma.put(String.valueOf(s) + String.valueOf((new Integer(j)).toString()), stringbuffer.toString());
    }

    public Principal()
    {
        
        lNick = null;
        isStandalone = false;
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = new Color(29, 163, 248);
        expulsadoPrincipal = false;
        conectado = false;
        filtroBusq = 3;
        idioma = "es";
        conectadoDirecto = false;
        label1 = new Label();
        tablaIdioma = new Hashtable();
        tamFuente = 12;
        nopinta = false;
        hayQueSeguir = false;
        creado = false;
        sizeie4 = false;
    }

    public Color colorConsola;
    public Color colorMensajesOtros;
    public Color colorMensajesPropios;
    public boolean expulsadoPrincipal;
    public ConexionIrc c;
    private ContenidoSencillo m;
    public hiloRecibir hiloReceptor;
    public ListaCanales f;
    public CambiaNick h;
    public NickUsado nickusado;
    public CreaCanal ventanaCanal;
    public Busqueda ventanaBuscar;
    public PropUsu ventanaProp;
    public VentanaIrcCanal ivjMarcoChat;
    boolean conectado;
    public String UrlIconos;
    public String servidor;
    public int puerto;
    public String Acciones[];
    public String itemsAcciones[];
    private int red;
    private int green;
    private int blue;
    public Color colorFondo;
    public Color colorFondoTexto;
    public Color colorTexto;
    public int filtroBusq;
    public int minimoAnchoCanal;
    public int minimoAltoCanal;
    private String nickParam;
    private String ncanalParam;
    public String URLAyuda;
    private String URLSalida;
    private String idioma;
    private boolean conectadoDirecto;
    private Label label1;
    Hashtable tablaIdioma;
    public int tamFuente;
    private boolean nopinta;
    public boolean hayQueSeguir;
    private boolean creado;
    private boolean sizeie4;
    private leerVar lNick;
    private boolean isStandalone;
}
