/*
 * vector3d.java
 *
 * Created on 7 de mayo de 2006, 20:17
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */
public class vector {
    private double m[];
    
    /** Creates a new instance of vector3d */
    public vector(int n) {
        m=new double[n];
    }
    public vector(double m[]) {
        this.m=m;
    }
    public vector copia() {
        vector nuevo=new vector(dimension());
        for(int i=0; i<dimension(); i++) {
            nuevo.m[i]=m[i];
        }
        return nuevo;
    }
    public int dimension() {
        return m.length;
    }
    public vector suma(vector v) {
        if (dimension()==v.dimension()) {
            vector w=new vector(dimension());
            for(int i=0; i<dimension(); i++) {
                w.m[i]=m[i]+v.m[i];
            }
            return w;
        }
        return null;
    }
    public vector resta(vector v) {
        if (dimension()==v.dimension()) {
            vector w=new vector(dimension());
            for(int i=0; i<dimension(); i++) {
                w.m[i]=m[i]-v.m[i];
            }
            return w;
        }
        return null;
    }
    public double modulo() {
        double res=0.;
        for(int i=0; i<dimension(); i++) {
            res+=m[i]*m[i];
        }
        return Math.sqrt(res);
    }
    public double productoEscalar(vector v) {
        if (dimension()==v.dimension()) {
            double res=0.;
            for(int i=0; i<dimension(); i++) {
                res+=m[i]*v.m[i];
            }
            return res;
        }
        return Double.NaN;
    }
    public vector productoVectorial(vector v) {
        //i    j    k
        //x    y    z
        //v.x  v.y  v.z
        //return new vector(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
        return null;
    }
    public vector vectorUnitario() {
        vector v=this.copia();
        double modulo=this.modulo();
        for(int i=0; i<dimension(); i++) {
            v.m[i]/=modulo;
        }
        return v;
    }
    public vector productoPorEscalar(double k) {
        vector v=new vector(this.dimension());
        for(int i=0; i<dimension(); i++) {
            m[i]=k*m[i];
        }
        return v;
    }
    public double anguloRadianes(vector v) {
        return productoEscalar(v)/(this.modulo()*v.modulo());
    }
    public double anguloGrados(vector v) {
        //pi    vale    180
        //rad   vale    grados   ---> grados= rad*180/pi 
        return anguloRadianes(v)*180/Math.PI;
    }
}
