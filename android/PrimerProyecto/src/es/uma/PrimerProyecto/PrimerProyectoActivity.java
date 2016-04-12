package es.uma.PrimerProyecto;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class PrimerProyectoActivity extends Activity {
	/** Called when the activity is first created. */
	private String cadena="";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main); 

		TextView txt=(TextView) findViewById(R.id.TextoBienvenida);
		txt.setText("Hola");
		txt.setTextColor(Color.GREEN);
		String link="http://www.uma.es";
		final PackageManager packageManager = getPackageManager();
		final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(link));
		List<ResolveInfo> resolveInfo =packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
		int num=resolveInfo.size();
		
		for (int i=0;i<num;i++) {
			cadena+=resolveInfo.get(i).activityInfo.name+"\n";
		}
		txt.setText("Hemos encontrado: "+num+"\n"+cadena);


		Button botonNavegar = (Button) findViewById(R.id.botonNavegar);
		Button botonLlamar  = (Button) findViewById(R.id.botonLlamar);
		Button botonMaps	= (Button) findViewById(R.id.botonMaps);
		Button botonMarcar  = (Button) findViewById(R.id.botonMarcar);

		//txt.setBackgroundColor(Color.BLUE);
		//txt.setTextColor(Color.YELLOW);
		//txt.setTextSize(24.5f);
		botonNavegar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//String link="http://www.lcc.uma.es";
				//String link="http://www.marca.es/tenis.html";
				String link=(String) ((TextView)findViewById(R.id.txtBusca)).getText().toString();
				Intent intent =  new Intent(Intent.ACTION_VIEW,  Uri.parse(link));
				startActivity(intent);
			}
		});
		botonLlamar.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String tlf="952132898";
				Intent intent =	new Intent(Intent.ACTION_CALL,Uri.parse("tel:" + tlf));
				startActivity(intent);

			}
		});
		botonMaps.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String dir=(String) ((TextView)findViewById(R.id.txtBusca)).getText().toString();
				Intent intent = null;
				intent=new Intent(Intent.ACTION_VIEW, Uri.parse("geo:0,0?q="+dir));
				startActivity(intent);

			}
		});
		botonMarcar.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String tlf="952132898";
				Intent intent =	new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + tlf));
				startActivity(intent);

			}
		});
	}
	final int CLEAR_MENU_ID=0;
	final int ABOUT_MENU_ID=1;
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, CLEAR_MENU_ID, Menu.NONE, "Limpiar");
		menu.add(Menu.NONE, ABOUT_MENU_ID, Menu.NONE, "About");
		return super.onCreateOptionsMenu(menu);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		TextView tb1 = (TextView) findViewById(R.id.TextoBienvenida);
		switch (item.getItemId()) {
		case CLEAR_MENU_ID: 
			tb1.setText(""); 
			return true;
		case ABOUT_MENU_ID:
			tb1.setText(cadena); 
			Toast.makeText(this, "Curso Android", Toast.LENGTH_LONG).show();
			return true;
		default: 
			return false;
		}
	}

}