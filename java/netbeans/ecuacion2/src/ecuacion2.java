/*
 * ecuacion2.java
 *
 * Created on 2 de mayo de 2006, 20:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */
import java.io.*;
public class ecuacion2 {
    
    /** Creates a new instance of ecuacion2 */
    private double a,b,c, discr;
    
    public ecuacion2() {
        a=b=c=discr=0;
    }
    private void ponDiscr() {
        discr=Math.sqrt(b*b-4*a*c);
    }
    public ecuacion2(double a, double b, double c) {
        this.a=a;
        this.b=b;
        this.c=c;
        this.ponDiscr();
    }
    public double evalua(double x) {
        return a*x*x+b*x+c;
    }
    public double discriminante() {
        return this.discr;
    }
    public double solucion1() {
         return (-b+discriminante())/(2*a);
    }
    public double solucion2() {
        return (-b-discriminante())/(2*a);
    }
    public double getA() {
        return a;
    }
    public double getB() {
        return b;
    }
    public double getC() {
        return c;
    }
    public void setA(double a) {
        this.a=a;
        this.ponDiscr();
    }
    public void setB(double b) {
        this.b=b;
        this.ponDiscr();
    }
    public void setC(double c) {
        this.c=c;
        this.ponDiscr();
    }
    public void getCoeficientes() {
        
        java.util.Scanner input=new java.util.Scanner(System.in);
        System.out.println("Introduce coefientes a b c: ");
        this.a=input.nextDouble();
        this.b=input.nextDouble();
        this.c=input.nextDouble();
        this.ponDiscr();
    }
}
