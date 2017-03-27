import java.awt.event.*;
import terminador;
/**
 * Esta Clase se utiliza para mostrar un mensaje.
 * Se lanza como un Thread independiente para que no bloquee al
 * programa que la lanza, y así poder controlar simultáneamente
 * el resultado de otros Threads.
 */
public class mensaThread extends Thread implements terminador {
   String mensaje;
   String botones[];
   private boolean terminado=false;
   private int resultado=-1;
   private UserInput ui=new UserInput();

   /**
   * Constructor.
   * @param mensa mensaje a mostrar
   * @param botones matriz de String's que indica los botones a mostrar
   */
   public mensaThread (String mensaje, String[]botones) {
      super();
      this.mensaje=mensaje;
      this.botones=botones;
   }

   /**
    * Indica si se ha salido de la ventana del mensaje
    * @return valor booleano indicando dicho evento
    */
   public boolean getTerminado() {
      return terminado;
   }

   /**
    * @return valor ascoaido al botoón pulsado en la ventana del
    * mensaje
    */
   public int getResultado() {
      return resultado;
   }

   /**
   * 'Mata' este Thread
   */
   public void finaliza()
   {
      ui.this_windowClosing(new WindowEvent(ui, WindowEvent.WINDOW_CLOSING));
      super.stop();
   }

   /**
   * Muestra el mensaje por la pantalla
   */
   public void run() {
      resultado=ui.showDialog(mensaje, botones, true);
      terminado=true;
   }
}