package com.example.mobilepayment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	private EditText usernameEditText;
	private EditText passwordEditText;
	private Button sendPostReqButton;
	private Button clearButton;
	

    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
        String res1=preferences1.getString("mobilepaymentotphash", null);
        if (res1 != null)
        {
        	Intent myIntent = new Intent(this, paymentbalance.class);
        	this.startActivity(myIntent);
        }
       
        usernameEditText = (EditText) findViewById(R.id.editText1);
        passwordEditText = (EditText) findViewById(R.id.editText2);

        sendPostReqButton = (Button) findViewById(R.id.button1);
        sendPostReqButton.setOnClickListener(this);

        clearButton = (Button) findViewById(R.id.button2);
        clearButton.setOnClickListener(this);        
    }

	/*private void passcall() {
		setContentView(R.layout.welcome);
		payment = (EditText) findViewById(R.id.editText3);
		paybutton = (Button) findViewById(R.id.button4);
        paybutton.setOnClickListener(this);
        balancebutton = (Button) findViewById(R.id.button3);
        balancebutton.setOnClickListener(this);
       
        	
          	}

	public void onClick1(View v) {
		if(v.getId() == R.id.button4){
			String paymenttext = payment.getEditableText().toString();
			makepayment(paymenttext);
		
		}
		else if(v.getId() == R.id.button3)
		{
			makepayment(null);
		}
	}
	private void makepayment(String paymenttext) {
		    SharedPreferences preferences1 = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
	        String res1=preferences1.getString("mobilepaymentotphash", null);
	        paymentclass payee = new paymentclass();
	        payee.execute(paymenttext,res1);
		
	}
*/
	@Override
	public void onClick(View v) {

		if(v.getId() == R.id.button2){
			usernameEditText.setText("");
			passwordEditText.setText("");
			passwordEditText.setCursorVisible(false);
			passwordEditText.setFocusable(false);
			usernameEditText.setCursorVisible(true);
			passwordEditText.setFocusable(true);
		}else if(v.getId() == R.id.button1){
			String givenUsername = usernameEditText.getEditableText().toString();
			String givenPassword = passwordEditText.getEditableText().toString();

			System.out.println("Given username :" + givenUsername + " Given password :" + givenPassword);

			sendPostRequest(givenUsername, givenPassword);
		}	
	}

	private void sendPostRequest(String givenUsername, String givenPassword) {

		class SendPostReqAsyncTask extends AsyncTask<String, Void, String>{

			@Override
			protected String doInBackground(String... params) {

				String paramUsername = params[0];
				String paramPassword = params[1];

				System.out.println("*** doInBackground ** paramUsername " + paramUsername + " paramPassword :" + paramPassword);

				HttpClient httpClient = new DefaultHttpClient();

				
				HttpPost httpPost = new HttpPost("http://contestlaxman.yzi.me/mobileverify.php");

				
				BasicNameValuePair usernameBasicNameValuePair = new BasicNameValuePair("email", paramUsername);
				BasicNameValuePair passwordBasicNameValuePAir = new BasicNameValuePair("otp", paramPassword);

				
				List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
				nameValuePairList.add(usernameBasicNameValuePair);
				nameValuePairList.add(passwordBasicNameValuePAir);

				try {
					
					UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(nameValuePairList);

					
					httpPost.setEntity(urlEncodedFormEntity);

					try {
						
						HttpResponse httpResponse = httpClient.execute(httpPost);

						
						InputStream inputStream = httpResponse.getEntity().getContent();

						InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

						BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

						StringBuilder stringBuilder = new StringBuilder();

						String bufferedStrChunk = null;

						while((bufferedStrChunk = bufferedReader.readLine()) != null){
							stringBuilder.append(bufferedStrChunk);
						}

						return stringBuilder.toString();

					} catch (ClientProtocolException cpe) {
						System.out.println("First Exception caz of HttpResponese :" + cpe);
						cpe.printStackTrace();
					} catch (IOException ioe) {
						System.out.println("Second Exception caz of HttpResponse :" + ioe);
						ioe.printStackTrace();
					}

				} catch (UnsupportedEncodingException uee) {
					System.out.println("An Exception given because of UrlEncodedFormEntity argument :" + uee);
					uee.printStackTrace();
				}

				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
							

				if(result.equals("error")){
					Toast.makeText(getApplicationContext(), "Invalid OTP", Toast.LENGTH_LONG).show();
				}else{
					SharedPreferences preferences = getApplicationContext().getSharedPreferences("pref", MODE_PRIVATE);
					Editor editor = preferences.edit();
					editor.putString("mobilepaymentotphash", result);
					editor.commit();
					
					Toast.makeText(getApplicationContext(), "Success. Your Application Is Verified. Please Restart to use!" , Toast.LENGTH_LONG).show();
				}
				
				
			}			
		}

		SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();
		sendPostReqAsyncTask.execute(givenUsername, givenPassword);		
	}
}
