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

import com.example.coolworld.InformationActivity;
import com.example.coolworld.InformationListAdapter;
import com.example.coolworld.Jsonizer;

public class InformationListMaker extends AsyncTask<String, Void, Void> {
	private InformationActivity informationActivity;
	private Jsonizer json_country;
	private Handler handler;
  private InformationListAdapter adapter;
  private ProgressDialog progressDialog;
  private Map<String, String> httpGetParams;
	
	public InformationListMaker(InformationActivity pinformationActivity, String url, Map<String, String> pHTTPGetParams){
		informationActivity = pinformationActivity;
		json_country = new Jsonizer(url);
	  handler = new Handler(Looper.getMainLooper());
	  progressDialog = new ProgressDialog(informationActivity);
	  httpGetParams = pHTTPGetParams;
	  informationActivity.informationList = new ArrayList<HashMap<String,String>>();
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
			JSONArray informations = json_country.getArray("", httpGetParams);					

			if (informations.length() > 0) {
				for (int i = 0; i < informations.length(); i++) {
					JSONObject jo = json_country.getArray("", httpGetParams).getJSONObject(i);

					HashMap<String, String> map = new HashMap<String, String>();
					map.put("englishName", jo.getString("en_name"));
					map.put("localName", jo.getString("local_name"));
					
					if(jo.has("mobile_long"))
						map.put("mobile", jo.getString("mobile_long"));
					else
						map.put("mobile", "NA");
							
					if(jo.has("categories"))
						map.put("category", jo.getString("categories"));
					else
						map.put("category", "NA");

					if(jo.has("featured"))
						map.put("imageUrl", jo.getString("featured"));
					else
						map.put("imageUrl", "NA");
						
					informationActivity.informationList.add(map);
				}
						  
	  	  handler.post(new Runnable() {				
	  	  	@Override
	  	  	public void run() {
	  	  		progressDialog.hide();
	  	      adapter=new InformationListAdapter(informationActivity, informationActivity.informationList, progressDialog);        
	  	      informationActivity.list.setAdapter(adapter);
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
	
	