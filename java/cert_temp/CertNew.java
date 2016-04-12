import java.io.*;
import java.util.*;
import java.text.*;
import java.security.*;
import java.security.cert.*;

import iaik.pkcs.*;
import iaik.asn1.*;

public class CertNew
{

  public CertNew (Authority auth)
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

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    KeyPairUser kpf=new KeyPairUser (a);
    SubjectUser sf=new SubjectUser (a);
    CertNew p12u=new CertNew (a);
    System.exit (0);
  }
}
