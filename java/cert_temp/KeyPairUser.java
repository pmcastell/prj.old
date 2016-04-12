import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.security.*;
import java.lang.*;
import Authority;

public class KeyPairUser
{
  String
    title="Parametros del par de Claves",
    lalgorithm="Algoritmo",
    lbits="Numero de bits",
    values[],
    defaultBits="1024",
    acept[]={"Aceptar"};

  public KeyPairUser (Authority auth)
  {
    UserInput userInput=new UserInput ();
    String o;

    values=auth.getKeyPairGenerator ();
    if (values==null)
    {
	userInput.showDialog ("Error: No hay Algoritmos de clave publica y privada.\n",acept);
	return;
    }
    try
    {
	values=userInput.getFromUser (title,lbits,defaultBits,lalgorithm,values);
	if (values!=null) auth.generateKeyPair (values[1],Integer.parseInt (values[0]));
    }
    catch (NoSuchAlgorithmException e)
    {
        userInput.showDialog ("Error: Algoritmo no implementado.\n"+e,acept);
    }
    catch (NoSuchProviderException e)
    {
        userInput.showDialog ("Error: IAIK no encontrado.\n"+e,acept);
    }
    catch (Exception e)
    {
        userInput.showDialog ("Error: En los datos.\n"+e,acept);
    }
  }

  public static void main (String arg[])
  {
    Authority a=new Authority ();
    KeyPairUser kpf=new KeyPairUser (a);
    System.exit (0);
  }
}
