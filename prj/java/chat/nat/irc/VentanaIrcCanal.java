// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VentanaIrcCanal.java

package nat.irc;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Label;
import java.awt.LayoutManager;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.io.PrintStream;
import java.net.URL;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            CajaGraficaScroll, Principal

public class VentanaIrcCanal extends Panel
{

    public VentanaIrcCanal()
    {
        initialize();
    }

    public VentanaIrcCanal(LayoutManager layoutmanager)
    {
        super(layoutmanager);
    }

    public VentanaIrcCanal(Principal principal)
    {
        padre = principal;
        initialize();
    }

    public Label getLabel4()
    {
        if(label4 == null)
            try
            {
                label4 = new Label();
                label4.setName("label4");
                label4.setBounds(120, 48, 100, 12);
                label4.setBackground(padre.colorFondoTexto);
                label4.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label4;
    }

    public List getAreas()
    {
        if(ivjAreas == null)
            try
            {
                ivjAreas = new List();
                ivjAreas.setName("Areas");
                ivjAreas.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjAreas;
    }

    public ImageViewer getAyuda()
    {
        if(ivjAyuda == null)
            try
            {
                ivjAyuda = new ImageViewer();
                ivjAyuda.setName("Ayuda");
                ivjAyuda.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("lista_t.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjAyuda;
    }

    public ImageViewer getCambiaNick()
    {
        if(ivjCambiaNick == null)
            try
            {
                ivjCambiaNick = new ImageViewer();
                ivjCambiaNick.setName("CambiaNick");
                ivjCambiaNick.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("antifaz_t.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCambiaNick;
    }

    public List getCanales()
    {
        if(ivjCanales == null)
            try
            {
                ivjCanales = new List();
                ivjCanales.setName("Canales");
                ivjCanales.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCanales;
    }

    public TextArea getComando()
    {
        if(comando == null)
            try
            {
                comando = new TextArea("", 0, 0, 1);
                comando.setName("comando");
                comando.setRows(1);
                comando.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return comando;
    }

    public Choice getExpresiones()
    {
        if(ivjExpresiones == null)
            try
            {
                ivjExpresiones = new Choice();
                ivjExpresiones.setName("Expresiones");
                ivjExpresiones.setBackground(Color.white);
                ivjExpresiones.add((String)padre.tablaIdioma.get("emotic"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjExpresiones;
    }

    public Label getLabel1()
    {
        if(label1 == null)
            try
            {
                label1 = new Label();
                label1.setName("label1");
                label1.setText((String)padre.tablaIdioma.get("usuMay"));
                label1.setBackground(padre.colorFondoTexto);
                label1.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label1;
    }

    public Label getLabel0()
    {
        if(label0 == null)
            try
            {
                label0 = new Label();
                label0.setName("label0");
                label0.setText((String)padre.tablaIdioma.get("canAct"));
                label0.setBackground(padre.colorFondoTexto);
                label0.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label0;
    }

    public Label getLabel2()
    {
        if(label2 == null)
            try
            {
                label2 = new Label();
                label2.setName("label2");
                label2.setAlignment(2);
                label2.setText((String)padre.tablaIdioma.get("usuMin"));
                label2.setBackground(padre.colorFondoTexto);
                label2.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label2;
    }

    public Label getLabel3()
    {
        if(label3 == null)
            try
            {
                label3 = new Label();
                label3.setName("label3");
                label3.setText("#".concat(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("canal")))));
                label3.setBackground(padre.colorFondoTexto);
                label3.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label3;
    }

    public Label getLabel5()
    {
        if(label5 == null)
            try
            {
                label5 = new Label();
                label5.setName("label5");
                label5.setText((String)padre.tablaIdioma.get("usuars"));
                label5.setBackground(padre.colorFondoTexto);
                label5.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label5;
    }

    public Label getLabel6()
    {
        if(label6 == null)
            try
            {
                label6 = new Label();
                label6.setName("label6");
                label6.setText((String)padre.tablaIdioma.get("areas"));
                label6.setBackground(padre.colorFondoTexto);
                label6.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label6;
    }

    public Label getLabel7()
    {
        if(label7 == null)
            try
            {
                label7 = new Label();
                label7.setName("label7");
                label7.setText((String)padre.tablaIdioma.get("canals"));
                label7.setBackground(padre.colorFondoTexto);
                label7.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label7;
    }

    public Label getLabel8()
    {
        if(label8 == null)
            try
            {
                label8 = new Label();
                label8.setName("label8");
                label8.setText((String)padre.tablaIdioma.get("mensaj"));
                label8.setBackground(padre.colorFondoTexto);
                label8.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label8;
    }

    public Label getLabel9()
    {
        if(label9 == null)
            try
            {
                label9 = new Label();
                label9.setName("label9");
                label9.setText((String)padre.tablaIdioma.get("emotiM"));
                label9.setBackground(padre.colorFondoTexto);
                label9.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label9;
    }

    public ImageViewer getQuien()
    {
        if(ivjQuien == null)
            try
            {
                ivjQuien = new ImageViewer();
                ivjQuien.setName("Quien");
                ivjQuien.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("quien_t.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjQuien;
    }

    public ImageViewer getSalir()
    {
        if(ivjSalir == null)
            try
            {
                ivjSalir = new ImageViewer();
                ivjSalir.setName("Salir");
                ivjSalir.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("sal_t.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjSalir;
    }

    public CajaGraficaScroll getTexto()
    {
        if(texto == null)
            try
            {
                texto = new CajaGraficaScroll();
                texto.setName("texto");
                texto.setVisible(false);
                texto.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return texto;
    }

    public CajaGraficaScroll getTextoConv()
    {
        if(textoConv == null)
            try
            {
                textoConv = new CajaGraficaScroll();
                textoConv.setName("textoConv");
                textoConv.setVisible(false);
                textoConv.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return textoConv;
    }

    public ImageViewer getUnir()
    {
        if(ivjUnir == null)
            try
            {
                ivjUnir = new ImageViewer();
                ivjUnir.setName("Unir");
                ivjUnir.setImageURL(new URL(String.valueOf(String.valueOf(padre.UrlIconos)).concat("entra_t.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjUnir;
    }

    public List getUsuarios()
    {
        if(ivjUsuarios == null)
            try
            {
                ivjUsuarios = new List();
                ivjUsuarios.setName("Usuarios");
                ivjUsuarios.setBackground(Color.white);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjUsuarios;
    }

    private void handleException(Throwable throwable1)
    {
    }

    private void initialize()
    {
        try
        {
            setFont(new Font("Dialog", 1, padre.tamFuente));
            setBackground(padre.colorFondo);
            setName("Prueba");
            setLayout(new GridBagLayout());
            setSize(padre.getSize());
            setForeground(Color.black);
            GridBagConstraints gridbagconstraints = new GridBagConstraints();
            gridbagconstraints.gridx = 7;
            gridbagconstraints.gridy = 4;
            gridbagconstraints.fill = 1;
            gridbagconstraints.weightx = 0.0D;
            gridbagconstraints.weighty = 1.0D;
            gridbagconstraints.ipadx = 5;
            gridbagconstraints.ipady = 5;
            gridbagconstraints.insets = new Insets(0, 4, 4, 4);
            add(getCanales(), gridbagconstraints);
            GridBagConstraints gridbagconstraints1 = new GridBagConstraints();
            gridbagconstraints1.gridx = 7;
            gridbagconstraints1.gridy = 2;
            gridbagconstraints1.fill = 1;
            gridbagconstraints1.weightx = 0.0D;
            gridbagconstraints1.weighty = 1.0D;
            gridbagconstraints1.ipadx = 5;
            gridbagconstraints1.ipady = 5;
            gridbagconstraints1.insets = new Insets(0, 4, 4, 4);
            add(getAreas(), gridbagconstraints1);
            GridBagConstraints gridbagconstraints2 = new GridBagConstraints();
            gridbagconstraints2.gridx = 6;
            gridbagconstraints2.gridy = 2;
            gridbagconstraints2.gridheight = 3;
            gridbagconstraints2.fill = 1;
            gridbagconstraints2.weightx = 0.0D;
            gridbagconstraints2.weighty = 1.0D;
            gridbagconstraints2.ipadx = -25;
            gridbagconstraints2.ipady = 5;
            gridbagconstraints2.insets = new Insets(0, 15, 4, 15);
            add(getUsuarios(), gridbagconstraints2);
            GridBagConstraints gridbagconstraints3 = new GridBagConstraints();
            gridbagconstraints3.gridx = 7;
            gridbagconstraints3.gridy = 6;
            gridbagconstraints3.fill = 1;
            gridbagconstraints3.weighty = 1.0D;
            gridbagconstraints3.ipadx = 5;
            gridbagconstraints3.ipady = 5;
            gridbagconstraints3.insets = new Insets(0, 4, 4, 4);
            add(getExpresiones(), gridbagconstraints3);
            GridBagConstraints gridbagconstraints4 = new GridBagConstraints();
            gridbagconstraints4.gridx = 0;
            gridbagconstraints4.gridy = 6;
            gridbagconstraints4.gridwidth = 7;
            gridbagconstraints4.fill = 2;
            gridbagconstraints4.weightx = 1.0D;
            gridbagconstraints4.weighty = 0.0D;
            gridbagconstraints4.insets = new Insets(0, 4, 4, 15);
            add(getComando(), gridbagconstraints4);
            GridBagConstraints gridbagconstraints5 = new GridBagConstraints();
            gridbagconstraints5.gridx = 2;
            gridbagconstraints5.gridy = 1;
            gridbagconstraints5.fill = 2;
            gridbagconstraints5.anchor = 17;
            gridbagconstraints5.ipady = -10;
            gridbagconstraints5.insets = new Insets(8, 0, 0, 0);
            add(getLabel1(), gridbagconstraints5);
            GridBagConstraints gridbagconstraints6 = new GridBagConstraints();
            gridbagconstraints6.gridx = 3;
            gridbagconstraints6.gridy = 1;
            gridbagconstraints6.fill = 2;
            gridbagconstraints6.ipady = -10;
            gridbagconstraints6.insets = new Insets(8, 0, 0, 4);
            add(getLabel2(), gridbagconstraints6);
            GridBagConstraints gridbagconstraints7 = new GridBagConstraints();
            gridbagconstraints7.gridx = 1;
            gridbagconstraints7.gridy = 1;
            gridbagconstraints7.fill = 2;
            gridbagconstraints7.weightx = 0.0D;
            gridbagconstraints7.ipady = -10;
            gridbagconstraints7.insets = new Insets(8, 0, 0, 0);
            add(getLabel3(), gridbagconstraints7);
            GridBagConstraints gridbagconstraints8 = new GridBagConstraints();
            gridbagconstraints8.gridx = 6;
            gridbagconstraints8.gridy = 1;
            gridbagconstraints8.fill = 2;
            gridbagconstraints8.ipady = -10;
            gridbagconstraints8.ipadx = -25;
            gridbagconstraints8.insets = new Insets(8, 15, 0, 15);
            add(getLabel5(), gridbagconstraints8);
            GridBagConstraints gridbagconstraints9 = new GridBagConstraints();
            gridbagconstraints9.gridx = 7;
            gridbagconstraints9.gridy = 1;
            gridbagconstraints9.fill = 2;
            gridbagconstraints9.ipady = -10;
            gridbagconstraints9.insets = new Insets(8, 4, 0, 4);
            add(getLabel6(), gridbagconstraints9);
            GridBagConstraints gridbagconstraints10 = new GridBagConstraints();
            gridbagconstraints10.gridx = 0;
            gridbagconstraints10.gridy = 5;
            gridbagconstraints10.gridwidth = 7;
            gridbagconstraints10.fill = 2;
            gridbagconstraints10.ipady = -10;
            gridbagconstraints10.insets = new Insets(0, 4, 0, 15);
            add(getLabel8(), gridbagconstraints10);
            GridBagConstraints gridbagconstraints11 = new GridBagConstraints();
            gridbagconstraints11.gridx = 7;
            gridbagconstraints11.gridy = 3;
            gridbagconstraints11.fill = 2;
            gridbagconstraints11.ipady = -10;
            gridbagconstraints11.insets = new Insets(0, 4, 0, 4);
            add(getLabel7(), gridbagconstraints11);
            GridBagConstraints gridbagconstraints12 = new GridBagConstraints();
            gridbagconstraints12.gridx = 0;
            gridbagconstraints12.gridy = 0;
            gridbagconstraints12.fill = 0;
            gridbagconstraints12.anchor = 17;
            gridbagconstraints12.insets = new Insets(4, 4, 4, 4);
            add(getCambiaNick(), gridbagconstraints12);
            GridBagConstraints gridbagconstraints13 = new GridBagConstraints();
            gridbagconstraints13.gridx = 1;
            gridbagconstraints13.gridy = 0;
            gridbagconstraints13.gridwidth = 1;
            gridbagconstraints13.fill = 0;
            gridbagconstraints13.weightx = 0.0D;
            gridbagconstraints13.anchor = 10;
            gridbagconstraints13.insets = new Insets(4, 4, 4, 4);
            add(getQuien(), gridbagconstraints13);
            GridBagConstraints gridbagconstraints14 = new GridBagConstraints();
            gridbagconstraints14.gridx = 2;
            gridbagconstraints14.gridy = 0;
            gridbagconstraints14.gridwidth = 2;
            gridbagconstraints14.weightx = 1.0D;
            gridbagconstraints14.fill = 0;
            gridbagconstraints14.anchor = 17;
            gridbagconstraints14.insets = new Insets(4, 4, 4, 4);
            add(getUnir(), gridbagconstraints14);
            GridBagConstraints gridbagconstraints15 = new GridBagConstraints();
            gridbagconstraints15.gridx = 6;
            gridbagconstraints15.gridy = 0;
            gridbagconstraints15.fill = 1;
            gridbagconstraints15.insets = new Insets(4, 4, 4, 4);
            add(getAyuda(), gridbagconstraints15);
            GridBagConstraints gridbagconstraints16 = new GridBagConstraints();
            gridbagconstraints16.gridx = 7;
            gridbagconstraints16.gridy = 0;
            gridbagconstraints16.fill = 1;
            gridbagconstraints16.insets = new Insets(4, 4, 4, 4);
            add(getSalir(), gridbagconstraints16);
            GridBagConstraints gridbagconstraints17 = new GridBagConstraints();
            gridbagconstraints17.gridx = 7;
            gridbagconstraints17.gridy = 5;
            gridbagconstraints17.fill = 2;
            gridbagconstraints17.ipady = -10;
            gridbagconstraints17.insets = new Insets(0, 4, 0, 4);
            add(getLabel9(), gridbagconstraints17);
            GridBagConstraints gridbagconstraints18 = new GridBagConstraints();
            gridbagconstraints18.gridx = 0;
            gridbagconstraints18.gridy = 2;
            gridbagconstraints18.gridwidth = 4;
            gridbagconstraints18.gridheight = 3;
            gridbagconstraints18.fill = 1;
            String s = System.getProperty("os.name");
            if(s.toUpperCase().indexOf("WINDOWS") == -1)
                gridbagconstraints18.ipady = (getSize().height + getComando().getMinimumSize().height) - 80;
            else
                gridbagconstraints18.ipady = (getSize().height + getComando().getMinimumSize().height) - 30;
            gridbagconstraints18.weightx = 1.0D;
            gridbagconstraints18.weighty = 1.0D;
            gridbagconstraints18.insets = new Insets(0, 4, 4, 4);
            add(getTextoConv(), gridbagconstraints18);
            add(getTexto(), gridbagconstraints18);
            GridBagConstraints gridbagconstraints19 = new GridBagConstraints();
            gridbagconstraints19.gridx = 0;
            gridbagconstraints19.gridy = 1;
            gridbagconstraints19.fill = 2;
            gridbagconstraints19.ipady = -10;
            gridbagconstraints19.insets = new Insets(8, 4, 0, 0);
            add(getLabel0(), gridbagconstraints19);
            return;
        }
        catch(Throwable throwable)
        {
            System.out.println("initialize excepcion.....");
            handleException(throwable);
            return;
        }
    }

    public void cambiaIpad(boolean flag)
    {
        if(getComponentCount() > 0)
        {
            GridBagConstraints gridbagconstraints;
            if(flag)
                gridbagconstraints = ((GridBagLayout)getLayout()).getConstraints(getTextoConv());
            else
                gridbagconstraints = ((GridBagLayout)getLayout()).getConstraints(getTexto());
            String s = System.getProperty("os.name");
            if(s.toUpperCase().indexOf("WINDOWS") == -1)
                gridbagconstraints.ipady = (getSize().height + getComando().getMinimumSize().height) - 80;
            else
                gridbagconstraints.ipady = (getSize().height + getComando().getMinimumSize().height) - 30;
            if(flag)
            {
                ((GridBagLayout)getLayout()).setConstraints(getTextoConv(), gridbagconstraints);
                getTextoConv().validate();
            } else
            {
                ((GridBagLayout)getLayout()).setConstraints(getTexto(), gridbagconstraints);
                getTexto().validate();
            }
            try
            {
                paintComponents(getGraphics());
                return;
            }
            catch(Exception _ex)
            {
                System.out.println("Excepci\363n en VentanaIrcCanal:paintComponents");
            }
            return;
        } else
        {
            return;
        }
    }

    public Insets getInsets()
    {
        return new Insets(5, 5, 5, 5);
    }

    private TextArea comando;
    private CajaGraficaScroll texto;
    private Label label0;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Label label5;
    private Label label6;
    private Label label7;
    private Label label8;
    private Label label9;
    private CajaGraficaScroll textoConv;
    private List ivjCanales;
    private List ivjAreas;
    private ImageViewer ivjAyuda;
    private ImageViewer ivjCambiaNick;
    private Choice ivjExpresiones;
    private ImageViewer ivjQuien;
    private ImageViewer ivjSalir;
    private ImageViewer ivjUnir;
    private List ivjUsuarios;
    private Principal padre;
}
