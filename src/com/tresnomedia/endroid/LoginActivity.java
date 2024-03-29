package com.tresnomedia.endroid;

import org.json.JSONException;
import org.json.JSONObject;

import com.tresnomedia.endroid.library.DatabaseHandler;
import com.tresnomedia.endroid.utils.ConnectionDetector;
import com.tresnomedia.endroid.utils.UserFunctions;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	Button btnLogin;
	TextView btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	
	private ProgressDialog pDialog;
	
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		// import all assets
		inputEmail = (EditText) findViewById(R.id.log_email);
		inputPassword = (EditText) findViewById(R.id.log_password);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (TextView) findViewById(R.id.link_to_register);
		
		cd = new ConnectionDetector(getApplicationContext());
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				isInternetPresent = cd.isConnectingToInternet();
        		if (isInternetPresent) {
        			new LoginTask().execute();
        		} else {
        			cd.showAlertDialog(LoginActivity.this, "No Internet Connection",
                            "You don't have internet connection.", false);
        		}
			}
		});
		
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent newActivity = new Intent(getApplicationContext(), RegisterActivity.class);
				startActivity(newActivity);
			}
		});
	}
	
	class LoginTask extends AsyncTask<String, Void, JSONObject> {
		
		@Override
		protected void onPreExecute() {
			pDialog = new ProgressDialog(LoginActivity.this);
	        pDialog.setMessage("Logging in...");
	        pDialog.setIndeterminate(false);
	        pDialog.setCancelable(true);
	        pDialog.show();
		}
		
		@Override
		protected JSONObject doInBackground(String... urls) {
			// TODO Auto-generated method stub
			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			UserFunctions userFunction = new UserFunctions();
			
			Log.d("Button", "Login");
			JSONObject json = userFunction.loginUser(email, password);		
			return json;
		}
		
		@Override
    	protected void onPostExecute(JSONObject json) {
			UserFunctions userFunction = new UserFunctions();
			pDialog.dismiss();
			try {
				if (json.getString(KEY_SUCCESS) != null) {
					String res = json.getString(KEY_SUCCESS); 
					if(Integer.parseInt(res) == 1){
						// user successfully logged in
						// Store user details in SQLite Database
						DatabaseHandler db = new DatabaseHandler(getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");
					
						// Clear all previous data in database
						userFunction.logoutUser(getApplicationContext());
						db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT));						
					
						// Launch Dashboard Screen
						Intent dashboard = new Intent(getApplicationContext(), MainMenuActivity.class);
					
						// Close all views before launching Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);
					
						// Close Login Screen
						finish();
					} else {
						// Error in login
						isInternetPresent = cd.isConnectingToInternet();
						cd.showAlertDialog(LoginActivity.this, "Error",
	                            "Username/Password Salah!", false);
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} 
    	}
	}
}
