import java.awt.*;
import java.awt.event.*;

import iaik.asn1.*;
import iaik.asn1.structures.*;

public class SubjectUser
{
  String
    names[]={"Pais","Estado o Provincia","Organizacion","Departamento","Email","Nombre"},
    values[]={"ES","Andalucia","CICA","Certification Services","sec-team@cica.es","CAcica"},
    title="Datos de Usuario o Autoridad de Certificacion",
    acept[]={"Aceptar"};

  public SubjectUser (Authority auth)
  {
    UserInput userInput=new UserInput ();
    if (auth.userName!=null)
    {
	if (auth.userName.getRDN (ObjectID.country)!=null) values[0]=auth.userName.getRDN (ObjectID.country);
	if (auth.userName.getRDN (ObjectID.stateOrProvince)!=null) values[1]=auth.userName.getRDN (ObjectID.stateOrProvince);
	if (auth.userName.getRDN (ObjectID.organization)!=null) values[2]=auth.userName.getRDN (ObjectID.organization);
	if (auth.userName.getRDN (ObjectID.organizationalUnit)!=null) values[3]=auth.userName.getRDN (ObjectID.organizationalUnit);
	if (auth.userName.getRDN (ObjectID.emailAddress)!=null) values[4]=auth.userName.getRDN (ObjectID.emailAddress);
	if (auth.userName.getRDN (ObjectID.commonName)!=null) values[5]=auth.userName.getRDN (ObjectID.commonName);
    }
    values=userInput.getFromUser (title,names,values);
    if (values==null) return;
    auth.userName=new Name ();
    auth.userName.addRDN (ObjectID.country,values[0]);
    auth.userName.addRDN (ObjectID.stateOrProvince,values[1]);
    auth.userName.addRDN (ObjectID.organization,values[2]);
    auth.userName.addRDN (ObjectID.organizationalUnit,values[3]);
    auth.userName.addRDN (ObjectID.commonName,values[5]);
    auth.userName.addRDN (ObjectID.emailAddress,values[4]);
  }

/*  public static void main (String arg[])
  {
    Authority a=new Authority ();
    SubjectUser sf=new SubjectUser (a);
    System.exit (0);
  }*/
}
