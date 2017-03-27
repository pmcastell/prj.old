import java.math.BigInteger;
import genParClaves;

public class RSAKey {
   public BigInteger modulo;
   public BigInteger exponente;
   byte type;
   public static final int minimumPadLen=8;
   public static final BigInteger UNO=genParClaves.UNO,
                                  DOS=genParClaves.DOS,
                                  CERO= genParClaves.CERO;
   public RSAKey() {
   }

   public RSAKey(BigInteger modulo, BigInteger exponente) {
      this.modulo=modulo;
      this.exponente=exponente;
   }

   byte nuevoPadByte() {
      if (type==1) { // según PKCS#1
         return (byte)255;
      } else { // if (type==2)
         byte b=(byte) (System.currentTimeMillis() % 256);
         while (b==0) { // número aleatorio <> 0
            b=(byte) (System.currentTimeMillis() % 256);
         }
         return b;
      }
   }
   public byte[] ensamblaBloque(byte b[]) {
      int k=byteLen(modulo);
      int pad=k-3-b.length;
      if (pad<8) {
         ventanaDialogo.vMensaje("Error, máxima longitud de mensaje permitida: "+(k-11));
      } else {
         int i;
         byte mensa[]= new byte[k];
         mensa[0]=0; // aseguramos que el mensaje < modulo
         mensa[1]=type; // tipo 1 para clave privada, 2 para pública
         for(i=2; i-2<pad; i++) {
            mensa[i]=nuevoPadByte();
         }
         mensa[i++]=0; // separador del padding con el texto plano
         for(int j=0; j<b.length; j++) {
            mensa[i++]=b[j]; // texto plano
         }
         return mensa;
      }
      return null;
   }
   public byte[] desensamblaBloque(byte b[]) {
      if (b[0]!=0) {
         ventanaDialogo.vMensaje("Error en el bloque byte[0] es distinto de 0.");
         return null;
      }
      if (b[1]!=1 && b[1]!=2) {
         ventanaDialogo.vMensaje("Error en el tipo de bloque (es <>1 y 2).");
         return null;
      }
      int i, lenPad=0;
      for(i=2; i<b.length && b[i]!=0; i++) { // pasamos el padding string
         lenPad++;
      }
      int l=b.length-3-lenPad; // longitud datos=long_bloque-3-long_padding
      int offset=3+lenPad; // donde comienzan los datos
      byte r[]=new byte[l];
      for(int c=0; c<l; c++) {
         r[c]=b[offset+c];
      }
      return r;
   }
   byte[] compruebaBloque(byte b[]) {
      int k=byteLen(modulo); // longitud del modulo n en bytes
      if (b.length!=k) {
         byte r[]=new byte[k];
         if (b.length>k) {
            int pos=0;
            for(; pos<b.length-k; pos++) {
               if (b[pos]!=0) {
                  ventanaDialogo.vMensaje("Error: bloque encriptado de longitud mayor que el módulo");
               }
            }
            for(int i=0; i<r.length; i++) {
               r[i]=b[pos+i];
            }
         } else {
            int i=0;
            for(; i<r.length-b.length; i++) {
               r[i]=0;
            }
            for(int j=0;j<b.length; j++) {
               r[j+i]=b[j];
            }
         }
         return r;
      }
      return b;
   }

   public byte[] positiviza(byte b[]) {
      if (b!=null) {
         byte r[];
         if (b[0]<0) {
            r=new byte[b.length+1];
            r[0]=0;
            for(int i=0; i<b.length; i++) {
               r[i+1]=b[i];
            }
            return r;
         }
      }
      return b;
   }
   public byte[] encript(byte b[]) {
      BigInteger cifrado=genParClaves.expMod(new BigInteger(b),exponente,modulo);
      return compruebaBloque(cifrado.toByteArray());
   }
   public byte[] extrae(byte t[], int pos, int tam) {
      byte r[];
      if (tam<t.length-pos) {
         r=new byte[tam];
      } else {
         r=new byte[t.length-pos];
      }
      for(int i=0; i<r.length; i++) {
         r[i]=t[pos+i];
      }
      return r;
   }
   public void copia(byte dest[], byte fte[], int pos) {
      for(int i=0; i<fte.length; i++) {
         dest[pos+i]=fte[i];
      }
   }
   int byteLen(BigInteger m) {
      int k=m.bitLength()/8;
      if (m.bitLength() % 8 !=0) {
         k++;
      }
      return k;
   }
   public byte[] encriptaBloque(byte textoPlano[]) {
      int k=byteLen(modulo); // longitud modulo en bytes
      int numBloques=textoPlano.length/(k-11); // 11 bytes son para relleno y
      if (textoPlano.length % (k-11)!=0) { // estructura del bloque
         numBloques++;
      }
      byte r[]=new byte[numBloques*k]; // aquí devolveremos el texto cifrado
      byte bloque[];
      for(int i=0; i<numBloques; i++) {
         bloque=extrae(textoPlano,i*(k-11),(k-11));
         copia(r, encript(ensamblaBloque(bloque)), i*k);
      }
      return r;
   }
   int tamano(byte[][]m) {
      int r=0;
      if (m!=null) {
         for(int i=0; i<m.length; i++) {
            if (m[i]!=null) {
               r+=m[i].length;
            }
         }
      }
      return r;
   }

   byte[] linealiza(byte[][]m) {
      int t=tamano(m);
      byte r[]=new byte[t];
      int h=0;
      for(int i=0; i<m.length; i++) {
         if (m[i]!=null) {
            for(int j=0; j<m[i].length; j++) {
               r[h++]=m[i][j];
            }
         }
      }
      return r;
   }

   public byte[] desencriptaBloque(byte textoCifrado[]) {
      int k=byteLen(modulo);
      int numBloques=textoCifrado.length/k;
      int i=0;
      byte bloque[];
      byte r[][]=new byte[numBloques][];
      while (i<numBloques) {
         bloque=extrae(textoCifrado, i*k, k);
         r[i]=desensamblaBloque(encript(positiviza(bloque)));
         i++;
      }
      return linealiza(r);
   }
}
