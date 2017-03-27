import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class PKCS12Read
{
  static String
    title="Importar un PKCS12",
    titleKey="Frase con la que se cifro la clave privada",
    fileName="PKCS12.p12",
    lfileName="Nombre de Fichero",
    lformat="Formato",
    acept[]={"Aceptar"};
  String
    formats[],
    values[];

  public PKCS12Read (Authority auth)
  {
    formats=auth.PKCS12Formats;
    UserInput userInput=new UserInput ();
    while (true)
    {
      values=userInput.getFromUser (title,lfileName,fileName,true,lformat,formats);
      if (values==null) return;
      auth.userPassword=userInput.getFromUserKey (titleKey);
	if (auth.userPassword!=null) break;
    }
    try
    {
      if (auth.userPassword.equals ("")) auth.userPassword=null;
      auth.readKeyAndCert (values[0],values[1]);
    }
    catch (NoSuchAlgorithmException e)
    {
        userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
    }
    catch (GeneralSecurityException e)
    {
        userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
    }
    catch (InvalidKeyException e)
    {
	userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
    }
    catch (PKCSException e)
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
    PKCS12Read p12u=new PKCS12Read (a);
    System.exit (0);
  }
}
