// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CambiaNick.java

package nat.irc;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.EventObject;
import java.util.Hashtable;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            Principal, hiloRecibir, VentanaIrcCanal

public class CambiaNick extends Frame
    implements MouseListener, WindowListener, KeyListener, ComponentListener
{

    public CambiaNick()
    {
        initialize();
    }

    public CambiaNick(String s)
    {
        super(s);
    }

    public CambiaNick(Principal principal)
    {
        padre = principal;
        initialize();
    }

    public void cambiarlo()
    {
        padre.hiloReceptor.asignaVentanaActual("Consola");
        if(ivjNick.getText().trim().length() > 9)
            ivjNick.setText(ivjNick.getText().trim().substring(0, 9));
        new Character(ivjNick.getText().trim().charAt(0));
        if(Character.isDigit(ivjNick.getText().trim().charAt(0)))
        {
            return;
        } else
        {
            padre.getMarcoChat().getComando().setText("/NICK ".concat(String.valueOf(String.valueOf(ivjNick.getText().trim()))));
            padre.hiloReceptor.lanzaComando();
            padre.setEnabled(true);
            ivjNick.setText("");
            dispose();
            return;
        }
    }

    public void cancelar_MouseClicked()
    {
        ivjNick.setText("");
        padre.setEnabled(true);
        dispose();
    }

    private void connEtoC1(WindowEvent windowevent)
    {
        try
        {
            padre.setEnabled(true);
            dispose();
            return;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoC2(MouseEvent mouseevent)
    {
        try
        {
            cancelar_MouseClicked();
            return;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    public ImageViewer getTituloGrafico()
    {
        if(ivjTituloGrafico == null)
            try
            {
                ivjTituloGrafico = new ImageViewer();
                ivjTituloGrafico.setName("TituloGr");
                ivjTituloGrafico.setBounds(0, 12, 144, 36);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTituloGrafico;
    }

    public ImageViewer getAceptarGrafico()
    {
        if(ivjAceptarGrafico == null)
            try
            {
                ivjAceptarGrafico = new ImageViewer();
                ivjAceptarGrafico.setName("AceptarGr");
                ivjAceptarGrafico.setBounds(24, 108, 96, 24);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjAceptarGrafico;
    }

    public ImageViewer getCancelarGrafico()
    {
        if(ivjCancelarGrafico == null)
            try
            {
                ivjCancelarGrafico = new ImageViewer();
                ivjCancelarGrafico.setName("CancelarGr");
                ivjCancelarGrafico.setBounds(120, 108, 96, 24);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCancelarGrafico;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                ivjContentsPane.setBounds(0, 0, 280, 200);
                ivjContentsPane.setBackground(padre.colorFondo);
                getContentsPane().add(getTituloGrafico(), getTituloGrafico().getName());
                getTituloGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cambiar_alias.gif")));
                getContentsPane().add(getLabel1(), getLabel1().getName());
                getContentsPane().add(getNick(), getNick().getName());
                getContentsPane().add(getAceptarGrafico(), getAceptarGrafico().getName());
                getContentsPane().add(getCancelarGrafico(), getCancelarGrafico().getName());
                getAceptarGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("aceptar.gif")));
                getCancelarGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cancelar.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private Label getLabel1()
    {
        if(ivjLabel1 == null)
            try
            {
                ivjLabel1 = new Label();
                ivjLabel1.setName("Label1");
                ivjLabel1.setFont(new Font("Dialog", 1, padre.tamFuente));
                ivjLabel1.setText((String)padre.tablaIdioma.get("intAli"));
                ivjLabel1.setBounds(24, 48, 192, 24);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    public TextField getNick()
    {
        if(ivjNick == null)
            try
            {
                ivjNick = new TextField();
                ivjNick.setName("Nick");
                ivjNick.setBounds(24, 72, 192, 26);
                ivjNick.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjNick;
    }

    private void handleException(Throwable throwable1)
    {
    }

    private void initConnections()
    {
        ivjAceptarGrafico.addMouseListener(this);
        addWindowListener(this);
        ivjCancelarGrafico.addMouseListener(this);
        ivjNick.addKeyListener(this);
        addComponentListener(this);
    }

    private void initialize()
    {
        setBackground(Color.white);
        setFont(new Font("dialog", 1, padre.tamFuente));
        setName("CambiaNick");
        setLayout(null);
        setSize(500, 300);
        setTitle((String)padre.tablaIdioma.get("camAli"));
        UrlIconosBase = padre.UrlIconos;
        add(getContentsPane(), "Center");
        initConnections();
    }

    public static void main(String args[])
    {
        try
        {
            CambiaNick cambianick = new CambiaNick();
            try
            {
                Class class1 = Class.forName("com.ibm.uvm.abt.edit.WindowCloser");
                Class aclass[] = {
                    java.awt.Window.class
                };
                Object aobj[] = {
                    cambianick
                };
                Constructor constructor = class1.getConstructor(aclass);
                constructor.newInstance(aobj);
            }
            catch(Throwable throwable1) { }
            cambianick.setVisible(true);
            return;
        }
        catch(Throwable throwable)
        {
            System.err.println("Exception occurred in main() of java.awt.Frame");
            throwable.printStackTrace(System.out);
            return;
        }
    }

    public void mouseClicked(MouseEvent mouseevent1)
    {
    }

    public void mouseEntered(MouseEvent mouseevent1)
    {
    }

    public void mouseExited(MouseEvent mouseevent1)
    {
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        try
        {
            if(mouseevent.getSource() == getCancelarGrafico())
            {
                ivjCancelarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cancelar_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjAceptarGrafico)
            {
                ivjAceptarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("aceptar_t2.gif")));
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Error al traer un icono.");
        }
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        try
        {
            if(mouseevent.getSource() == getAceptarGrafico())
            {
                ivjAceptarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("aceptar.gif")));
                cambiarlo();
            }
            if(mouseevent.getSource() == getCancelarGrafico())
            {
                ivjCancelarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cancelar.gif")));
                connEtoC2(mouseevent);
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Excepcisn al traer un icono.");
        }
    }

    public void keyReleased(KeyEvent keyevent)
    {
        if(keyevent.getSource() == ivjNick && keyevent.getKeyChar() == '\n' && ivjNick.getText().compareTo("") != 0)
            cambiarlo();
    }

    public void keyPressed(KeyEvent keyevent1)
    {
    }

    public void keyTyped(KeyEvent keyevent1)
    {
    }

    public void windowActivated(WindowEvent windowevent1)
    {
    }

    public void windowClosed(WindowEvent windowevent1)
    {
    }

    public void windowClosing(WindowEvent windowevent)
    {
        if(windowevent.getSource() == this)
            connEtoC1(windowevent);
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
            int k = i - 224;
            if(k < 0)
            {
                getContentsPane().setSize(224, j);
                getNick().setSize(192, 26);
                getCancelarGrafico().setBounds(120, 108, 96, 24);
                return;
            }
            getContentsPane().setSize(i, j);
            getNick().setSize(192 + k, 26);
            getCancelarGrafico().setBounds(120 + k, 108, 96, 24);
        }
    }

    private Panel ivjContentsPane;
    private Label ivjLabel1;
    private TextField ivjNick;
    public Principal padre;
    private ImageViewer ivjAceptarGrafico;
    private ImageViewer ivjCancelarGrafico;
    private ImageViewer ivjTituloGrafico;
    public String UrlIconosBase;
}
