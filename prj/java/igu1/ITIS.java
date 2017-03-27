

public class ITIS extends carrera{
   static String Cursos[][]= {
      {"Cálculo", "Álgebra", "Física", "Circuitos y Sistemas Digitales", "Informática"},
      {"Programación", "Estadística", "Cálculo Numérico", "Electrónica",
       "Circuitos y Sistemas Digitales II", "Inglés"},
      {"Arquitectura de Ordenadores", "Tecnología de Comunicaciones", "Derecho",
       "Bases de Datos", "Instrumentación"}
   };
   public ITIS() {
   }
   public boolean matricula(String alumno, int curso) {
      if(1<=curso && curso<=3) {
         this.anadir(new alumno(alumno, Cursos[curso-1]));
         return true;
      } else {
         ventanaDialogo.vMensaje("Error en esta carrera solo existen los cursos: 1, 2 y 3");
         return true;
      }
   }

}
