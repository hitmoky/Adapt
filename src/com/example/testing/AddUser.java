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
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddUser extends Activity implements OnClickListener {
	public EditText firstname, lastname, email, password, className;
    public ProgressDialog progressDialog;
    
    public String URL = "http://smoothalicious.nl/webservice/user/add.php",
    		RESULT = "result",
    		DETAILS = "details";
    
    JSONParser jsonParser = new JSONParser();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_user);
		
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE); fails..
      	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		firstname = (EditText)findViewById(R.id.firstname);
		lastname = (EditText)findViewById(R.id.lastname);
		email = (EditText)findViewById(R.id.email);
		password = (EditText)findViewById(R.id.password);
		className = (EditText)findViewById(R.id.className);

		Button buttonRegister = (Button)findViewById(R.id.register);
		buttonRegister.setOnClickListener(this);
	}
	
	public void onClick(View view) {
		new CreateUser().execute();
		//startActivityForResult(new Intent(this, LoginUser.class), 0);
	}

	class CreateUser extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(AddUser.this);
            progressDialog.setMessage("Registreren...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

		@Override
		protected String doInBackground(String... args) {
			try {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("firstname", firstname.getText().toString()));
                params.add(new BasicNameValuePair("lastname", lastname.getText().toString()));
                params.add(new BasicNameValuePair("email", email.getText().toString()));
                params.add(new BasicNameValuePair("password", password.getText().toString()));
                params.add(new BasicNameValuePair("className", className.getText().toString()));

                JSONObject json = jsonParser.makeHttpRequest(URL, "POST", params);
                
                if(json.getInt(RESULT) == 1) {
                	finish();
                	
                	AddUser.this.startActivity(new Intent(AddUser.this, LoginUser.class));
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
            	Toast.makeText(AddUser.this, file_url, Toast.LENGTH_LONG).show();
            }
        }
	}
}

