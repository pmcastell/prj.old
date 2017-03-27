// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Busqueda.java

package nat.irc;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Insets;
import java.awt.Label;
import java.awt.List;
import java.awt.Panel;
import java.awt.Point;
import java.awt.TextComponent;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyVetoException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            Principal, hiloRecibir, VentanaIrcCanal, MarcoVentanaIrcCanal

public class Busqueda extends Frame
    implements MouseListener, WindowListener, ActionListener, ComponentListener
{

    public Busqueda()
    {
        fComponentsAdjusted = false;
        panel1 = new Panel();
        imageViewer1 = new ImageViewer();
        imageViewer2 = new ImageViewer();
        label1 = new Label();
        label2 = new Label();
        textField1 = new TextField();
        choice1 = new Choice();
        imageViewer3 = new ImageViewer();
        imageViewer4 = new ImageViewer();
        imageViewer5 = new ImageViewer();
        label3 = new Label();
        list1 = new List(6);
        label4 = new Label();
        label5 = new Label();
        Inicializar();
    }

    public Busqueda(String s)
    {
        this();
        setTitle(s);
    }

    public Busqueda(Principal principal)
    {
        fComponentsAdjusted = false;
        panel1 = new Panel();
        imageViewer1 = new ImageViewer();
        imageViewer2 = new ImageViewer();
        label1 = new Label();
        label2 = new Label();
        textField1 = new TextField();
        choice1 = new Choice();
        imageViewer3 = new ImageViewer();
        imageViewer4 = new ImageViewer();
        imageViewer5 = new ImageViewer();
        label3 = new Label();
        list1 = new List(6);
        label4 = new Label();
        label5 = new Label();
        padre = principal;
        Inicializar();
    }

    private void Inicializar()
    {
        setLocation(50, 50);
        setLayout(null);
        setSize(313, 320);
        setVisible(false);
        setResizable(true);
        panel1.setLayout(null);
        panel1.setBackground(padre.colorFondo);
        panel1.setFont(new Font("Dialog", 1, padre.tamFuente));
        panel1.setBounds(0, 0, 312, 320);
        try
        {
            imageViewer1.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("buscar_usuario.gif")));
        }
        catch(MalformedURLException malformedurlexception) { }
        catch(PropertyVetoException propertyvetoexception) { }
        panel1.add(imageViewer1);
        imageViewer1.setBounds(12, 12, 144, 24);
        try
        {
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
        }
        catch(MalformedURLException malformedurlexception1) { }
        catch(PropertyVetoException propertyvetoexception1) { }
        panel1.add(imageViewer2);
        imageViewer2.setBounds(240, 12, 60, 24);
        label1.setText((String)padre.tablaIdioma.get("numUsu"));
        panel1.add(label1);
        label1.setFont(new Font("Dialog", 1, padre.tamFuente));
        label1.setBounds(12, 36, 212, 12);
        label2.setText((String)padre.tablaIdioma.get("calcul"));
        panel1.add(label2);
        label2.setBounds(224, 36, 35, 12);
        panel1.add(textField1);
        textField1.setBackground(Color.white);
        textField1.setBounds(12, 60, 156, 26);
        choice1.addItem((String)padre.tablaIdioma.get("aliExa"));
        choice1.addItem((String)padre.tablaIdioma.get("empPor"));
        choice1.setBackground(Color.white);
        panel1.add(choice1);
        choice1.select(0);
        choice1.setBounds(172, 60, 128, 24);
        try
        {
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("aceptar.gif")));
        }
        catch(MalformedURLException malformedurlexception2) { }
        catch(PropertyVetoException propertyvetoexception2) { }
        panel1.add(imageViewer3);
        imageViewer3.setBounds(216, 96, 87, 24);
        try
        {
            imageViewer4.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("barra.gif")));
        }
        catch(MalformedURLException malformedurlexception3) { }
        catch(PropertyVetoException propertyvetoexception3) { }
        panel1.add(imageViewer4);
        imageViewer4.setBounds(12, 120, 295, 12);
        try
        {
            imageViewer5.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("flecha.gif")));
        }
        catch(MalformedURLException malformedurlexception4) { }
        catch(PropertyVetoException propertyvetoexception4) { }
        panel1.add(imageViewer5);
        imageViewer5.setBounds(12, 144, 12, 12);
        label3.setText((String)padre.tablaIdioma.get("resBus"));
        panel1.add(label3);
        label3.setBounds(24, 144, 260, 14);
        panel1.add(list1);
        list1.setFont(new Font("Monospaced", 1, padre.tamFuente));
        list1.setBounds(12, 180, 287, 120);
        list1.setBackground(Color.white);
        label4.setText("  ".concat(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("usuMay")))));
        panel1.add(label4);
        label4.setBackground(padre.colorFondoTexto);
        label4.setForeground(padre.colorTexto);
        label4.setBounds(12, 168, 112, 12);
        label5.setText("  ".concat(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("canalM")))));
        panel1.add(label5);
        label5.setBackground(padre.colorFondoTexto);
        label5.setForeground(padre.colorTexto);
        label5.setBounds(122, 168, 178, 12);
        setTitle((String)padre.tablaIdioma.get("busUsu"));
        add(panel1);
        addWindowListener(this);
        imageViewer2.addMouseListener(this);
        imageViewer3.addMouseListener(this);
        list1.addMouseListener(this);
        textField1.addActionListener(this);
        addComponentListener(this);
    }

    public void setVisible(boolean flag)
    {
        super.setVisible(flag);
    }

    public static void main(String args[])
    {
        (new Busqueda()).setVisible(true);
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
            int k = i - 321;
            int l = j - 365;
            if(k < 0)
            {
                panel1.setSize(321, j);
                list1.setSize(287, 120 + l);
                label5.setSize(178, 12);
                textField1.setSize(156, 26);
                choice1.setBounds(192, 60, 108, 24);
                imageViewer2.setBounds(240, 12, 60, 24);
                imageViewer3.setBounds(216, 96, 87, 24);
                return;
            }
            panel1.setSize(i, j);
            list1.setSize(287 + k, 120 + l);
            label5.setSize(178 + k, 12);
            textField1.setSize(156 + k, 26);
            choice1.setBounds(192 + k, 60, 108, 24);
            imageViewer2.setBounds(240 + k, 12, 60, 24);
            imageViewer3.setBounds(216 + k, 96, 87, 24);
        }
    }

    public void windowActivated(WindowEvent windowevent1)
    {
    }

    public void windowClosed(WindowEvent windowevent1)
    {
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

    public void windowClosing(WindowEvent windowevent)
    {
        Object obj = windowevent.getSource();
        if(obj == this)
            Busqueda_WindowClosing(windowevent);
    }

    void Busqueda_WindowClosing(WindowEvent windowevent)
    {
        textField1.setText("");
        setVisible(false);
        list1.removeAll();
    }

    public void mouseExited(MouseEvent mouseevent1)
    {
    }

    public void mouseEntered(MouseEvent mouseevent1)
    {
    }

    public void mouseClicked(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == list1)
            list1_MouseClicked(mouseevent);
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == imageViewer2)
        {
            imageViewer2_mouseReleased(mouseevent);
            return;
        }
        if(obj == imageViewer3)
            imageViewer3_mouseReleased(mouseevent);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == imageViewer2)
        {
            imageViewer2_mousePressed(mouseevent);
            return;
        }
        if(obj == imageViewer3)
            imageViewer3_mousePressed(mouseevent);
    }

    public void imageViewer2_mousePressed(MouseEvent mouseevent)
    {
        try
        {
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("sal_t2.gif")));
            return;
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
    }

    public void imageViewer2_mouseReleased(MouseEvent mouseevent)
    {
        try
        {
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
        textField1.setText("");
        setVisible(false);
        list1.removeAll();
    }

    public void imageViewer3_mousePressed(MouseEvent mouseevent)
    {
        try
        {
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("aceptar_t2.gif")));
            return;
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
    }

    public void imageViewer3_mouseReleased(MouseEvent mouseevent)
    {
        try
        {
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("aceptar.gif")));
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
        list1.removeAll();
        enviar_whois();
    }

    public void list1_MouseClicked(MouseEvent mouseevent)
    {
        if(mouseevent.getClickCount() >= 2)
        {
            String s = list1.getSelectedItem();
            int i = s.indexOf("#");
            if(i == -1)
                i = s.indexOf("&");
            String s1 = s.substring(i);
            if(padre.hiloReceptor.VentanaActual == 0)
            {
                padre.hiloReceptor.asignaVentanaActual("Consola");
                padre.getMarcoChat().getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(s1))));
                padre.hiloReceptor.lanzaComando();
                padre.setEnabled(true);
            } else
            {
                padre.hiloReceptor.Chats[padre.hiloReceptor.posicionMarco()].ivjMarcoIrc.getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(s1))));
                padre.hiloReceptor.lanzaComando();
            }
            textField1.setText("");
            setVisible(false);
            list1.removeAll();
        }
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj == textField1)
            textField1_ActionPerformed(actionevent);
    }

    public void textField1_ActionPerformed(ActionEvent actionevent)
    {
        list1.removeAll();
        enviar_whois();
    }

    private void enviar_whois()
    {
        String s = textField1.getText();
        boolean flag = true;
        s = s.trim();
        if(s.trim().endsWith("*"))
            s = s.substring(0, s.length() - 1);
        if(s.length() >= padre.filtroBusq)
        {
            if(choice1.getSelectedItem().compareTo((String)padre.tablaIdioma.get("empPor")) == 0)
                flag = false;
            if(!s.trim().endsWith("*") && !flag)
                s = String.valueOf(String.valueOf(s)).concat("*");
            if(padre.hiloReceptor.VentanaActual == 0)
            {
                padre.hiloReceptor.asignaVentanaActual("Consola");
                padre.getMarcoChat().getComando().setText("/WHOIS ".concat(String.valueOf(String.valueOf(s))));
                padre.hiloReceptor.lanzaComando();
                padre.setEnabled(true);
                return;
            } else
            {
                padre.hiloReceptor.Chats[padre.hiloReceptor.posicionMarco()].ivjMarcoIrc.getComando().setText("/WHOIS ".concat(String.valueOf(String.valueOf(s))));
                padre.hiloReceptor.lanzaComando();
                return;
            }
        } else
        {
            String s1 = (String)padre.tablaIdioma.get("errFil");
            s1 = padre.reemplazarTexto(s1, "%MINIMO_NUMERO%", Integer.toString(padre.filtroBusq - 1));
            list1.addItem(s1);
            return;
        }
    }

    boolean fComponentsAdjusted;
    public Panel panel1;
    public ImageViewer imageViewer1;
    public ImageViewer imageViewer2;
    public Label label1;
    public Label label2;
    public TextField textField1;
    public Choice choice1;
    public ImageViewer imageViewer3;
    public ImageViewer imageViewer4;
    public ImageViewer imageViewer5;
    public Label label3;
    public List list1;
    public Label label4;
    public Label label5;
    private Principal padre;
}
