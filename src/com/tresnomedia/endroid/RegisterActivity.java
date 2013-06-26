package com.tresnomedia.endroid;

import org.json.JSONException;
import org.json.JSONObject;

import com.tresnomedia.endroid.library.DatabaseHandler;
import com.tresnomedia.endroid.utils.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	
	Button btnRegister;
	TextView btnLinkToLogin;
	EditText inputFullName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		inputFullName = (EditText) findViewById(R.id.reg_fullname);
		inputEmail = (EditText) findViewById(R.id.reg_email);
		inputPassword = (EditText) findViewById(R.id.reg_password);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (TextView) findViewById(R.id.link_to_login);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);
		
		btnRegister.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String name = inputFullName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				UserFunctions userFunction = new UserFunctions();
				JSONObject json = userFunction.registerUser(name, email, password);
				
				// check for login response
				try {
					if (json.getString(KEY_SUCCESS) != null) {
						registerErrorMsg.setText("");
						String res = json.getString(KEY_SUCCESS); 
						if(Integer.parseInt(res) == 1){
							// user successfully registred
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
							// Close Registration Screen
							finish();
						} else {
							// Error in registration
							registerErrorMsg.setText("Error occured in registration");
						}
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}
		});
		
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}
}
