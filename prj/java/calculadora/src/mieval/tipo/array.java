/******************************************************************************/
/******************         array.java          *******************************/
/******************************************************************************/
package mieval.tipo;
import java.util.Vector;
import java.lang.Double;
import java.lang.Integer;
import java.lang.String;
import mieval.tipo.variable;
import mieval.SyntaxErrorException;
public class array {
    private int num_dimensiones;
    private int dimensiones[];
    private int coeficientes[];
    private variable valores[];
    private int contador;
    private variable r[];

/******************************************************************************/
/*** Constructores                                                   **********/
/******************************************************************************/
    public array(int dimensiones[], variable valores[])
    {
        inicializa(dimensiones, valores);
    }
    public array(Vector v, variable valores[])
    {
        this.num_dimensiones=v.size();
        this.dimensiones=new int[num_dimensiones];
        for(int i=0;i<v.size();i++) {
            this.dimensiones[i]=((variable)v.elementAt(i)).intValue();
        }
        this.coeficientes=new int[this.num_dimensiones];
        calcula_coeficientes();
        if (valores==null) {
            int tam=this.coeficientes[0]*this.dimensiones[0];
            this.valores=new variable[tam];
            for(int i=0; i<tam; i++) {
               this.valores[i]=new variable("");
            }
        } else {
            this.valores=valores;
        }
    }
    public array(int dimensiones[], int[] valores) {
       variable valores_aux[];

       valores_aux=new variable[valores.length];
       for(int i=0;i<valores.length;i++) {
           valores_aux[i]= new variable("",'D',new miDouble(valores[i]));
       }
       inicializa(dimensiones, (variable []) valores_aux);
    }
    public array(double[] valores) {
       variable valores_aux[];
       int dimensiones[]={valores.length};

       valores_aux=new variable[valores.length];
       for(int i=0;i<valores.length;i++) {
           valores_aux[i]= new variable("",'D',new miDouble(valores[i]));
       }
       inicializa(dimensiones, (variable []) valores_aux);
    }    
    public array(double[][] valores) {
       variable valores_aux[];
       int dimensiones[]={valores.length,valores[0].length};

       valores_aux=new variable[valores.length*valores[0].length];
       for(int i=0,k=0;i<valores.length;i++) {
          for(int j=0;j<valores[0].length;j++,k++) {
              valores_aux[k]= new variable("",'D',new miDouble(valores[i][j]));
          }
       }
       inicializa(dimensiones, (variable []) valores_aux);
    }
    public array(Vector v)
    {
        contador=0;
        r= new variable[calcula_tam(v)];
        linealiza(v);
        this.valores=r;
    }

    public array(array m)
    {
        this.num_dimensiones=m.num_dimensiones;
        this.dimensiones=new int[m.num_dimensiones];
        this.coeficientes=new int[m.num_dimensiones];
        for(int i=0;i<m.num_dimensiones;i++) {
            this.dimensiones[i]=m.dimensiones[i];
            this.coeficientes[i]=m.coeficientes[i];
        }
        this.valores=new variable[m.valores.length];
        for(int i=0;i<m.valores.length;i++) {
            asigna(i,m.elemento(i));
        }
    }

/*** Calcula los coeficientes para el acceso a un elemento cualquiera**********/
/***     de la matriz                                                **********/
/*** Parámetros: nada                                                **********/
/*** Deuelve: nada                                                   **********/
    void calcula_coeficientes()
    {
        int producto,i;

        for(i=0;i<dimensiones.length;i++) {
            producto=1;
            for(int j=i+1;j<dimensiones.length;j++) {
                producto*=dimensiones[j];
            }
            coeficientes[i]=producto;
       }
    }
/*** Nos dice el n�mero total de elementos que tiene la matriz       **********/
/*** Par�metros: ninguno                                             **********/
    public int len()
    {
       if (num_dimensiones>0) {
          int r=dimensiones[0];
          for(int i=1; i<num_dimensiones; i++) {
             r*=dimensiones[i];
          }
          return r;
       }
       return 0;
    }

/*** Inicializaci�n de variables internas                            **********/
/*** Par�metros: dimensiones-> matriz con las dimensiones del array  **********/
/***             valores-> valores iniciales de los elementos del array *******/
/*** Deuelve: nada                                                   **********/
    public void inicializa(int [] dimensiones, variable [] valores)
    {
       this.num_dimensiones=dimensiones.length;
       this.dimensiones=dimensiones;
       this.coeficientes=new int[dimensiones.length];
       calcula_coeficientes();
       if (valores==null) {
          this.valores=new variable[this.coeficientes[0]*this.dimensiones[0]];
       } else {
          this.valores=valores;
       }
    }

/*** Calcula el tama�o necesario para guardar de forma lineal una    **********/
/***     matriz                                                      **********/
/*** Par�metros: v-> lista de listas de listas ... representando a   **********/
/***     una matriz de cualquier n�mero de dimensiones               **********/
/*** Deuelve: el tama�o que tendr� la matriz linealizada             **********/
    private int calcula_tam(Vector v)
    {
        int res=1;
        Vector a, dim=new Vector(),coef=new Vector();
        a=v;
        this.num_dimensiones=0;
        while(true) {
            res*=a.size();
            dim.addElement((Object) new Integer(a.size()));
            coef.addElement((Object) new Integer(1));
            for(int i=0;i<this.num_dimensiones; i++) {
                    coef.setElementAt(new Integer(((Integer) coef.elementAt(i)).
                                                        intValue()*a.size()),i);
            }
            this.num_dimensiones++;
            if ((a.elementAt(0)) instanceof Vector) {
                a=(Vector) a.elementAt(0);
            } else {
                break;
            }
        }
        this.dimensiones=new int[this.num_dimensiones];
        this.coeficientes=new int[this.num_dimensiones];
        for(int i=0;i<this.num_dimensiones;i++) {
            this.dimensiones[i]=((Integer) dim.elementAt(i)).intValue();
            this.coeficientes[i]=((Integer) coef.elementAt(i)).intValue();
        }
        return res;
    }

/*** Linealiza una matriz multidimensional representada por una lista**********/
/***     de listas de listas....de listas                            **********/
/*** Par�metros: v-> lista de listas de listas...de listas           **********/
/*** Deuelve: nada                                                   **********/
    private void linealiza(Vector v)
    {
        for(int i=0;i<v.size();i++) {
            if (v.elementAt(i) instanceof Vector) {
                linealiza((Vector) v.elementAt(i));
            } else {
                for (int j=0;j<v.size();j++) {
                    r[contador++]=(variable) v.elementAt(j);
                }
                return;
            }
        }
    }

/*** Devuelve el elemento i-�simo de la matriz linealizada           **********/
/*** Par�metros: ind-> �ndice del elemento a devolver                **********/
    public variable elemento(int ind)
    {
        return(valores[ind]);
    }

/*** Realiza la transformaci�n lineal de una lista de �ndicesa un    **********/
/***     valor entero que representa la posici�n lineal del elemento **********/
/*** Par�metros: v-> lista de �ndices                                **********/
/*** Deuelve: el valor que representa la posici�n correspondiente    **********/
    int indice(Vector v) throws SyntaxErrorException
    {
        int res=0,i;
        if (v.size()!=dimensiones.length) {
              throw new SyntaxErrorException("N� de indices no coincide con"+
                                                          " N� de dimensiones");
        }
        res=0;
        for(i=0;i<v.size();i++) {
            if (((variable) v.elementAt(i)).intValue()>=dimensiones[i]) {
                throw new SyntaxErrorException("Indice fuera de rango");
            }
            res+=(((variable) v.elementAt(i)).intValue())*coeficientes[i];
        }
        return res;
    }

/*** Idem anterior                                                   **********/
/*** Par�metros: ind-> matriz que representa la lista de �ndices     **********/
    int indice(int ind[]) throws SyntaxErrorException
    {
        int res=0,i;
        if (ind.length!=dimensiones.length) {
              throw new SyntaxErrorException("N� de Indices no coincide con"+
                                                          " N� de dimensioens");
        }
        res=0;
        for(i=0;i<ind.length;i++) {
            if (ind[i]>=dimensiones[i]) {
               throw new SyntaxErrorException("Indice fuera de rango");
            }
            res+=ind[i]*coeficientes[i];
        }
        return res;
    }

/*** Asigna un valor al elemento i-�simo de la matriz lineal         **********/
/*** Par�metros: i-> posici�n del elemento a asignar                 **********/
/***             valor-> nuevo valor de dicho elemento               **********/
/*** Deuelve: nada                                                   **********/
    private void asigna(int i,variable valor)
    {
        if (valor!=null) {
            this.valores[i]=valor.copia();
            this.valores[i].nombre="";
        } else {
            this.valores[i]=null;
        }
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Par�metros: i-> lista de �ndices                                **********/
/***             valor-> valor num�rico que se asignar�              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(int []i,double valor) throws SyntaxErrorException
    {
        valores[indice(i)]=new variable("",'D',new miDouble(valor));
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Par�metros: v-> lista de �ndices                                **********/
/***             valor-> valor num�rico que se asignar�              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v,Double valor) throws SyntaxErrorException
    {
        asigna(v,valor.doubleValue());
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Par�metros: v-> lista de �ndices                                **********/
/***             valor-> valor num�rico que se asignar�              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v, variable valor) throws SyntaxErrorException
    {
        asigna(indice(v),valor);
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Par�metros: i-> lista de �ndices                                **********/
/***             valor-> valor num�rico que se asignar�              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v,double valor) throws SyntaxErrorException
    {
        valores[indice(v)]=new variable("",'D', new miDouble(valor));
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Par�metros: i-> lista de �ndices                                **********/
/***             valor-> valor num�rico que se asignar�              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(int i[], variable valor) throws SyntaxErrorException
    {
        asigna(indice(i), valor);
    }

/*** Convierte a cadena a la matriz                                  **********/
/*** Par�metros: ninguno                                             **********/
/*** Deuelve: la cadena representando a la matriz                    **********/
    public String toString()
    {
        contador=0;
        return _toString(0);
    }

    String _toString(int dim_actual)
    {
        String r=new String("{");

        if (dim_actual==this.num_dimensiones-1) {
            for(int i=0;i<dimensiones[dim_actual];i++) {
                if (valores[contador]!=null) {
                    r+=valores[contador].StringValue();
                }
                contador++;
                if (i!=dimensiones[dim_actual]-1) {
                    r+=",";
                }
            }
        } else if(dim_actual<this.num_dimensiones-1) {
            for(int i=0;i<dimensiones[dim_actual];i++) {
                r+=_toString(dim_actual+1);
                if (i!=dimensiones[dim_actual]-1) {
                    r+=",";
                }
            }
        }
        r+="}";
        return r;
    }

/*** Devuelve el elemento i-�simo de la matriz                       **********/
/*** Par�metros: i-> lista de �ndices                                **********/
/*** Deuelve: la variable situada en dicha posici�n                  **********/
    public variable elemento(int []i) throws SyntaxErrorException
    {
        return(valores[indice(i)]);
    }

/*** Devuelve el elemento i-�simo de la matriz                       **********/
/*** Par�metros: v-> lista de �ndices                                **********/
/*** Deuelve: la variable situada en dicha posici�n                  **********/
    public variable elemento(Vector v) throws SyntaxErrorException
    {
        return(valores[indice(v)]);
    }

/*** Devuelve el n�mero de dimensiones de la matriz                  **********/
/*** Par�metros: ninguno                                          **********/
    public int getNumDimensiones()
    {
       return num_dimensiones;
    }

/*** Devuelve el tama�o de la dimensi�n i-�sima de la matriz         **********/
/*** Par�metros: i-> dimensi�n que se desea conocer                  **********/
    public int getDimension(int i)
    {
       return dimensiones[i];
    }
}



