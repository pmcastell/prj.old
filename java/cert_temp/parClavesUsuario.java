import java.security.*;
/**
 * Esta Clase se utiliza para generar un par de claves de usuario.
 * Se lanza como un Thread independiente para que el usuario pueda
 * Cancelar la operación en cualquier momento, si lo desea.
 */
public class parClavesUsuario extends Thread {
   String title="Parametros del par de Claves",
           lalgorithm="Algoritmo: ",
           lbits="Numero de bits: ",
           values[],
           defaultBits="1024",
           acept[]={"Aceptar"};
   UserInput userInput;
   String o;
   terminador notiFin=null;



   private boolean terminado=false;
   private Authority autoridad;

   /**
   * Constructor.
   * @param auth autoridad sobre la que se generarán las claves de
   * usuario.
   */
   public parClavesUsuario (Authority auth) {
      super();
      this.autoridad=auth;
   }
   /**
   * Constructor.
   * @param auth autoridad sobre la que se generarán las claves de
   * usuario.
   * @param t objeto que implementa la interfaz terminador al que
   * se le 'notifica' la terminación del proceso
   */
   public parClavesUsuario (Authority auth, terminador t)
   {
      this(auth);
      notiFin=t;
   }
   /**
   * @return booleano indicando si se ha terminado con éxito
   * la generación del par de claves
   */

   public boolean getTerminado() {
      return terminado;
   }
   /**
   * Obtiene los datos necesarios para la generación de las claves
   */
   public boolean obtenerParametros()
   {
     values=autoridad.getKeyPairGenerator ();
     userInput=new UserInput();
     if (values==null) {
        userInput.showDialog ("Error: No hay Algoritmos de clave publica y "+
                              "privada.\n",acept);
        return false;
     }
     values=userInput.getFromUser (title,lbits,defaultBits,lalgorithm,values);
     if (values==null) {
        return false;
     }
     return true;
   }

   /**
   * Genera un par de claves pública/privadas
   */
   public void run() {
     try {
        if (values!=null) {
          autoridad.generateKeyPair (values[1],Integer.parseInt (values[0]));
          terminado=true;
        }
     } catch (NoSuchAlgorithmException e) {
        userInput.showDialog ("Error: Algoritmo no implementado.\n"+e,acept);
     } catch (NoSuchProviderException e) {
        userInput.showDialog ("Error: IAIK no encontrado.\n"+e,acept);
     } catch (Exception e) {
        userInput.showDialog ("Error: En los datos.\n"+e,acept);
     } finally {
        if (notiFin!=null) {
           notiFin.finaliza();
        }
     }
   }
}