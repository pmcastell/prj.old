package es.franc.NavegadorIntent;

import es.franc.NavegadorIntent.R;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class NavegadorIntentActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		TextView text=(TextView) findViewById(R.id.text);
		
		if (Intent.ACTION_MAIN.equals(getIntent().getAction()) ){
			String link="http://www.marca.es/tenis.html";
			Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse(link));
			startActivity(intent);
		}
		else{
			Uri data=getIntent().getData();
			if (data==null){
				text.setText("Sin datos");
			}
			else{
				text.setText(data.toString());
			}
		}
	}
}
