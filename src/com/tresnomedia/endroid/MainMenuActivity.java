package com.tresnomedia.endroid;

import java.util.ArrayList;

import com.tresnomedia.endroid.utils.MyCustomBaseAdapter;
import com.tresnomedia.endroid.utils.SearchResults;
import com.tresnomedia.endroid.utils.UserFunctions;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.Activity;
import android.content.Intent;

public class MainMenuActivity extends Activity {
	UserFunctions userFunctions;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) {
        	setContentView(R.layout.activity_main_menu);
            ArrayList<SearchResults> searchResults = GetSearchResults();
            final ListView lv1 = (ListView) findViewById(R.id.list);
            lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));
		
            lv1.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            		// TODO Auto-generated method stub
            		switch(position) {
            		case 0: break;
            		case 1: break;
            		case 2: break;
            		case 3: break;
            		case 4: Intent newActivity = new Intent(getApplicationContext(), ProfileActivity.class);
        					startActivity(newActivity);
        					break;
            		case 5:	userFunctions.logoutUser(getApplicationContext());
							Intent login = new Intent(getApplicationContext(), LoginActivity.class);
							login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(login);
							// Closing dashboard screen
							finish();
							break;
							}
            		}
            	});
            } else {
            	// user is not logged in show login screen
            	Intent login = new Intent(getApplicationContext(), LoginActivity.class);
            	login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            	startActivity(login);
            	// Closing dashboard screen
            	finish();
            }
        }
    
    private ArrayList<SearchResults> GetSearchResults() {
    	ArrayList<SearchResults> results = new ArrayList<SearchResults>();
    	
    	SearchResults sr1 = new SearchResults();
    	sr1.setName("Klinik Belajar");
    	sr1.setImageNumber(1);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Bank Soal");
    	sr1.setImageNumber(2);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Kelas Diskusi");
    	sr1.setImageNumber(3);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Media Pustaka");
    	sr1.setImageNumber(4);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Profil");
    	sr1.setImageNumber(5);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Log out");
    	sr1.setImageNumber(6);
    	results.add(sr1);
    	
    	return results;
    }
}
