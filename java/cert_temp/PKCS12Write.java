import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;
public class PKCS12Write
{
  static String
    title="Exportar un PKCS12",
    filename="PKCS12.p12",
    titleKey="Frase para cifrar la clave privada",
    confirmKey="Confirmacion de la Frase",
    acept[]={"Aceptar"};
  String
    labelField="Nombre de Fichero",
    labelChoice="Formato",
    optionChoice[],
    values[];

  public PKCS12Write (Authority auth)
  {
    UserInput userInput=new UserInput ();
    optionChoice=Authority.PKCS12Formats;
    String auxp;
    while (true)
    {
      values=userInput.getFromUser (title,labelField,filename,true,labelChoice,optionChoice);
      if (values==null) return;
      if (values[1].equals (Authority.formatApache)) break;
      auth.userPassword=userInput.getFromUserKey (titleKey);
	if (auth.userPassword==null) continue;
      if (auth.userPassword.equals (""))
      {
        userInput.showDialog ("Error: ¡ Hay que escribir algo en la frase !\n",acept);
        continue;
      }
      auxp=userInput.getFromUserKey (confirmKey);
	if (auxp==null) continue;
      if (auth.userPassword.equals(auxp)) break;
      else userInput.showDialog ("Error: ¡ Las Frases no coinciden !\n",acept);
    }
    try
    {
      auth.saveKeyAndCert (values[0],values[1]);
    }
    catch (NoSuchAlgorithmException e)
    {
	userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
    }
    catch (PKCSException e)
    {
	userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
    }
    catch (CodingException e)
    {
	userInput.showDialog ("Error: Error de codificacion ASN1.\n"+e,acept);
    }
    catch (Exception ex)
    {}
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    KeyPairUser kpf=new KeyPairUser (a);
    SubjectUser sf=new SubjectUser (a);
    PKCS12Write p12u=new PKCS12Write (a);
    System.exit (0);
  }
}
