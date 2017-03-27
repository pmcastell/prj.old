import java.math.*;
public class prueba {
public static void main(String args[]) {
   BigDecimal b=new BigDecimal("123456789012345678901234567890.1234567890");
   BigDecimal c=new BigDecimal(b.toString());
   BigInteger d=b.toBigInteger();
   BigDecimal e=new BigDecimal(1);
   BigDecimal f=new BigDecimal(3);
   MathContext mc=new MathContext(80);
   
   System.out.println("b: "+b);
   System.out.println("c: "+c);
   System.out.println("La suma es: "+b.add(c));
   System.out.println("1/3 es: "+e.divide(f,mc));
}
}
