

public class prueba {

 public prueba() {
 }

 public static void main(String[] args) {
  prueba vPrueba = new prueba();
  vPrueba.invokedStandalone = true;
  String mensaje="Esto es una prueba",
         botones[]={"Aceptar","Cancelar"};
  Authority autoridad=new Authority();       

  mensaThread mt=new mensaThread(mensaje, botones);
  parClavesUsuario k=new parClavesUsuario(autoridad);
  mt.start();
  k.start();
  try {
     Thread.currentThread().wait();
  } catch (InterruptedException ex) {}   
  if (k.getTerminado()) {
     new UserInput().showDialog("k ha terminado");
  } else {
     new UserInput().showDialog("mt ha terminado");
  }      
 }
 private boolean invokedStandalone = false;
} 