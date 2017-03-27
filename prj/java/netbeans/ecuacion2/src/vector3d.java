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
public class vector3d {
    private double x,y,z;
    
    /** Creates a new instance of vector3d */
    public vector3d() {
    }
    public vector3d(double x, double y, double z) {
        this.x=x;
        this.y=y;
        this.z=z;
    }
    public vector3d suma(vector3d v) {
        return new vector3d(x+v.x,y+v.y,z+v.z);
    }
    public vector3d resta(vector3d v) {
        return new vector3d(x-v.x,y-v.y,z-v.z);
    }
    public double modulo() {
        return Math.sqrt(x*x+y*y+z*z);
    }
    public double productoEscalar(vector3d v) {
        return x*v.x+y*v.y+z*v.z;
    }
    public vector3d productoVectorial(vector3d v) {
        //i    j    k
        //x    y    z
        //v.x  v.y  v.z
        return new vector3d(y*v.z-z*v.y,z*v.x-x*v.z,x*v.y-y*v.x);
    }
    public vector3d vectorUnitario() {
        return new vector3d(x/modulo(),y/modulo(),z/modulo());
    }
    public vector3d productoPorEscalar(double k) {
        return new vector3d(k*x,k*y,k*z);
    }
    public double anguloRadianes(vector3d v) {
        return productoEscalar(v)/(this.modulo()*v.modulo());
    }
    public double anguloGrados(vector3d v) {
        //pi    vale    180
        //rad   vale    grados   ---> grados= rad*180/pi 
        return anguloRadianes(v)*180/Math.PI;
    }
}
