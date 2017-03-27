import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

import iaik.security.rsa.*;

public class PKCS1Read
{
  static String
    title="Leer la clave Privada",
    titleKey="Frase con la que se cifro la clave privada",
    fileName="privateKey.p1",
    lfileName="Nombre de Fichero",
    acept[]={"Aceptar"};
  String
    value;

  public PKCS1Read (Authority auth)
  {
    UserInput userInput=new UserInput ();
    value=userInput.getFromUser (title,lfileName,fileName,true);
    if (value==null) return;
    try
    {
      auth.readPrivateRSAKey (value);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
    }
    catch (CodingException e)
    {
	userInput.showDialog ("Error: Formato del fichero erroneo.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    PKCS1Read p1u=new PKCS1Read (a);
    System.exit (0);
  }
}
