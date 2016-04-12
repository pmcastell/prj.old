import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class PKCS8Write
{
  static String
    title="Exportar un PKCS8",
    filename="privateKey.p8",
    titleKey="Frase para cifrar la clave privada",
    confirmKey="Confirmacion de la Frase",
    optionFormat[]={"DER","PEM"},
    acept[]={"Aceptar"};
  String
    labelField="Nombre de Fichero",
    labelChoice="Formato",
    optionField,
    value[];

  public PKCS8Write (Authority auth)
  {
    UserInput userInput=new UserInput ();
    optionField=filename;
    String auxp;
    while (true)
    {
      value=userInput.getFromUser (title,labelField,optionField,true,labelChoice,optionFormat);
      if (value==null) return;
      auth.userPassword=userInput.getFromUserKey (titleKey);
	if (auth.userPassword==null) continue;
      if (auth.userPassword.equals (""))
      {
        userInput.showDialog ("¡ Va a guardar la clave privada sin proteccion !\n",acept);
      }
      auxp=userInput.getFromUserKey (confirmKey);
	if (auxp==null) continue;
      if (auth.userPassword.equals (auxp))
        break;
      else
        userInput.showDialog ("Error: ¡ Las Frases no coinciden !\n",acept);
    }
    if (value[1].equals ("PEM")) auth.format=ASN1.PEM;
    else auth.format=ASN1.DER;
    try
    {
      auth.savePrivateKey (value[0]);
    }
    catch (NoSuchAlgorithmException e)
    {
	userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
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
    PKCS8Write p8u=new PKCS8Write (a);
    System.exit (0);
  }
}
