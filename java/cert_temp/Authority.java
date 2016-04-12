import java.lang.reflect.*;
import java.io.*;
import java.security.*;
import java.util.*;
import java.net.*;
import java.math.BigInteger;
import java.security.cert.CertificateException;

import iaik.x509.*;
import iaik.security.cipher.*;
import iaik.security.pbe.*;
import iaik.security.rsa.*;
import iaik.security.dsa.*;
import iaik.security.dh.*;
import iaik.asn1.structures.*;
import iaik.asn1.*;
import iaik.x509.extensions.*;
import iaik.security.provider.IAIK;
import iaik.pkcs.*;
import iaik.pkcs.pkcs7.*;
import iaik.pkcs.pkcs8.*;
import iaik.pkcs.pkcs10.*;
import iaik.pkcs.pkcs12.*;
import iaik.utils.*;


public class Authority
{
  static final String   /* identificadores de diferentes formatos */
    formatPKCS12="PKCS12",
    formatNetscapeP12="Netscape",
    formatMicrosoftP12="Microsoft",
    formatCicaP12="Cica",
    formatNetscapeP10="Netscape",
    formatApache="Apache",
    formatPKCS10="PKCS10";
  static final String   /* formatos soportados para PKCS12 y PKCS10 */
    PKCS12Formats[]={formatPKCS12,formatCicaP12,formatNetscapeP12,formatMicrosoftP12,formatApache},
    PKCS10Formats[]={formatPKCS10,formatNetscapeP10};
  static Provider providerIAIK;

  int format=ASN1.PEM;
  String userPassword;  /* password de usuario para proteger la clave privada */

  Name caName,userName;         /* datos del usuario y de la CA */

  PublicKeyInfo caPublicKey,userPublicKey; /* claves publicas de usuario y de la CA */

  PrivateKeyInfo caPrivateKey,userPrivateKey;   /* claves privadas de usuario y de la CA */

  X509Certificate userChain[],caChain[];        /* cadena de certificados de usuario y de la CA */

  public Authority ()
  {
    if (providerIAIK==null)     /* Solo se carga IAIK una vez */
    {
        providerIAIK=new IAIK ();
        Security.addProvider (providerIAIK);
    }
  }

  public void userToCa ()
  {
    caName=userName;
    caPublicKey=userPublicKey;
    caPrivateKey=userPrivateKey;
    caChain=userChain;
    userPassword=null;
    userName=null;
    userPublicKey=null;
    userPrivateKey=null;
    userChain=null;
  }

  public void caToUser ()
  {
    userName=caName;
    userPublicKey=caPublicKey;
    userPrivateKey=caPrivateKey;
    userChain=caChain;
    userPassword=null;
    caName=null;
    caPublicKey=null;
    caPrivateKey=null;
    caChain=null;
  }

  public void readPrivateKey (String fileName) throws IOException,NoSuchAlgorithmException,InvalidKeyException,GeneralSecurityException
  {
    InputStream f;

    f=new FileInputStream (fileName);
    EncryptedPrivateKeyInfo epki=new EncryptedPrivateKeyInfo (f);
    userPrivateKey=epki.getPrivateKeyInfo ();
    epki.decrypt (userPassword);
    userPrivateKey=epki.getPrivateKeyInfo ();
  }

  public void readPrivateRSAKey (String fileName) throws IOException,CodingException
  {
    FileInputStream f=new FileInputStream (fileName);

    ASN1 a=new ASN1 (f);
    BigInteger
      modulus,
      publicExponent,
      privateExponent,
      prime1,
      prime2,
      exponent1,
      exponent2,
      crtCoefficient;

    modulus=(BigInteger)a.getComponentAt (1).getValue ();
    publicExponent=(BigInteger)a.getComponentAt (2).getValue ();
    privateExponent=(BigInteger)a.getComponentAt (3).getValue ();
    prime1=(BigInteger)a.getComponentAt (4).getValue ();
    prime2=(BigInteger)a.getComponentAt (5).getValue ();
    exponent1=(BigInteger)a.getComponentAt (6).getValue ();
    exponent2=(BigInteger)a.getComponentAt (7).getValue ();
    crtCoefficient=(BigInteger)a.getComponentAt (8).getValue ();
    userPrivateKey=new RSAPrivateKey (modulus,publicExponent,privateExponent,prime1,prime2,exponent1,exponent2,crtCoefficient);
    userPublicKey=(PublicKeyInfo)((RSAPrivateKey)userPrivateKey).getPublicKey ();
  }

  public void savePrivateRSAKey (String fileName) throws IOException,
    CodingException,InstantiationException
  {
    FileOutputStream f=new FileOutputStream (fileName);
    BigInteger
      modulus,
      publicExponent,
      privateExponent,
      prime1,
      prime2,
      exponent1,
      exponent2,
      crtCoefficient;
    modulus=((RSAPrivateKey)userPrivateKey).getModulus ();
    publicExponent=((RSAPrivateKey)userPrivateKey).getPublicExponent ();
    privateExponent=((RSAPrivateKey)userPrivateKey).getPrivateExponent ();
    prime1=((RSAPrivateKey)userPrivateKey).getPrimeP ();
    prime2=((RSAPrivateKey)userPrivateKey).getPrimeQ ();
    exponent1=((RSAPrivateKey)userPrivateKey).getPrimeExponentP ();
    exponent2=((RSAPrivateKey)userPrivateKey).getPrimeExponentQ ();
    crtCoefficient=((RSAPrivateKey)userPrivateKey).getCrtCoefficient ();
    ASN1Object a=ASN.create (ASN.SEQUENCE);
    ASN1Object aux=ASN.create (ASN.INTEGER);
    aux.setValue (new BigInteger ("0"));
    a.addComponent (aux);
    aux.setValue (modulus);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (publicExponent);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (privateExponent);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (prime1);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (prime2);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (exponent1);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (exponent2);
    a.addComponent (aux);
    aux=ASN.create (ASN.INTEGER);
    aux.setValue (crtCoefficient);
    a.addComponent (aux);
    ASN1 asn1=new ASN1 (a);
System.out.println (asn1.toString ());
    writePEM (f,"RSA PRIVATE KEY",asn1.toByteArray ());
    f.close ();
  }

  public void writePEM (OutputStream f,String s,byte b[]) throws IOException
        /* procedimiento para escribir los ficheros en formato PEM, los parametros son el fichero, el nombre de lo que se guarda para construir la cabecera y la cola, y un array de bytes con los datos */
  {
    Base64OutputStream b64f=new Base64OutputStream (f);
    String aux;

    aux=new String ("-----BEGIN "+s+"-----\n");
    f.write (aux.getBytes ());
    b64f.write (b);
    b64f.flush ();
    aux=new String ("\n-----END "+s+"-----\n");
    f.write (aux.getBytes ());
  }

  public boolean verifyCertificateChain ()
  {
    try
    {
      userChain[userChain.length-1].verify ();
      for (int i=1;i<userChain.length;i++)
      {
        userChain[i-1].verify (userChain[i].getPublicKey ());
      }
      return true;
    }
    catch (Exception e) {return false;}
  }

  public boolean isTrusted ()
  {
    try
    {
      for (int i=0;i<userChain.length;i++)
      {
        if (((Name)userChain[i].getIssuerDN ()).equals (caName))
        {
          userChain[i].verify (caPublicKey);
          return true;
        }
      }
      return false;
    }
    catch (Exception e) {return false;}
  }

  public String[] getSignersString ()   /* funcion que devuelve un array de String con los nombres de los posibles tipos de firma */
  {
    int i;
    AlgorithmID alg[]=getSigners ();    /* utiliza esta funcion que devuelve lo mismo pero como array de AlgorithmID */

    if (alg==null) return null;         /* si hay algun algoritmo se copia el nombre en el array de string */

    String aux[]=new String[alg.length];
    for (i=0;i<alg.length;i++) aux[i]=alg[i].getName ();
    return aux;
  }

  public AlgorithmID[] getSigners ()    /* funcion que devuelve un array de AlgorithmID con los nombres de los posibles tipos de firma */
  {
    if (caPrivateKey==null && userPrivateKey==null) return null;
    int i,j;    /* recogemos todos los algoritmos */
    Field f[]=new AlgorithmID ().getClass ().getFields ();
    PrivateKeyInfo pki;         /* la idea es de un Tanzano que invento un lenguaje estructurado para programar un abaco: se cojen todos los algoritmos disponibles, y se intenta firmar con cada uno, si no da una excepcion vale ! */
    AlgorithmID values[]=null,aux[];
    CertificateRequest cr;

    pki=userPrivateKey;

    for (i=0;i<f.length;i++)
    {
        try
        {
          cr=new CertificateRequest (userPublicKey,userName);
        }
        catch (InvalidKeyException e)
        {
          return null;  /* si la clave no sirve no se puede firmar */
        }
        try
        {
          cr.sign ((AlgorithmID)f[i].get (null),pki);   /* se cojen todos los algoritmos y si no hay una excepcion se incluyen en values */
          if (values==null)     /* si values no tiene niguno le ponemos uno */
          {
            values=new AlgorithmID[1];
            values[0]=(AlgorithmID)f[i].get (null);
          }
          else
          {     /* si values tiene alguno le incluimos uno mas a traves de una variable auxiliar */
            aux=new AlgorithmID[values.length+1];
            for (j=0;j<values.length;j++) aux[j+1]=values[j];   /* copiamos los que ya hay */
            aux[0]=(AlgorithmID)f[i].get (null);        /* incluimos el ultimo */
            values=aux;         /* lo dejamos en values */
          }
        }
        catch (Exception e) {}
    }
    return values;
  }

  public String[] getKeyPairGenerator ()        /* funcion que devuelve un array con los nombres de los generadores de pares de clave publica/privada */
  {
    Enumeration enum=providerIAIK.propertyNames ();
                                /* buscamos elementos que comiencen por KeyPairGenerator */
    String o,values[]=null;
    int i;

    for (;enum.hasMoreElements ();)     /* bonito while */
    {
        o=enum.nextElement ().toString ();
        if (o.startsWith ("KeyPairGenerator.")) /* si hay un KeyPairGenerator lo incluimos en values */
        {
          if (values==null)
          {
            values=new String[1];
            values[0]=o.substring ("KeyPairGenerator.".length ());
          }
          else
          {
            String aux[]=new String[values.length+1];
            for (i=0;i<values.length;i++) aux[i]=values[i];
            aux[i]=o.substring ("KeyPairGenerator.".length ());
            values=aux;
          }
        }
    }
    return values;
  }

  public void readCertificate (String fileName)  throws IOException,CertificateException        /* funcion que lee un certificado y lo incluye en userChain al final */
  {
    FileInputStream f;

    f=new FileInputStream (fileName);
    X509Certificate auxCert=new X509Certificate (f);
    if (userName==null) userName=(Name)auxCert.getSubjectDN ();
    if (caName==null) caName=(Name)auxCert.getIssuerDN ();
    if (userPublicKey==null) userPublicKey=(PublicKeyInfo)auxCert.getPublicKey ();
    if (userChain==null)
    {
      userChain=new X509Certificate[1];
      userChain[0]=auxCert;
    }
    else
    {
      int i;
      X509Certificate auxChain[]=new X509Certificate[userChain.length+1];
      for (i=0;i<userChain.length;i++) auxChain[i]=userChain[i];
      auxChain[i]=auxCert;
      userChain=auxChain;
    }
    f.close ();
  }

  public void saveCertificate (String fileName)  throws IOException     /* funcion para grabar el primer certificado de usuario */
  {
    OutputStream f;

    f=new FileOutputStream (fileName);
    if (format==ASN1.DER) userChain[0].writeTo (f);
    else writePEM (f,"CERTIFICATE",userChain[0].toByteArray ());
    f.close ();
  }

  public void savePrivateKey (String fileName) throws IOException,NoSuchAlgorithmException
/* funcion para grabar la clave privada. */
  {
    OutputStream f;

    f=new FileOutputStream (fileName);
    if (userPassword.equals (""))
    {
      if (format==ASN1.DER) userPrivateKey.writeTo (f);
      else writePEM (f,"PRIVATE KEY",userPrivateKey.getEncoded ());
    }
    else
    {
      EncryptedPrivateKeyInfo epki=new EncryptedPrivateKeyInfo (userPrivateKey);
      epki.encrypt (userPassword,AlgorithmID.pbeWithSHAAnd40BitRC2_CBC,null);
      if (format==ASN1.DER) epki.writeTo (f);
      else writePEM (f,"PRIVATE KEY",epki.getEncoded ());
    }
    f.close ();
  }

  public void saveKeyAndCert (String fileName,String format) throws IOException,NoSuchAlgorithmException,PKCSException,CodingException  /*  funcion para generar un PKCS12 */
  {
    OutputStream f;

    f=new FileOutputStream (fileName);

    if (format.equals (formatPKCS12))   /* se crea la estructura necesaria */
    {
        AuthenticatedSafe as[]=new AuthenticatedSafe[2];
        SafeBag sb[]=new SafeBag[1];
        EncryptedPrivateKeyInfo epki=new EncryptedPrivateKeyInfo (userPrivateKey);
        epki.encrypt (userPassword,AlgorithmID.pbeWithSHAAnd40BitRC2_CBC,null);
        sb[0]=new SafeBag (epki);
        as[0]=new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb);
        sb=new SafeBag[userChain.length];
        for (int i=0;i<sb.length;i++) sb[i]=new SafeBag (userChain[i]); /* se guarda la cadena de certificados de usuario */
        as[1]=new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb);
        PKCS12 p12=new PKCS12 (as,PKCS12.PASSWORD_INTEGRITY_MODE);
        p12.encrypt (userPassword,AlgorithmID.pbeWithSHAAnd40BitRC2_CBC);
        p12.writeTo (f);
    }
    else if (format.equals (formatCicaP12))
    {
      CicaP12 cp12=new CicaP12 (userPrivateKey,userChain,caChain);
      cp12.encrypt (userPassword);
      cp12.writeTo (f,ASN1.PEM);
    }
    else if (format.equals (formatNetscapeP12)) /* igual para netscape */
    {
        NetscapeP12 np12=new NetscapeP12 (userPrivateKey,userChain);
        if (userPassword!=null) np12.encrypt (userPassword);
        else System.err.println ("Aviso: Se va ha grabar una clave privada en claro.");
        np12.writeTo (f);
    }
    else if (format.equals (formatMicrosoftP12))        /* igual para MIE */
    {
      MicrosoftP12 mp12=new MicrosoftP12 (userPrivateKey,userChain);
      if (userPassword==null) System.err.println ("Aviso: Se va ha grabar una clave privada en claro.");
      else mp12.encrypt (userPassword);
        mp12.writeTo (f);
    }
    else if (format.equals (formatApache))
    {
      f.close ();
      KeyAndCertificate kac=new KeyAndCertificate (userPrivateKey,userChain);
      kac.saveTo (fileName,ASN1.PEM);
      return;
    }
    f.close ();
  }

  public static AlgorithmID algorithmID (String algorithm)      /* funcion que transforma un string a un AlgorithmID */
  {
    int i;
    Field f[]=new AlgorithmID ().getClass ().getFields ();

    for (i=0;i<f.length;i++)    /* miramos los nombres de los AlgorithmID y nos quedamos con el que coincide */
      try
      {
        if (f[i].getName ().equals (algorithm))
          return (AlgorithmID)f[i].get (null);
      } catch (IllegalAccessException e) {}
    return null;
  }

  public void createCertificate (String algorithmSign,Date validNotBefore,Date validNotAfter) throws CertificateException,InvalidKeyException,NoSuchAlgorithmException  /* funcion para crear un certificado a partir de un algoritmo de firma y las fechas de comienzo de validez y fin de validez */
  {
    createCertificate (algorithmID (algorithmSign),validNotBefore,validNotAfter);
  }

  public void deleteCertificate ()
  {
    X509Certificate auxChain[]=new X509Certificate[userChain.length-1];

    for (int i=0;i<userChain.length-1;i++)
      auxChain[i]=userChain[i+1];
    userChain=auxChain;
  }

  public void createCertificate (AlgorithmID algorithmSign,Date validNotBefore,Date validNotAfter) throws CertificateException,InvalidKeyException,NoSuchAlgorithmException     /* funcion para crear un certificado a partir de un algoritmo de firma y las fechas de comienzo de validez y fin de validez */
  {
    X509Certificate cert=new X509Certificate ();

    cert.setSerialNumber (new BigInteger(20, new Random()));
    cert.setPublicKey (userPublicKey);
    if (userName!=null)
    {
        cert.setSubjectDN (userName);
        if (caName==null) cert.setIssuerDN (userName);
    }
    if (caName!=null) cert.setIssuerDN (caName);

    cert.setValidNotBefore (validNotBefore);
    cert.setValidNotAfter (validNotAfter);

    if (caPrivateKey!=null)
    {
      cert.sign (algorithmSign,caPrivateKey);
        userChain=new X509Certificate[1+caChain.length];
        userChain[0]=cert;
      for (int i=0;i<caChain.length;i++)
      {
        userChain[i+1]=caChain[i];
      }
    }
    else
    {
      cert.sign (algorithmSign,userPrivateKey);
        userChain=new X509Certificate[1];
        userChain[0]=cert;
    }
  }

  public void generateKeyPair (String algorithm,int bits) throws NoSuchAlgorithmException,NoSuchProviderException       /* funcion para generar un par de claves a partir de un algoritmo y un numero de bits */
  {
    KeyPairGenerator generator=KeyPairGenerator.getInstance (algorithm,"IAIK");
    generator.initialize (bits);
    KeyPair kp=generator.generateKeyPair();
    userPublicKey=(PublicKeyInfo)kp.getPublic ();
    userPrivateKey=(PrivateKeyInfo)kp.getPrivate ();
  }

  public void saveCertificateRequest (String fileName,String signerAlgorithm) throws IOException,InvalidKeyException,SignatureException,NoSuchAlgorithmException
/* funcion que genera un PKCS10 */
  {
    saveCertificateRequest (fileName,algorithmID (signerAlgorithm));
  }

  public void saveCertificateRequest (String fileName,AlgorithmID signerAlgorithm)
 throws IOException,InvalidKeyException,SignatureException,NoSuchAlgorithmException
/* funcion que genera un PKCS10 */
  {
    OutputStream f;
    f=new FileOutputStream (fileName);
    CertificateRequest cr=new CertificateRequest (userPublicKey,userName);
    if (caPrivateKey==null) cr.sign (signerAlgorithm,userPrivateKey);
    else cr.sign (signerAlgorithm,caPrivateKey);
    cr.writeTo (f);
    f.close ();
  }

  public void readCertificateRequest (String fileName,String format) throws FileNotFoundException,PKCSParsingException,IOException,CodingException,InvalidKeyException
/* funcion que importa un PKCS10 */
  {
    FileInputStream f=new FileInputStream (fileName);

    if (format.equals (formatPKCS10))
    {
        CertificateRequest cr=new CertificateRequest (f);
        userName=cr.getSubject ();
        userPublicKey=(PublicKeyInfo)cr.getPublicKey ();
    }
    else if (format.equals (formatNetscapeP10))
    {
        NetscapeCertRequest ncr=new NetscapeCertRequest (f);
        userName=null;
        userPublicKey=(PublicKeyInfo)ncr.getPublicKey ();
    }
    f.close ();
  }

  public void readKeyAndCert (String fileName,String format) throws FileNotFoundException,PKCSParsingException,IOException,PKCSException,NoSuchAlgorithmException,CodingException,InvalidKeyException,GeneralSecurityException
/* funcion para leer un PKCS12 */
  {
    FileInputStream f=new FileInputStream (fileName);

    if (format.equals (formatCicaP12))
    {
      CicaP12 p12;
      if (userPassword!=null) p12=new CicaP12 (f,userPassword);
      else p12=new CicaP12 (f);
      userPrivateKey=p12.getPrivateKey ();
      userChain=p12.getCertificateChain ();
      caChain=p12.getCertificateIssuer ();
      userPublicKey=(PublicKeyInfo)userChain[0].getPublicKey ();
      userName=(Name)userChain[0].getSubjectDN ();
    }
    else if (format.equals (formatPKCS12))
    {
        int i,j;        /* si esta en nuestro formato lo desencriptamos */
                        /* y buscamos una clave privada y una cadena de certificados */
        PKCS12 p12=new PKCS12 (f);
        if (userPassword!=null) p12.decrypt (userPassword);
        AuthenticatedSafe as[]=p12.getAuthenticatedSafes ();
        SafeBag sb[][]=new SafeBag[as.length][];
        userChain=null;
        userPrivateKey=null;
        userPublicKey=null;
        for (i=0;i<as.length;i++) sb[i]=as[i].getSafeBags ();

        for (i=0;i<sb.length;i++)
          for (j=0;j<sb[i].length;j++)
          {
            if (sb[i][j].getBagType ().equals (ObjectID.pkcs12_keyBag))
                userPrivateKey=PrivateKeyInfo.getPrivateKeyInfo (sb[i][j].getBagContent ().toASN1Object ());
            if (sb[i][j].getBagType ().equals (ObjectID.pkcs12_pkcs8ShroudedKeyBag))
            {
              EncryptedPrivateKeyInfo epki=new EncryptedPrivateKeyInfo (sb[i][j].getBagContent ().toASN1Object ());
              userPrivateKey=epki.decrypt (userPassword);
            }
            else if (sb[i][j].getBagType ().equals (ObjectID.pkcs12_certBag))
            {
                if (userChain==null)
                {
                  userChain=new X509Certificate[1];
                  userChain[0]=new X509Certificate ();
                  userChain[0].decode (sb[i][j].getBagContent ().toASN1Object ());
                }
                else
                {
                  X509Certificate auxChain[]=new X509Certificate[userChain.length+1];
                  for (i=0;i<userChain.length;i++) auxChain[i]=userChain[i];
                  auxChain[i]=new X509Certificate ();
                  auxChain[i].decode (sb[i][j].getBagContent ().toASN1Object ());
                  userChain=auxChain;
                }
            }
          }
        if (userPrivateKey==null || userChain==null) throw new PKCSException ();
        userName=(Name)userChain[0].getSubjectDN ();
        userPublicKey=(PublicKeyInfo)userChain[0].getPublicKey ();
    }
    else if (format.equals (formatNetscapeP12))
    {
        NetscapeP12 np12=new NetscapeP12 (f);
        if (userPassword!=null) np12.decrypt (userPassword);
        userChain=np12.getCertificateChain ();
        userName=(Name)userChain[0].getSubjectDN ();
        userPrivateKey=np12.getPrivateKey ();
        userPublicKey=(PublicKeyInfo)userChain[0].getPublicKey ();
    }
    else if (format.equals (formatMicrosoftP12))
    {
        MicrosoftP12 mp12=new MicrosoftP12 (f);
      if (userPassword!=null) mp12.decrypt (userPassword);
      userChain=mp12.getCertificateChain ();
      userName=(Name)userChain[0].getSubjectDN ();
      userPrivateKey=mp12.getPrivateKey ();
      userPublicKey=(PublicKeyInfo)userChain[0].getPublicKey ();
    }
    else if (format.equals (formatApache))
    {
      f.close ();
        KeyAndCertificate kac=new KeyAndCertificate (fileName);
        userChain=kac.getCertificateChain ();
        userName=(Name)userChain[0].getSubjectDN ();
        userPrivateKey=(PrivateKeyInfo)kac.getPrivateKey ();
        userPublicKey=(PublicKeyInfo)userChain[0].getPublicKey ();
      return;
    }
    f.close ();
  }
}
