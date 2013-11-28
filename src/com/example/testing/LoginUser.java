package com.example.testing;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginUser extends Activity implements OnClickListener {
	public EditText email, password;
    public ProgressDialog progressDialog;
    
    public String URL = "http://smoothalicious.nl/webservice/user/login.php",
    		RESULT = "result",
    		DETAILS = "details",
    		ENTRY_TEST = "first";
    
    JSONParser jsonParser = new JSONParser();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_user);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE); fails..
      	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
      	
		email = (EditText)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password);

		Button buttonLogin = (Button)findViewById(R.id.login);
		buttonLogin.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.login:
			new Login().execute();
			
			break;
		case R.id.register:
			finish();
			LoginUser.this.startActivity(new Intent(LoginUser.this, AddUser.class));
			
			break;
		}
	}

	class Login extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginUser.this);
            progressDialog.setMessage("Inloggen...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("email", email.getText().toString()));
                params.add(new BasicNameValuePair("password", password.getText().toString()));

                JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
                
                if(json.getInt(RESULT) == 1) {
                	finish();
                	
                	if(json.getInt(ENTRY_TEST) == 1) {
                		//LoginUser.this.startActivity(new Intent(LoginUser.this, FirstTest.class));
                	} else {
                		
                	}
                }
                
                return json.getString(DETAILS);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
		}
		
        protected void onPostExecute(String file_url) {
        	progressDialog.dismiss();
            
            if (file_url != null) {
            	Toast.makeText(LoginUser.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}

