package com.example.httpcliente;

import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity implements HttpClientListener{

	private Gson gson = new Gson();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	public void buscar(View v){
		GetHttpClientTask task = new GetHttpClientTask();
		
		task.addHttpClientListener(this);
		
		task.execute("http://intranet.ifg.edu.br/eventos/admin/congresso.json");
			
	}

	@Override
	public void updateHttpClientListener(String result) {

		
	}


}
