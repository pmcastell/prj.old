
import java.util.Vector;
public class alumno {
   String nombre=new String("");
   Vector asignaturas= new Vector();

   public alumno() {
   }
   public alumno(String nombre) {
      this.nombre=nombre;
   }
   public alumno(String nombre, String[] asignaturas) {
      this(nombre);
      for(int i=0; i<asignaturas.length; i++) {
         this.asignaturas.addElement(asignaturas[i]);
      }
   }      

   public alumno(String nombre, Vector asignaturas) {
      this(nombre);
      this.asignaturas=asignaturas;
   }
   public void matricula(String asignatura) {
      this.asignaturas.addElement(asignatura);
   }




} 
