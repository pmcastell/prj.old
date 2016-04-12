import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class PKCS10Read
{
  static String
    title="Importar una peticion de certificado",
    fileName="CertReq.p10",
    lfileName="Nombre de Fichero",
    lformat="Formato",
    acept[]={"Aceptar"};
  String
    formats[],
    values[];

  public PKCS10Read (Authority auth)
  {
    formats=auth.PKCS10Formats;
    UserInput userInput=new UserInput ();
    values=userInput.getFromUser (title,lfileName,fileName,true,lformat,formats);
    if (values==null) return;
    try
    {
      auth.readCertificateRequest (values[0],values[1]);
    }
    catch (InvalidKeyException e)
    {
	userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
    }
    catch (PKCSParsingException e)
    {
        userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
    }
    catch (CodingException e)
    {
        userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
    }
    catch (Exception e)
    {
        userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    PKCS10Read p12u=new PKCS10Read (a);
    System.exit (0);
  }
}
