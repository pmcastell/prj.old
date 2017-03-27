

public class ITIG extends carrera{
   static String Cursos[][]= {
      {"Cálculo", "Álgebra", "Física", "Circuitos y Sistemas Digitales", "Informática"},
      {"Programación", "Estadística", "Investigación Operativa", "Contabilidad"},
      {"Arquitectura de Ordenadores", "Investigación Operativa II", "Electrónica",
       "Bases de Datos"}
   };

   public ITIG() {
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
