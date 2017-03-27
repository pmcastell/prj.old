import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class PKCS1Write
{
  static String
    title="Exportar un PKCS1",
    filename="privateKey.p1",
    acept[]={"Aceptar"};
  String
    labelField="Nombre de Fichero",
    optionField,
    value;

  public PKCS1Write (Authority auth)
  {
    UserInput userInput=new UserInput ();
    optionField=filename;
    String auxp;
    value=userInput.getFromUser (title,labelField,optionField,true);
    if (value==null) return;
    try
    {
      auth.savePrivateRSAKey (value);
    }
    catch (InstantiationException e)
    {
	userInput.showDialog ("Error: ASN1.\n"+e,acept);
    }
    catch (CodingException e)
    {
	userInput.showDialog ("Error: No es la clave esperada.\n"+e,acept);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    KeyPairUser kpf=new KeyPairUser (a);
    PKCS1Write p1u=new PKCS1Write (a);
    System.exit (0);
  }
}
