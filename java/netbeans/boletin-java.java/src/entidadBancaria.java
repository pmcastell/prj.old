/*
 * entidadBancaria.java
 *
 * Created on 14 de mayo de 2006, 19:06
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */

/*
  5.- Escribir una clase en JAVA que represente a una entidad bancaria. Una entidad 
 bancaria tiene los siguientes atributos: codigoEntidad, nif, direccion y cuentas 
 (array de objetos de la clase del ejercicio anterior). Toda entidad bancaria puede 
 tener un número máximo de 100 cuentas. Incluir un método en dicha clase que nos permita 
 añadir una cuenta (los clientes quedan en el mismo orden en el que se van añadiendo) y 
 otro método que nos devuelva el efectivo disponible por la entidad (la suma del saldo de 
 todos los clientes).
 */
public class entidadBancaria {
    private String codigoEntidad,
                   nif,
                   direccion;
    private cuentaBancaria cuentas[]=new cuentaBancaria[100];
    private int numCuentas=0;
    
    /** Creates a new instance of entidadBancaria */
    public entidadBancaria() {
    }
    public void añadirCuenta(cuentaBancaria c) {
        if (this.numCuentas<100) {
            this.cuentas[numCuentas]=c;
            numCuentas++;
        } else {
            System.out.println("No se pueden añadir más cuentas a entidad.");
        }
    }
    public double efectivo() {
        double res=0.;
        for(int i=0; i<numCuentas; i++) {
            res+=this.cuentas[i].getSaldo();
        }
        return res;
    }
}
// versión 2. Con un número ilimitado de cuentas (utilizando la clase java.util.Vector)
class entidadBancaria2 {
    private String codigoEntidad,
                   nif,
                   direccion;
    java.util.Vector<cuentaBancaria> cuentas=new java.util.Vector<cuentaBancaria>(); // representa a un array que puede crecer indefinidamente
    
    public entidadBancaria2() {
    }
    public void añadirCuenta(cuentaBancaria c) {
        cuentas.addElement(c);
    }
    public double efectivo() {
        double res=0.;
        for(int i=0; i<cuentas.size(); i++) {
            res+=((cuentaBancaria)cuentas.elementAt(i)).getSaldo();
        }
        return res;
    }
    

}
