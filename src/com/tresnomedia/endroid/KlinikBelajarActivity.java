package com.tresnomedia.endroid;

import java.util.ArrayList;

import com.tresnomedia.endroid.utils.ConnectionDetector;
import com.tresnomedia.endroid.utils.MyCustomBaseAdapterKlinik;
import com.tresnomedia.endroid.utils.SearchResults;
import com.tresnomedia.endroid.utils.UserFunctions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class KlinikBelajarActivity extends Activity {
	UserFunctions userFunctions;
	ConnectionDetector cd;
	Boolean isInternetPresent = false;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
               
        userFunctions = new UserFunctions();
        if(userFunctions.isUserLoggedIn(getApplicationContext())) {
        	setContentView(R.layout.activity_klinik_belajar);
            ArrayList<SearchResults> searchResults = GetSearchResults();
            final ListView lv1 = (ListView) findViewById(R.id.list);
            lv1.setAdapter(new MyCustomBaseAdapterKlinik(this, searchResults));
		
            cd = new ConnectionDetector(getApplicationContext());
            
            lv1.setOnItemClickListener(new OnItemClickListener() {

            	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
            		// TODO Auto-generated method stub
            		
            		isInternetPresent = cd.isConnectingToInternet();
            		if (isInternetPresent) {
            			switch(position) {
            			case 0: Intent sd = new Intent(getApplicationContext(), SDActivity.class);
    							startActivity(sd);
            					break;
            			case 1: Intent smp = new Intent(getApplicationContext(), SMPActivity.class);
								startActivity(smp);
            					break;
            			case 2: Intent sma = new Intent(getApplicationContext(), SMAActivity.class);
								startActivity(sma);
								break;
            			case 3:	finish();
            					break;
								}
            		} else {
            			cd.showAlertDialog(KlinikBelajarActivity.this, "No Internet Connection",
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
       

	@Override
    public void onBackPressed() {
    finish();
    return;
    }
	
    private ArrayList<SearchResults> GetSearchResults() {
    	ArrayList<SearchResults> results = new ArrayList<SearchResults>();
    	
    	SearchResults sr1 = new SearchResults();
    	sr1.setName("Sekolah Dasar");
    	sr1.setImageNumber(1);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Sekolah Menengah Pertama");
    	sr1.setImageNumber(2);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Sekolah Menengah Atas");
    	sr1.setImageNumber(3);
    	results.add(sr1);
    	
    	sr1 = new SearchResults();
    	sr1.setName("Kembali");
    	sr1.setImageNumber(1);
    	results.add(sr1);
    	
    	return results;
    }
}
