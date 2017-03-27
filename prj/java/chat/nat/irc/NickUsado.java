// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NickUsado.java

package nat.irc;

import java.awt.*;
import java.awt.event.*;
import java.io.PrintStream;
import java.net.URL;
import java.util.EventObject;
import java.util.Hashtable;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            Principal, hiloRecibir, VentanaIrcCanal, MarcoVentanaIrcCanal, 
//            MarcoVentanaIrcPrivada, VentanaIrcPrivada

public class NickUsado extends Frame
    implements MouseListener, WindowListener, KeyListener, ComponentListener
{

    public NickUsado()
    {
        initialize();
    }

    public NickUsado(String s)
    {
        super(s);
    }

    public NickUsado(Principal principal)
    {
        padre = principal;
        initialize();
    }

    public void cambiarlo()
    {
        padre.hiloReceptor.asignaVentanaActual("Consola");
        if(ivjNick.getText().trim().length() > 9)
            ivjNick.setText(ivjNick.getText().trim().substring(0, 9));
        if(Character.isDigit(ivjNick.getText().trim().charAt(0)))
            return;
        padre.getMarcoChat().getComando().setText("/NICK ".concat(String.valueOf(String.valueOf(ivjNick.getText().trim()))));
        padre.hiloReceptor.lanzaComando();
        padre.setEnabled(true);
        if(ivjNick.getText().trim().compareTo("") != 0)
        {
            padre.hiloReceptor.MiNick = ivjNick.getText().trim();
            padre.getMarcoChat().getLabel2().setText(padre.hiloReceptor.MiNick);
            for(int i = 0; i < padre.hiloReceptor.Chats.length; i++)
                if(padre.hiloReceptor.Chats[i] != null)
                    padre.hiloReceptor.Chats[i].getMarcoIrcCanal().getLabel2().setText(padre.hiloReceptor.MiNick);

            for(int j = 0; j < padre.hiloReceptor.ChatsPrivada.length; j++)
                if(padre.hiloReceptor.ChatsPrivada[j] != null)
                    padre.hiloReceptor.ChatsPrivada[j].getMarcoIrcPrivada().getLabel2().setText(padre.hiloReceptor.MiNick);

        }
        ivjNick.setText("");
        dispose();
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

    public ImageViewer getAceptarGrafico()
    {
        if(ivjAceptarGrafico == null)
            try
            {
                ivjAceptarGrafico = new ImageViewer();
                ivjAceptarGrafico.setName("AceptarGr");
                ivjAceptarGrafico.setBounds(24, 115, 96, 24);
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
                ivjCancelarGrafico.setBounds(120, 115, 96, 24);
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
                getContentsPane().add(getLabel1(), getLabel1().getName());
                getContentsPane().add(getLabel2(), getLabel2().getName());
                getContentsPane().add(getLabel3(), getLabel3().getName());
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
                ivjLabel1.setText((String)padre.tablaIdioma.get("menUsa1"));
                ivjLabel1.setBounds(24, 24, 192, 12);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel1;
    }

    private Label getLabel2()
    {
        if(ivjLabel2 == null)
            try
            {
                ivjLabel2 = new Label();
                ivjLabel2.setName("Label2");
                ivjLabel2.setFont(new Font("Dialog", 1, padre.tamFuente));
                ivjLabel2.setText((String)padre.tablaIdioma.get("menUsa2"));
                ivjLabel2.setBounds(24, 40, 192, 12);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel2;
    }

    private Label getLabel3()
    {
        if(ivjLabel3 == null)
            try
            {
                ivjLabel3 = new Label();
                ivjLabel3.setName("Label3");
                ivjLabel3.setFont(new Font("Dialog", 1, padre.tamFuente));
                ivjLabel3.setText((String)padre.tablaIdioma.get("menUsa3"));
                ivjLabel3.setBounds(24, 56, 192, 12);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLabel3;
    }

    private TextField getNick()
    {
        if(ivjNick == null)
            try
            {
                ivjNick = new TextField();
                ivjNick.setName("Nick");
                ivjNick.setBounds(24, 82, 188, 26);
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
        setName("NickUsado");
        setLayout(null);
        setSize(500, 170);
        setTitle((String)padre.tablaIdioma.get("aliUsa"));
        UrlIconosBase = padre.UrlIconos;
        add(getContentsPane(), "Center");
        initConnections();
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
                getNick().setSize(188, 26);
                getCancelarGrafico().setBounds(120, 115, 96, 24);
                return;
            }
            getContentsPane().setSize(i, j);
            getNick().setSize(188 + k, 26);
            getCancelarGrafico().setBounds(120 + k, 115, 96, 24);
        }
    }

    private Panel ivjContentsPane;
    private Label ivjLabel1;
    private Label ivjLabel2;
    private Label ivjLabel3;
    private TextField ivjNick;
    Principal padre;
    private ImageViewer ivjAceptarGrafico;
    private ImageViewer ivjCancelarGrafico;
    String UrlIconosBase;
}
