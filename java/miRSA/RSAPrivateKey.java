import java.math.BigInteger;
public class RSAPrivateKey extends RSAKey {
   public BigInteger e;
   public BigInteger p;
   public BigInteger q;
   public BigInteger fn;
   public final static BigInteger UNO=new BigInteger("1");
   public RSAPrivateKey() {
      this.type=1;
   }
   public RSAPrivateKey(BigInteger modulo,BigInteger exponente,
                        BigInteger e, BigInteger p, BigInteger q) {
      this();
      this.modulo=modulo;
      this.exponente=exponente;
      this.e=e;
      this.p=p;
      this.q=q;
      this.fn=p.subtract(UNO).multiply(q.subtract(UNO));
   }
      
} 