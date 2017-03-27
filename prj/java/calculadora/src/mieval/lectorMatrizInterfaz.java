/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mieval;

import mieval.tipo.miArray;


/**
 *
 * @author usuario
 */
public interface lectorMatrizInterfaz {
           
   public miArray getMatriz();
   public void leerMatriz(miArray a);
   public void leerMatriz(int f, int c,miArray a);
   public void leerMatriz(int c);
   public void leerMatriz(int f,int c);


   
}
