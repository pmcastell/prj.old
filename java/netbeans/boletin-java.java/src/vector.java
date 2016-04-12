/*
 * vector.java
 *
 * Created on 14 de mayo de 2006, 19:43
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */

/*
 6.- Escribir una clase en JAVA que represente a un vector de cualquier dimensión. 
 Implementar las siguientes operaciones: suma, resta, producto escalar, módulo, un método 
 para leer las componentes del vector y otro que devuelva una cadena que represente al 
 vector y que tenga el siguiente prototipo:
	public String toString() {
		.......
	}

 */
public class vector {
    private double componentes[];
    
    /** Creates a new instance of vector */
    public vector(int n) {
        componentes=new double[n];
    }
    public vector(double []comp) {
        componentes=new double[comp.length];
        for(int i=0; i<comp.length; i++) {
            this.componentes[i]=comp[i];
        }
    }
    static int comprueba(String s) { // comprueba que la cadena pasada representa a un vector
        int r=0, lon=s.length(); // un ejemplo válido ( 1.0, 2.5 ,   3.75  )
        char c;
        s=s.trim();
        if (s.charAt(0)!='(' || s.charAt(s.length()-1)!=')') {
            return -1; //error esta cadena no representa a un vector
        }
        s=s.substring(1,s.length());
        for(int i=0; i<s.length(); i++) {
            c=s.charAt(i);
            if (!Character.isDigit(c) || c!='.' || c!=',') {
                return -1;
            }
            if (c==',') {
                r++;
            }
        }
        return r+1;
    }
    public static vector creaVector(String s) {
        int numComp=comprueba(s);
        vector v=new vector(numComp);
        String cadenaNumero="";
        
        for(int i=0; i<s.length(); ) {
            if (Character.isDigit(s.charAt(i))) {
                cadenaNumero="";
                while(i<s.length() && Character.isDigit(s.charAt(i))) {
                    cadenaNumero+=s.charAt(i);
                    i++;
                }
                if (s.charAt(i)=='.') {
                    cadenaNumero+=s.charAt(i);
                    i++;
                    while(i<s.length() && Character.isDigit(s.charAt(i))) {
                        cadenaNumero+=s.charAt(i);
                        i++;
                    }
                }
            } else if (s.charAt(i)==',' || s.charAt(i)==')')  {
                v.componentes[v.componentes.length-numComp]=Double.parseDouble(cadenaNumero);
                numComp--; // número de componentes que quedan por leer
                i++;
            } else if (s.charAt(i)==' ' || s.charAt(i)=='\t') {
                i++;
            } else {
                return null; //error en la cadena
            }
        }
        return v;
    }
    public int dimension() {
        return this.componentes.length;
    }
    public vector suma(vector v) {
        if (this.dimension()==v.dimension()) {
            vector r=new vector(this.dimension());
            for(int i=0; i<this.dimension(); i++) {
                r.componentes[i]=this.componentes[i]+v.componentes[i];
            }
            return r;
        }
        return null; // no tenían la misma dimensión no se puede hacer la operación
    }
    public vector resta(vector v) {
        if (this.dimension()==v.dimension()) {
            vector r=new vector(this.dimension());
            for(int i=0; i<this.dimension(); i++) {
                r.componentes[i]=this.componentes[i]-v.componentes[i];
            }
            return r;
        }
        return null;
    }
    public double modulo() {
        double res=0.;
        for(int i=0; i<dimension(); i++) {
            res+=componentes[i]*componentes[i];
        }
        return Math.sqrt(res);
    }
    public void leerCoeficientes() {
        java.util.Scanner t=new java.util.Scanner(System.in);
        System.out.println("Introduce las "+dimension()+" componentes: ");
        for(int i=0; i<dimension(); i++) {
            componentes[i]=t.nextDouble();
        }
    }
    public String toString() {
        String res="(";
        for(int i=0; i<dimension(); i++) {
            res+=this.componentes[i];
            if (i<dimension()-1) {
                res+=',';
            }
        }
        return res;
    }
}
