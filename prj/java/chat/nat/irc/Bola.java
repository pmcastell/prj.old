// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Bola.java

package nat.irc;


class Bola
{

    public Bola()
    {
    }

    public Bola(boolean flag)
    {
        esFinalLinea = flag;
        ancho = 0;
        alto = 0;
    }

    public Bola(boolean flag, int i, int j)
    {
        esFinalLinea = flag;
        ancho = i;
        alto = j;
    }

    public void setSize(int i, int j)
    {
        ancho = i;
        alto = j;
    }

    public boolean esFinalLinea;
    public int ancho;
    public int alto;
}
