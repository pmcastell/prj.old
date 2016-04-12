package es.franc.miCalc;

import java.lang.reflect.Field;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import mieval.evaluador;




public class MiCalcActivity extends Activity { 
    /** Called when the activity is first created. */
	private evaluador e=new evaluador(); 
	
	private void calculaRes() { 
		TextView res=(TextView) findViewById(R.id.resultado);
		MultiAutoCompleteTextView expr=(MultiAutoCompleteTextView) findViewById(R.id.expresion);
		String expres=expr.getText().toString();
		if (!expres.equalsIgnoreCase("")) {
			if (expres.indexOf(';')<0) {
				expres+=";";
			}
			String result=e.programa(expres).toString();
			res.setText(result);
			expr.setText("");
		}
	}
	private void retrocedeHist() {
		MultiAutoCompleteTextView expr=(MultiAutoCompleteTextView) findViewById(R.id.expresion);
    	e.hist.retrocede();
    	expr.setText(e.hist.actual());
	}
	private void avanzaHist() {
		MultiAutoCompleteTextView expr=(MultiAutoCompleteTextView) findViewById(R.id.expresion);
    	e.hist.avanza();
    	expr.setText(e.hist.actual());
	}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main); 
        Button botonCalc  = (Button) findViewById(R.id.botonCalc);
        /*int botones[]={R.id.botonSuma,R.id.botonResta,R.id.botonProduc,R.id.botonDiv,R.id.botonMod,
        		R.id.botonLlaveAbierta,R.id.botonLlaveCerrada,R.id.botonCorCheteAbierto,
        		R.id.botonCorcheteCerrado,R.id.botonIgual,R.id.botonComa,R.id.botonPunto,R.id.botonPuntoYcoma};*/
        MultiAutoCompleteTextView expr=(MultiAutoCompleteTextView) findViewById(R.id.expresion);
        View.OnClickListener teclasListener=new myViewOnclickListener(this);
        
        Field[] fs=new R().getClass().getDeclaredClasses()[2].getDeclaredFields();
        for(int i=0; i<fs.length;i++) {
        	try {
        		if (fs[i].getName().indexOf("tecla")==0) {
        			Button b=(Button) findViewById(fs[i].getInt(fs[i]));
        			b.setOnClickListener(teclasListener);
        		}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
        }
        
        
        
        //for(int i=0;i<botones.length;i++) {
        	//Button b=(Button) findViewById(botones[i]);
        	//b.setOnClickListener(teclasListener);

        //}
        
        botonCalc.setOnClickListener(new View.OnClickListener() {
    		@Override
    		public void onClick(View v) {
    			// TODO Auto-generated method stub
    			//String link="http://www.lcc.uma.es";
    			//String link="http://www.marca.es/tenis.html";
    			
    			calculaRes();
    		}
    	});
        expr.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
            	if (event.getAction()==KeyEvent.ACTION_DOWN) {
	                if (keyCode == KeyEvent.KEYCODE_ENTER) { 
	                    /* do something */ 
	                	calculaRes();
	                	return true; 	
	                } else if (keyCode==KeyEvent.KEYCODE_DPAD_UP) {
	                	retrocedeHist();
	                	return true;
	                } else if (keyCode==KeyEvent.KEYCODE_DPAD_DOWN) {
	                	avanzaHist();
	                	return true;
	                }
            	}
				return false;
            }
        });
    }
}

class myViewOnclickListener implements View.OnClickListener {
		private MiCalcActivity vista=null;
		
		public myViewOnclickListener(MiCalcActivity calc) {
			this.vista=calc;
		}
		public void onClick(View v) {
			
			Button pulsado=(Button) v;
			MultiAutoCompleteTextView expr=(MultiAutoCompleteTextView)vista.findViewById(R.id.expresion);
			String tecla=pulsado.getText().toString();
			expr.setText(expr.getText()+tecla);
			expr.setCursorVisible(true);
			expr.setSelection(expr.getText().length());
		}
};