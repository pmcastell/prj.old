
import java.text.*;
import java.util.*;
import java.security.*;
import java.security.cert.*;
import java.io.*;
import iaik.pkcs.*;
import iaik.asn1.structures.*;
import iaik.asn1.*;

/**
 * Esta clase incorpora utilidades para leer y guardar PKCS's
 * en ficheros
 */
public class pkcsUtil {
  /**
   * Transforma un Distinguished Name a String
   * @param n Distinguished Name a transformar
   * @return Una cadena con todos los campos del Distinguished Name
   */
  public static String rdnToString(Name n)
  {
     String res=null;
     StringBuffer text=new StringBuffer();

     text.append("                            País: ");
     text.append(getRDN_Campo(n, ObjectID.country)+"\n");

     text.append(" Estado o Provincia: ");
     text.append(getRDN_Campo(n,ObjectID.stateOrProvince)+"\n");

     text.append("           Organización: ");
     text.append(getRDN_Campo(n,ObjectID.organization)+"\n");

     text.append("         Departamento: ");
     text.append(getRDN_Campo(n,ObjectID.organizationalUnit)+"\n");

     text.append("                     Nombre: ");
     text.append(getRDN_Campo(n,ObjectID.commonName)+"\n");

     text.append("                         Email: ");
     text.append(getRDN_Campo(n,ObjectID.emailAddress)+"\n");
     return text.toString();

  }
  /**
   * Toma los datos del usuario con el que se va a trabajar
   * @param auth autoridad que certifica
   */
  public static void subjectUser(Name userName)
  {
    UserInput userInput=new UserInput ();
    String values[]={"ES",
                     "Andalucia",
                     "CICA",
                     "Certification Services",
                     "CAcica",
                     "sec-team@cica.es"},
           título="Datos de Usuario o Autoridad de Certificacion",
           nombres[]={"País: ",
                      "Estado o Provincia: ",
                      "Organización: ",
                      "Departamento: ",
                      "Nombre: ",
                      "Email: "};


    if (userName!=null) {
       if (userName.getRDN (ObjectID.country)!=null) {
          values[0]=userName.getRDN (ObjectID.country);
       }
       if (userName.getRDN (ObjectID.stateOrProvince)!=null) {
          values[1]=userName.getRDN (ObjectID.stateOrProvince);
       }
       if (userName.getRDN (ObjectID.organization)!=null) {
          values[2]=userName.getRDN (ObjectID.organization);
       }
       if (userName.getRDN (ObjectID.organizationalUnit)!=null) {
          values[3]=userName.getRDN (ObjectID.organizationalUnit);
       }
       if (userName.getRDN (ObjectID.commonName)!=null) {
          values[4]=userName.getRDN (ObjectID.commonName);
       }
       if (userName.getRDN (ObjectID.emailAddress)!=null) {
          values[5]=userName.getRDN (ObjectID.emailAddress);
       }
    }
    values=userInput.getFromUser (título,nombres,values);
    if (values==null) {
       return;
    }
    if (userName.getRDN (ObjectID.country)!=null) {
       userName.removeRDN (ObjectID.country);
    }
    userName.addRDN (ObjectID.country,values[0]);
    if (userName.getRDN (ObjectID.stateOrProvince)!=null) {
       userName.removeRDN (ObjectID.stateOrProvince);
    }
    userName.addRDN (ObjectID.stateOrProvince,values[1]);
    if (userName.getRDN (ObjectID.organization)!=null) {
       userName.removeRDN (ObjectID.organization);
    }
    userName.addRDN (ObjectID.organization,values[2]);
    if (userName.getRDN (ObjectID.organizationalUnit)!=null) {
       userName.removeRDN (ObjectID.organizationalUnit);
    }
    userName.addRDN (ObjectID.organizationalUnit,values[3]);
    if (userName.getRDN (ObjectID.commonName)!=null) {
       userName.removeRDN (ObjectID.commonName);
    }
    userName.addRDN (ObjectID.commonName,values[4]);
    if (userName.getRDN (ObjectID.emailAddress)!=null) {
       userName.removeRDN (ObjectID.emailAddress);
    }
    userName.addRDN (ObjectID.emailAddress,values[5]);
  }

  /**
   * Devuelve un 'Campo' de un R. Distinguished Name
   * @param n de tipo Name que contiene el RDN
   * @param campo de tipo ObjectID y representa el campo que queremos
   * @return el 'Campo' indicado
   */
  public static String getRDN_Campo(Name n, ObjectID campo)
  {
     String res="";
     if (n!=null) {
        res=n.getRDN(campo);
        if (res==null) {
           res="";
        }
     }
     return res;
  }
  /**
  * Crea un nuevo certificado
  */
  public static void nuevoCertificado(Authority auth)
  {
    String title="Generar un Certificado",
           acept[]={"Aceptar"},
           optionDate[]={"Anual","Mensual","Diario"},
           labelsField[]={"Comienzo de validez: ","Numero de periodos: "},
           optionField[],
           labelsChoice[]={"Tipo de periodo: ","Algoritmo de firma: "},
           optionChoice[][]=new String[2][],
           values[];
    UserInput userInput=new UserInput ();
    optionChoice[0]=optionDate;
    optionChoice[1]=auth.getSignersString ();
    if (optionChoice[1]==null) {
      userInput.showDialog ("Error: No hay Algoritmos de firma no disponibles.\n",acept);
 	   return;
    }
    optionField=new String[2];
    SimpleDateFormat df=new SimpleDateFormat ("dd/MM/yyyy");
    optionField[0]=df.format (new Date ());
    optionField[1]="1";
    values=userInput.getFromUser (title,labelsField,optionField,labelsChoice,optionChoice );
    if (values==null) {
       return;
    }
    try {
      Date d=df.parse (values[0],new ParsePosition (0));
      GregorianCalendar gc=new GregorianCalendar ();
      gc.setTime (d);
      if (values[2].equals (optionDate[0])) {
        gc.add (Calendar.YEAR,Integer.parseInt (values[1]));
      } else if (values[2].equals (optionDate[1])) {
        gc.add (Calendar.MONTH,Integer.parseInt (values[1]));
      } else if (values[2].equals (optionDate[2])) {
        gc.add (Calendar.DATE,Integer.parseInt (values[1]));
      }
      auth.createCertificate (values[3],d,gc.getTime ());
    } catch (NoSuchAlgorithmException e) {
	    userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
    } catch (CertificateException e) {
  	    userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
    } catch (InvalidKeyException e) {
        userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
    } catch (Exception e) {
        userInput.showDialog ("Error: En los datos.\n"+e,acept);
    }
  }

  /**
  * Lee un certificado de un fichero
  */
  public static void leerCertificado(Authority auth)
  {
     String
       title="Importar un Certificado",
       fileName="Certificate.cert",
       acept[]={"Aceptar"},
       lfileName="Nombre de Fichero",
       values;
     UserInput userInput=new UserInput ();
     values=userInput.getFromUser (title,lfileName,fileName,true);
     if (values==null) {
        return;
     }
     try {
        auth.readCertificate (values);
     } catch (CertificateException e) {
        userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
     } catch (IOException e) {
        userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
     } catch (Exception e) {
         userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
     }
  }

  /**
  * Lee un PKCS1
  */
  public static void leerPKCS1(Authority auth)
  {
    String
      title="Leer la clave Privada",
      titleKey="Frase con la que se cifro la clave privada",
      fileName="privateKey.p1",
      lfileName="Nombre de Fichero",
      acept[]={"Aceptar"},
      value;
    UserInput userInput=new UserInput ();
    value=userInput.getFromUser (title,lfileName,fileName,true);
    if (value==null) {
       return;
    }
    try {
      auth.readPrivateRSAKey (value);
    } catch (IOException e){
 	    userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
    } catch (CodingException e) {
	    userInput.showDialog ("Error: Formato del fichero erroneo.\n"+e,acept);
    }
  }

  /**
  * Lee un PKCS8
  */
  public static void leerPKCS8(Authority auth)
  {
     String
        title="Leer la clave Privada",
        titleKey="Frase con la que se cifro la clave privada",
        fileName="privateKey.p8",
        lfileName="Nombre de Fichero",
        acept[]={"Aceptar"},
        value;
     UserInput userInput=new UserInput ();
     while (true) {
       value=userInput.getFromUser (title,lfileName,fileName,true);
       if (value==null) {
          return;
       }
       auth.userPassword=userInput.getFromUserKey (titleKey);
       if (auth.userPassword!=null) {
          break;
       }
     }
     try {
       auth.readPrivateKey (value);
     } catch (NoSuchAlgorithmException e) {
       userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
     } catch (InvalidKeyException e) {
       userInput.showDialog ("Error: Clave no valida.\n"+e,acept);       
     } catch (GeneralSecurityException e) {
       userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
     } catch (IOException e) {
       userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
     }
  }

  /**
  * Lee un PKCS10
  */
  public static void leerPKCS10(Authority auth)
  {
     String
       title="Importar una peticion de certificado",
       fileName="CertReq.p10",
       lfileName="Nombre de Fichero",
       lformat="Formato",
       acept[]={"Aceptar"},
       formats[],
       values[];
     formats=auth.PKCS10Formats;
     UserInput userInput=new UserInput ();
     values=userInput.getFromUser (title,lfileName,fileName,true,lformat,formats);
     if (values==null) {
        return;
     }
     try {
       auth.readCertificateRequest (values[0],values[1]);
     } catch (InvalidKeyException e) {
       userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
     } catch (PKCSParsingException e) {
       userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     } catch (CodingException e) {
       userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     } catch (IOException e) {
       userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
     } catch (Exception e) {
         userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     }
  }

  /**
  * Lee un PKCS12
  */

  public static void leerPKCS12 (Authority auth)
  {
     String
       title="Importar un PKCS12",
       titleKey="Frase con la que se cifro la clave privada",
       fileName="PKCS12.p12",
       lfileName="Nombre de Fichero",
       lformat="Formato",
       acept[]={"Aceptar"},
       formats[],
       values[];
     formats=auth.PKCS12Formats;
     UserInput userInput=new UserInput ();
     while (true) {
       values=userInput.getFromUser (title,lfileName,fileName,true,lformat,formats);
       if (values==null) {
          return;
       }
       auth.userPassword=userInput.getFromUserKey (titleKey);
       if (auth.userPassword!=null) {
          break;
       }
     }
     try {
       if (auth.userPassword.equals ("")) {
          auth.userPassword=null;
       }
       auth.readKeyAndCert (values[0],values[1]);
     } catch (NoSuchAlgorithmException e) {
       userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
     } catch (InvalidKeyException e) {
       userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
     } catch (GeneralSecurityException e) {
       userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);       
     } catch (PKCSException e) {
       userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     } catch (CodingException e) {
       userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     } catch (IOException e){
       userInput.showDialog ("Error: Leyendo el fichero.\n"+e,acept);
     } catch (Exception e) {
         userInput.showDialog ("Error: Formato del fichero no valido.\n"+e,acept);
     }
  }
  /**
  * Guarda un Certificado en un fichero
  */
  public static void escribirCert(Authority auth)
  {
     String
        title="Exportar un Certificado",
        filename="Certificate.cert",
        acept[]={"Aceptar"},
        optionFormat[]={"DER","PEM"},
        labelField="Nombre de Fichero",
        labelChoice="Formato",
        optionChoice[],
        values[];
     UserInput userInput=new UserInput ();
     optionChoice=optionFormat;
     values=userInput.getFromUser (title,labelField,filename,true,labelChoice,optionChoice);
     if (values==null) {
        return;
     }
     if (values[1].equals ("PEM")) {
        auth.format=ASN1.PEM;
     } else {
        auth.format=ASN1.DER;
     }
     try {
       auth.saveCertificate (values[0]);
     } catch (IOException e) {
       userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
     }
  }
  /**
  * Guarda un PKCS1 en un fichero
  */
  public static void escribirPKCS1 (Authority auth)
  {
     String
       title="Exportar un PKCS1",
       filename="privateKey.p1",
       acept[]={"Aceptar"},
       labelField="Nombre de Fichero",
       optionField,
       value;
     UserInput userInput=new UserInput ();
     optionField=filename;
     String auxp;
     value=userInput.getFromUser (title,labelField,optionField,true);
     if (value==null) {
        return;
     }
     try {
       auth.savePrivateRSAKey (value);
     } catch (InstantiationException e) {
       userInput.showDialog ("Error: ASN1.\n"+e,acept);
     } catch (CodingException e) {
       userInput.showDialog ("Error: No es la clave esperada.\n"+e,acept);
     } catch (IOException e) {
       userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
     }
  }
  /**
  * Guarda un PKCS8 en un fichero
  */   
  public static void escribirPKCS8 (Authority auth)
  {
     String
       title="Exportar un PKCS8",
       filename="privateKey.p8",
       titleKey="Frase para cifrar la clave privada",
       confirmKey="Confirmacion de la Frase",
       optionFormat[]={"DER","PEM"},
       acept[]={"Aceptar"},
       labelField="Nombre de Fichero",
       labelChoice="Formato",
       optionField,
       value[];
     UserInput userInput=new UserInput ();
     optionField=filename;
     String auxp;
     while (true) {
       value=userInput.getFromUser (title,labelField,optionField,true,labelChoice,optionFormat);
       if (value==null) {
          return;
       }
       auth.userPassword=userInput.getFromUserKey (titleKey);
       if (auth.userPassword==null) {
          continue;
       }
       if (auth.userPassword.equals ("")) {
         userInput.showDialog ("¡ Va a guardar la clave privada sin proteccion !\n",acept);
       }
       auxp=userInput.getFromUserKey (confirmKey);
       if (auxp==null) {
          continue;
       }
       if (auth.userPassword.equals (auxp)) {
          break;
       } else {
          userInput.showDialog ("Error: ¡ Las Frases no coinciden !\n",acept);
       }
     }
     if (value[1].equals ("PEM")) {
        auth.format=ASN1.PEM;
     } else {
        auth.format=ASN1.DER;
     }
     try {
       auth.savePrivateKey (value[0]);
     } catch (NoSuchAlgorithmException e) {
       userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
     } catch (IOException e) {
       userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
     }
  }
  /**
  * Guarda un PKCS10 en un fichero
  */
  public static void escribirPKCS10 (Authority auth)
  {
     String
       fileName="CertReq.p10",
       lfileName="Nombre de Fichero",
       lalgorithm="Algoritmo de firma",
       title="Exportar una peticion de certificado",
       acept[]={"Aceptar"},
       values[];
     UserInput userInput=new UserInput ();
     int i;

     values=auth.getSignersString ();
     if (values==null) {
        userInput.showDialog ("Error: No hay Algoritmos de firma disponibles.\n",acept);
        return;
     }
     values=userInput.getFromUser (title,lfileName,fileName,true,lalgorithm,values);
     if (values==null) {
        return;
     }
     try {
        auth.saveCertificateRequest (values[0],values[1]);
     } catch (InvalidKeyException e) {
        userInput.showDialog ("Error: Clave no valida.\n"+e,acept);
     } catch (IOException e) {
        userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
     } catch (NoSuchAlgorithmException e) {
        userInput.showDialog ("Error: Algoritmo para firmar no disponible.\n"+e,acept);
     } catch (SignatureException e) {
        userInput.showDialog ("Error: No se puede firmar con el algoritmo elegido.\n"+e,acept);
     }
  }
  /**
  * Guarda un PKCS12 en un fichero
  */
  public static void escribirPKCS12(Authority auth)
  {
     String
       title="Exportar un PKCS12",
       filename="PKCS12.p12",
       titleKey="Frase para cifrar la clave privada",
       confirmKey="Confirmacion de la Frase",
       acept[]={"Aceptar"},
       labelField="Nombre de Fichero",
       labelChoice="Formato",
       optionChoice[],
       values[];
     UserInput userInput=new UserInput ();
     optionChoice=Authority.PKCS12Formats;
     String auxp;
     while (true) {
       values=userInput.getFromUser (title,labelField,filename,true,labelChoice,optionChoice);
       if (values==null) {
          return;
       }
       if (values[1].equals (Authority.formatApache)) {
          break;
       }
       auth.userPassword=userInput.getFromUserKey (titleKey);
       if (auth.userPassword==null) {
          continue;
       }
       if (auth.userPassword.equals ("")) {
         userInput.showDialog ("Error: ¡ Hay que escribir algo en la frase !\n",acept);
         continue;
       }
       auxp=userInput.getFromUserKey (confirmKey);
       if (auxp==null) {
          continue;
       }
       if (auth.userPassword.equals(auxp)) {
          break;
       } else {
          userInput.showDialog ("Error: ¡ Las Frases no coinciden !\n",acept);
       }
     }
     try {
       auth.saveKeyAndCert (values[0],values[1]);
     } catch (NoSuchAlgorithmException e) {
       userInput.showDialog ("Error: Algoritmo no disponible.\n"+e,acept);
     } catch (IOException e) {
       userInput.showDialog ("Error: Escribiendo el fichero.\n"+e,acept);
     } catch (PKCSException e) {
       userInput.showDialog ("Error: No se pudo crear el certificado.\n"+e,acept);
     } catch (CodingException e) {
       userInput.showDialog ("Error: Error de codificacion ASN1.\n"+e,acept);
     } catch (Exception ex) {
     }
  }
}