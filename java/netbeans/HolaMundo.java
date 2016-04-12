import java.util.Scanner;

class ecuacion2 {
   private double a,b,c,discr;
	
   public ecuacion2() {
	}
	public ecuacion2(double a, double b, double c) {
	   this.a=a;
		this.b=b;
		this.c=c;
		calculaDiscriminante();
	}
	public void leeCoeficientes() {
	   java.util.Scanner input=new java.util.Scanner(System.in);
	   System.out.println("Introduce a b c: ");
		this.a=input.nextDouble();
		this.b=input.nextDouble();
		this.c=input.nextDouble();
		calculaDiscriminante();
	}
	void calculaDiscriminante() {
	   this.discr=Math.sqrt(b*b-4*a*c);
	}
	public double discriminante() {
	   return discr;
	}
	int numSoluciones() {
	   if (discr==0) {
		   return 1;
		} else if (discr>0) {
		   return 2;
		} else {
		   return 0;
		}
	}
	public double solucion1() {
	   return (-b+discr)/(2*a);
	}
	public double solucion2() {
	   return (-b-discr)/(2*a);
	}
	
}
		
	
	
   
public class HolaMundo {
   public static void main(String args[]) {
	   ecuacion2 e1=new ecuacion2();
		e1.leeCoeficientes();
		
	   System.out.println("Hola Mundo!");
	}
}

class prueba extends HolaMundo {
}