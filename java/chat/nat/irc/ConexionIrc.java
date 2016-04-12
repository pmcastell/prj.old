// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ConexionIrc.java

package nat.irc;

import java.io.*;
import java.net.UnknownHostException;

// Referenced classes of package nat.irc:
//            Conexion, Contenido, ContenidoSencillo

public class ConexionIrc extends Conexion
{

    public ConexionIrc(String s, int i)
        throws IOException, UnknownHostException
    {
        super(s, i);
    }

    public void connect(String s, String s1, String s2, String s3, String s4, String s5)
        throws IOException
    {
        sendCommand(String.valueOf(String.valueOf((new StringBuffer("USER ")).append(s).append(" ").append(s1).append(" ").append(s2).append(" :").append(s3))));
        sendCommand("NICK ".concat(String.valueOf(String.valueOf(s4))));
        if(s5 != "")
            sendCommand("PASS ".concat(String.valueOf(String.valueOf(s5))));
    }

    public String getResponse()
        throws IOException
    {
        String s = getInput().readLine();
        return s;
    }

    public void sendCommand(String s)
        throws IOException
    {
        getOutput().println(s);
        getOutput().flush();
    }

    public void sendMessage(Contenido contenido)
        throws IOException
    {
        sendCommand(contenido.toString());
    }

    public ConexionIrc c;
    public ContenidoSencillo m;
}
