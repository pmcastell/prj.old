// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Conexion.java

package nat.irc;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class Conexion
{

    public Conexion(String s, int i)
        throws IOException, UnknownHostException
    {
        setSocket(new Socket(s, i));
        setInput(new BufferedReader(new InputStreamReader(socket.getInputStream())));
        setOutput(new PrintWriter(new OutputStreamWriter(socket.getOutputStream())));
    }

    public void disconnect()
        throws IOException
    {
        getOutput().flush();
        getSocket().close();
    }

    public BufferedReader getInput()
    {
        return input;
    }

    public PrintWriter getOutput()
    {
        return output;
    }

    public String getResponse()
        throws IOException
    {
        String s = "";
        s = getInput().readLine();
        return s;
    }

    public Socket getSocket()
    {
        return socket;
    }

    public void sendCommand(String s)
        throws IOException
    {
        getOutput().println(s);
        getOutput().flush();
    }

    public void setInput(BufferedReader bufferedreader)
    {
        input = bufferedreader;
    }

    public void setOutput(PrintWriter printwriter)
    {
        output = printwriter;
    }

    public void setSocket(Socket socket1)
    {
        socket = socket1;
    }

    public String toString()
    {
        return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getClass().getName())))).append(" to ").append(getSocket().getInetAddress()).append(":").append(getSocket().getPort())));
    }

    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;
}
