import com.sun.jndi.url.rmi.rmiURLContext;
import java.util.Scanner;
class matriz {
    matriz triang=null;
    protected double m[][];
    
    public matriz(int filas, int cols) {
        m=new double[filas][cols];
    }
    public matriz(double m[][]) {
        this.m=new double[m.length][m[0].length];
        for(int i=0; i<filas(); i++) {
            for(int j=0; j<cols(); j++) {
                this.m[i][j]=m[i][j];
            }
        }
    }
    public matriz(matriz m) {
        this(m.m);
    }
    public int filas() {
        return m.length;
    }
    public int cols() {
        return m[0].length;
    }
    public matriz suma(matriz a) {
        if (filas()==a.filas() && cols()==a.cols()) {
            matriz b=new matriz(filas(),cols());
            for(int i=0; i<filas(); i++) {
                for(int j=0; j<cols(); j++) {
                    b.m[i][j]=m[i][j]+a.m[i][j];
                }
            }
            return b;
        }
        return null;
    }
    public matriz producto(matriz a) {
        if (this.cols()==a.filas()) {
            matriz b=new matriz(filas(),a.cols());
            for(int i=0; i<filas(); i++) {
                for(int j=0; j<a.cols(); j++) {
                    b.m[i][j]=0.0;
                    for(int k=0; k<cols(); k++) {
                        b.m[i][j]+=m[i][k]*a.m[k][j];
                    }
                }
            }
            return b;
        }
        return null;
    }
    public matriz producto(double k) {//producto de esta matriz por un escalar
        matriz res=new matriz(filas(),cols());
        for(int i=0; i<filas(); i++) {
            for(int j=0; j<cols(); j++) {
                res.m[i][j]=k*m[i][j];
            }
        }
        return res;
    }
    
    public matriz copia() {
        return new matriz(this.m);
    }
    private int permutarFilaSinCero(int i) {//busca una fila sin cero y la permutar por la i
        int d, k, numPermutaciones=0;
        for(d=i; d<filas() && d<cols(); d++) {
            for(k=i; k<filas() && m[k][d]==0.0; k++);
            if (k<filas()) {
                _permutarFila(i,k);
                numPermutaciones++;
                if (d!=i) {
                    _permutarColumna(d,i);
                    numPermutaciones++;
                }
                return numPermutaciones;
            }
        }
        return numPermutaciones;
    }
    private int permutarColumSinCero(int i) {//idem columna
        int d, k, numPermutaciones=0;
        for(d=i; d<filas() && d<cols(); d++) {
            for(k=i; k<cols() && m[d][k]==0.0; k++);
            if (k<cols()) {
                _permutarColumna(i,k);
                numPermutaciones++;
                if (d!=i) {
                    _permutarFila(d,k);
                    numPermutaciones++;
                }
                return numPermutaciones;
            } 
        }
        return numPermutaciones;
    }
    /*public matriz triangular() {//triangulariaza sin que varíe el determinante
        triang=copia();
        double pivote;
        for(int i=0; i<filas()-1; i++) {
            for(int f=i+1; f<filas(); f++) {
                pivote=triang.m[f][i]/triang.m[i][i];
                for(int k=0; k<cols(); k++) {
                    triang.m[f][k]-=pivote*triang.m[i][k];
                }
            }
        }
        return triang;
    }*/
    public matriz triangPerm() {//triangulariza sin que varíe el determinante
        if (triang==null) {
            int i,f,k, numPermutaciones=0;
            double pivote;
            triang=new matriz(this.m);

            for(i=0; i<filas()-1; i++) {
                if (triang.m[i][i]==0.0) {
                    int perm=triang.permutarFilaSinCero(i);
                    if (perm>0) {
                        numPermutaciones+=perm;
                    } else if ((perm=triang.permutarColumSinCero(i))>0) {
                        numPermutaciones+=perm;
                    } else {
                        return null;
                    }
                }
                for(f=i+1; f<filas(); f++) {
                    pivote=triang.m[f][i]/triang.m[i][i];
                    for(k=i; k<cols(); k++) {
                        triang.m[f][k]-=pivote*triang.m[i][k];
                    }
                }
            }
            if (numPermutaciones % 2 != 0) {
                for(k=filas()-1;k<cols();k++) {
                    triang.m[filas()-1][k]=-triang.m[filas()-1][k];
                }
            }
        }
        return triang;
    }
    private void _permutarFila(int f1, int f2) {
        double tmp[];
        
        tmp=m[f1];
        m[f1]=m[f2];
        m[f2]=tmp;
    }
    public void permutarFila(int f1, int f2) {
        triang=null;
        _permutarFila(f1,f2);
    }
    private void _permutarColumna(int c1, int c2) {
        double tmp;
        for(int f=0; f<filas(); f++) {
            tmp=m[f][c1];
            m[f][c1]=m[f][c2];
            m[f][c2]=tmp;
        }
    }
    public void permutarColumna(int c1, int c2) {
        triang=null;
        _permutarColumna(c1,c2);
    }
    public double prodDiagPrinc() {
        double res=1;
        for(int i=0; i<filas(); i++) {
            res*=m[i][i];
        }
        return res;
    }
    public double determinante() {
        if (this.filas()==this.cols()) {
            matriz a=this.triangPerm();
            if (a!=null) {
                return a.prodDiagPrinc();
            }
            return .0;
        }
        return Double.NaN;
    }
    public void leerCoeficientes() {
        Scanner t=new Scanner(System.in);
        for(int i=0; i<filas(); i++) {
            System.out.println("Introduce los "+cols()+" elementos de la fila "+i+":\n");
            for(int j=0; j<cols();j++) {
                m[i][j]=t.nextDouble();
            }
        }
        triang=null;
    }
    public matriz traspuesta() {
        matriz res=new matriz(cols(),filas());
        for(int i=0; i<filas(); i++) {
            for(int j=0; j<cols(); j++) {
                res.m[j][i]=m[i][j];
            }
        }
        return res;
    }
    public boolean igual(matriz a) {
        if (a==null || filas()!=a.filas() || cols()!=a.cols()) {
            return false;
        }
        for(int i=0; i<filas(); i++) {
            for(int j=0; j<cols(); j++) {
                if (m[i][j]!=a.m[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    /*boolean esSimetrica() {
     return igual(traspuesta());
     }*/
    public boolean esSimetrica() {
        if (filas()!=cols()) {
            return false;
        }
        for(int i=0; i<filas(); i++) {
            for(int j=i+1; j<cols(); j++) {
                if (m[i][j]!=m[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean esAntiSimetrica() {
        if (filas()!=cols()) {
            return false;
        }
        for(int i=0; i<filas(); i++) {
            if (m[i][i]!=0.0) {
                return false;
            }   
        }
        for(int i=0; i<filas(); i++) {
            for (int j=i+1; j<cols(); j++) {
                if (m[i][j]!=-m[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }
    public String toString() {
        String res="\n";
        for(int i=0; i<filas(); i++) {
            res+='|';
            for(int j=0; j<cols(); j++) {
                res+=" "+m[i][j];
            }
            res+="|\n";
        }
        return res;
    }
    public int rango() {
        double det=1.0;
        int i;
        matriz triang=this.triangPerm();
        for(i=0; i<filas() && triang.m[i][i]!=0.0; ) {
            i++;
        }
        return i;
    }
}
