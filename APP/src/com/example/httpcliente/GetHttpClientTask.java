package com.example.httpcliente;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

public class GetHttpClientTask extends AsyncTask<String, Void, String> {

	
	private List<HttpClientListener> listeners = new ArrayList<HttpClientListener>();
	private static final int HTTP_TIMEOUT = 30*1000;
	private static HttpClient httpClient;
	
	
	@Override
	protected String doInBackground(String... params) {
			
		String output = "";
		try {
			output = executaHttpGet(params[0]);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return output;
	}
	
	
	
	
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		for(HttpClientListener l : listeners){
			l.updateHttpClientListener(result);
		}
	}


	public static String executaHttpGet(String url) throws Exception {
		BufferedReader bufferreader = null;

		try{
			HttpClient client = getHttpClient();
			HttpGet httpGet = new HttpGet(url);
			
			httpGet.setURI(new URI(url));
			HttpResponse httpResponse = client.execute(httpGet);
			bufferreader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferreader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferreader.close();

			String resultado = stringBuffer.toString();
			return resultado;

		}finally{
			if(bufferreader != null){
				try{
					bufferreader.close();
				}catch(IOException e){
					e.printStackTrace();
				}               
			}           
		}
	}

	
	
	
	private static HttpClient getHttpClient(){
		if(httpClient==null){  
			httpClient = new DefaultHttpClient();
			final HttpParams httpParams = httpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(httpParams, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(httpParams, HTTP_TIMEOUT);
		}

		return httpClient;
	}

	
	public void addHttpClientListener(HttpClientListener l){
		listeners.add(l);
	}
	
	
}
