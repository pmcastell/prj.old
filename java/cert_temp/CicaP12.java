
import java.io.*;
import java.security.*;

import iaik.asn1.*;
import iaik.asn1.structures.*;
import iaik.pkcs.*;
import iaik.pkcs.pkcs12.*;
import iaik.pkcs.pkcs8.*;
import iaik.x509.*;
import iaik.utils.*;

public class CicaP12
{
  private EncryptedPrivateKeyInfo privateKey=null;
  private X509Certificate certificateChain[]=null,certificateIssuer[]=null;
  private int version=3;
  public static final String
    startLine="-----BEGIN CICAP12-----",
    endLine="-----END CICAP12-----";

  public CicaP12 (PrivateKeyInfo pki,X509Certificate chain[],
                  X509Certificate issuer[])
  {
    privateKey=new EncryptedPrivateKeyInfo (pki);
    certificateChain=chain;
    certificateIssuer=issuer;
  }

  public CicaP12 (PrivateKeyInfo pki,X509Certificate chain[])
  {
    privateKey=new EncryptedPrivateKeyInfo (pki);
    certificateChain=chain;
  }

  public CicaP12 (PrivateKeyInfo pki)
  {
    privateKey=new EncryptedPrivateKeyInfo (pki);
  }

  public CicaP12 (EncryptedPrivateKeyInfo pki,X509Certificate chain[],
                  X509Certificate issuer[])
  {
    privateKey=pki;
    certificateChain=chain;
    certificateIssuer=issuer;
  }

  public CicaP12 (EncryptedPrivateKeyInfo pki,X509Certificate chain[])
  {
    privateKey=pki;
    certificateChain=chain;
  }

  public CicaP12 (EncryptedPrivateKeyInfo pki)
  {
    privateKey=pki;
  }

  public CicaP12 (byte b[]) throws PKCSException,CodingException
  {
    create (new ASN1 (b),null);
  }

  public CicaP12 (byte b[],String p) throws PKCSException,CodingException
  {
    create (new ASN1 (b),p);
  }

  public CicaP12 (ASN1 p) throws PKCSException
  {
    create (p,null);
  }

  public CicaP12 (ASN1 pk,String p) throws PKCSException
  {
    create (pk,p);
  }

  public CicaP12 (InputStream f) throws PKCSException,CodingException,
                                        IOException
  {
    ASN1 pkcs12=new ASN1 (f);
    create (pkcs12,null);
  }

  public CicaP12 (InputStream f,String p) throws PKCSException,CodingException,
                                                 IOException
  {
    ASN1 pkcs12=new ASN1 (f);
    create (pkcs12,p);
  }

  public void setCertificateIssuer (X509Certificate issuer[])
  {
    certificateIssuer=issuer;
  }

  public void setCertificateChain (X509Certificate chain[])
  {
    certificateIssuer=chain;
  }

  public void setEncryptedPrivateKey (EncryptedPrivateKeyInfo pki)
  {
    privateKey=pki;
  }

  public void setPrivateKey (PrivateKeyInfo pki)
  {
    privateKey=new EncryptedPrivateKeyInfo (pki);
  }

  public Name[] getIssuers ()
  {
    if (certificateIssuer==null) return null;
    Name r[]=new Name[certificateIssuer.length];
    for (int i=0;i<r.length;i++) r[i]=(Name)certificateIssuer[i].getSubjectDN ();
    return r;
  }

  public X509Certificate[] getCertificateIssuer ()
  {
    return certificateIssuer;
  }

  public X509Certificate[] getCertificateChain ()
  {
    return certificateChain;
  }

  public PrivateKeyInfo getPrivateKey ()
  {
    return privateKey.getPrivateKeyInfo ();
  }

  public EncryptedPrivateKeyInfo getEncryptedPrivateKey ()
  {
    return privateKey;
  }

  public void encrypt (String p) throws PKCSException,NoSuchAlgorithmException
  {
    privateKey.encrypt (p,AlgorithmID.pbeWithSHAAnd40BitRC2_CBC,null);
  }

  public void encrypt (String p,AlgorithmID a) throws PKCSException,NoSuchAlgorithmException
  {
    privateKey.encrypt (p,a,null);
  }

  public void decrypt (String p) throws PKCSException,GeneralSecurityException,NoSuchAlgorithmException
  {
    privateKey.decrypt (p);
  }

  public byte[] toByteArray () throws PKCSException,CodingException
  {
    return new ASN1 (toASN1Object ()).toByteArray ();
  }

  public void writeTo (String f,int t) throws PKCSException,CodingException,IOException
  {
    FileOutputStream os=new FileOutputStream (f);
    writeTo (os,t);
  }

  public void writeTo (OutputStream os,int t) throws PKCSException,CodingException,IOException
  {
    if (t==ASN1.DER)
      new ASN1 (toASN1Object ()).writeTo (os);
    else
    {
      os.write ((startLine+"\n").getBytes ());
      Base64OutputStream b64f=new Base64OutputStream (os);
      b64f.write (toByteArray ());
      b64f.flush ();
      os.write (("\n"+endLine+"\n").getBytes ());
      os.flush ();
    }
  }

  public ASN1Object toASN1Object () throws PKCSException,CodingException
  {
    SEQUENCE p12=new SEQUENCE ();
    p12.addComponent (new INTEGER (version));
    SafeBag sb[]=new SafeBag[1];
    try
    {
      sb[0]=new SafeBag (privateKey);
      p12.addComponent (new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb).toASN1Object ());
    }
    catch (NullPointerException e)
    {
      sb[0]=new SafeBag (privateKey.getPrivateKeyInfo ());
      p12.addComponent (new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb).toASN1Object ());
    }
    int i;
    if (certificateChain!=null)
    {
      sb=new SafeBag[certificateChain.length];
      for (i=0;i<sb.length;i++)
        sb[i]=new SafeBag (certificateChain[i]);
      p12.addComponent (new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb).toASN1Object ());
    }
    if (certificateIssuer!=null)
    {
      sb=new SafeBag[certificateIssuer.length];
      for (i=0;i<sb.length;i++)
        sb[i]=new SafeBag (certificateIssuer[i]);
      p12.addComponent (new AuthenticatedSafe (AuthenticatedSafe.UNENCRYPTED,sb).toASN1Object ());
    }
    return p12;
  }

  private void create (ASN1 pkcs12,String p) throws PKCSException
  {
    try
    {
      AuthenticatedSafe as[]=new AuthenticatedSafe[pkcs12.countComponents ()-1];
      int i;
      for (i=0;i<as.length;i++)
        as[i]=new AuthenticatedSafe (pkcs12.getComponentAt(i+1));
      SafeBag sb[][]=new SafeBag[as.length][];
      for (i=0;i<sb.length;i++) sb[i]=as[i].getSafeBags ();
      if (sb[0][0].getBagType ().equals (ObjectID.pkcs12_pkcs8ShroudedKeyBag))
      {
        privateKey=new EncryptedPrivateKeyInfo
          (sb[0][0].getBagContent ().toASN1Object ());
        if (p!=null) privateKey.decrypt (p);
      }
      else if (sb[0][0].getBagType ().equals (ObjectID.pkcs12_keyBag))
      {
        privateKey=new EncryptedPrivateKeyInfo
          (PrivateKeyInfo.getPrivateKeyInfo (sb[0][0].getBagContent ().toASN1Object ()));
      }
      else throw new PKCSException ("Bad format CicaP12 in PrivateKeyInfo.");
      if (sb.length==1)
      {
        certificateChain=null;
        certificateIssuer=null;
        return;
      }
      certificateChain=new X509Certificate[sb[1].length];
      for (i=0;i<certificateChain.length;i++)
      {
        if (sb[1][i].getBagType ().equals (ObjectID.pkcs12_certBag))
        {
          certificateChain[i]=new X509Certificate ();
          certificateChain[i].decode (sb[1][i].getBagContent ().toASN1Object ());
        }
        else throw new PKCSException ("Bad format CicaP12 in certificateChain.");
      }
      if (sb.length==2)
      {
        certificateIssuer=null;
        return;
      }
      certificateIssuer=new X509Certificate[sb[2].length];
      for (i=0;i<certificateIssuer.length;i++)
      {
        if (sb[2][i].getBagType ().equals (ObjectID.pkcs12_certBag))
        {
          certificateIssuer[i]=new X509Certificate ();
          certificateIssuer[i].decode (sb[2][i].getBagContent ().toASN1Object ());
        }
        else throw new PKCSException ("Bad format CicaP12 in certificateIssuer.");
      }
    }
    catch (Exception e)
    {
      throw new PKCSException ("Bad format CicaP12.");
    }
  }
}

