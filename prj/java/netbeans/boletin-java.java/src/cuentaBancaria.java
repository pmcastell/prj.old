/*
 * cuentaBancaria.java
 *
 * Created on 14 de mayo de 2006, 19:00
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 **/

/*
4.- Escribir una clase en JAVA que represente una cuenta bancaria. Un objeto de este 
tipo tiene los siguientes atributos: codigoCuenta, dni, nombre, apellidos, saldo. 
Añadir un método que nos permita realizar ingresos, otro para retirar fondos y otro 
que nos diga si la cuenta está en números rojos (el saldo sea negativo).
**/
public class cuentaBancaria {
    private String codigoCuenta,
            dni,
            nombre,
            apellidos;
    private double saldo;
    
    /** Creates a new instance of cuentaBancaria */
    public cuentaBancaria() {
    }
    public double getSaldo() {
        return this.saldo;
    }
    public void ingreso(double cantidad) {
        saldo+=cantidad;
    }
    public void reintegro(double cantidad) {
        saldo-=cantidad;
    }
    boolean estaEnNumerosRojos() {
        return saldo<0;
    }
    
}
