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
/*** Nos dice el número total de elementos que tiene la matriz       **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Inicialización de variables internas                            **********/
/*** Parámetros: dimensiones-> matriz con las dimensiones del array  **********/
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

/*** Calcula el tamaño necesario para guardar de forma lineal una    **********/
/***     matriz                                                      **********/
/*** Parámetros: v-> lista de listas de listas ... representando a   **********/
/***     una matriz de cualquier número de dimensiones               **********/
/*** Deuelve: el tamaño que tendrá la matriz linealizada             **********/
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
/*** Parámetros: v-> lista de listas de listas...de listas           **********/
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

/*** Devuelve el elemento i-ésimo de la matriz linealizada           **********/
/*** Parámetros: ind-> índice del elemento a devolver                **********/
    public variable elemento(int ind)
    {
        return(valores[ind]);
    }

/*** Realiza la transformación lineal de una lista de índicesa un    **********/
/***     valor entero que representa la posición lineal del elemento **********/
/*** Parámetros: v-> lista de índices                                **********/
/*** Deuelve: el valor que representa la posición correspondiente    **********/
    int indice(Vector v) throws SyntaxErrorException
    {
        int res=0,i;
        if (v.size()!=dimensiones.length) {
              throw new SyntaxErrorException("Nº de indices no coincide con"+
                                                          " Nº de dimensiones");
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
/*** Parámetros: ind-> matriz que representa la lista de índices     **********/
    int indice(int ind[]) throws SyntaxErrorException
    {
        int res=0,i;
        if (ind.length!=dimensiones.length) {
              throw new SyntaxErrorException("Nº de Indices no coincide con"+
                                                          " Nº de dimensioens");
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

/*** Asigna un valor al elemento i-ésimo de la matriz lineal         **********/
/*** Parámetros: i-> posición del elemento a asignar                 **********/
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
/*** Parámetros: i-> lista de índices                                **********/
/***             valor-> valor numérico que se asignará              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(int []i,double valor) throws SyntaxErrorException
    {
        valores[indice(i)]=new variable("",'D',new miDouble(valor));
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Parámetros: v-> lista de índices                                **********/
/***             valor-> valor numérico que se asignará              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v,Double valor) throws SyntaxErrorException
    {
        asigna(v,valor.doubleValue());
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Parámetros: v-> lista de índices                                **********/
/***             valor-> valor numérico que se asignará              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v, variable valor) throws SyntaxErrorException
    {
        asigna(indice(v),valor);
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Parámetros: i-> lista de índices                                **********/
/***             valor-> valor numérico que se asignará              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(Vector v,double valor) throws SyntaxErrorException
    {
        valores[indice(v)]=new variable("",'D', new miDouble(valor));
    }

/*** Idem anterior sobrecargada                                      **********/
/*** Parámetros: i-> lista de índices                                **********/
/***             valor-> valor numérico que se asignará              **********/
/*** Deuelve: nada                                                   **********/
    public void asigna(int i[], variable valor) throws SyntaxErrorException
    {
        asigna(indice(i), valor);
    }

/*** Convierte a cadena a la matriz                                  **********/
/*** Parámetros: ninguno                                             **********/
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

/*** Devuelve el elemento i-ésimo de la matriz                       **********/
/*** Parámetros: i-> lista de índices                                **********/
/*** Deuelve: la variable situada en dicha posición                  **********/
    public variable elemento(int []i) throws SyntaxErrorException
    {
        return(valores[indice(i)]);
    }

/*** Devuelve el elemento i-ésimo de la matriz                       **********/
/*** Parámetros: v-> lista de índices                                **********/
/*** Deuelve: la variable situada en dicha posición                  **********/
    public variable elemento(Vector v) throws SyntaxErrorException
    {
        return(valores[indice(v)]);
    }

/*** Devuelve el número de dimensiones de la matriz                  **********/
/*** Parámetros: ninguno                                          **********/
    public int getNumDimensiones()
    {
       return num_dimensiones;
    }

/*** Devuelve el tamaño de la dimensión i-ésima de la matriz         **********/
/*** Parámetros: i-> dimensión que se desea conocer                  **********/
    public int getDimension(int i)
    {
       return dimensiones[i];
    }
}



