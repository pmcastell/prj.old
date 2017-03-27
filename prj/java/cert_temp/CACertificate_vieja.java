import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;
import iaik.asn1.structures.*;
import iaik.utils.*;

public class CACertificate_vieja
{
  static String
    opt1[]={"Aceptar"},
    opt2[]={"Aceptar","Cancelar"},
    options[]
    =
    {
	"Datos de usuario",
	"Generar par de claves de usuario",
	"Crear un certificado de usuario",
	"Importar certificado",
	"Importar PKCS1",
	"Importar PKCS8",
	"Importar PKCS10",
	"Importar PKCS12",
	"Exportar un certificado",
      "Exportar un PKCS1",
	"Exportar un PKCS8",
	"Exportar un PKCS10",
	"Exportar un PKCS12",
	"Borrar el ultimo certificado",
	"Verificar certificados",
	"Utilizar usuario como CA",
	"Utilizar CA como usuario",
	"Salir"
    };

  public CACertificate (Authority auth)
  {
    StringBuffer text;
    int value,i;
    UserInput userInput=new UserInput ();
   for (;;)
   {
    text=new StringBuffer ("Autoridad de certificacion.\n");
    if (auth.caName!=null)
    {
      text.append ("  Pais: ");
	text.append (auth.caName.getRDN (ObjectID.country)+"\n");
      text.append ("  Estado o Provincia: ");
	text.append (auth.caName.getRDN (ObjectID.stateOrProvince)+"\n");
	text.append ("  Organizacion: ");
	text.append (auth.caName.getRDN (ObjectID.organization)+"\n");
	text.append ("  Departamento: ");
	text.append (auth.caName.getRDN (ObjectID.organizationalUnit)+"\n");
	text.append ("  Email: ");
	text.append (auth.caName.getRDN (ObjectID.emailAddress)+"\n");
	text.append ("  Nombre: ");
	text.append (auth.caName.getRDN (ObjectID.commonName)+"\n");
    }
    else
    {
	text.append ("  No presente.\n");
    }
    if (auth.caPublicKey!=null)
    {
	text.append ("  Clave: ");
	text.append (auth.caPublicKey.getAlgorithm ()+"\n");
	if (auth.caChain!=null)
	{
	  text.append ("  Firmas:\n");
	  for (i=0;i<auth.caChain.length;i++)
	    text.append ("    "+((Name)auth.caChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+"\n");
	}
    }
    text.append ("Usuario.\n");
    if (auth.userName!=null)
    {
        text.append ("  Pais: ");
        text.append (auth.userName.getRDN (ObjectID.country)+"\n");
        text.append ("  Estado o Provincia: ");
	  text.append (auth.userName.getRDN (ObjectID.stateOrProvince)+"\n");
        text.append ("  Organizacion: ");
        text.append (auth.userName.getRDN (ObjectID.organization)+"\n");
        text.append ("  Departamento: ");
        text.append (auth.userName.getRDN (ObjectID.organizationalUnit)+"\n");
        text.append ("  Email: ");
        text.append (auth.userName.getRDN (ObjectID.emailAddress)+"\n");
        text.append ("  Nombre:");
        text.append (auth.userName.getRDN (ObjectID.commonName)+"\n");
    }
    else
    {
        text.append ("  No presente.\n");
    }
    if (auth.userPublicKey!=null)
    {
        text.append ("  Clave: ");
        text.append (auth.userPublicKey.getAlgorithm ()+"\n");
        if (auth.userChain!=null)
        {
          text.append ("  Firmas:\n");
          for (i=0;i<auth.userChain.length;i++)
            text.append ("    "+((Name)auth.userChain[i].getIssuerDN ()).getRDN (ObjectID.commonName)+" "+Util.toString (auth.userChain[i].getFingerprint ())+"\n");
        }
    }
    value=userInput.showDialog (options,text.toString ());
    switch (value)
    {
	case 0:
	  new SubjectUser (auth);
	  break;
	case 1:
	  new KeyPairUser (auth);
	  break;
	case 2:
          if (auth.userName==null || auth.userPublicKey==null || (auth.caPrivateKey==null && auth.userPrivateKey==null))
	    userInput.showDialog ("Es necesario tener los datos de usuario junto con su clave publica\ny una clave privada para firmar.",opt1);
	  else
	    new CertNew (auth);
	  break;
	case 3:
	  new CertRead (auth);
	  break;
	case 4:
	  new PKCS1Read (auth);
	  break;
	case 5:
	  new PKCS8Read (auth);
	  break;
	case 6:
	  new PKCS10Read (auth);
          break;
	case 7:
	  new PKCS12Read (auth);
          break;
	case 8:
          if (auth.userChain==null)
            userInput.showDialog ("Es necesario tener un certificado del usuario.",opt1);
          else
	    new CertWrite (auth);
	  break;
      case 9:
          if (auth.userPrivateKey==null)                                      
            userInput.showDialog ("Es necesario tener la clave privada del usuario.",opt1);
          else            
	    new PKCS1Write (auth);
          break;
	case 10:
          if (auth.userPrivateKey==null)                                      
            userInput.showDialog ("Es necesario tener la clave privada del usuario.",opt1);
          else            
	    new PKCS8Write (auth);
          break;
      case 11:
          if (auth.userName==null || auth.userPublicKey==null || auth.userPrivateKey==null)          
            userInput.showDialog ("Es necesario tener los datos de usuario junto con su clave publica\ny privada para firmar.",opt1);
          else    
	    new PKCS10Write (auth);
          break;
      case 12:
/*          if (auth.userName==null || auth.userPublicKey==null || (auth.caPrivateKey==null && auth.userPrivateKey==null) || auth.userChain==null)
            userInput.showDialog ("Es necesario tener los datos de usuario\njunto con sus claves y certificados.",opt1);
          else
*/	    new PKCS12Write (auth);
          break;
      case 13:
	  if (auth.userChain==null)
	    userInput.showDialog ("No hay una cadena de certificados para el usuario.",opt1);
        else if (auth.userChain.length==1)
          auth.userChain=null;
        else
          auth.deleteCertificate ();
        break; 
	case 14:
	  if (auth.userChain==null)
	    userInput.showDialog ("No hay una cadena de certificados para el usuario.",opt1);
	  else if (auth.verifyCertificateChain ())
        {
          if (auth.isTrusted ())
	      userInput.showDialog ("La cadena de certificados para el usuario es correcta\ny la autoridad de certificacion es conocida y valida.",opt1);            
          else
	      userInput.showDialog ("La cadena de certificados para el usuario es correcta\naunque la autoridad de certificacion es desconocida.",opt1);
        }
	  break;
	case 15:
	  if (auth.userName==null || auth.userPublicKey==null || auth.userPrivateKey==null)
	    userInput.showDialog ("Es necesario tener los datos de usuario\njunto con las claves publica y privada",opt1);
	  else
          auth.userToCa ();
	  break;
	case 16:
          if (auth.caName==null || auth.caPublicKey==null || auth.caPrivateKey==null)
            userInput.showDialog ("Es necesario tener los datos de la CA\njunto con las claves publica y privada",opt1);
          else
            auth.caToUser ();
	  break;
	case -1:
	case 17:
    System.exit(0);
	  value=userInput.showDialog ("Va a Salir de la aplicacion.",opt2);
	  if (value==0) return;
	  break;
    }
   }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    new CACertificate (a);
    System.exit (0);
  }
}
