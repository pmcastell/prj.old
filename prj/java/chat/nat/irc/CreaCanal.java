// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CreaCanal.java

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
//            Principal, hiloRecibir, VentanaIrcCanal

public class CreaCanal extends Frame
    implements MouseListener, WindowListener, ActionListener, ComponentListener
{

    public CreaCanal()
    {
        fComponentsAdjusted = false;
        panel1 = new Panel();
        imageViewer1 = new ImageViewer();
        imageViewer3 = new ImageViewer();
        imageViewer2 = new ImageViewer();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        textField1 = new TextField();
        inicializar();
    }

    public CreaCanal(String s)
    {
        fComponentsAdjusted = false;
        panel1 = new Panel();
        imageViewer1 = new ImageViewer();
        imageViewer3 = new ImageViewer();
        imageViewer2 = new ImageViewer();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        textField1 = new TextField();
        setTitle(s);
    }

    public CreaCanal(Principal principal)
    {
        fComponentsAdjusted = false;
        panel1 = new Panel();
        imageViewer1 = new ImageViewer();
        imageViewer3 = new ImageViewer();
        imageViewer2 = new ImageViewer();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        textField1 = new TextField();
        padre = principal;
        UrlIconosBase = padre.UrlIconos;
        inicializar();
    }

    public static void main(String args[])
    {
        (new CreaCanal()).setVisible(true);
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

    private void inicializar()
    {
        setFont(new Font("dialog", 1, padre.tamFuente));
        setLayout(null);
        setSize(280, 145);
        setVisible(false);
        panel1.setLayout(null);
        add(panel1);
        panel1.setBackground(padre.colorFondo);
        panel1.setBounds(0, 0, 280, 145);
        try
        {
            imageViewer1.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("crear_canal.gif")));
        }
        catch(MalformedURLException malformedurlexception) { }
        catch(PropertyVetoException propertyvetoexception) { }
        panel1.add(imageViewer1);
        imageViewer1.setBounds(12, 12, 132, 24);
        try
        {
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
        }
        catch(MalformedURLException malformedurlexception1) { }
        catch(PropertyVetoException propertyvetoexception1) { }
        panel1.add(imageViewer3);
        imageViewer3.setBounds(192, 12, 61, 26);
        try
        {
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("aceptar.gif")));
        }
        catch(MalformedURLException malformedurlexception2) { }
        catch(PropertyVetoException propertyvetoexception2) { }
        panel1.add(imageViewer2);
        imageViewer2.setBounds(168, 102, 87, 24);
        panel1.add(textField1);
        textField1.setBackground(Color.white);
        textField1.setBounds(24, 102, 132, 26);
        label2.setText((String)padre.tablaIdioma.get("menTem1"));
        panel1.add(label2);
        label2.setFont(new Font("Dialog", 1, padre.tamFuente));
        label2.setBounds(24, 40, 260, 16);
        label3.setText((String)padre.tablaIdioma.get("menTem2"));
        panel1.add(label3);
        label3.setFont(new Font("Dialog", 1, padre.tamFuente));
        label3.setBounds(24, 56, 260, 16);
        label4.setText((String)padre.tablaIdioma.get("menTem3"));
        panel1.add(label4);
        label4.setFont(new Font("Dialog", 1, padre.tamFuente));
        label4.setBounds(24, 72, 240, 16);
        setTitle((String)padre.tablaIdioma.get("canTem"));
        addWindowListener(this);
        addComponentListener(this);
        imageViewer3.addMouseListener(this);
        imageViewer2.addMouseListener(this);
        textField1.addActionListener(this);
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
            CreaCanal_WindowClosing(windowevent);
    }

    public void CreaCanal_WindowClosing(WindowEvent windowevent)
    {
        dispose();
    }

    public void mouseExited(MouseEvent mouseevent1)
    {
    }

    public void mouseEntered(MouseEvent mouseevent1)
    {
    }

    public void mouseClicked(MouseEvent mouseevent1)
    {
    }

    public void mouseReleased(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == imageViewer3)
        {
            imageViewer3_mouseReleased(mouseevent);
            return;
        }
        if(obj == imageViewer2)
            imageViewer2_mouseReleased(mouseevent);
    }

    public void mousePressed(MouseEvent mouseevent)
    {
        Object obj = mouseevent.getSource();
        if(obj == imageViewer3)
        {
            imageViewer3_mousePressed(mouseevent);
            return;
        }
        if(obj == imageViewer2)
            imageViewer2_mousePressed(mouseevent);
    }

    public void imageViewer3_mousePressed(MouseEvent mouseevent)
    {
        try
        {
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("sal_t2.gif")));
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
            imageViewer3.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("cerrar.gif")));
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
        dispose();
    }

    public void imageViewer2_mousePressed(MouseEvent mouseevent)
    {
        try
        {
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("aceptar_t2.gif")));
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
            imageViewer2.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("aceptar.gif")));
        }
        catch(Exception _ex)
        {
            System.out.println("Error en URL de iconos");
        }
        crearlo();
    }

    public void actionPerformed(ActionEvent actionevent)
    {
        Object obj = actionevent.getSource();
        if(obj == textField1)
            textField1_ActionPerformed(actionevent);
    }

    public void textField1_ActionPerformed(ActionEvent actionevent)
    {
        crearlo();
    }

    private void crearlo()
    {
        if(textField1.getText().trim().compareTo("") != 0)
        {
            padre.hiloReceptor.asignaVentanaActual("Consola");
            String s = textField1.getText().trim();
            if(s.indexOf('#') == 0 || s.indexOf('&') == 0)
                padre.getMarcoChat().getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(textField1.getText().trim()))));
            else
                padre.getMarcoChat().getComando().setText("/JOIN #".concat(String.valueOf(String.valueOf(textField1.getText().trim()))));
            padre.hiloReceptor.lanzaComando();
            padre.setEnabled(true);
        }
        dispose();
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
            int k = i - 280;
            if(k < 0)
            {
                panel1.setSize(280, j);
                textField1.setSize(132, 26);
                imageViewer2.setBounds(168, 102, 87, 24);
                imageViewer3.setBounds(192, 12, 61, 26);
                return;
            }
            panel1.setSize(i, j);
            textField1.setSize(132 + k, 26);
            imageViewer2.setBounds(168 + k, 102, 87, 24);
            imageViewer3.setBounds(192 + k, 12, 61, 26);
        }
    }

    public boolean fComponentsAdjusted;
    public Panel panel1;
    public ImageViewer imageViewer1;
    public ImageViewer imageViewer3;
    public ImageViewer imageViewer2;
    public Label label2;
    public Label label3;
    public Label label4;
    public TextField textField1;
    public Principal padre;
    public String UrlIconosBase;
}
