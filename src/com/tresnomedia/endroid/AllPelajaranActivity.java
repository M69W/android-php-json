package com.tresnomedia.endroid;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.tresnomedia.endroid.library.JSONParser;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class AllPelajaranActivity extends ListActivity {
	
	private ProgressDialog pDialog;
	JSONParser jParser = new JSONParser();
	ArrayList<HashMap<String, String>> pelList;
	private static String url_all_products = "http://10.0.2.2/android_login_api/get_all_materi_listview.php";
	private static final String TAG_SUCCESS = "success";
    private static final String TAG_PRODUCTS = "products";
    private static final String TAG_ID = "id";
    private static final String TAG_JUDUL = "judul";
    JSONArray products = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_pelajaran);
        
        pelList = new ArrayList<HashMap<String, String>>();
        
        Intent get = getIntent();
        String parameter = get.getStringExtra("params");
        
        new LoadAllProducts().execute(parameter);
        
        // Get listview
        ListView lv = getListView();
 
        // on seleting single product
        // launching Edit Product Screen
        lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View view, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String id = ((TextView) view.findViewById(R.id.lid)).getText().toString();
 
                // Starting new intent
                //Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                // sending pid to next activity
                //in.putExtra(TAG_ID, id);
 
                // starting new activity and expecting some response back
                //startActivityForResult(in, 100);
			}
        	
        });
    }

    class LoadAllProducts extends AsyncTask<String, String, String> {

    	@Override
    	protected void onPreExecute() {
    		super.onPreExecute();
            pDialog = new ProgressDialog(AllPelajaranActivity.this);
            pDialog.setMessage("Loading data. Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
    	}
    	
		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("table", args[0]));
			JSONObject json = jParser.getJSONFromUrl(url_all_products, params);
			Log.d("All Products: ", json.toString());
			try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);
 
                if (success == 1) {
                	products = json.getJSONArray(TAG_PRODUCTS);
                	 
                    // looping through All Products
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject c = products.getJSONObject(i);
 
                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String judul = c.getString(TAG_JUDUL);
 
                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();
 
                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_JUDUL, judul);
 
                        // adding HashList to ArrayList
                        pelList.add(map);
                    }
                } else {
                	
                }
			} catch (JSONException e) {
	                e.printStackTrace();
	        }
			return null;
		}
    	
		@Override
		protected void onPostExecute(String file_url) {
			pDialog.dismiss();
            // updating UI from Background Thread
            runOnUiThread(new Runnable() {
                public void run() {
                    /**
                     * Updating parsed JSON data into ListView
                     * */
                    ListAdapter adapter = new SimpleAdapter(
                            AllPelajaranActivity.this, pelList,
                            R.layout.list_row, new String[] { TAG_ID,
                                    TAG_JUDUL},
                            new int[] { R.id.lid, R.id.title });
                    // updating listview
                    setListAdapter(adapter);
                }
            });
		}
    }
}
