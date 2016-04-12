//package prueba2;

import java.util.Scanner;
import java.lang.Math;



public class Main {
   static double discriminante(double a, double b, double c) {
      return Math.sqrt(b*b -4*a*c);
   }

   public static void main(String[] args) {
      double a,b,c;

      Scanner input=new Scanner(System.in);
      System.out.println("Introduce coeficientes de la ecuación a,b,c: ");
      /*a=input.nextDouble();
      b=input.nextDouble();
      c=input.nextDouble();*/
      a=1;b=-10;c=24;
      double disc=discriminante(a,b,c);
      if (disc<0) {
         System.out.println("La ecuación no tiene solución");
      } else if (disc==0) {
         System.out.println("La ecuación tiene una solución doble: "+(-b/(2*a)));
      } else {
         double x1=(-b+disc)/(2*a);
         double x2=(-b-disc)/(2*a);
         System.out.println("La ecuación tiene dos soluciones: "+x1+", "+x2);
      }

   }
}
    
