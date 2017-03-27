/******************************************************************************/
/******************         booleano.java       *******************************/
/******************************************************************************/
package mieval.tipo;
public class booleano {
    boolean b;

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
    public booleano()
    {
        b=false;
    }
    public booleano(booleano b)
    {
        this.b=b.b;
    }
    public booleano(int v)
    {
        if (v==0) {
            b=false;
        } else {
            b=true;
        }
    }
    public booleano(boolean b)
    {
        this.b=b;
    }

/*** Convierte a cadena a éste booleano                              **********/
/*** Parámetros: ninguno                                             **********/
/*** Deuelve: una cadena representando al valor actual: True o False **********/
    public String toString()
    {
        if (b) {
            return("True");
        } else {
            return("False");
        }
    }

/*** Convierte a cadena a doble true-> 1.0, false->0.0              **********/
/*** Parámetros: ninguno                                             **********/
    public double doubleValue()
    {
        if (b) {
            return(1.0);
        } else {
            return(0.0);
        }
    }

/*** Devuelve un 'boolean' representando el valor actual de éste     **********/
/*** Parámetros: ninguno                                             **********/
    public boolean bolValue()
    {
        return b;
    }
}