// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   hiloRecibir.java

package nat.irc;

import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.Label;
import java.awt.List;
import java.awt.Point;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintStream;
import java.net.SocketException;
import java.net.UnknownHostException;

// Referenced classes of package nat.irc:
//            MarcoVentanaIrcCanal, MarcoVentanaIrcPrivada, CajaGraficaScroll, Registro, 
//            Datos, ConexionIrc, ContenidoSencillo, Principal, 
//            VentanaIrcCanal, VentanaIrcPrivada, Contenido, Busqueda, 
//            Conexion, ListaCanales

public class hiloRecibir extends Thread
    implements ActionListener
{

    public hiloRecibir()
    {
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(Runnable runnable)
    {
        super(runnable);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(Runnable runnable, String s)
    {
        super(runnable, s);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(String s)
    {
        super(s);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(ThreadGroup threadgroup, Runnable runnable)
    {
        super(threadgroup, runnable);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(ThreadGroup threadgroup, Runnable runnable, String s)
    {
        super(threadgroup, runnable, s);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(ThreadGroup threadgroup, String s)
    {
        super(threadgroup, s);
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
    }

    public hiloRecibir(Principal principal, ConexionIrc conexionirc, ContenidoSencillo contenidosencillo, String s, String s1)
    {
        colorConsola = Color.gray;
        colorMensajesOtros = Color.black;
        colorMensajesPropios = Color.blue;
        buscando = false;
        listlanzado = false;
        wholleno = true;
        ignoreActivo = true;
        listadoIgnorados = new String[0];
        padre = principal;
        Comando = padre.getMarcoChat().getComando();
        Comando.setBackground(Comando.getBackground());
        Chats = new MarcoVentanaIrcCanal[10];
        for(int i = 0; i < Chats.length; i++)
            Chats[i] = null;

        ChatsPrivada = new MarcoVentanaIrcPrivada[15];
        for(int j = 0; j < ChatsPrivada.length; j++)
            ChatsPrivada[j] = null;

        Textos = new CajaGraficaScroll[26];
        LUsuarios = new List[11];
        LUsuarios[0] = new List();
        LUsuarios[0] = padre.getMarcoChat().getUsuarios();
        MiNick = s;
        can = s1;
        ht = new java.util.Hashtable();
        ventanas = new java.util.Hashtable();
        colorConsola = padre.colorConsola;
        colorMensajesOtros = padre.colorMensajesOtros;
        colorMensajesPropios = padre.colorMensajesPropios;
    }

    public void actionPerformed(ActionEvent actionevent1)
    {
    }

    public void ordenaListaConAlias(List list, String s, int i)
    {
        String s1 = new String();
        String as[] = new String[list.getItemCount()];
        for(int j = 0; j < as.length; j++)
        {
            if(j == i)
            {
                for(int k = 0; k < listadoIgnorados.length; k++)
                {
                    if(listadoIgnorados[k].equals(s))
                    {
                        s = "*".concat(String.valueOf(String.valueOf(s)));
                        continue;
                    }
                    if("@".concat(String.valueOf(String.valueOf(listadoIgnorados[k]))).equals(s))
                        s = "*".concat(String.valueOf(String.valueOf(s)));
                }

                as[j] = s;
            } else
            {
                as[j] = list.getItem(j);
            }
            for(int l = 0; l < as.length; l++)
            {
                if(as[j] == null || as[l] == null)
                    continue;
                String s3 = as[j].toLowerCase();
                if(s3.startsWith("*"))
                    s3 = s3.substring(1);
                String s4 = as[l].toLowerCase();
                if(s4.startsWith("*"))
                    s4 = s4.substring(1);
                if(s3.compareTo(s4) < 0)
                {
                    String s2 = as[j];
                    as[j] = as[l];
                    as[l] = s2;
                }
            }

        }

        list.removeAll();
        for(int i1 = 0; i1 < as.length; i1++)
            list.add(as[i1]);

    }

    public synchronized void cambiarNick(String s, String s1)
    {
        String s4 = (String)padre.tablaIdioma.get("camNik");
        s4 = padre.reemplazarTexto(s4, "%ANTIGUO_NOMBRE%", s);
        s4 = padre.reemplazarTexto(s4, "%NUEVO_NOMBRE%", s1);
        for(int i = 1; i < 11; i++)
        {
            if(LUsuarios[i] == null)
                continue;
            for(int k = 0; k < LUsuarios[i].getItemCount(); k++)
            {
                String s5 = LUsuarios[i].getItem(k);
                if(s5.startsWith("*"))
                    s5 = s5.substring(1);
                if(s5.compareTo(s) == 0)
                {
                    ordenaListaConAlias(LUsuarios[i], s1, k);
                    Textos[i].append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
                    continue;
                }
                if(s5.compareTo("@".concat(String.valueOf(String.valueOf(s)))) == 0)
                {
                    ordenaListaConAlias(LUsuarios[i], "@".concat(String.valueOf(String.valueOf(s1))), k);
                    Textos[i].append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
                }
            }

        }

        for(int i1 = 0; i1 < ChatsPrivada.length; i1++)
        {
            if(ChatsPrivada[i1] == null)
                continue;
            String s3 = ChatsPrivada[i1].getMarcoIrcPrivada().getLabel4().getText().toUpperCase();
            if(s3.compareTo(s.toUpperCase()) == 0 || s3.compareTo(String.valueOf(String.valueOf(s.toUpperCase())).concat("  !!!")) == 0)
            {
                String s2 = s.toUpperCase();
                Registro registro = new Registro((Registro)ventanas.get(s2));
                registro.nReal = s1;
                int j = registro.refer;
                Textos[j].append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
                ChatsPrivada[i1].getMarcoIrcPrivada().getLabel4().setText(s1);
                ventanas.put(s1.toUpperCase(), registro);
                ventanas.remove(s2);
            }
        }

        for(int l = 0; l < padre.getMarcoChat().getUsuarios().getItemCount(); l++)
        {
            String s6 = padre.getMarcoChat().getUsuarios().getItem(l);
            if(s6.startsWith("*"))
                s6 = s6.substring(1);
            if(s6.compareTo(s) == 0)
            {
                ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), s1, l);
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
                continue;
            }
            if(s6.compareTo("@".concat(String.valueOf(String.valueOf(s)))) == 0)
            {
                ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), "@".concat(String.valueOf(String.valueOf(s1))), l);
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
            }
        }

        if(s.compareTo(MiNick) == 0)
            MiNick = s1;
        padre.getMarcoChat().getLabel2().setText(MiNick);
        for(int j1 = 0; j1 < padre.hiloReceptor.Chats.length; j1++)
            if(padre.hiloReceptor.Chats[j1] != null)
                padre.hiloReceptor.Chats[j1].getMarcoIrcCanal().getLabel2().setText(MiNick);

        for(int k1 = 0; k1 < padre.hiloReceptor.ChatsPrivada.length; k1++)
            if(padre.hiloReceptor.ChatsPrivada[k1] != null)
                padre.hiloReceptor.ChatsPrivada[k1].getMarcoIrcPrivada().getLabel2().setText(MiNick);

    }

    public synchronized void cierraVentana(String s)
    {
        s = s.trim().toUpperCase();
        Registro registro = new Registro((Registro)ventanas.get(s));
        int j = registro.posicion;
        int i = registro.refer;
        boolean flag = false;
        if(i > 10)
            flag = false;
        else
            flag = true;
        if(VentanaActual == i)
            asignaVentanaActual("Consola");
        if(i > 0 && i < 11)
        {
            if(s.compareTo(padre.getMarcoChat().getLabel4().getText().toUpperCase()) == 0)
            {
                padre.expulsadoPrincipal = true;
                return;
            }
            ventanas.remove(s);
            if(Chats[j].isVisible())
                Chats[j].dispose();
            LUsuarios[i] = null;
            Textos[i] = null;
            return;
        } else
        {
            ventanas.remove(s);
            ChatsPrivada[j].getMarcoIrcPrivada().getTexto().setText("");
            ChatsPrivada[j].dispose();
            Textos[i] = null;
            return;
        }
    }

    public synchronized void escribeVentana(String s, String s1, Color color, boolean flag)
    {
        boolean flag1 = false;
        boolean flag2 = false;
        boolean flag3 = false;
        s = s.trim();
        String s2 = s.toUpperCase();
        if(s.substring(0, 1).compareTo("#") == 0 || s.substring(0, 1).compareTo("&") == 0)
            flag1 = true;
        try
        {
            if(ventanas.containsKey(s2))
            {
                Registro registro = new Registro((Registro)ventanas.get(s2));
                int k = registro.posicion;
                String s3 = registro.nReal;
                int i = registro.refer;
                VentanaActual = i;
                if(!esCanal())
                {
                    padre.getLocationOnScreen();
                    padre.getLocationOnScreen();
                    ChatsPrivada[posicionMarco()].pack();
                    ChatsPrivada[posicionMarco()].show();
                    System.out.println("");
                    try
                    {
                        if(VentanaRealUsuario >= 11)
                        {
                            ChatsPrivada[posicionMarco(VentanaRealUsuario)].getMarcoIrcPrivada().getComando().requestFocus();
                        } else
                        {
                            int i1 = posicionMarco(VentanaRealUsuario);
                            if(i1 == 0)
                                padre.getMarcoChat().getComando().requestFocus();
                            else
                                Chats[posicionMarco(VentanaRealUsuario)].getMarcoIrcCanal().getComando().requestFocus();
                        }
                    }
                    catch(Exception exception1) { }
                }
                Textos[VentanaActual].append(String.valueOf(String.valueOf(s1)).concat("\n"), color, flag);
                if(VentanaActual - 1 == 0)
                {
                    padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s1)).concat("\n"), color, flag);
                    return;
                }
            } else
            {
                byte byte0;
                byte byte1;
                if(flag1)
                {
                    byte0 = 1;
                    byte1 = 11;
                } else
                {
                    byte0 = 11;
                    byte1 = 26;
                }
                int j;
                for(j = byte0; j < byte1 && Textos[j] != null; j++);
                if(flag1)
                {
                    String s4;
                    if(s.length() > 15)
                        s4 = String.valueOf(String.valueOf(s.substring(0, 13))).concat("...");
                    else
                        s4 = s;
                    Chats[j - 1] = new MarcoVentanaIrcCanal(this);
                    Chats[j - 1].getMarcoIrcCanal().getLabel4().setText(s);
                    Chats[j - 1].getMarcoIrcCanal().getLabel3().setText(s4);
                    Chats[j - 1].getMarcoIrcCanal().getLabel2().setText(MiNick);
                    if(j - 1 == 0)
                    {
                        padre.getMarcoChat().getLabel4().setText(s);
                        padre.getMarcoChat().getLabel3().setText(s4);
                        padre.getMarcoChat().getLabel2().setText(MiNick);
                    }
                    Chats[j - 1].pack();
                    Textos[j] = Chats[j - 1].getMarcoIrcCanal().getTexto();
                    Textos[j].append(String.valueOf(String.valueOf(s1)).concat("\n"), color, flag);
                    LUsuarios[j] = Chats[j - 1].getMarcoIrcCanal().getUsuarios();
                    Chats[j - 1].getMarcoIrcCanal().getComando().setText("/LIST");
                    listlanzado = false;
                    if(j - 1 == 0)
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s1)).concat("\n"), color, flag);
                    if(j - 1 != 0)
                        Chats[j - 1].show();
                } else
                {
                    ChatsPrivada[j - 11] = new MarcoVentanaIrcPrivada(this);
                    ChatsPrivada[j - 11].getMarcoIrcPrivada().getLabel4().setText(s);
                    ChatsPrivada[j - 11].getMarcoIrcPrivada().getLabel2().setText(MiNick);
                    ChatsPrivada[j - 11].pack();
                    Textos[j] = ChatsPrivada[j - 11].getMarcoIrcPrivada().getTexto();
                    Textos[j].append(String.valueOf(String.valueOf(s1)).concat("\n"), color, flag);
                    ChatsPrivada[j - 11].show();
                    System.out.print(" ");
                    if(VentanaRealUsuario >= 11)
                    {
                        System.out.print(" ");
                        ChatsPrivada[posicionMarco(VentanaRealUsuario)].toFront();
                        ChatsPrivada[posicionMarco(VentanaRealUsuario)].getMarcoIrcPrivada().getComando().requestFocus();
                    } else
                    {
                        int j1 = posicionMarco(VentanaRealUsuario);
                        if(j1 == 0)
                        {
                            System.out.print(" ");
                            padre.getMarcoChat().getComando().requestFocus();
                            flag3 = true;
                        } else
                        {
                            System.out.println(" ");
                            Chats[posicionMarco(VentanaRealUsuario)].toFront();
                            Chats[posicionMarco(VentanaRealUsuario)].getMarcoIrcCanal().getComando().requestFocus();
                        }
                    }
                }
                int l;
                if(flag1)
                {
                    l = j - 1;
                    CanalActual = j;
                } else
                {
                    l = j - 11;
                    CanalActual = 0;
                }
                Registro registro1 = new Registro(s, l, j);
                ventanas.put(s2, registro1);
                if(flag3)
                    VentanaActual = 0;
                else
                    VentanaActual = j;
                if(!listlanzado)
                {
                    lanzaComando();
                    listlanzado = true;
                }
            }
            return;
        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }
    }

    public synchronized void expulsar(String s, String s1, String s2, String s3)
    {
        if(s1.toUpperCase().compareTo(MiNick.toUpperCase()) == 0)
        {
            cierraVentana(s);
            String s4 = (String)padre.tablaIdioma.get("teExpu");
            s4 = padre.reemplazarTexto(s4, "%NOMBRE_USUARIO%", s2);
            s4 = padre.reemplazarTexto(s4, "%NOMBRE_CANAL%", s);
            s4 = padre.reemplazarTexto(s4, "%MOTIVO%", s3);
            padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s4)).concat("\n"), colorConsola, true);
            return;
        }
        s1 = s1.toUpperCase();
        Registro registro = new Registro((Registro)ventanas.get(s.toUpperCase()));
        int i = registro.refer;
        for(int j = 0; j < LUsuarios[i].getItemCount(); j++)
        {
            String s5 = LUsuarios[i].getItem(j).trim().toUpperCase();
            if(s5.compareTo(s1) == 0 || s5.compareTo("@".concat(String.valueOf(String.valueOf(s1)))) == 0)
                if(i == 1)
                {
                    padre.getMarcoChat().getUsuarios().remove(j);
                    LUsuarios[i].remove(j);
                    return;
                } else
                {
                    LUsuarios[i].remove(j);
                    return;
                }
        }

    }

    public void lanzaComando()
    {
        try
        {
            String s1 = Comando.getText().trim();
            if(VentanaActual != 0)
            {
                if(esCanal())
                {
                    s1 = Chats[posicionMarco()].getMarcoIrcCanal().getComando().getText().trim();
                    Chats[posicionMarco()].getMarcoIrcCanal().getComando().setText("");
                } else
                {
                    s1 = ChatsPrivada[posicionMarco()].getMarcoIrcPrivada().getComando().getText().trim();
                    ChatsPrivada[posicionMarco()].getMarcoIrcPrivada().getComando().setText("");
                }
            } else
            {
                s1 = Comando.getText().trim();
            }
            if(s1.compareTo("") == 0)
                return;
            if(s1.length() > 1020)
                s1 = s1.substring(0, 1020);
            Comando.setText("");
            if(s1.indexOf("/") != 0)
                if(VentanaActual != 0)
                {
                    men.setCommand("PRIVMSG");
                    String s2;
                    if(esCanal())
                        s2 = Chats[posicionMarco()].getMarcoIrcCanal().getLabel4().getText();
                    else
                        s2 = ChatsPrivada[posicionMarco()].getMarcoIrcPrivada().getLabel4().getText();
                    String as1[] = {
                        s2
                    };
                    Datos datos = new Datos(as1, s1);
                    men.setParameters(datos);
                    cli.sendMessage(men);
                    Textos[VentanaActual].append(String.valueOf(String.valueOf((new StringBuffer("<")).append(MiNick).append("> ").append(s1).append("\n"))), colorMensajesPropios, true);
                    return;
                } else
                {
                    men.setCommand("PRIVMSG");
                    String s3 = padre.getMarcoChat().getLabel4().getText();
                    String as2[] = {
                        s3
                    };
                    Datos datos1 = new Datos(as2, s1);
                    men.setParameters(datos1);
                    cli.sendMessage(men);
                    padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((new StringBuffer("<")).append(MiNick).append("> ").append(s1).append("\n"))), colorMensajesPropios, true);
                    return;
                }
            s1 = s1.substring(1);
            if(s1.compareTo("") == 0)
                return;
            java.util.StringTokenizer stringtokenizer = new java.util.StringTokenizer(s1);
            String s = stringtokenizer.nextToken().toString().toUpperCase();
            int i = ht.containsKey(s) ? (new Integer(ht.get(s).toString())).intValue() : 1000;
            switch(i)
            {
            case 1013: 
            case 1015: 
            case 1016: 
            case 1017: 
            case 1018: 
            case 1019: 
            default:
                break;

            case 1014: 
                if(VentanaActual == 0)
                {
                    padre.getMarcoChat().getTextoConv().setText("");
                    return;
                } else
                {
                    Textos[VentanaActual].setText("");
                    return;
                }

            case 1020: 
                return;

            case 1021: 
                stringtokenizer = new java.util.StringTokenizer(s1);
                if(stringtokenizer.countTokens() == 1)
                    s1 = String.valueOf(String.valueOf(s1)).concat(" >0");
                break;

            case 1023: 
                stringtokenizer = new java.util.StringTokenizer(s1);
                if(stringtokenizer.countTokens() != 2)
                    if(VentanaActual == 0)
                    {
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("parInc"))).concat("\n"), colorConsola, true);
                        return;
                    } else
                    {
                        Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("parInc"))).concat("\n"), colorConsola, true);
                        return;
                    }
                String s4 = stringtokenizer.nextToken();
                s4 = stringtokenizer.nextToken();
                if(s4.toUpperCase().equals("ON"))
                {
                    ignoreActivo = true;
                    if(VentanaActual == 0)
                    {
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("ignAct"))).concat("\n"), colorConsola, true);
                        return;
                    } else
                    {
                        Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("ignAct"))).concat("\n"), colorConsola, true);
                        return;
                    }
                }
                if(s4.toUpperCase().equals("OFF"))
                {
                    ignoreActivo = false;
                    if(VentanaActual == 0)
                    {
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("ignIna"))).concat("\n"), colorConsola, true);
                        return;
                    } else
                    {
                        Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("ignIna"))).concat("\n"), colorConsola, true);
                        return;
                    }
                }
                if(s4.toUpperCase().equals("LIST"))
                {
                    if(listadoIgnorados.length == 0)
                        if(VentanaActual == 0)
                        {
                            padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("noEIgn"))).concat("\n"), colorConsola, true);
                            return;
                        } else
                        {
                            Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("noEIgn"))).concat("\n"), colorConsola, true);
                            return;
                        }
                    if(VentanaActual == 0)
                    {
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("lisIgn"))).concat("\n"), colorConsola, true);
                        for(int k = 0; k < listadoIgnorados.length; k++)
                            padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(listadoIgnorados[k])).concat("\n"), colorConsola, true);

                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("finLis"))).concat("\n"), colorConsola, true);
                        return;
                    }
                    Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("lisIgn"))).concat("\n"), colorConsola, true);
                    for(int l = 0; l < listadoIgnorados.length; l++)
                        Textos[VentanaActual].append(String.valueOf(String.valueOf(listadoIgnorados[l])).concat("\n"), colorConsola, true);

                    Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("finLis"))).concat("\n"), colorConsola, true);
                    return;
                }
                if(s4.indexOf("@") == 0)
                    s4 = s4.substring(1, s4.length());
                if(s4.equals(MiNick))
                    if(VentanaActual == 0)
                    {
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("noIncl"))).concat("\n"), colorConsola, true);
                        return;
                    } else
                    {
                        Textos[VentanaActual].append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("noIncl"))).concat("\n"), colorConsola, true);
                        return;
                    }
                boolean flag = false;
                for(int j1 = 0; j1 < listadoIgnorados.length; j1++)
                    if(listadoIgnorados[j1].equals(s4) || listadoIgnorados[j1].equals(s4))
                        flag = true;

                if(!flag)
                {
                    String as3[] = new String[listadoIgnorados.length + 1];
                    int k1;
                    for(k1 = 0; k1 < listadoIgnorados.length; k1++)
                        as3[k1] = listadoIgnorados[k1];

                    as3[k1] = s4;
                    listadoIgnorados = as3;
                    String s5 = (String)padre.tablaIdioma.get("usuIgn");
                    s5 = padre.reemplazarTexto(s5, "%NOMBRE_USUARIO%", s4);
                    if(VentanaActual == 0)
                        padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s5)).concat("\n"), colorConsola, true);
                    else
                        Textos[VentanaActual].append(String.valueOf(String.valueOf(s5)).concat("\n"), colorConsola, true);
                    ignoreActivo = true;
                    for(int j2 = 0; j2 < padre.getMarcoChat().getUsuarios().getItemCount(); j2++)
                    {
                        padre.getMarcoChat().getUsuarios().getItem(j2);
                        if(padre.getMarcoChat().getUsuarios().getItem(j2).equals(s4))
                        {
                            padre.getMarcoChat().getUsuarios().replaceItem("*".concat(String.valueOf(String.valueOf(s4))), j2);
                            continue;
                        }
                        if(padre.getMarcoChat().getUsuarios().getItem(j2).substring(1).equals(s4))
                            padre.getMarcoChat().getUsuarios().replaceItem("*@".concat(String.valueOf(String.valueOf(s4))), j2);
                    }

                    for(int k2 = 1; k2 < 11; k2++)
                    {
                        if(LUsuarios[k2] == null)
                            continue;
                        for(int i3 = 0; i3 < LUsuarios[k2].getItemCount(); i3++)
                        {
                            if(LUsuarios[k2].getItem(i3).equals(s4))
                            {
                                LUsuarios[k2].replaceItem("*".concat(String.valueOf(String.valueOf(s4))), i3);
                                continue;
                            }
                            if(LUsuarios[k2].getItem(i3).substring(1).equals(s4))
                                LUsuarios[k2].replaceItem("*@".concat(String.valueOf(String.valueOf(s4))), i3);
                        }

                    }

                    return;
                }
                String as4[] = new String[listadoIgnorados.length - 1];
                int l1 = 0;
                for(int i2 = 0; i2 < listadoIgnorados.length; i2++)
                    if(!listadoIgnorados[i2].equals(s4))
                    {
                        as4[l1] = listadoIgnorados[i2];
                        l1++;
                    }

                listadoIgnorados = as4;
                String s6 = (String)padre.tablaIdioma.get("salIgn");
                s6 = padre.reemplazarTexto(s6, "%NOMBRE_USUARIO%", s4);
                if(VentanaActual == 0)
                    padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s6)).concat("\n"), colorConsola, true);
                else
                    Textos[VentanaActual].append(String.valueOf(String.valueOf(s6)).concat("\n"), colorConsola, true);
                for(int l2 = 0; l2 < padre.getMarcoChat().getUsuarios().getItemCount(); l2++)
                {
                    padre.getMarcoChat().getUsuarios().getItem(l2);
                    if(padre.getMarcoChat().getUsuarios().getItem(l2).substring(1).equals(s4))
                    {
                        padre.getMarcoChat().getUsuarios().replaceItem(s4, l2);
                        continue;
                    }
                    if(padre.getMarcoChat().getUsuarios().getItem(l2).substring(2).equals(s4))
                        padre.getMarcoChat().getUsuarios().replaceItem("@".concat(String.valueOf(String.valueOf(s4))), l2);
                }

                for(int j3 = 1; j3 < 11; j3++)
                {
                    if(LUsuarios[j3] == null)
                        continue;
                    for(int k3 = 0; k3 < LUsuarios[j3].getItemCount(); k3++)
                    {
                        if(LUsuarios[j3].getItem(k3).substring(1).equals(s4))
                        {
                            LUsuarios[j3].replaceItem(s4, k3);
                            continue;
                        }
                        if(LUsuarios[j3].getItem(k3).substring(2).equals(s4))
                            LUsuarios[j3].replaceItem("@".concat(String.valueOf(String.valueOf(s4))), k3);
                    }

                }

                return;

            case 1024: 
                return;

            case 1022: 
                if(buscando)
                    return;
                stringtokenizer = new java.util.StringTokenizer(s1);
                if(stringtokenizer.countTokens() != 2)
                {
                    padre.ventanaBuscar.list1.addItem((String)padre.tablaIdioma.get("parInc"));
                    return;
                }
                stringtokenizer.nextToken();
                s1 = "WHOIS ".concat(String.valueOf(String.valueOf(stringtokenizer.nextToken())));
                buscando = true;
                break;
            }
            stringtokenizer = new java.util.StringTokenizer(s1);
            men.setCommand(stringtokenizer.nextToken());
            int j = stringtokenizer.countTokens();
            String as[] = new String[j];
            for(int i1 = 0; i1 < j; i1++)
                as[i1] = new String(stringtokenizer.nextToken());

            Datos datos2 = new Datos(as, "");
            men.setParameters(datos2);
            cli.sendMessage(men);
            if(i == 1013)
            {
                cerrarVentanas(false);
                parar();
                return;
            }
        }
        catch(Exception exception)
        {
            System.out.println("Excepcion: ".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public synchronized void operador(String s, String s1, boolean flag)
    {
        Registro registro = (Registro)ventanas.get(s.toUpperCase());
        int i = registro.refer;
        s1 = s1.toUpperCase();
        boolean flag1 = false;
        int j = 0;
        do
        {
            if(j >= listadoIgnorados.length)
                break;
            if(s1.equals(listadoIgnorados[j].toUpperCase()))
            {
                flag1 = true;
                break;
            }
            j++;
        } while(true);
        if(flag)
        {
            for(int k = 0; k < LUsuarios[i].getItemCount(); k++)
            {
                String s2 = LUsuarios[i].getItem(k).trim().toUpperCase();
                if(flag1)
                {
                    if(s2.compareTo("*".concat(String.valueOf(String.valueOf(s1))).toUpperCase()) != 0)
                        continue;
                    ordenaListaConAlias(LUsuarios[i], "*@".concat(String.valueOf(String.valueOf(LUsuarios[i].getItem(k).substring(1)))), k);
                    break;
                }
                if(s2.compareTo(s1.toUpperCase()) != 0)
                    continue;
                ordenaListaConAlias(LUsuarios[i], "@".concat(String.valueOf(String.valueOf(LUsuarios[i].getItem(k)))), k);
                break;
            }

            if(i == 1)
            {
                for(int i1 = 0; i1 < padre.getMarcoChat().getUsuarios().getItemCount(); i1++)
                {
                    String s4 = padre.getMarcoChat().getUsuarios().getItem(i1).toUpperCase();
                    if(flag1)
                    {
                        if(s4.compareTo("*".concat(String.valueOf(String.valueOf(s1.toUpperCase())))) == 0)
                        {
                            ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), "*@".concat(String.valueOf(String.valueOf(padre.getMarcoChat().getUsuarios().getItem(i1).substring(1)))), i1);
                            return;
                        }
                        continue;
                    }
                    if(s4.compareTo(s1.toUpperCase()) == 0)
                    {
                        ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), "@".concat(String.valueOf(String.valueOf(padre.getMarcoChat().getUsuarios().getItem(i1)))), i1);
                        return;
                    }
                }

                return;
            }
        } else
        {
            for(int l = 0; l < LUsuarios[i].getItemCount(); l++)
            {
                String s3 = LUsuarios[i].getItem(l).trim().toUpperCase();
                if(flag1)
                {
                    if(s3.compareTo("*@".concat(String.valueOf(String.valueOf(s1))).toUpperCase()) != 0)
                        continue;
                    ordenaListaConAlias(LUsuarios[i], "*".concat(String.valueOf(String.valueOf(LUsuarios[i].getItem(l).substring(2)))), l);
                    break;
                }
                if(s3.compareTo("@".concat(String.valueOf(String.valueOf(s1))).toUpperCase()) != 0)
                    continue;
                ordenaListaConAlias(LUsuarios[i], LUsuarios[i].getItem(l).substring(1), l);
                break;
            }

            if(i == 1)
            {
                for(int j1 = 0; j1 < padre.getMarcoChat().getUsuarios().getItemCount(); j1++)
                {
                    String s5 = padre.getMarcoChat().getUsuarios().getItem(j1).toUpperCase();
                    if(flag1)
                    {
                        if(s5.compareTo("*@".concat(String.valueOf(String.valueOf(s1))).toUpperCase()) == 0)
                        {
                            ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), "*".concat(String.valueOf(String.valueOf(padre.getMarcoChat().getUsuarios().getItem(j1).substring(2)))), j1);
                            return;
                        }
                        continue;
                    }
                    if(s5.compareTo("@".concat(String.valueOf(String.valueOf(s1))).toUpperCase()) == 0)
                    {
                        ordenaListaConAlias(padre.getMarcoChat().getUsuarios(), padre.getMarcoChat().getUsuarios().getItem(j1).substring(1), j1);
                        return;
                    }
                }

            }
        }
    }

    public void cerrarVentanas(boolean flag)
    {
        if(flag)
        {
            padre.getMarcoChat().getComando().setText("/QUIT");
            asignaVentanaActual("Consola");
            lanzaComando();
        } else
        {
            try
            {
                for(int i = 0; i < Chats.length; i++)
                    if(Chats[i] != null && Chats[i] != null)
                    {
                        Chats[i].dispose();
                        Chats[i] = null;
                    }

                for(int j = 0; j < ChatsPrivada.length; j++)
                    if(ChatsPrivada[j] != null)
                    {
                        ChatsPrivada[j].dispose();
                        ChatsPrivada[j] = null;
                    }

            }
            catch(Exception exception) { }
        }
        parar();
    }

    public void parar()
    {
        try
        {
            cli.disconnect();
            padre.conectado = false;
            stop();
            return;
        }
        catch(Exception exception)
        {
            System.out.println(exception);
        }
    }

    public synchronized void ponUsuario(String s, String s1)
    {
        Registro registro = new Registro((Registro)ventanas.get(s.toUpperCase()));
        int i = buscarPosicion(registro, s, s1);
        for(int j = 0; j < listadoIgnorados.length; j++)
            if(listadoIgnorados[j].equals(s1) || "@".concat(String.valueOf(String.valueOf(listadoIgnorados[j]))).equals(s1))
                s1 = "*".concat(String.valueOf(String.valueOf(s1)));

        LUsuarios[registro.refer].add(s1, i);
        if(registro.refer == 1)
            padre.getMarcoChat().getUsuarios().add(s1, i);
    }

    private int buscarPosicion(Registro registro, String s, String s1)
    {
        java.util.Vector vector = new java.util.Vector();
        if(registro.refer == 1)
        {
            for(int i = 0; i < padre.getMarcoChat().getUsuarios().getItemCount(); i++)
                if(i <= 0 || !padre.getMarcoChat().getUsuarios().getItem(i).equals(padre.getMarcoChat().getUsuarios().getItem(i - 1)))
                    vector.addElement(padre.getMarcoChat().getUsuarios().getItem(i));

        } else
        {
            for(int j = 0; j < LUsuarios[registro.refer].getItemCount(); j++)
                if(j <= 0 || !LUsuarios[registro.refer].getItem(j).equals(LUsuarios[registro.refer].getItem(j - 1)))
                    vector.addElement(LUsuarios[registro.refer].getItem(j));

        }
        for(int k = 0; k < vector.size(); k++)
        {
            String s2 = ((String)vector.elementAt(k)).toLowerCase();
            if(s2.startsWith("*"))
                s2 = s2.substring(1);
            if(s2.compareTo(s1.toLowerCase()) > 0)
                return k;
        }

        return vector.size();
    }

    public synchronized void quita(String s, String s1)
    {
        boolean flag = false;
        for(int i = 0; i < listadoIgnorados.length; i++)
            if(listadoIgnorados[i].equals(s))
                flag = true;

        for(int j = 1; j < 11; j++)
        {
            if(LUsuarios[j] == null)
                continue;
            try
            {
                if(j == 1)
                    if(flag)
                        padre.getMarcoChat().getUsuarios().remove("*".concat(String.valueOf(String.valueOf(s))));
                    else
                        padre.getMarcoChat().getUsuarios().remove(s);
                if(flag)
                    LUsuarios[j].remove("*".concat(String.valueOf(String.valueOf(s))));
                else
                    LUsuarios[j].remove(s);
            }
            catch(Exception _ex)
            {
                try
                {
                    if(j == 1)
                        if(flag)
                            padre.getMarcoChat().getUsuarios().remove("*@".concat(String.valueOf(String.valueOf(s))));
                        else
                            padre.getMarcoChat().getUsuarios().remove("@".concat(String.valueOf(String.valueOf(s))));
                    if(flag)
                        LUsuarios[j].remove("*@".concat(String.valueOf(String.valueOf(s))));
                    else
                        LUsuarios[j].remove("@".concat(String.valueOf(String.valueOf(s))));
                }
                catch(Exception _ex2)
                {
                    continue;
                }
            }
            if(j == 1)
                padre.getMarcoChat().getTextoConv().append(s1, colorConsola, true);
            Textos[j].append(s1, colorConsola, true);
        }

        try
        {
            if((Registro)ventanas.get(s.toUpperCase()) != null)
            {
                escribeVentana(s, s1, colorConsola, true);
                return;
            }
        }
        catch(NullPointerException nullpointerexception) { }
    }

    public synchronized void quita(String s)
    {
        String s1 = (String)padre.tablaIdioma.get("abaSes");
        s1 = padre.reemplazarTexto(s1, "%NOMBRE_USUARIO%", s);
        quita(s, String.valueOf(String.valueOf(s1)).concat("\n"));
    }

    public synchronized void quitaUsuario(String s, String s1)
    {
        boolean flag = false;
        Registro registro = new Registro((Registro)ventanas.get(s.toUpperCase()));
        try
        {
            for(int i = 0; i < listadoIgnorados.length; i++)
                if(listadoIgnorados[i].equals(s1))
                    flag = true;

            if(registro.refer == 1)
                if(flag)
                    padre.getMarcoChat().getUsuarios().remove("*".concat(String.valueOf(String.valueOf(s1))));
                else
                    padre.getMarcoChat().getUsuarios().remove(s1);
            if(flag)
            {
                LUsuarios[registro.refer].remove("*".concat(String.valueOf(String.valueOf(s1))));
                return;
            } else
            {
                LUsuarios[registro.refer].remove(s1);
                return;
            }
        }
        catch(IllegalArgumentException illegalargumentexception) { }
        try
        {
            if(registro.refer == 1)
                if(flag)
                    padre.getMarcoChat().getUsuarios().remove("*@".concat(String.valueOf(String.valueOf(s1))));
                else
                    padre.getMarcoChat().getUsuarios().remove("@".concat(String.valueOf(String.valueOf(s1))));
            if(flag)
            {
                LUsuarios[registro.refer].remove("*@".concat(String.valueOf(String.valueOf(s1))));
                return;
            } else
            {
                LUsuarios[registro.refer].remove("@".concat(String.valueOf(String.valueOf(s1))));
                return;
            }
        }
        catch(IllegalArgumentException _ex)
        {
            System.out.println(String.valueOf(String.valueOf((new StringBuffer("Canal: ")).append(s).append(". Se ha producido un error al intentar eliminar al usuario ").append(s1).append(" de la lista."))));
        }
        System.out.println("La lista est\341 formada por los usuarios: ");
        if(registro.refer == 1)
        {
            for(int j = 0; j < padre.getMarcoChat().getUsuarios().getItemCount(); j++)
                if(j <= 0 || !padre.getMarcoChat().getUsuarios().getItem(j).equals(padre.getMarcoChat().getUsuarios().getItem(j - 1)))
                    System.out.println(padre.getMarcoChat().getUsuarios().getItem(j));

            return;
        }
        for(int k = 0; k < LUsuarios[registro.refer].getItemCount(); k++)
            if(k <= 0 || !LUsuarios[registro.refer].getItem(k).equals(LUsuarios[registro.refer].getItem(k - 1)))
                System.out.println(LUsuarios[registro.refer].getItem(k));

    }

    public synchronized void rellenaUsuarios(String s, String s1)
    {
        Registro registro = new Registro();
        boolean flag = false;
        java.util.StringTokenizer stringtokenizer = new java.util.StringTokenizer(s1);
        int i = stringtokenizer.countTokens();
        if(ventanas.containsKey(s.toUpperCase()))
        {
            flag = true;
            registro = (Registro)ventanas.get(s.toUpperCase());
            if(registro.refer == 1)
                LUsuarios[registro.refer - 1].removeAll();
            else
                LUsuarios[registro.refer].removeAll();
        }
        for(int j = 0; j < i; j++)
        {
            String s2 = stringtokenizer.nextToken();
            if(!flag)
                continue;
            for(int k = 0; k < listadoIgnorados.length; k++)
                if(listadoIgnorados[k].equals(s2) || "@".concat(String.valueOf(String.valueOf(listadoIgnorados[k]))).equals(s2))
                    s2 = "*".concat(String.valueOf(String.valueOf(s2)));

            if(registro.refer == 1)
                LUsuarios[registro.refer - 1].add(s2);
            LUsuarios[registro.refer].add(s2);
        }

    }

    public void run()
    {
        String s = "";
        boolean flag = false;
        try
        {
            String s1 = "";
            String s2 = "";
            String s4 = "";
            String s5 = "";
            if(s1.length() > 50)
                s1 = s1.substring(0, 50);
            s1 = s1.compareTo("") == 0 ? MiNick : s1;
            String s8 = "";
            String s9 = "";
            String s11 = "";
            ht.put("PRIVMSG", "1001");
            ht.put("PONG", "1002");
            ht.put("JOIN", "1003");
            ht.put("USER", "1004");
            ht.put("NICK", "1005");
            ht.put("PART", "1006");
            ht.put("PASS", "1007");
            ht.put("SERVER", "1008");
            ht.put("TOPIC", "1009");
            ht.put("KILL", "1010");
            ht.put("UNKNOWN", "1011");
            ht.put("NOTICE", "1012");
            ht.put("BYE", "1013");
            ht.put("EXIT", "1013");
            ht.put("QUIT", "1013");
            ht.put("SIGNOFF", "1013");
            ht.put("CLEAR", "1014");
            ht.put("INVITE", "1015");
            ht.put("NOTICE", "1016");
            ht.put("ERROR", "1017");
            ht.put("KICK", "1018");
            ht.put("MODE", "1019");
            ht.put("HELP", "1020");
            ht.put("LIST", "1021");
            ht.put("WHO", "1022");
            ht.put("WHOIS", "1022");
            ht.put("WHOWAS", "1022");
            ht.put("IGNORE", "1023");
            ht.put("NAMES", "1024");
            ventanas.put("CONSOLA", new Registro("CONSOLA", 0, 0));
            cli = new ConexionIrc(padre.servidor, padre.puerto);
            cli.connect(MiNick, "host", "servidor", s1, MiNick, "");
            men = new ContenidoSencillo();
            do
            {
                if((s4 = cli.getResponse()) == null)
                    break;
                if(s4.substring(0, 4).compareTo("PING") == 0)
                {
                    s4 = s4.replace('I', 'O');
                    men.setCommand(s4);
                    cli.sendMessage(men);
                } else
                {
                    try
                    {
                        String s13 = new String(s4);
                        s13 = s13.substring(s13.indexOf(":", 1) + 1);
                        java.util.StringTokenizer stringtokenizer = new java.util.StringTokenizer(s4, ":");
                        int i3 = stringtokenizer.countTokens();
                        String as[] = new String[i3];
                        for(int l1 = 0; l1 < i3; l1++)
                            as[l1] = new String(stringtokenizer.nextToken());

                        java.util.StringTokenizer stringtokenizer1 = new java.util.StringTokenizer(as[0]);
                        int j3 = stringtokenizer1.countTokens();
                        String as1[] = new String[j3];
                        for(int i2 = 0; i2 < j3; i2++)
                            as1[i2] = new String(stringtokenizer1.nextToken());

                        int i;
                        try
                        {
                            i = (new Integer(as1[1])).intValue();
                        }
                        catch(IllegalArgumentException _ex)
                        {
                            i = ht.containsKey(as1[1]) ? (new Integer(ht.get(as1[1]).toString())).intValue() : 1000;
                        }
                        int j2 = as1[0].indexOf("!");
                        String s3;
                        if(j2 != -1)
                            s3 = as1[0].substring(0, j2);
                        else
                            s3 = "";
                        s4 = padre.reemplazarTexto(s4, "(abcde1234 por ejemplo)", ",abcde1234 por ejemplo,");
                        j2 = s4.lastIndexOf("(");
                        int k3 = s4.lastIndexOf("!");
                        if(j2 != -1 && j2 > k3)
                            s = s4.substring(j2, s4.length());
                        else
                        if(k3 != -1)
                            s = String.valueOf(String.valueOf((new StringBuffer("(")).append(s4.substring(k3 + 1, s4.length())).append(")")));
                        if(s.indexOf(menRobot1) != -1)
                            s = (String)padre.tablaIdioma.get("motiv1");
                        else
                        if(s.indexOf(menRobot2) != -1)
                            s = (String)padre.tablaIdioma.get("motiv2");
                        else
                        if(s.indexOf(menRobot3) != -1)
                            s = (String)padre.tablaIdioma.get("motiv3");
                        else
                        if(s.indexOf(menRobot4) != -1)
                            s = (String)padre.tablaIdioma.get("motiv4");
                        else
                        if(s.indexOf(menRobot5) != -1)
                            s = (String)padre.tablaIdioma.get("motiv5");
                        boolean flag1 = false;
                        if(ignoreActivo)
                        {
                            for(int l3 = 0; l3 < listadoIgnorados.length; l3++)
                                if(listadoIgnorados[l3].equals(s3))
                                    flag1 = true;

                            if(flag1 && i != 1005 && i != 1006 && i != 1013 && i != 1019 && i != 1003)
                                i = 2000;
                        }
                        switch(i)
                        {
                        case 2: // '\002'
                        case 3: // '\003'
                        case 5: // '\005'
                        case 7: // '\007'
                        case 204: 
                        case 206: 
                        case 253: 
                        case 254: 
                        case 255: 
                        case 302: 
                        case 303: 
                        case 305: 
                        case 312: 
                        case 314: 
                        case 315: 
                        case 317: 
                        case 331: 
                        case 332: 
                        case 334: 
                        case 341: 
                        case 351: 
                        case 352: 
                        case 364: 
                        case 365: 
                        case 369: 
                        case 371: 
                        case 372: 
                        case 374: 
                        case 375: 
                        case 376: 
                        case 391: 
                        case 401: 
                        case 402: 
                        case 403: 
                        case 404: 
                        case 405: 
                        case 406: 
                        case 411: 
                        case 412: 
                        case 421: 
                        case 422: 
                        case 431: 
                        case 441: 
                        case 442: 
                        case 443: 
                        case 461: 
                        case 462: 
                        case 468: 
                        case 471: 
                        case 472: 
                        case 473: 
                        case 474: 
                        case 475: 
                        case 481: 
                        case 482: 
                        case 491: 
                        case 501: 
                        case 502: 
                        case 1000: 
                        case 1015: 
                        default:
                            break;

                        case 1: // '\001'
                            if(can.compareTo("") != 0 && !padre.conectado)
                            {
                                men.setCommand("JOIN #".concat(String.valueOf(String.valueOf(can))));
                                cli.sendMessage(men);
                            }
                            padre.conectado = true;
                            break;

                        case 251: 
                            java.util.StringTokenizer stringtokenizer2 = new java.util.StringTokenizer(as[1]);
                            stringtokenizer2.nextToken();
                            stringtokenizer2.nextToken();
                            padre.ventanaBuscar.label2.setText(stringtokenizer2.nextToken());
                            break;

                        case 301: 
                            escribeVentana(as1[3], String.valueOf(String.valueOf((new StringBuffer("<")).append(as1[3]).append("> ").append(as[1]))), colorConsola, true);
                            break;

                        case 311: 
                            if(wholleno)
                                wholleno = false;
                            s8 = as1[3];
                            padre.ventanaBuscar.setVisible(true);
                            break;

                        case 318: 
                            wholleno = true;
                            if(padre.ventanaBuscar.list1.getItemCount() == 0)
                                padre.ventanaBuscar.list1.addItem((String)padre.tablaIdioma.get("errNoE"));
                            buscando = false;
                            break;

                        case 319: 
                            java.util.StringTokenizer stringtokenizer3 = new java.util.StringTokenizer(as[1], " ");
                            int i4 = 15 - s8.length();
                            for(int j4 = 0; j4 < i4; j4++)
                                s8 = String.valueOf(String.valueOf(s8)).concat(" ");

                            while(stringtokenizer3.hasMoreTokens()) 
                            {
                                String s10 = stringtokenizer3.nextToken();
                                if(s10.startsWith("@"))
                                    s10 = s10.substring(1, s10.length());
                                String s12 = String.valueOf(String.valueOf((new StringBuffer(" ")).append(s8).append(s10)));
                                padre.ventanaBuscar.list1.addItem(s12);
                            }
                            break;

                        case 321: 
                            int j = padre.getLocationOnScreen().x;
                            int i1 = padre.getLocationOnScreen().y;
                            padre.f.setBounds(j > 0 ? j : 0, i1 > 0 ? i1 : 0, 426, 320);
                            padre.f.getLista().removeAll();
                            padre.f.todosLosCanales = null;
                            padre.f.todosLosCanales = new String[0];
                            break;

                        case 322: 
                            padre.f.meterEnLista(String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(as1[3])))).append("    ").append(as1[4]).append("    ").append(i3 < 2 ? "" : as[1]))));
                            break;

                        case 323: 
                            padre.f.ordenaLista();
                            padre.getMarcoChat().getCanales().removeAll();
                            padre.getMarcoChat().getAreas().removeAll();
                            for(int k4 = 0; k4 < Chats.length; k4++)
                                if(Chats[k4] != null)
                                {
                                    Chats[k4].getMarcoIrcCanal().getCanales().removeAll();
                                    Chats[k4].getMarcoIrcCanal().getAreas().removeAll();
                                }

                            canales = padre.f.getLista().getItems();
                            generaAreasyCanales();
                            for(int l4 = 0; l4 < AreasyCanales.length; l4++)
                                if(AreasyCanales[l4][0].compareTo("") != 0)
                                    padre.getMarcoChat().getAreas().add(AreasyCanales[l4][0]);

                            for(int i5 = 1; i5 < AreasyCanales[0].length; i5++)
                                if(AreasyCanales[0][i5].compareTo("") != 0)
                                {
                                    java.util.StringTokenizer stringtokenizer4 = new java.util.StringTokenizer(AreasyCanales[0][i5]);
                                    String s14 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(stringtokenizer4.nextToken())))).append("   ").append(stringtokenizer4.nextToken())));
                                    padre.getMarcoChat().getCanales().add(s14);
                                }

                            for(int j5 = 0; j5 < Chats.length; j5++)
                            {
                                if(Chats[j5] == null)
                                    continue;
                                for(int k5 = 0; k5 < AreasyCanales.length; k5++)
                                    if(AreasyCanales[k5][0].compareTo("") != 0)
                                        Chats[j5].getMarcoIrcCanal().getAreas().add(AreasyCanales[k5][0]);

                                for(int l5 = 1; l5 < AreasyCanales[0].length; l5++)
                                    if(AreasyCanales[0][l5].compareTo("") != 0)
                                    {
                                        java.util.StringTokenizer stringtokenizer5 = new java.util.StringTokenizer(AreasyCanales[0][l5]);
                                        String s16 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(stringtokenizer5.nextToken())))).append("   ").append(stringtokenizer5.nextToken())));
                                        Chats[j5].getMarcoIrcCanal().getCanales().add(s16);
                                    }

                            }

                            boolean flag2 = false;
                            boolean flag3 = false;
                            int i6 = 0;
                            Registro registro = new Registro((Registro)ventanas.get(nombreMarco().toUpperCase()));
                            int j6 = registro.posicion;
                            String s15;
                            if(j6 == 0)
                            {
                                flag3 = true;
                                s15 = padre.getMarcoChat().getLabel4().getText();
                            } else
                            {
                                s15 = Chats[j6].getMarcoIrcCanal().getLabel4().getText();
                            }
                            for(int k6 = 0; k6 < AreasyCanales.length && !flag2; k6++)
                            {
                                for(int k7 = 1; k7 < AreasyCanales[k6].length; k7++)
                                {
                                    if(AreasyCanales[k6][k7].compareTo("") == 0)
                                        continue;
                                    java.util.StringTokenizer stringtokenizer7 = new java.util.StringTokenizer(AreasyCanales[k6][k7]);
                                    if(stringtokenizer7.nextToken().compareTo(s15) != 0)
                                        continue;
                                    if(flag3)
                                        padre.getMarcoChat().getAreas().select(k6);
                                    else
                                        Chats[j6].getMarcoIrcCanal().getAreas().select(k6);
                                    flag2 = true;
                                    i6 = k6;
                                    break;
                                }

                            }

                            if(flag3)
                                padre.getMarcoChat().getCanales().removeAll();
                            else
                                Chats[j6].getMarcoIrcCanal().getCanales().removeAll();
                            int l6 = 1;
                            while(l6 < AreasyCanales[i6].length) 
                            {
                                if(AreasyCanales[i6][l6].compareTo("") != 0)
                                {
                                    java.util.StringTokenizer stringtokenizer6 = new java.util.StringTokenizer(AreasyCanales[i6][l6]);
                                    String s26 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(stringtokenizer6.nextToken())))).append("   ").append(stringtokenizer6.nextToken())));
                                    if(flag3)
                                        padre.getMarcoChat().getCanales().add(s26);
                                    else
                                        Chats[j6].getMarcoIrcCanal().getCanales().add(s26);
                                }
                                l6++;
                            }
                            break;

                        case 353: 
                            if(listaTemporal == null)
                                listaTemporal = as[1];
                            else
                                listaTemporal = String.valueOf(listaTemporal) + String.valueOf(" ".concat(String.valueOf(String.valueOf(as[1]))));
                            break;

                        case 366: 
                            rellenaUsuarios(as1[3], ordenaListaUsuarios(listaTemporal));
                            listaTemporal = null;
                            break;

                        case 432: 
                            if(!padre.conectado)
                            {
                                int k = padre.getLocationOnScreen().x;
                                int j1 = padre.getLocationOnScreen().y;
                                padre.nickusado.setBounds(k > 0 ? k : 0, j1 > 0 ? j1 : 0, 224, 180);
                                padre.nickusado.show();
                            }
                            break;

                        case 433: 
                            MiNick = as1[2];
                            padre.getMarcoChat().getLabel2().setText(as1[2]);
                            for(int i7 = 0; i7 < padre.hiloReceptor.Chats.length; i7++)
                                if(padre.hiloReceptor.Chats[i7] != null)
                                    padre.hiloReceptor.Chats[i7].getMarcoIrcCanal().getLabel2().setText(as1[2]);

                            for(int j7 = 0; j7 < padre.hiloReceptor.ChatsPrivada.length; j7++)
                                if(padre.hiloReceptor.ChatsPrivada[j7] != null)
                                    padre.hiloReceptor.ChatsPrivada[j7].getMarcoIrcPrivada().getLabel2().setText(as1[2]);

                            int l = padre.getLocationOnScreen().x;
                            int k1 = padre.getLocationOnScreen().y;
                            padre.nickusado.setBounds(l > 0 ? l : 0, k1 > 0 ? k1 : 0, 224, 180);
                            padre.nickusado.show();
                            padre.hayQueSeguir = true;
                            break;

                        case 438: 
                            String s17 = (String)padre.tablaIdioma.get("camRap");
                            s17 = padre.reemplazarTexto(s17, "%NOMBRE_USUARIO%", as1[2]);
                            int l7 = 1;
                            while(l7 < 11) 
                            {
                                if(l7 == 1)
                                    padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s17)).concat("\n"), colorConsola, true);
                                else
                                if(Textos[l7] != null)
                                    Textos[l7].append(String.valueOf(String.valueOf(s17)).concat("\n"), colorConsola, true);
                                l7++;
                            }
                            break;

                        case 1001: 
                            String s6;
                            if(as.length < 2)
                                s6 = s13;
                            else
                                s6 = as[1];
                            for(int k2 = 2; k2 < i3; k2++)
                                s6 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s6)))).append(":").append(as[k2])));

                            if(!s6.equals(s13))
                                s6 = s13;
                            if(as1[2].compareTo(MiNick) != 0)
                                escribeVentana(as1[2], String.valueOf(String.valueOf((new StringBuffer("<")).append(s3).append("> ").append(s6))), colorMensajesOtros, true);
                            else
                                escribeVentana(s3, String.valueOf(String.valueOf((new StringBuffer("<")).append(s3).append("> ").append(s6))), colorMensajesOtros, true);
                            break;

                        case 1003: 
                            String s18 = (String)padre.tablaIdioma.get("entCan");
                            s18 = padre.reemplazarTexto(s18, "%NOMBRE_USUARIO%", s3);
                            s18 = padre.reemplazarTexto(s18, "%NOMBRE_CANAL%", as[1]);
                            escribeVentana(as[1], s18, colorConsola, true);
                            if(MiNick.compareTo(s3) != 0)
                                ponUsuario(as[1], s3);
                            break;

                        case 1005: 
                            if(ignoreActivo)
                            {
                                for(int i8 = 0; i8 < listadoIgnorados.length; i8++)
                                    if(listadoIgnorados[i8].equals(s3))
                                        listadoIgnorados[i8] = as[1];

                            }
                            if(padre.ventanaProp != null && padre.ventanaProp.isVisible() && (padre.ventanaProp.getTitle().compareTo(s3) == 0 || padre.ventanaProp.getTitle().compareTo("@".concat(String.valueOf(String.valueOf(s3)))) == 0))
                                if(padre.ventanaProp.getTitle().indexOf("@") != -1)
                                    padre.ventanaProp.setTitle("@".concat(String.valueOf(String.valueOf(as[1]))));
                                else
                                    padre.ventanaProp.setTitle(as[1]);
                            for(int j8 = 0; j8 < Chats.length; j8++)
                            {
                                if(Chats[j8] == null || Chats[j8].ventanaProp == null || !Chats[j8].ventanaProp.isVisible() || Chats[j8].ventanaProp.getTitle().compareTo(s3) != 0 && Chats[j8].ventanaProp.getTitle().compareTo("@".concat(String.valueOf(String.valueOf(s3)))) != 0)
                                    continue;
                                if(Chats[j8].ventanaProp.getTitle().indexOf("@") != -1)
                                    Chats[j8].ventanaProp.setTitle("@".concat(String.valueOf(String.valueOf(as[1]))));
                                else
                                    Chats[j8].ventanaProp.setTitle(as[1]);
                            }

                            cambiarNick(s3, as[1]);
                            break;

                        case 1006: 
                            if(s3.compareTo(MiNick) == 0)
                            {
                                cierraVentana(as1[2]);
                            } else
                            {
                                String s19 = (String)padre.tablaIdioma.get("abanCn");
                                s19 = padre.reemplazarTexto(s19, "%NOMBRE_USUARIO%", s3);
                                s19 = padre.reemplazarTexto(s19, "%NOMBRE_CANAL%", as1[2]);
                                escribeVentana(as1[2], s19, colorConsola, true);
                                quitaUsuario(as1[2], s3);
                            }
                            break;

                        case 1009: 
                            String s20 = (String)padre.tablaIdioma.get("camTit");
                            s20 = padre.reemplazarTexto(s20, "%NOMBRE_USUARIO%", s3);
                            s20 = padre.reemplazarTexto(s20, "%NOMBRE_CANAL%", String.valueOf(String.valueOf((new StringBuffer("\"")).append(as[1]).append("\""))));
                            escribeVentana(as1[2], s20, colorConsola, true);
                            break;

                        case 1010: 
                            String s21 = (String)padre.tablaIdioma.get("expPo2");
                            s21 = padre.reemplazarTexto(s21, "%NOMBRE_USUARIO%", s3);
                            s21 = padre.reemplazarTexto(s21, "%MOTIVO%", s);
                            padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf(s21)).concat("\n"), colorConsola, true);
                            cerrarVentanas(false);
                            flag = true;
                            break;

                        case 1013: 
                            String s27 = "";
                            String s28 = "";
                            s4 = padre.reemplazarTexto(s4, "(abcde1234 por ejemplo)", ",abcde1234 por ejemplo,");
                            if(s4.indexOf("Kill") != -1)
                            {
                                int i9 = s4.lastIndexOf("!");
                                int j9 = s4.lastIndexOf("(");
                                int k8 = Math.max(i9, j9);
                                int l8;
                                if(i9 > j9)
                                    l8 = -1;
                                else
                                    l8 = s4.lastIndexOf("(", k8 - 2);
                                if(k8 != -1)
                                {
                                    int k9 = s4.length();
                                    if(l8 != -1)
                                        k9--;
                                    s28 = "(".concat(String.valueOf(String.valueOf(s4.substring(k8 + 1, k9))));
                                }
                                if(l8 != -1)
                                    s27 = s4.substring(l8 + 1, k8);
                                String s22 = (String)padre.tablaIdioma.get("expuls");
                                s22 = padre.reemplazarTexto(s22, "%NOMBRE_USUARIO%", s3);
                                String s30 = s22;
                                if(s27 != "")
                                {
                                    String s23 = (String)padre.tablaIdioma.get("por");
                                    s23 = padre.reemplazarTexto(s23, "%NOMBRE_USUARIO%", s27);
                                    s30 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s30)))).append(s23).append(" ")));
                                }
                                if(s28.indexOf(menRobot1) != -1)
                                    s28 = (String)padre.tablaIdioma.get("motiv1");
                                else
                                if(s28.indexOf(menRobot2) != -1)
                                    s28 = (String)padre.tablaIdioma.get("motiv2");
                                else
                                if(s28.indexOf(menRobot3) != -1)
                                    s28 = (String)padre.tablaIdioma.get("motiv3");
                                else
                                if(s28.indexOf(menRobot4) != -1)
                                    s28 = (String)padre.tablaIdioma.get("motiv4");
                                else
                                if(s28.indexOf(menRobot5) != -1)
                                    s28 = (String)padre.tablaIdioma.get("motiv5");
                                s30 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s30)))).append(" (").append(s28).append(")\n")));
                                quita(s3, s30);
                            } else
                            {
                                quita(s3);
                            }
                            break;

                        case 1016: 
                            if(s3.trim().compareTo("") != 0)
                            {
                                String s7 = as[1];
                                for(int l2 = 2; l2 < i3; l2++)
                                    s7 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s7)))).append(":").append(as[l2])));

                                if(as1[2].compareTo(MiNick) != 0)
                                    escribeVentana(as1[2], String.valueOf(String.valueOf((new StringBuffer("<")).append(s3).append("> ").append(s7))), colorMensajesOtros, true);
                                else
                                    escribeVentana(s3, String.valueOf(String.valueOf((new StringBuffer("<")).append(s3).append("> ").append(s7))), colorMensajesOtros, true);
                            }
                            break;

                        case 1018: 
                            String s29 = as[1].trim();
                            if(s29.indexOf(menRobot1) != -1)
                                s29 = (String)padre.tablaIdioma.get("motiv1");
                            else
                            if(s29.indexOf(menRobot2) != -1)
                                s29 = (String)padre.tablaIdioma.get("motiv2");
                            else
                            if(s29.indexOf(menRobot3) != -1)
                                s29 = (String)padre.tablaIdioma.get("motiv3");
                            else
                            if(s29.indexOf(menRobot4) != -1)
                                s29 = (String)padre.tablaIdioma.get("motiv4");
                            else
                            if(s29.indexOf(menRobot5) != -1)
                                s29 = (String)padre.tablaIdioma.get("motiv5");
                            if(as1[3].compareTo(MiNick) != 0)
                            {
                                String s24 = (String)padre.tablaIdioma.get("expCan");
                                s24 = padre.reemplazarTexto(s24, "%NOMBRE_USUARIO%", s3);
                                s24 = padre.reemplazarTexto(s24, "%NOMBRE_USUARIO1%", as1[3]);
                                s24 = padre.reemplazarTexto(s24, "%NOMBRE_CANAL%", as1[2]);
                                escribeVentana(as1[2], String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s24)))).append(" (").append(s3.compareTo(as[1]) == 0 ? (String)padre.tablaIdioma.get("sinCom") : s29).append(")"))), colorConsola, true);
                            }
                            expulsar(as1[2], as1[3], s3, String.valueOf(String.valueOf((new StringBuffer(" (")).append(s3.compareTo(as[1]) == 0 ? (String)padre.tablaIdioma.get("sinCom") : s29).append(")"))));
                            break;

                        case 1019: 
                            if(j3 == 3)
                                break;
                            String s25 = (String)padre.tablaIdioma.get("camMod");
                            s25 = padre.reemplazarTexto(s25, "%NOMBRE_USUARIO%", s3);
                            escribeVentana(as1[2], String.valueOf(s25) + String.valueOf(as[0].substring(as[0].indexOf("MODE") + 4)), colorConsola, true);
                            if(as1[3].indexOf("o") != -1)
                                operador(as1[2], as1[j3 - 1], as1[3].indexOf("-") == -1);
                            break;
                        }
                    }
                    catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception) { }
                }
            } while(true);
            cli.disconnect();
            if(!flag)
            {
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("rotCon"))).concat("\n"), colorConsola, true);
                System.out.println("Se perdio la conexion con el servidor");
            }
            cerrarVentanas(false);
            return;
        }
        catch(UnknownHostException _ex)
        {
            if(!flag)
            {
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("rotCon"))).concat("\n"), colorConsola, true);
                System.out.println("Fallo del servidor. Host desconocido");
            }
            cerrarVentanas(false);
            return;
        }
        catch(SocketException _ex)
        {
            if(!flag)
            {
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("rotCon"))).concat("\n"), colorConsola, true);
                System.out.println("Fallo del servidor. No se puede conectar");
            }
            cerrarVentanas(false);
            return;
        }
        catch(IOException _ex)
        {
            if(!flag)
            {
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("rotCon"))).concat("\n"), colorConsola, true);
                System.out.println("Fallo del servidor. Se a cortado la conexi\363n");
            }
            cerrarVentanas(false);
            return;
        }
        catch(Exception exception)
        {
            if(!flag)
            {
                padre.getMarcoChat().getTextoConv().append(String.valueOf(String.valueOf((String)padre.tablaIdioma.get("rotCon"))).concat("\n"), colorConsola, true);
                System.out.println("Fallo en la conexion con el servidor");
                exception.printStackTrace();
            }
        }
        cerrarVentanas(false);
    }

    public void unirCanal(String s)
    {
        try
        {
            men.setCommand("JOIN");
            String as[] = {
                "#".concat(String.valueOf(String.valueOf(s)))
            };
            Datos datos = new Datos(as, "");
            men.setParameters(datos);
            cli.sendMessage(men);
            return;
        }
        catch(Exception exception)
        {
            System.out.println("Excepcion: ".concat(String.valueOf(String.valueOf(exception))));
        }
    }

    public int posicionMarco(int i)
    {
        for(java.util.Enumeration enumeration = ventanas.keys(); enumeration.hasMoreElements();)
        {
            Registro registro = new Registro((Registro)ventanas.get(enumeration.nextElement()));
            if(i == registro.refer)
                return registro.posicion;
        }

        return 0;
    }

    public int posicionMarco()
    {
        return posicionMarco(VentanaActual);
    }

    public String nombreMarco()
    {
        for(java.util.Enumeration enumeration = ventanas.keys(); enumeration.hasMoreElements();)
        {
            Registro registro = new Registro((Registro)ventanas.get(enumeration.nextElement()));
            if(VentanaActual == registro.refer)
                return registro.nReal;
        }

        return null;
    }

    public boolean esCanal()
    {
        String s = nombreMarco();
        return s.substring(0, 1).compareTo("#") == 0 || s.substring(0, 1).compareTo("&") == 0;
    }

    public void asignaVentanaActual(String s)
    {
        boolean flag = false;
        java.util.Enumeration enumeration = ventanas.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            Registro registro = new Registro((Registro)ventanas.get(enumeration.nextElement()));
            if(s.compareTo(registro.nReal) == 0)
            {
                VentanaActual = registro.refer;
                flag = true;
            }
        } while(true);
        if(s.compareTo("Consola") == 0)
            VentanaActual = 0;
        if(!flag)
            VentanaActual = 0;
    }

    public void asignaVentanaRealUsuario(String s)
    {
        boolean flag = false;
        java.util.Enumeration enumeration = ventanas.keys();
        do
        {
            if(!enumeration.hasMoreElements())
                break;
            Registro registro = new Registro((Registro)ventanas.get(enumeration.nextElement()));
            if(s.compareTo(registro.nReal) == 0)
            {
                VentanaRealUsuario = registro.refer;
                flag = true;
            }
        } while(true);
        if(s.compareTo("Consola") == 0)
            VentanaRealUsuario = 0;
        if(!flag)
            VentanaRealUsuario = 0;
    }

    private void generaAreasyCanales()
    {
        if(canales.length == 0)
        {
            AreasyCanales = new String[1][1];
            AreasyCanales[0][0] = "";
            return;
        }
        AreasyCanales = new String[canales.length][canales.length + 1];
        for(int i = 0; i < canales.length; i++)
        {
            for(int j = 0; j < canales.length + 1; j++)
                AreasyCanales[i][j] = new String("");

        }

        String s = (String)padre.tablaIdioma.get("sinCla");
        AreasyCanales[0][0] = new String(s);
label0:
        for(int k = 0; k < canales.length; k++)
        {
            java.util.StringTokenizer stringtokenizer = new java.util.StringTokenizer(canales[k]);
            String s1 = new String();
            if(stringtokenizer.countTokens() > 2)
            {
                String s2 = stringtokenizer.nextToken();
                s2 = stringtokenizer.nextToken();
                s2 = stringtokenizer.nextToken();
                boolean flag = false;
label1:
                for(int i1 = 0; i1 < canales.length && !flag; i1++)
                {
                    if(AreasyCanales[i1][0].compareTo(s2) != 0)
                        continue;
                    int j1 = 1;
                    do
                    {
                        if(j1 >= canales.length + 1)
                            continue label1;
                        if(AreasyCanales[i1][j1].compareTo("") == 0)
                        {
                            AreasyCanales[i1][j1] = canales[k];
                            flag = true;
                            continue label1;
                        }
                        j1++;
                    } while(true);
                }

                if(flag)
                    continue;
                int k1 = 1;
                do
                {
                    if(k1 >= canales.length)
                        continue label0;
                    if(AreasyCanales[k1][0].compareTo("") == 0)
                    {
                        AreasyCanales[k1][0] = s2;
                        AreasyCanales[k1][1] = canales[k];
                        continue label0;
                    }
                    k1++;
                } while(true);
            }
            int l = 1;
            do
            {
                if(l >= canales.length + 1)
                    continue label0;
                if(AreasyCanales[0][l].compareTo("") == 0)
                {
                    AreasyCanales[0][l] = canales[k];
                    continue label0;
                }
                l++;
            } while(true);
        }

    }

    private String ordenaListaUsuarios(String s)
    {
        java.util.StringTokenizer stringtokenizer = new java.util.StringTokenizer(s);
        int i = stringtokenizer.countTokens();
        String as[] = new String[i];
        for(int j = 0; j < i; j++)
            as[j] = stringtokenizer.nextToken();

        String s1 = new String();
        for(int k = 0; k < i; k++)
        {
            for(int l = 0; l < i; l++)
                if(as[k].toLowerCase().compareTo(as[l].toLowerCase()) < 0)
                {
                    String s2 = as[k];
                    as[k] = as[l];
                    as[l] = s2;
                }

        }

        String s3 = as[0];
        for(int i1 = 1; i1 < as.length; i1++)
            s3 = String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(s3)))).append(" ").append(as[i1])));

        return s3;
    }

    Color colorConsola;
    Color colorMensajesOtros;
    Color colorMensajesPropios;
    boolean buscando;
    boolean listlanzado;
    public MarcoVentanaIrcCanal Chats[];
    public MarcoVentanaIrcPrivada ChatsPrivada[];
    TextArea Comando;
    ConexionIrc cli;
    ContenidoSencillo men;
    public CajaGraficaScroll Textos[];
    public List LUsuarios[];
    Principal padre;
    int VentanaActual;
    int CanalActual;
    int VentanaRealUsuario;
    String MiNick;
    String can;
    String listaTemporal;
    java.util.Hashtable ht;
    java.util.Hashtable ventanas;
    boolean wholleno;
    public boolean ignoreActivo;
    public String listadoIgnorados[];
    public String canales[];
    public String AreasyCanales[][];
    public static String menRobot1 = "El nickname que utilizas ha sido registrado y bloqueado para el uso. Intenta acceder con otro nickname e identificarte para usar ese nick.";
    public static String menRobot2 = "El nickname que intentas utilizar est\341 registrado y bloqueado para el uso. Intenta acceder al servicio con un nickname extra\361o ,abcde1234 por ejemplo, y una vez dentro selecciona un nickname que no est\351 bloqueado.";
    public static String menRobot3 = "No hagas tonterias...";
    public static String menRobot4 = "No presente en las listas de acceso";
    public static String menRobot5 = "Acceso temporalmente cancelado por un operador del canal.";

}
