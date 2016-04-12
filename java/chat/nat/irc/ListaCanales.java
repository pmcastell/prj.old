// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ListaCanales.java

package nat.irc;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextComponent;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.PrintStream;
import java.net.URL;
import symantec.itools.multimedia.ImageViewer;

// Referenced classes of package nat.irc:
//            Principal, VentanaIrcCanal, hiloRecibir

public class ListaCanales extends Frame
    implements MouseListener, WindowListener, ActionListener
{

    public ListaCanales()
    {
        initialize();
    }

    public ListaCanales(String s)
    {
        super(s);
    }

    public ListaCanales(Principal principal)
    {
        super((String)principal.tablaIdioma.get("lisCan"));
        padre = principal;
        initialize();
        todosLosCanales = new String[0];
    }

    public void actualizarLista()
    {
        ivjLista.removeAll();
        padre.getMarcoChat().getComando().setText("/LIST >0");
        padre.hiloReceptor.lanzaComando();
    }

    public void ira(String s)
    {
        int i = s.indexOf(" ");
        padre.getMarcoChat().getComando().setText("/JOIN ".concat(String.valueOf(String.valueOf(s.substring(0, i)))));
        padre.hiloReceptor.lanzaComando();
        padre.setEnabled(true);
        dispose();
    }

    public void ordenaLista(String s)
    {
        String s1 = new String();
        String as[] = new String[getLista().getItemCount() + 1];
        int i;
        for(i = 0; i < getLista().getItemCount(); i++)
            as[i] = getLista().getItem(i);

        as[i] = s;
        for(int j = 0; j < as.length; j++)
        {
            for(int k = 0; k < as.length; k++)
                if(as[j].compareTo(as[k]) < 0)
                {
                    String s2 = as[j];
                    as[j] = as[k];
                    as[k] = s2;
                }

        }

        int l;
        for(l = 0; l < as.length - 1; l++)
            getLista().replaceItem(as[l], l);

        getLista().add(as[l]);
    }

    public void ordenaLista()
    {
        String s = new String();
        for(int i = 0; i < todosLosCanales.length; i++)
        {
            for(int j = 0; j < todosLosCanales.length; j++)
                if(todosLosCanales[i].compareTo(todosLosCanales[j]) < 0)
                {
                    String s1 = todosLosCanales[i];
                    todosLosCanales[i] = todosLosCanales[j];
                    todosLosCanales[j] = s1;
                }

        }

        for(int k = 0; k < todosLosCanales.length; k++)
            getLista().add(todosLosCanales[k]);

    }

    public void meterEnLista(String s)
    {
        String as[] = new String[todosLosCanales.length + 1];
        int i;
        for(i = 0; i < todosLosCanales.length; i++)
            as[i] = todosLosCanales[i];

        as[i] = s;
        todosLosCanales = as;
    }

    public void cancelar_MouseClicked(MouseEvent mouseevent)
    {
        padre.setEnabled(true);
        dispose();
    }

    public void cancelar_MouseClicked1()
    {
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
            actualizarLista();
            return;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private void connEtoC3(MouseEvent mouseevent)
    {
        try
        {
            cancelar_MouseClicked1();
            return;
        }
        catch(Throwable throwable)
        {
            handleException(throwable);
        }
    }

    private Button getActualizar()
    {
        if(ivjActualizar == null)
            try
            {
                ivjActualizar = new Button();
                ivjActualizar.setName("Actualizar");
                ivjActualizar.setBounds(29, 213, 61, 23);
                ivjActualizar.setLabel("Actualizar");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjActualizar;
    }

    public ImageViewer getActualizarGrafico()
    {
        if(ivjActualizarGrafico == null)
            try
            {
                ivjActualizarGrafico = new ImageViewer();
                ivjActualizarGrafico.setName("ActualizarGr");
                ivjActualizarGrafico.setBounds(29, 213, 61, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjActualizarGrafico;
    }

    public ImageViewer getCancelarGrafico()
    {
        if(ivjCancelarGrafico == null)
            try
            {
                ivjCancelarGrafico = new ImageViewer();
                ivjCancelarGrafico.setName("CancelarGr");
                ivjCancelarGrafico.setBounds(191, 213, 61, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCancelarGrafico;
    }

    private Button getCancelar()
    {
        if(ivjCancelar == null)
            try
            {
                ivjCancelar = new Button();
                ivjCancelar.setName("Cancelar");
                ivjCancelar.setBounds(191, 213, 61, 23);
                ivjCancelar.setLabel("Cancelar");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjCancelar;
    }

    private Panel getContentsPane()
    {
        if(ivjContentsPane == null)
            try
            {
                ivjContentsPane = new Panel();
                ivjContentsPane.setName("ContentsPane");
                ivjContentsPane.setLayout(null);
                getContentsPane().add(getLista(), getLista().getName());
                getContentsPane().add(getActualizarGrafico(), getActualizarGrafico().getName());
                getContentsPane().add(getIraGrafico(), getIraGrafico().getName());
                getContentsPane().add(getCancelarGrafico(), getCancelarGrafico().getName());
                getActualizarGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("actualizar.gif")));
                getIraGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("unirsea.gif")));
                getCancelarGrafico().setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cancelar.gif")));
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjContentsPane;
    }

    private Button getIra()
    {
        if(ivjIra == null)
            try
            {
                ivjIra = new Button();
                ivjIra.setName("Ira");
                ivjIra.setBounds(110, 213, 60, 23);
                ivjIra.setLabel("Unirse a");
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIra;
    }

    public ImageViewer getIraGrafico()
    {
        if(ivjIraGrafico == null)
            try
            {
                ivjIraGrafico = new ImageViewer();
                ivjIraGrafico.setName("ActualizarGr");
                ivjIraGrafico.setBounds(110, 213, 60, 23);
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjIraGrafico;
    }

    public List getLista()
    {
        if(ivjLista == null)
            try
            {
                ivjLista = new List();
                ivjLista.setName("Lista");
                ivjLista.setBounds(4, 0, 411, 204);
                ivjLista.setBackground(padre.getBackground());
            }
            catch(Throwable throwable)
            {
                handleException(throwable);
            }
        return ivjLista;
    }

    private void handleException(Throwable throwable1)
    {
    }

    private void initConnections()
    {
        ivjIraGrafico.addMouseListener(this);
        addWindowListener(this);
        getActualizarGrafico().addMouseListener(this);
        getCancelarGrafico().addMouseListener(this);
        ivjLista.addActionListener(this);
    }

    private void initialize()
    {
        setBackground(Color.white);
        setFont(new Font("dialog", 1, 12));
        setName("ListaCanales");
        setLayout(new BorderLayout());
        setSize(426, 320);
        setTitle((String)padre.tablaIdioma.get("lisCan"));
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
            if(mouseevent.getSource() == ivjActualizarGrafico)
            {
                ivjActualizarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("actualizar_t2.gif")));
                return;
            }
            if(mouseevent.getSource() == ivjIraGrafico)
            {
                ivjIraGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("unirsea_t2.gif")));
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
            if(mouseevent.getSource() == getActualizarGrafico())
            {
                ivjActualizarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("actualizar.gif")));
                connEtoC2(mouseevent);
            }
            if(mouseevent.getSource() == getCancelarGrafico())
            {
                ivjCancelarGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("cancelar.gif")));
                connEtoC3(mouseevent);
            } else
            if(mouseevent.getSource() == ivjIraGrafico)
                ivjIraGrafico.setImageURL(new URL(String.valueOf(String.valueOf(UrlIconosBase)).concat("unirsea.gif")));
            if(ivjLista.getSelectedIndex() != -1)
            {
                ira(ivjLista.getSelectedItem().trim());
                return;
            }
        }
        catch(Exception _ex)
        {
            System.out.println("Excepcion al traer un icono");
        }
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

    public void actionPerformed(ActionEvent actionevent)
    {
        if(actionevent.getSource() == ivjLista)
            ira(ivjLista.getSelectedItem().trim());
    }

    private Button ivjActualizar;
    private Button ivjCancelar;
    private Button ivjIra;
    private Panel ivjContentsPane;
    private List ivjLista;
    public Principal padre;
    private ImageViewer ivjActualizarGrafico;
    private ImageViewer ivjCancelarGrafico;
    private ImageViewer ivjIraGrafico;
    String UrlIconosBase;
    String todosLosCanales[];
}
