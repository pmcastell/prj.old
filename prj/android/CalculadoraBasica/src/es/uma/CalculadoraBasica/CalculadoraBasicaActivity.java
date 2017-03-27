package es.uma.CalculadoraBasica;

import es.uma.CalculadoraBasica.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CalculadoraBasicaActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        int botones[]={R.id.button1,R.id.button2,R.id.button3,R.id.button4,R.id.Button05,R.id.Button03,R.id.Button04,
        		R.id.Button01,R.id.Button02,R.id.Button06,R.id.Button07,R.id.Button08,R.id.Button10,R.id.Button11,
        		R.id.Button12,R.id.Button09,R.id.Button14,R.id.Button15,R.id.Button16,R.id.Button13,R.id.Button17,
        		R.id.Button18};
        Button b;
        
        View.OnClickListener teclasListener=new myViewOnclickListener(this);
        for(int i=0; i<botones.length;i++) {
        	b=(Button)this.findViewById(botones[i]);
        	b.setOnClickListener(teclasListener);
        }
    }
}


class myViewOnclickListener implements View.OnClickListener {
    
    
    private boolean operacionEnCurso=false;
    private int estado=0; //0 inicial , 1 operacion , 2 esperando segundo op
    String opEnCurso;
	private String acumulador="",op2="";
	private CalculadoraBasicaActivity vista=null;
	
	public myViewOnclickListener(CalculadoraBasicaActivity calc) {
		this.vista=calc;
	}
	public void onClick(View v) {
		
		Button pulsado=(Button) v;
		TextView res=(TextView)vista.findViewById(R.id.resultado);
		TextView oper=(TextView)vista.findViewById(R.id.operacion);
		String tecla=pulsado.getText().toString();
		String resultado=res.getText().toString();
		
		if (estado==0) {
			 if (esNumerica(tecla)) {
			     acumulador=añade(acumulador,tecla);
				 res.setText(acumulador);
			 } else if (esOp(tecla)) {
				 estado=1;
				 //acumulador=new Double(res.getText().toString());
				 opEnCurso=tecla;
				 oper.setText(tecla);
			 } else if (esIgual(tecla,"C")) {
				 acumulador=quitaUlt(acumulador);
				 res.setText(acumulador);
			 } else if (esIgual(tecla,"+/-")) {
				 acumulador=cambiaSigno(acumulador);
				 res.setText(acumulador);
			 }
		} else if (estado==1) { // tenemos ya un operando
			if (esNumerica(tecla)) {
				estado=2;
				op2=tecla;
				res.setText(op2);
			} else if (esOp(tecla)) {
				opEnCurso=tecla;
				oper.setText(tecla);
			} else if (esIgual("=", tecla)) {
				if (!esIgual(opEnCurso,"")) {
					acumulador=calcOp(opEnCurso,acumulador,acumulador);
					res.setText(acumulador);
				}
				estado=0;
				opEnCurso="";
				oper.setText("");
			} else if (esIgual(tecla,"C")) {
				 estado=0;
				 opEnCurso="";
				 oper.setText("");
			 }
		} else if (estado==2) { // tenemos ya dos operandos
			if (esNumerica(tecla)) {
				op2=añade(op2,tecla);
				res.setText(op2);
			} else if (esOp(tecla)) {
				acumulador=calcOp(opEnCurso, acumulador, op2);
				estado=1;
				res.setText(acumulador);
				op2="";
				opEnCurso=tecla;
				oper.setText(tecla);
			} else if (esIgual("=", tecla)) {
				acumulador=calcOp(opEnCurso, acumulador, op2);
				estado=0;
				res.setText(acumulador);
				op2="";
				opEnCurso="";
				oper.setText("");
			} else if (esIgual(tecla,"C")) {
				op2=quitaUlt(op2);
				res.setText(op2);
			 } else if (esIgual(tecla,"+/-")) {
				 op2=cambiaSigno(op2);
				 res.setText(op2);
			 }
		}  
	}
	private boolean esNumerica(String tecla) {
		return "1234567890,".indexOf(tecla)>=0;
	}
	private boolean esOp(String tecla) {
		return "+-X/".indexOf(tecla)>=0;
	}
	private boolean esIgual(String teclaBuscada,String tecla) {
		return teclaBuscada.equalsIgnoreCase(tecla);
	}
	private String añade(String numero, String tecla) {
		if (esIgual(",",tecla)) {
			if (numero.indexOf(",")<0) {
				return numero+tecla;
			} else {
				return numero;
			}
		} else if (esIgual(numero,"0")) {
			if (esIgual(tecla,"0")) {
				return numero;
			} else {
				return tecla;
			} 
		} else {
			return numero+tecla;
		}
	}
	private String quitaUlt(String cadena) {
		if (cadena.length()<=1) {
			return "0";
		} else {
			return cadena.substring(0,cadena.length()-1);
		}
	}
	private String cambiaSigno(String num) {
		 if (num.charAt(0)=='-') {
			 num=num.substring(1);
		 } else if (!esIgual(num,"0")) {
			 num="-"+num;
		 }
		 return num;
	}
	private String calcOp(String operacion,String sop1,String sop2) {
		Double res=0.0, op1=new Double(sop1.replace(',', '.')),op2=new Double(sop2.replace(',','.'));
		if (operacion.equalsIgnoreCase("+")) {
			res=new Double(op1.doubleValue()+op2.doubleValue());
		} else if (operacion.equalsIgnoreCase("-")) {
			res=new Double(op1.doubleValue()-op2.doubleValue());
		} else if (operacion.equalsIgnoreCase("/")) {
			try {
				res=new Double(op1.doubleValue()/op2.doubleValue());
			} catch (Exception ex) {
				res=Double.NaN;
			}
		} else if (operacion.equalsIgnoreCase("X")) {
			res=new Double(op1.doubleValue()*op2.doubleValue());
		}
		String resul=res.toString().replace('.',',');
		if (resul.indexOf(",0")==resul.length()-2) {
			return resul.substring(0, resul.length()-2);
		}
		return resul;
	}
};
