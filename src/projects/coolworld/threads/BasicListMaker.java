package projects.coolworld.threads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import com.example.coolworld.BasicListAdapter;
import com.example.coolworld.BasicListActivity;
import com.example.coolworld.Jsonizer;

public class BasicListMaker extends AsyncTask<String, Void, Void> {
	private BasicListActivity countrySelectActivity;
	private Jsonizer json_country;
	private Handler handler;
  private BasicListAdapter adapter;
  private ProgressDialog progressDialog;
  private Map<String, String> httpGetParams;
	private String target;
	private String flagUrl;
	
	public BasicListMaker(BasicListActivity pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams, String pTarget){
		countrySelectActivity = pCountrySelectActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(countrySelectActivity);
	  httpGetParams = pHTTPGetParams;
	  countrySelectActivity.countryList = new ArrayList<HashMap<String,String>>();
	  target = pTarget;
	}
	
	public BasicListMaker(BasicListActivity pCountrySelectActivity, String url, Map<String, String> pHTTPGetParams, String pTarget, String pFlagUrl){
		countrySelectActivity = pCountrySelectActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(countrySelectActivity);
	  httpGetParams = pHTTPGetParams;
	  countrySelectActivity.countryList = new ArrayList<HashMap<String,String>>();
	  target = pTarget;
	  flagUrl = pFlagUrl;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		progressDialog.setMessage("Please Wait. Retrieving Data..");
		progressDialog.show();
	}
	
	protected Void doInBackground(String... params) {
		try {
			JSONArray countries = json_country.getArray("", httpGetParams);					

			if (countries.length() > 0) {
				for (int i = 0; i < countries.length(); i++) {
					JSONObject jo = json_country.getArray("", httpGetParams).getJSONObject(i);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("englishName", jo.getString("en_name"));
					map.put("localName", jo.getString("local_name"));
					
					if(target.equals("countries"))
						map.put("flag", jo.getString("flag"));
					else
						map.put("flag", flagUrl);
						
					// adding HashList to ArrayList
					countrySelectActivity.countryList.add(map);
				}
						  
	  	  handler.post(new Runnable() {				
	  	  	@Override
	  	  	public void run() {
	  	  		progressDialog.hide();
	  	      adapter=new BasicListAdapter(countrySelectActivity, countrySelectActivity.countryList, progressDialog,target);        
	  	      countrySelectActivity.list.setAdapter(adapter);
	  	  	}
	  	  });
			} 
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return null;
	}	
}
	
	