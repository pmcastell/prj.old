/*
 * vector2.java
 *
 * Created on 14 de mayo de 2006, 14:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */
public class vector2 {
    double x,y;
    
    /** Creates a new instance of vector2 */
    public vector2() {
    }
    public vector2(double x, double y) {
        this.x=x; this.y=y;
    }
    public vector2(String s) {
        this.x=Double.parseDouble( s.substring( s.indexOf('(')+1 , s.indexOf(',') ) );
        this.y=Double.parseDouble( s.substring( s.indexOf(',')+1 , s.indexOf(')') ) );
    }
    public void setX(double x) { this.x=x; }
    public double getX() { return x; }
    public void setY(double y) { this.y=y; }
    public double getY() { return y; }
    public double modulo() { return Math.sqrt(x*x+y*y); }
    public vector2 prodEscalar(vector2 v) {
        return new vector2(x*v.x,y*v.y);
    }
    public vector2 suma(vector2 v) { return new vector2(x+v.x,y+v.y); }
    public vector2 resta(vector2 v) { return new vector2(x-v.x,y-v.y); }
    public String toString() { return "(" + x + "," + y + ")"; }
    
}
