package com.franc.sencillo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SencilloActivity extends Activity {
	/** Called when the activity is first created. */
	int contador = 0;
	Button botonSuma;
	Button botonResta;
	TextView textRes;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		botonSuma = (Button) findViewById(R.id.botonSuma);
		botonResta = (Button) findViewById(R.id.botonResta);
		textRes = (TextView) findViewById(R.id.textRes);
		botonSuma.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textRes.setText("Hola tienes "+(++contador)+" points");
			}
		});
		botonResta.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				textRes.setText("Hola tienes "+(--contador)+" points");

			}
		});

	}
}