import java.math.BigInteger;
import java.util.Random;
import RSAKey;
public class genParClaves extends Thread  {
   static Random r=new Random(System.currentTimeMillis());
   public static final BigInteger CERO=new BigInteger("0");
   public static final BigInteger UNO=new BigInteger("1");
   public static final BigInteger DOS=new BigInteger("2");
   public RSAKey[] claves=new RSAKey[2];
   int longModulo;
   boolean terminado=false;

   public genParClaves(int longModulo) { // inicializa la longitud par claves
      this.longModulo=longModulo; // y lanza el Thread generador de las mismas
      this.start();
   }
   public void run() {
      this.claves=this.parClaves();
   }


   private static double lg2(int n) {
      return Math.log(n)/Math.log(2);
   }

   public RSAKey[] parClaves() {
      return parClaves(this.longModulo);
   }

   public RSAKey[] parClaves(int longModulo) {
      RSAKey[] r=new RSAKey[2];
      RSAKey pK,PK;
      pK=(RSAKey) new RSAPrivateKey();
      PK=(RSAKey) new RSAPublicKey();
      r[0]=pK;
      r[1]=PK;
      BigInteger p,q,n,e,d,fn;
      p=generaPrimo(longModulo/2);
      q=generaPrimo(longModulo/2);
      while (q.compareTo(p)==0) {
         q=generaPrimo(longModulo/2);
      }
      n=p.multiply(q);
      fn=p.subtract(UNO).multiply(q.subtract(UNO));
      e=new BigInteger("3");
      while (e.gcd(fn).compareTo(UNO)!=0) {
         e=e.add(DOS);
      }
      d=this.euclidesExt(e,fn);
      PK.modulo=n;
      pK.modulo=n.add(CERO);
      pK.exponente=d; PK.exponente=e;
      ((RSAPrivateKey)pK).e=e;
      ((RSAPrivateKey)pK).p=p;
      ((RSAPrivateKey)pK).q=q;
      ((RSAPrivateKey)pK).fn=fn;
      this.terminado=true;
      return r;
   }
   public static boolean RabinMiller(BigInteger p, int n) {
      if (n <= 0) {
         return true;
      }
      p = p.abs();
      if (p.equals(DOS)) {
         return true;
      }
      if (!p.testBit(0) || p.equals(UNO)) {
          return false;
      }
      // Encontrar m tal que  p == 1 + 2^b * m
      BigInteger m = p.subtract(UNO);
      BigInteger p_1=m;
      int b = m.getLowestSetBit();
      m = m.shiftRight(b); //m = (p-1) / 2^b
      Random rnd = new Random(System.currentTimeMillis());
      for(int i=0; i<n; i++) {
          BigInteger a;       // Generar un a aleatorio comprendido en [1, p]
          do {
             a= new BigInteger(p.bitLength()-1, rnd);
          } while (a.bitLength()<=1);
          int j = 0;
          BigInteger z = a.modPow(m, p);
          if (z.equals(UNO) || z.equals(p_1)) {
             continue;
          }
          while (true) {
             if (j>0 && z.equals(UNO)) {
                return false; //no es primo
             }
             j++;
             if (j<b && !z.equals(p_1)) {
                z = z.modPow(DOS, p);
             } else if ((j==b) && !z.equals(p_1)) {
                return false;
             } else {// if (z.equals(p_1)) {
                break; // pasa el test y repetimos
             }
          }
      }
      return true;
   }

   public static BigInteger generaPrimo(int nBits) {
      BigInteger res;
      while (true) {
         res=new BigInteger(nBits, new Random(System.currentTimeMillis()));
         if (!res.testBit(nBits-1)) {
            res=res.setBit(nBits-1);
         }
         if (!res.testBit(0)) {
              res=res.setBit(0);
         }
         if (RabinMiller(res,10)) {
            return res;
         }
      }
   }
   public static BigInteger expMod(BigInteger x, BigInteger y, BigInteger n) {
      BigInteger s=UNO;
      BigInteger t=x.add(CERO);
      BigInteger u=y.add(CERO);

      while (u.compareTo(CERO)!=0) {
         if (u.mod(DOS).compareTo(UNO)==0) {
            s=s.multiply(t).mod(n);
         }
         u=u.divide(DOS);
         t=t.multiply(t).mod(n);
      }
      return s; // devuelve x^y mod n
   }
   public static BigInteger mcd(BigInteger a, BigInteger b) // alg. Euclides
   {
      BigInteger c= a.mod(b);
      while (c.compareTo(CERO)!=0) {
         a=b;
         b=c;
         c=a.mod(b);
      }
      return b;
   }
   public static BigInteger euclidesExt(BigInteger a, BigInteger n)
   {
      BigInteger g[]={n,a,CERO};
      BigInteger u[]={UNO,CERO,CERO};
      BigInteger v[]={CERO,UNO,CERO};
      BigInteger c;
      while (g[1].compareTo(CERO)!=0) {
         c=g[0].divide(g[1]);
         g[2]=g[0].mod(g[1]);
         u[2]=u[0].subtract(c.multiply(u[1]));
         v[2]=v[0].subtract(c.multiply(v[1]));
         g[0]=g[1]; g[1]=g[2];
         u[0]=u[1]; u[1]=u[2];
         v[0]=v[1]; v[1]=v[2];
      }
      BigInteger r=v[0].mod(n);
      if (r.compareTo(CERO)<0) {
         return r.add(n);
      }
      return r;
   }





/*   public static void main(String args[]) { // esto fue para hacer pruebas
      BigInteger n,p,q,e,d,fn;
      p=new BigInteger("47");
      System.out.println("p"+p);
      q=new BigInteger("71");
      System.out.println("q"+q);
      n=p.multiply(q);
      System.out.println("n"+n);
      fn=p.subtract(UNO).multiply(q.subtract(UNO));
      System.out.println("fn"+fn);
      e=new BigInteger("79");
      System.out.println("e"+e);
      d=euclidesExt(fn);
      System.out.println("d"+d);
      BigInteger m=new BigInteger("688");
      System.out.println("m"+m);
      BigInteger c=m.modPow(e,n);
      System.out.println("c"+c);
      BigInteger t=c.modPow(d,n);
      System.out.println("t"+t);
      //      while (true) {
//         BigInteger g=genParClaves.generaPrimo(8);
//         System.out.println("número generado: "+g);
//      }
   }*/




}