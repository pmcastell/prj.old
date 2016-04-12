import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

import iaik.security.rsa.*;

public class PKCS8Read
{
  static String
    title="Leer la clave Privada",
    titleKey="Frase con la que se cifro la clave privada",
    fileName="privateKey.p8",
    lfileName="Nombre de Fichero",
    acept[]={"Aceptar"};
  String
    value;

  public PKCS8Read (Authority auth)
  {
    UserInput userInput=new UserInput ();
    while (true)
    {
      value=userInput.getFromUser (title,lfileName,fileName,true);
      if (value==null) return;
      auth.userPassword=userInput.getFromUserKey (titleKey);
      if (auth.userPassword!=null) break;
    }
    try
    {
      auth.readPrivateKey (value);
    }
    catch (NoSuchAlgorithmException e)
    {
	userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
    } 
    catch (GeneralSecurityException e)
    {
	userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
    }
    catch (InvalidKeyException e)
    {
	userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
    }

   catch (IOException e)
    {
	userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    PKCS8Read p8u=new PKCS8Read (a);
    System.exit (0);
  }
}
