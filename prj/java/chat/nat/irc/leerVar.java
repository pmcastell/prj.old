// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   leerVar.java

package nat.irc;

import com.borland.jbcl.layout.XYConstraints;
import com.borland.jbcl.layout.XYLayout;
import java.awt.*;
import java.awt.event.*;

public class leerVar extends Dialog
{

    public leerVar(String title, boolean modal)
    {
        super(new Frame(), title, modal);
        panel1 = new Panel();
        xYLayout1 = new XYLayout();
        mensaje = new Label("", 1);
        val = new TextField();
        button1 = new Button();
        titulo = "";
        colorFondo = new Color(238, 119, 0);
        enableEvents(64L);
        try
        {
            jbInit();
            add(panel1);
            pack();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public leerVar(String title, boolean modal, Color c)
    {
        this(title, modal);
        colorFondo = c;
    }

    public String getValor()
    {
        return val.getText();
    }

    public void setMensaje(String s)
    {
        mensaje.setText(s);
    }

    public leerVar()
    {
        this("", false);
    }

    public leerVar(boolean modal)
    {
        this("", modal);
    }

    public String leerCadena()
    {
        return leerCadena(this);
    }

    public static String leerCadena(leerVar lv)
    {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        lv.setLocation((d.width - lv.getSize().width) / 2, (d.height - lv.getSize().height) / 2);
        lv.setMensaje("Introduce tu Nick (9 Caracteres M\341ximo)");
        do
            lv.show();
        while(lv.val.getText().length() <= 0);
        return lv.val.getText();
    }

    public leerVar(String title)
    {
        this(title, false);
    }

    void jbInit()
        throws Exception
    {
        panel1.setSize(new Dimension(503, 111));
        xYLayout1.setHeight(125);
        xYLayout1.setWidth(503);
        mensaje.setFont(new Font("Dialog", 1, 12));
        val.setFont(new Font("Dialog", 1, 12));
        val.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                val_actionPerformed(e);
            }

        }
);
        button1.setFont(new Font("Dialog", 1, 12));
        button1.setLabel("Aceptar");
        button1.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e)
            {
                button1_actionPerformed(e);
            }

        }
);
        panel1.setLayout(xYLayout1);
        setBackground(colorFondo);
        panel1.add(mensaje, new XYConstraints(13, 11, 482, 25));
        panel1.add(val, new XYConstraints(17, 51, 475, 28));
        panel1.add(button1, new XYConstraints(158, 92, 177, 22));
    }

    protected void processWindowEvent(WindowEvent e)
    {
        if(e.getID() == 201)
            cancel();
        super.processWindowEvent(e);
    }

    void cancel()
    {
        dispose();
    }

    void val_actionPerformed(ActionEvent e)
    {
        cancel();
    }

    void button1_actionPerformed(ActionEvent e)
    {
        cancel();
    }

    Panel panel1;
    XYLayout xYLayout1;
    Label mensaje;
    TextField val;
    Button button1;
    String titulo;
    Color colorFondo;
}
