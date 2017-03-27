
import java.util.Vector;
public class carrera {
   Vector alumnos= new Vector();
   public carrera() {

   }
   public void anadir(alumno a) {
      alumnos.addElement(a);
   }
   public alumno busqueda(String a) {
      String nombre;

      a=a.toUpperCase();
      for(int i=0; i<alumnos.size(); i++) {
         nombre=((alumno) alumnos.elementAt(i)).nombre;
         nombre=nombre.toUpperCase();
         if (nombre.indexOf(a)>=0) {
            return (alumno) alumnos.elementAt(i);
         }
      }
      return null;
   }         



} 
