/*
 * Main.java
 *
 * Created on 2 de mayo de 2006, 19:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
import java.util.Scanner;


/**
 *
 * @author fjcn
 */
public class Main {
    

    /**
     * @param args the command line arguments
     */
    public static void main2(String[] args) {
        ecuacion2 e2=new ecuacion2();
        
        e2.getCoeficientes();
        if (e2.discriminante()==0) {
            System.out.println("Hay una solución: "+e2.solucion1());
        } else if (e2.discriminante()>0) {
            System.out.println("Hay dos soluciones: "+e2.solucion1()+" y "+e2.solucion2()+".");
        } else {
            System.out.println("El discriminante es negativo las soluciones son imaginarias.");
        }
    }
    public static void main(String[] args) {
        matriz a,b,c;
        double m1[][]=new double[][]{{1,2,3,2},{3,1,4,2},{2,0,7,1}},
                m2[][]=new double[][]{{2,3,0,1},{3,1,1,3},{1,4,0,4}},
                m3[][]=new double[][]{{5,8,3,4},{9,3,6,8},{4,8,7,9}};
        a=new matriz(m1);
        b=new matriz(m2);
        c=new matriz(m3);
        boolean r=c.igual(a.suma(b.producto(2.)));
        double m4[][]=new double[][]{{1,2,3,2},{3,1,4,2}},
               m5[][]=new double[][]{{2,3,0},{-3,0,1},{-1,2,2},{5,1,1}},
               m6[][]=new double[][]{{3,11,10},{9,19,11}};
        a=new matriz(m4);
        b=new matriz(m5);
        c=new matriz(m6);
        r=c.igual(a.suma(b.producto(2.)));
        double m7[][]=new double[][]{{2,3},{5,9}},
               m8[][]=new double[][]{{1,2,3},{6,14,20},{2,8,12}},
               m9[][]=new double[][]{{3,2,5},{4,0,2},{6,3,1}};
        a=new matriz(m7);
        b=new matriz(m8);
        c=new matriz(m9);
        double det=a.determinante();
        det=b.determinante();
        det=c.determinante();
        double m10[][]=new double[][]{{1,0,3,2},{2,-3,1,0},{-3,1,2,0},{2,-5,3,1}};
        a=new matriz(m10);
        det=a.determinante();
        a=new matriz(new double[][]{{1,3,2,5},{2,1,3,4},{5,8,7,6},{3,8,9,2}});
        det=a.determinante();
        b=new matriz(new double[][]{{-5,-1,-6},{-7,-3,-19},{-1,3,-13}});
        det=b.determinante();
        
    }
   
}
