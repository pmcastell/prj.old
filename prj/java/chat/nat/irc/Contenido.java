// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Contenido.java

package nat.irc;


// Referenced classes of package nat.irc:
//            ExcepcionNumerica, Datos

public abstract class Contenido
{

    public String getCommand()
    {
        return command;
    }

    public int getCommandAsInt()
        throws ExcepcionNumerica
    {
        if(isCommandNumeric())
            return Integer.parseInt(getCommand());
        else
            throw new ExcepcionNumerica(getCommand());
    }

    public Datos getParameters()
    {
        return parametros;
    }

    public boolean isCommandNumeric()
    {
        boolean flag = false;
        if(getCommand().length() == 3 && Character.isDigit(getCommand().charAt(0)) & Character.isDigit(getCommand().charAt(1)) & Character.isDigit(getCommand().charAt(2)))
            flag = true;
        return flag;
    }

    public int length()
    {
        return toString().length();
    }

    public void setCommand(int i)
    {
        if((i >= 0) & (i <= 999))
            command = String.valueOf(i);
    }

    public void setCommand(String s)
    {
        command = s;
    }

    public void setParameters(String s)
    {
        parametros = new Datos(s);
    }

    public void setParameters(Datos datos)
    {
        parametros = datos;
    }

    public abstract String toString();

    public Contenido()
    {
        command = "";
    }

    protected String command;
    protected Datos parametros;
}
