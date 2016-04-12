// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VentanaIrcPrivada.java

package nat.irc;

import java.awt.*;
import java.util.Hashtable;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            CajaGraficaScroll, Principal

public class VentanaIrcPrivada extends Panel
{

    public VentanaIrcPrivada(Principal principal)
    {
        padre = principal;
        initialize();
    }

    public VentanaIrcPrivada()
    {
        initialize();
    }

    public VentanaIrcPrivada(LayoutManager layoutmanager)
    {
        super(layoutmanager);
    }

    public ImageViewer getTituloGrafico()
    {
        if(ivjTituloGrafico == null)
            try
            {
                ivjTituloGrafico = new ImageViewer();
                ivjTituloGrafico.setName("TituloGrafico");
                ivjTituloGrafico.setBounds(10, 0, 156, 36);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjTituloGrafico;
    }

    public ImageViewer getSalir()
    {
        if(ivjSalir == null)
            try
            {
                ivjSalir = new ImageViewer();
                ivjSalir.setName("Salir");
                ivjSalir.setBounds(260, 0, 75, 36);
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
                texto.setBackground(Color.white);
                texto.setBounds(12, 60, 312, 156);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return texto;
    }

    public TextArea getComando()
    {
        if(comando == null)
            try
            {
                comando = new TextArea("", 2, 60, 1);
                comando.setName("comando");
                comando.setBackground(Color.white);
                comando.setBounds(12, 228, 240, 24);
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
                ivjExpresiones.setBackground(Color.white);
                ivjExpresiones.setBounds(12, 260, 159, 24);
                ivjExpresiones.add((String)padre.tablaIdioma.get("emotic"));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjExpresiones;
    }

    private Label getLabel1()
    {
        if(label1 == null)
            try
            {
                label1 = new Label();
                label1.setName("label1");
                label1.setText((String)padre.tablaIdioma.get("usuMay"));
                label1.setBounds(12, 48, 72, 12);
                label1.setBackground(padre.colorFondoTexto);
                label1.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label1;
    }

    public Label getLabel2()
    {
        if(label2 == null)
            try
            {
                label2 = new Label();
                label2.setName("label2");
                label2.setBounds(84, 48, 84, 12);
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
                label3.setText(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("charla"))).concat(" #"));
                label3.setBounds(168, 48, 62, 12);
                label3.setBackground(padre.colorFondoTexto);
                label3.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label3;
    }

    public Label getLabel4()
    {
        if(label4 == null)
            try
            {
                label4 = new Label();
                label4.setName("label4");
                label4.setBounds(230, 48, 94, 12);
                label4.setBackground(padre.colorFondoTexto);
                label4.setForeground(padre.colorTexto);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return label4;
    }

    private void handleException(Throwable throwable1)
    {
    }

    private void initialize()
    {
        setFont(new Font("dialog", 1, padre.tamFuente));
        setName("VentanaIrc");
        setLayout(null);
        setBackground(padre.colorFondo);
        setSize(350, 350);
        add(getTituloGrafico(), getTituloGrafico().getName());
        add(getSalir(), getSalir().getName());
        add(getLabel1(), getLabel1().getName());
        add(getLabel2(), getLabel2().getName());
        add(getLabel3(), getLabel3().getName());
        add(getLabel4(), getLabel4().getName());
        add(getTexto(), getTexto().getName());
        add(getComando(), getComando().getName());
        add(getExpresiones(), getExpresiones().getName());
    }

    public CajaGraficaScroll texto;
    private TextArea comando;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private Choice ivjExpresiones;
    private ImageViewer ivjSalir;
    private ImageViewer ivjTituloGrafico;
    private Principal padre;
}
