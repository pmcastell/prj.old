import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class CertWrite
{
  static String
    title="Exportar un Certificado",
    filename="Certificate.cert",
    acept[]={"Aceptar"},
    optionFormat[]={"DER","PEM"};
  String
    labelField="Nombre de Fichero",
    labelChoice="Formato",
    optionChoice[],
    values[];

  public CertWrite (Authority auth)
  {
    UserInput userInput=new UserInput ();
    optionChoice=optionFormat;
    values=userInput.getFromUser (title,labelField,filename,true,labelChoice,optionChoice);
    if (values==null) return;
    if (values[1].equals ("PEM")) auth.format=ASN1.PEM;
    else auth.format=ASN1.DER;
    try
    {
      auth.saveCertificate (values[0]);
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
    SubjectUser sf=new SubjectUser (a);
    CertWrite p12u=new CertWrite (a);
    System.exit (0);
  }
}
