/*
 * sistemaEcuaciones.java
 *
 * Created on 20 de mayo de 2006, 19:32
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author fjcn
 */
public class sistemaEcuaciones extends matriz {
    protected matriz mTriangular;
    protected double soluciones[];
    
    /** Creates a new instance of sistemaEcuaciones */
    public sistemaEcuaciones(int fils, int cols) {
        super(fils,cols);
    }
    public sistemaEcuaciones(double [][]m) {
        super(m);
        _soluciones();
    }
    public void leerCoeficientes() {
        super.leerCoeficientes();
        _soluciones();
    }
    public matriz triangularizaSistema() {
        int i,f,k, numPermutaciones=0;
        double pivote,primero;
        matriz r=new matriz(this.m);
        
        for(i=0; i<filas()-1; i++) {
            pivote=r.m[i][i];
            for(f=i+1; f<filas(); f++) {
                primero=r.m[f][i];
                for(k=i; k<cols(); k++) {
                    r.m[f][k]=r.m[f][k]*pivote-r.m[i][k]*primero;
                }
            }
        }
        return r;
    }
    private void _soluciones() {
        mTriangular=this.triangularizaSistema();
        if (mTriangular!=null) {
            soluciones=new double[filas()];
            for(int i=filas()-1; i>=0; i--) {
                soluciones[i]=mTriangular.m[i][cols()-1];
                for(int j=cols()-2; j>i; j--) {
                    soluciones[i]-=mTriangular.m[i][j]*soluciones[j];
                }
                soluciones[i]/=mTriangular.m[i][i];
            }
        } else {
            soluciones=null;
        }
    }
    public double[] soluciones() {
        return soluciones;
    }
    public String solucionesToString() {
        String res="{";
        for(int i=0; i<filas(); i++) {
            res+=soluciones[i];
            if (i<filas()-1) {
                res+=',';
            }
        }
        res+="}";
        return res;
    }
            
    public static void main(String args[]) {
        sistemaEcuaciones s=new sistemaEcuaciones(new double[][]{{2,3,1,5},{3,-1,5,20},{5,1,-6,2}}),
                s2=new sistemaEcuaciones(new double[][]{{2,1,-1,4},{3,2,1,7},{5,-2,2,1}}),
                s3=new sistemaEcuaciones(new double[][]{{2,3,-1,2},{3,1,2,4},{1,-9,10,1}});
        sistemaEcuaciones s4=new sistemaEcuaciones(new double[][]{{2,1,1,1,7},{1,-2,1,2,1},{3,2,-3,-1,0},{5,-1,1,4,8}});
        sistemaEcuaciones s5=new sistemaEcuaciones(new double[][]{{1,1,1,11},{2,-1,1,5},{3,2,1,24}});
        sistemaEcuaciones s6=new sistemaEcuaciones(new double[][]{{2,-1,-2,-2},{-1,1,1,0},{1,-2,1,8},{2,-2,0,6}});
        sistemaEcuaciones s7=new sistemaEcuaciones(new double[][]{{1,2,3,4},{0,0,0,0},{0,0,2,-1},{0,0,1,2}});
        int r=s7.rango();

    }
        
}
   