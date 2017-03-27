/******************************************************************************/
/******************         miFecha.java       *******************************/
/******************************************************************************/
package mieval.tipo; 
import mieval.SyntaxErrorException;
import java.lang.Math;
import java.util.Calendar;

public class miFecha extends objeto {
   int d,
       m,
       a;
   int err=0; //2 fallo en día , 4 fallo en mes , 8 fallo en año
   public miFecha() {
      d=m=a=1; // primer día de nuestra era
   }   
   public miFecha(int dia,int mes,int anio) {
      a=anio;m=mes;d=dia;
   } 
   public miFecha(String f) {
      int desp=0;
      if (f.substring(0,1).equalsIgnoreCase("#")) {
         desp++;
      }
      d=Integer.parseInt(f.substring(0+desp,2+desp));
      m=Integer.parseInt(f.substring(3+desp,5+desp));
      a=Integer.parseInt(f.substring(6+desp,10+desp));
   }      

   //public miFecha(int n) {
   //   inc(n-1);
   //}

   public static boolean esBisiesto(int anio) {
	   if (anio % 4==0) {
		   if (anio % 100!=0) {
			   return true;
		   } else if (anio % 400==0) { //anio % 100==0
			   return true;
		   }
	   }
	   return false;
   }
   public boolean esBisiesto() {
      return esBisiesto(this.a);
   }
   public boolean esValida(miFecha f) {
       if (f.m<1 || f.m>12) {
           err=4;
           return false;
       }
       int maxDia=numDiasMes(f.m);
       if (f.m==2 && esBisiesto(f.a)) {
           maxDia++;
       }
       if(f.d<1 || f.d>maxDia) {
           err=2;
           return false;
       }
       if (f.a<1) {
           err=8;
           return false;
       }
       err=0;
       return true;
   }
   
   public boolean esValida() {
      return esValida(this);
   }

   public static int numDiasMes(int mes, int anio) {
       if (mes!=2) {
           return numDiasMes(mes);
       } else if (esBisiesto(anio)) { //mes ==2 (febrero)
           return 29;
       } else { // febrero y no bisiesto
           return 28;
       }
   }
   
   public int numDiasMes() {
      return numDiasMes(this.m,this.a);
   }
 
   public static int _numDiasMes(String mes) {
      if (mes.equalsIgnoreCase("enero")) {
         return 31;
      } else if (mes.equalsIgnoreCase("febrero")) {
         return 28;
      } else if (mes.equalsIgnoreCase("marzo")) {
         return 31;
      } else if (mes.equalsIgnoreCase("abril")) {
         return 30;
      } else if (mes.equalsIgnoreCase("mayo")) {
         return 31;
      } else if (mes.equalsIgnoreCase("junio")) {
         return 30;
      } else if (mes.equalsIgnoreCase("julio")) {
         return 31;
      } else if (mes.equalsIgnoreCase("agosto")) {
         return 31;
      } else if (mes.equalsIgnoreCase("septiembre")) {
         return 30;
      } else if (mes.equalsIgnoreCase("octubre")) {
         return 31;
      } else if (mes.equalsIgnoreCase("noviembre")) {
         return 30;
      } else if (mes.equalsIgnoreCase("diciembre")) {
         return 31;
      } else {
         return -1;
      }
   }
   
   public static int numDiasMes(int mes) {
      String meses[]={"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre","diciembre"};
      return _numDiasMes(meses[mes-1]);
   }
   public void inc() {
       d++;
       if (d>=29) {
           if (d>numDiasMes()) {
               d=1;
               m++;
               if (m>12) {
                   m=1;
                   a++;
               }
           }
       }
   }

   public void dec() {
       d--;
       if (d>0) {
           return;
       } else { //d<=0
           m--;
           if (m>0) {
               d=numDiasMes();
           } else { //m<=0
               m=12;
               d=31;
               a--;
           }
       }
   }
 
    //número de días transcurrido entre dos fechas
   public static int resta(miFecha f2, miFecha f1) {
       //return fechaToNum(f2)-fechaToNum(f1);
       return f2.intValue()-f1.intValue();
   }
   //fecha num días después de la fecha dada
   public static miFecha inc(miFecha f, int num) {
      miFecha fecha=(miFecha)f.copia();
      fecha.inc(num);
      return fecha;
   }
   public void inc(int num) {
       int i;

       for(i=1; i<=num; i++) {
           inc();
       }
   }
   //fecha num días antes de la fecha dada
   public void dec(int num) {
       int i;

       for(i=1; i<=num; i++) {
           dec();
       }
   }
   public static miFecha dec(miFecha f,int num) {
      miFecha fecha=(miFecha)f.copia();
      fecha.dec(num);
      return fecha;
   }
   //devuelve el primer día de nuestra era 01/01/0001
   static miFecha diaUNO() {
      return new miFecha();
   }

   
   public static miFecha fechaActual() {
       Calendar hoy=Calendar.getInstance();
       return new miFecha(hoy.get(Calendar.DAY_OF_MONTH),hoy.get(Calendar.MONTH)+1,hoy.get(Calendar.YEAR));
   }

   public static int numAniosBisiestos(miFecha f1, miFecha f2) {
       int res=0, anio1;

       if (f1.mayor(f2)) {
           return -numAniosBisiestos(f2,f1);
       }
       if (f1.m<=2 && f1.esBisiesto()) {
           res++;
       }
       for(anio1=f1.a+1; f1.a<f2.a; anio1++) {
           if (miFecha.esBisiesto(anio1)) {
               res++;
           }
       }
       if (f2.m>2 && f2.esBisiesto()) {
           res++;
       }
       return res;
   }

   public static double numAnios(miFecha f1, miFecha f2) {
      if (f1.menor(f2)) {
         return (f2.intValue()-f1.intValue()-numAniosBisiestos(f1,f2))/365.0;
      } else {
         return -numAnios(f2,f1);
      }
   }
   public double numAnios(miFecha f) {
      return numAnios(this,f);
   }
   
/*   
double fechaNumAnios2(FECHA f1, FECHA f2) {
    int numAnios=f2.anio-f1.anio;

    if (f2.mes<f1.mes) {
        numAnios--;
    } else if (f2.mes==f1.mes) {
        if (f2.dia<f1.dia) {
            numAnios--;
        }
    }
    return numAnios;
}
*/

   /*FUNCIONES DE CONVERSION*/
   //convierte una fecha al número de días transcurridos
   //entre el primer día de nuestra era y esa fecha
   public int intValue() {
       int dias=0, i;

       dias+=(a-1)*365;
       for(i=1; i<m; i++) {
           dias+=numDiasMes(i);
       }
       dias+=d;
       if (this.esBisiesto() && m>2) {
           dias++;
       }
       dias+=(a-1)/4;
       dias-=(a-1)/100;
       dias+=(a-1)/400;
       return dias;
   }
   public miFecha(int n) {
       a=n/365+1;
       d=n-((a-1)*365);
       d-=(a-1)/4;
       d+=(a-1)/100;
       d-=(a-1)/400;
       while (d<=0) {
           a--;
           d+=365;
           if (esBisiesto(a)) {
               d++;
           }
       }
       for(m=1;m<=12 && d>numDiasMes(m,a);m++) {
           d-=numDiasMes(m,a);
       }
   }
   public static miFecha numToMiFecha(int n) {
       return new miFecha(n);
   }
   //convierte fecha a cadena
   public String toString(String sep) {
      String res="";
      if (d<10) {
         res+="0";
      }
      res+=d+sep;
      if (m<10) {
         res+="0";
      }
      res+=m+sep;
      if (a<10) {
         res+="000";
      } else if (a<100) {
         res+="00";
      } else if (a<1000) {
         res+="0";
      }
      res+=a;
      return "#"+res+"#";
   }
   public String toString() {
      return toString("/");
   }
   

//convierte una cadena a fecha



   public objeto resta(objeto o) throws SyntaxErrorException {
      if (o instanceof miFecha) {
         return new miDouble(resta(this,(miFecha)o));
      } else if (o instanceof miDouble) {
         return new miFecha(this.intValue()-((miDouble) o).intValue());
      } else {
         throw new SyntaxErrorException("Error a una fecha sólo se le puede restar otra fecha o un número.");
      }
   }  
   public objeto suma(objeto o) throws SyntaxErrorException {
      if (o instanceof miDouble) {
         miFecha f=(miFecha)this.copia();
         f.inc(o.intValue());
         return f;
      } else {
         throw new SyntaxErrorException("Error a una fecha sólo se le puede sumar un número.");
      }
   }  
   public boolean igual(objeto o) {
      if (o instanceof miFecha) {
         miFecha f=(miFecha) o;
         return this.d==f.d && this.m==f.m && this.a==f.a;
      } else {
         return false;
      }
   }
   public boolean menor(objeto o)
   {
      if (o instanceof miFecha) {
         miFecha f=(miFecha) o;
         if (this.a<f.a) {
            return true;
         } else if (this.a==f.a) {
            if (this.m<f.m) {
               return true;
            } else if (this.m==f.m) {
               return this.d<f.d;
            }
         }
      }
      return false;
   }
   public boolean mayor(objeto o) {
      if (o instanceof miFecha) {
         return !(this.menor((miFecha)o) || this.igual((miFecha)o));
      }
      return false;
   }
   public void asigna(objeto o) {
      if (o instanceof miFecha) {
         miFecha f=(miFecha) o;
         this.a=f.a; this.m=f.m; this.d=f.d;
      } 
   }
   public objeto copia() {
      return new miFecha(d,m,a);
   }
   public objeto modulo(objeto o) throws SyntaxErrorException {
      throw new SyntaxErrorException("Error no tiene sentido calcular el módulo de una fecha.");
   }
   public objeto potencia(objeto o) throws SyntaxErrorException {
      throw new SyntaxErrorException("Error no tiene sentido potencias de fechas.");
   }  

   public objeto division(objeto o) throws SyntaxErrorException {
      throw new SyntaxErrorException("Error no tiene sentido dividir de fechas.");
   }  
   public objeto producto(objeto o) throws SyntaxErrorException {
      throw new SyntaxErrorException("Error no tiene sentido potencias de fechas.");
   }
   //para hacer pruebas  
   public static void main(String args[]) {
      try {
         //miFecha f1=new miFecha("01/01/2011"),f2=new miFecha("03/03/2011");
         //miFecha f1=new miFecha(args[0]),f2=new miFecha(args[1]);
         //System.out.println("Entre "+f1+" y "+f2+" hay "+f2.resta(f1));
         //System.out.println("f1.intValue: "+f1.intValue());
         //System.out.println("f2.intValue: "+f2.intValue());
         //System.out.println("fechaActual: "+miFecha.fechaActual());
          miFecha f,g=miFecha.diaUNO();
         for(int i=1156;i<=734208;i++) {
             f=miFecha.numToMiFecha(i);
             if (i!=f.intValue()) {
                 System.out.println("problema: "+i+" f.int: "+f.intValue());
                 System.out.println("debería ser: "+g+" num: "+g.intValue());

             }
                              g.inc();
         }
      } catch (Exception ex) {
         System.out.println(ex);
      }
   }


}
