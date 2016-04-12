// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Registro.java

package nat.irc;


public class Registro
{

    public Registro()
    {
    }

    public Registro(String s, int i, int j)
    {
        nReal = s;
        posicion = i;
        refer = j;
    }

    public Registro(Registro registro)
    {
        nReal = registro.nReal;
        posicion = registro.posicion;
        refer = registro.refer;
    }

    String nReal;
    int posicion;
    int refer;
}
