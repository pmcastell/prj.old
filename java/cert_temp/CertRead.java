import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class CertRead
{
  static String
    title="Importar un Certificado",
    fileName="Certificate.cert",
    acept[]={"Aceptar"};
  String
    lfileName="Nombre de Fichero",
    values;

  public CertRead (Authority auth)
  {
    UserInput userInput=new UserInput ();
    values=userInput.getFromUser (title,lfileName,fileName,true);
    if (values==null) return;
    try
    {
      auth.readCertificate (values);
    }
    catch (CertificateException e)
    {
	userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
    }
    catch (Exception e)
    {
        userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    CertRead p12u=new CertRead (a);
    System.exit (0);
  }
}
