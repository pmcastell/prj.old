
import java.math.BigInteger;
public class RSAPublicKey extends RSAKey {

   public RSAPublicKey() {
      this.type=2;
   }

   public RSAPublicKey(BigInteger modulo, BigInteger exponente) {
      this();
      this.modulo=modulo;
      this.exponente=exponente;
   }

} 