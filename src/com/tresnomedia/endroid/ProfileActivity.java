package com.tresnomedia.endroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.app.Activity;

public class ProfileActivity extends Activity {
	
	TextView fullNameProfile;
	TextView eMailProfile;
	Button btnChangePass;
	Button btnBacktoMenu;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        fullNameProfile = (TextView) findViewById(R.id.profile_fullname);
        eMailProfile = (TextView) findViewById(R.id.profile_email);
        btnChangePass = (Button) findViewById(R.id.btnProf_ChangePass);
        btnBacktoMenu = (Button) findViewById(R.id.btnProf_BackToMain);
        
        btnChangePass.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
        btnBacktoMenu.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
    }
}
