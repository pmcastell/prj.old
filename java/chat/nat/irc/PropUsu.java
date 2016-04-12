// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   PropUsu.java

package nat.irc;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyVetoException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.EventObject;
import java.util.Hashtable;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            Principal, hiloRecibir, VentanaIrcCanal, MarcoVentanaIrcCanal, 
//            MarcoVentanaIrcPrivada, VentanaIrcPrivada, Busqueda

public class PropUsu extends Frame
    implements MouseListener, WindowListener
{

    public PropUsu(Principal principal)
    {
        fComponentsAdjusted = false;
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        imageViewer1 = new ImageViewer();
        padre = principal;
        labelNegrita = new Font("Dialog", 1, padre.tamFuente);
        labelNormal = new Font("Dialog", 0, padre.tamFuente);
        setLayout(null);
        setSize(100, 65);
        setBackground(padre.colorFondo);
        setVisible(false);
        try
        {
            imageViewer1.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("ok1.gif")));
        }
        catch(MalformedURLException malformedurlexception) { }
        catch(PropertyVetoException propertyvetoexception) { }
        imageViewer1.setBounds(55, 0, 15, 20);
        label1.setText((String)padre.tablaIdioma.get("ignora"));
        add(label1);
        label1.setBounds(8, 0, 93, 20);
        label1.setFont(labelNormal);
        label2.setText((String)padre.tablaIdioma.get("chaPri"));
        add(label2);
        label2.setBounds(8, 20, 93, 20);
        label2.setFont(labelNormal);
        label3.setText((String)padre.tablaIdioma.get("quienE"));
        add(label3);
        label3.setBounds(8, 40, 93, 20);
        label3.setFont(labelNormal);
        setResizable(false);
        addWindowListener(this);
        label1.addMouseListener(this);
        label2.addMouseListener(this);
        label3.addMouseListener(this);
    }

    public void addNotify()
    {
        Dimension dimension = getSize();
        super.addNotify();
        if(fComponentsAdjusted)
            return;
        Insets insets = getInsets();
        setSize(insets.left + insets.right + dimension.width, insets.top + insets.bottom + dimension.height);
        Component acomponent[] = getComponents();
        for(int i = 0; i < acomponent.length; i++)
        {
            Point point = acomponent[i].getLocation();
            point.translate(insets.left, insets.top);
            acomponent[i].setLocation(point);
        }

        fComponentsAdjusted = true;
    }

    public PropUsu(Principal principal, String s)
    {
        this(principal);
        setTitle(s);
        confirmaIgnorado(s);
    }

    public void setVisible(boolean flag)
    {
        super.setVisible(flag);
    }

    public void windowActivated(WindowEvent windowevent1)
    {
    }

    public void windowClosed(WindowEvent windowevent1)
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

    public void windowClosing(WindowEvent windowevent)
    {
        Object obj = windowevent.getSource();
        if(obj == this)
            PropUsu_WindowClosing(windowevent);
    }

    public void windowDeactivated(WindowEvent windowevent)
    {
        Object obj = windowevent.getSource();
        String s = System.getProperty("os.name");
        if(s.compareTo("SunSO") == -1 && obj == this)
            PropUsu_WindowClosing(windowevent);
    }

    void PropUsu_WindowClosing(WindowEvent windowevent)
    {
        dispose();
    }

    public void mouseReleased(MouseEvent mouseevent1)
    {
    }

    public void mousePressed(MouseEvent mouseevent1)
    {
    }

    public void mouseExited(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == label1)
        {
            label1_MouseExited(mouseevent);
            return;
        }
        if(obj == label2)
        {
            label2_MouseExited(mouseevent);
            return;
        }
        if(obj == label3)
            label3_MouseExited(mouseevent);
    }

    public void mouseEntered(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == label1)
        {
            label1_MouseEntered(mouseevent);
            return;
        }
        if(obj == label2)
        {
            label2_MouseEntered(mouseevent);
            return;
        }
        if(obj == label3)
            label3_MouseEntered(mouseevent);
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
        if(mouseevent.getClickCount() >= 1)
        {
            Object obj = mouseevent.getSource();
            if(obj == label1)
            {
                label1_MouseClicked(mouseevent);
                return;
            }
            if(obj == label2)
            {
                label2_MouseClicked(mouseevent);
                return;
            }
            if(obj == label3)
                label3_MouseClicked(mouseevent);
        }
    }

    void label1_MouseClicked(MouseEvent mouseevent)
    {
        label1_MouseClicked_Interaction1(mouseevent);
    }

    void label1_MouseClicked_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            if(padre.hiloReceptor.VentanaActual == 0)
            {
                padre.hiloReceptor.asignaVentanaActual("Consola");
                padre.getMarcoChat().getComando().setText("/IGNORE ".concat(String.valueOf(String.valueOf(getTitle()))));
            } else
            {
                padre.hiloReceptor.asignaVentanaActual(padre.hiloReceptor.Chats[padre.hiloReceptor.posicionMarco(padre.hiloReceptor.VentanaActual)].getMarcoIrcCanal().getLabel4().getText());
                padre.hiloReceptor.Chats[padre.hiloReceptor.posicionMarco(padre.hiloReceptor.VentanaActual)].getMarcoIrcCanal().getComando().setText("/IGNORE ".concat(String.valueOf(String.valueOf(getTitle()))));
            }
            padre.hiloReceptor.lanzaComando();
            dispose();
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label2_MouseClicked(MouseEvent mouseevent)
    {
        label2_MouseClicked_Interaction1(mouseevent);
    }

    void label2_MouseClicked_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            String s;
            if(getTitle().startsWith("@"))
                s = getTitle().substring(1);
            else
                s = getTitle();
            String s1 = (String)padre.tablaIdioma.get("iniPri");
            s1 = padre.reemplazarTexto(s1, "%NOMBRE_USUARIO%", s);
            padre.hiloReceptor.escribeVentana(s, s1, padre.hiloReceptor.colorConsola, true);
            padre.hiloReceptor.ChatsPrivada[padre.hiloReceptor.posicionMarco()].getMarcoIrcPrivada().getComando().requestFocus();
            dispose();
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label3_MouseClicked(MouseEvent mouseevent)
    {
        label3_MouseClicked_Interaction1(mouseevent);
    }

    void label3_MouseClicked_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            String s;
            if(getTitle().startsWith("@"))
                s = getTitle().substring(1);
            else
                s = getTitle();
            padre.getMarcoChat().getComando().setText("/LUSERS");
            padre.hiloReceptor.asignaVentanaActual("Consola");
            padre.hiloReceptor.lanzaComando();
            padre.getMarcoChat().getComando().setText("");
            padre.hiloReceptor.asignaVentanaActual("Consola");
            padre.getMarcoChat().getComando().setText("/WHOIS ".concat(String.valueOf(String.valueOf(s))));
            padre.hiloReceptor.lanzaComando();
            padre.ventanaBuscar.setVisible(true);
            dispose();
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label1_MouseEntered(MouseEvent mouseevent)
    {
        label1_MouseEntered_Interaction1(mouseevent);
    }

    void label1_MouseEntered_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label1.setFont(labelNegrita);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label2_MouseEntered(MouseEvent mouseevent)
    {
        label2_MouseEntered_Interaction1(mouseevent);
    }

    void label2_MouseEntered_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label2.setFont(labelNegrita);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label3_MouseEntered(MouseEvent mouseevent)
    {
        label3_MouseEntered_Interaction1(mouseevent);
    }

    void label3_MouseEntered_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label3.setFont(labelNegrita);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label1_MouseExited(MouseEvent mouseevent)
    {
        label1_MouseExited_Interaction1(mouseevent);
    }

    void label1_MouseExited_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label1.setFont(labelNormal);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label2_MouseExited(MouseEvent mouseevent)
    {
        label2_MouseExited_Interaction1(mouseevent);
    }

    void label2_MouseExited_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label2.setFont(labelNormal);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    void label3_MouseExited(MouseEvent mouseevent)
    {
        label3_MouseExited_Interaction1(mouseevent);
    }

    void label3_MouseExited_Interaction1(MouseEvent mouseevent)
    {
        try
        {
            label3.setFont(labelNormal);
            return;
        }
        catch(Exception _ex)
        {
            return;
        }
    }

    private void confirmaIgnorado(String s)
    {
        if(s.charAt(0) == '@')
            s = s.substring(1, s.length());
        for(int i = 0; i < padre.hiloReceptor.listadoIgnorados.length; i++)
            if(padre.hiloReceptor.listadoIgnorados[i].compareTo(s) == 0)
            {
                add(imageViewer1);
                return;
            }

    }

    private Principal padre;
    private Font labelNegrita;
    private Font labelNormal;
    boolean fComponentsAdjusted;
    Label label1;
    Label label2;
    Label label3;
    ImageViewer imageViewer1;
}
