import java.lang.reflect.*;
import java.util.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.security.*;

import iaik.asn1.*;
import iaik.asn1.structures.*;
import iaik.pkcs.pkcs8.*;
import iaik.pkcs.pkcs10.*;

public class PKCS10Write
{
  static String
    fileName="CertReq.p10",
    lfileName="Nombre de Fichero",
    lalgorithm="Algoritmo de firma",
    title="Exportar una peticion de certificado",
    acept[]={"Aceptar"};
  String values[];

  public PKCS10Write (Authority auth)
  {
    UserInput userInput=new UserInput ();
    int i;

    values=auth.getSignersString ();
    if (values==null)
    {
	userInput.showDialog ("Error: No hay Algoritmos de firma disponibles.\n",acept);
	return;
    }
    values=userInput.getFromUser (title,lfileName,fileName,true,lalgorithm,values);
    if (values==null) return;
    try
    {
	auth.saveCertificateRequest (values[0],values[1]);
    }
    catch (InvalidKeyException e)
    {
	userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
    }
    catch (IOException e)
    {
	userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
    }
    catch (NoSuchAlgorithmException e)
    {
	userInput.showDialog ("Error: Algoritmo para firmar no disponible.\n"+e,acept);
    }
    catch (SignatureException e)
    {
	userInput.showDialog ("Error: No se puede firmar con el algoritmo elegido.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    KeyPairUser kpf=new KeyPairUser (a);
    SubjectUser sf=new SubjectUser (a);
    PKCS10Write p10f=new PKCS10Write (a);
    System.exit (0);
  }
}
