

public class II extends carrera{
   static String Cursos[][]= {
      {"Cálculo", "Álgebra", "Física", "Circuitos y Sistemas Digitales", "Informática"},
      {"Programación", "Estadística", "Cálculo Numérico", "Electrónica",
       "Circuitos y Sistemas Digitales II", "Inglés"},
      {"Arquitectura de Ordenadores", "Tecnología de Comunicaciones", "Derecho",
       "Bases de Datos", "Instrumentación"},
      {"Arquitectura de Redes de Ordenadores", "Ingeniería del Software",
       "Arquitectura de Sistemas Paralelos", "Procesadores de Lenguajes",
       "Inteligencia Artificial"},
      {"Ingeniería del Software III", "Control por Computador",
       "Diseño de Computadores Síntesis Lógica", "Procesamiento de Imágenes Digitales",
       "Fotónica", "Síntesis de Imágenes por Ordenador",
       "Criptografía", "Programación en Internet"}
   };


   public II() {
   }
   public boolean matricula(String alumno, int curso) {
      if(1<=curso && curso<=5) {
         this.anadir(new alumno(alumno, Cursos[curso-1]));
         return true;
      } else {
         ventanaDialogo.vMensaje("Error en esta carrera solo existen los cursos: 1, 2, 3, 4 y 5");
         return true;
      }
   }
} 
