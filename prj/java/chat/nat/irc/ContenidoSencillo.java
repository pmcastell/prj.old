// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ContenidoSencillo.java

package nat.irc;


// Referenced classes of package nat.irc:
//            Contenido

public class ContenidoSencillo extends Contenido
{

    public String toString()
    {
        return String.valueOf(String.valueOf((new StringBuffer(String.valueOf(String.valueOf(getCommand())))).append(' ').append(getParameters()).append('\r').append('\n')));
    }

    public ContenidoSencillo()
    {
    }
}
