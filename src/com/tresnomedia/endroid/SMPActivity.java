package com.tresnomedia.endroid;

import java.util.ArrayList;

import com.tresnomedia.endroid.utils.ConnectionDetector;
import com.tresnomedia.endroid.utils.MyCustomBaseAdapter;
import com.tresnomedia.endroid.utils.SearchResults;
import com.tresnomedia.endroid.utils.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SMPActivity extends Activity {
	UserFunctions userFunctions;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) {
        	setContentView(R.layout.activity_smp);
            ArrayList<SearchResults> searchResults = GetSearchResults();
            final ListView lv1 = (ListView) findViewById(R.id.list);
            lv1.setAdapter(new MyCustomBaseAdapter(this, searchResults));
		
            cd = new ConnectionDetector(getApplicationContext());
            
            lv1.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            		// TODO Auto-generated method stub
            		
            		isInternetPresent = cd.isConnectingToInternet();
            		if (isInternetPresent) {
            			switch(position) {
            			case 0: break;
            			case 1: break;
            			case 2: break;
            			case 3: break;
								}
            		} else {
            			cd.showAlertDialog(SMPActivity.this, "No Internet Connection",
                                "You don't have internet connection.", false);
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
    	sr1.setName("Bahasa Indonesia");
    	sr1.setImageNumber(5);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Bahasa Inggris");
    	sr1.setImageNumber(4);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Ilmu Pengetahuan Alam");
    	sr1.setImageNumber(1);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Matematika");
    	sr1.setImageNumber(3);
    	results.add(sr1);
    	
    	return results;
    }
}
