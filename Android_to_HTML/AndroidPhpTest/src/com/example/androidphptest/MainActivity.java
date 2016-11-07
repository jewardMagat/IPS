package com.example.androidphptest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{
	
	EditText etName, etAge, etEmail;
	Button btnSubmit;
	TextView txtProgress, txtProgress2;
	SeekBar sbProgress, sbProgress2;
	int value1, value2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.activity_main);
		etName = (EditText)findViewById(R.id.etName);
		etAge = (EditText)findViewById(R.id.etAge);
		etEmail = (EditText)findViewById(R.id.etEmail);
		btnSubmit = (Button)findViewById(R.id.btnSubmit);
		
		txtProgress = (TextView)findViewById(R.id.txtProgress);
		sbProgress = (SeekBar)findViewById(R.id.sbProgress);
		
		txtProgress2 = (TextView)findViewById(R.id.txtProgress2);
		sbProgress2 = (SeekBar)findViewById(R.id.sbProgress2);
		
		seekBar(sbProgress, txtProgress, value1, "Data", "abc@yahoo.com");
		seekBar(sbProgress2, txtProgress2, value2, "Data2", "def@yahoo.com");
		
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			InputStream inputStream = null;
			@Override
			public void onClick(View v) {
				String name = ""+etName.getText().toString();
				String age = ""+etAge.getText().toString();
				String email = ""+etEmail.getText().toString();
				
				List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
				nameValuePair.add(new BasicNameValuePair("name", name));
				nameValuePair.add(new BasicNameValuePair("age", age));
				nameValuePair.add(new BasicNameValuePair("email", email));
				
				try{
					HttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost("http://192.168.2.3/client/insertDb.php");
//					HttpPost httpPost = new HttpPost("http://10.0.2.2/client/insertDb.php");

					httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
					HttpResponse response = httpClient.execute(httpPost);
					HttpEntity entity = response.getEntity();	
					
					inputStream = entity.getContent();
					String msg = "Data entered successfully";
					Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
				}catch(ClientProtocolException e){
					Log.e("ClientProtocol", "StackTrace error");
					e.printStackTrace();
				}catch(IOException e){
					Log.e("IOException", "StackTrace error");
					e.printStackTrace();
				}
			}
		});

	}
	
	private void updateData(String name, int age, String email){
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(1);
		nameValuePair.add(new BasicNameValuePair("name", name));
		nameValuePair.add(new BasicNameValuePair("age", String.valueOf(age)));
		nameValuePair.add(new BasicNameValuePair("email", email));
		
		InputStream inputStream = null;
		try{
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost("http://192.168.2.3/client/insertDb.php");
//			HttpPost httpPost = new HttpPost("http://10.0.2.2/client/insertDb.php");

			httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();	
			
			inputStream = entity.getContent();
			String msg = "Data entered successfully";
//			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
		}catch(ClientProtocolException e){
			Log.e("ClientProtocol", "StackTrace error");
			e.printStackTrace();
		}catch(IOException e){
			Log.e("IOException", "StackTrace error");
			e.printStackTrace();
		}
	}
	
	private void seekBar(final SeekBar seekBar, final TextView txtView, final int value, final String name, final String email){
			
		txtView.setText("Covered: " + seekBar.getProgress() + " / " + seekBar.getMax());
		
		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			int progressVal = value;
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {				
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				txtView.setText("Covered: " + progressVal + " / " + seekBar.getMax());
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				progressVal = progress;
				txtView.setText("Covered: " + progressVal + " / " + seekBar.getMax());
				updateData(name, progressVal, email);
			}
		});
		
		
	}
}
