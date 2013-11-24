package com.example.coolworld.gateways;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.coolworld.R;

public final class OnlineGateway {
	private static OnlineGateway instance;
	public String mobileGETURL; 
	public String downloadedImgDir;
	public String DEFAULT_IMAGE;
	public String baseURL;
	
	private final String ENCODING = "UTF-8";
  private final int TIMEOUT = 1000;
  private final HttpClient httpClient;
	public final String DEFAULT_STRING = "Undefined";
	
	private OnlineGateway(Activity activity){
		baseURL = activity.getResources().getString(R.string.urlRootLink); //the root URL for the sigte
		mobileGETURL = baseURL+"/e"; //url for mobile database queries
		DEFAULT_IMAGE = baseURL+"css/not_found.jpg"; //default image for data that has not define pictures
		downloadedImgDir = Environment.getExternalStorageDirectory().toString()+"/coolworld/downloaded/";
    final HttpParams basicParams = new BasicHttpParams();

    HttpConnectionParams.setConnectionTimeout(basicParams, TIMEOUT);
    HttpConnectionParams.setSoTimeout(basicParams, TIMEOUT);

    httpClient = new DefaultHttpClient(basicParams);
	}
	
	public static OnlineGateway getInstance(Activity activity){
		if(instance == null)
			instance = new OnlineGateway(activity);
		
		return instance;
	}
	
	//public methods
	public String getSignedInUser(final HashMap<String, String> userMap){		
		String username = null;
		
		try {
			System.out.println("TEST: maps: "+userMap);
			JSONObject jo = getJSONObject(userMap);
			
			if(jo != null){				
				System.out.println("TEST: objects: "+jo);
				username = jo.getString("email");
			}else{
				
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}				
				
		System.out.println("TEST: username:"+username);
		return username;
	}
		
	public ArrayList<HashMap<String, String>> getCountryList(HashMap<String, String> userMap){		
		HashMap<String, String> MElemsJElems = new HashMap<String, String>();
		MElemsJElems.put("englishName", "en_name"); 
		MElemsJElems.put("localName", "local_name"); 
		MElemsJElems.put("image", "flag"); 
		
		System.out.println("TEST: usermap paramter in getCountryList contains: "+userMap);
		
		return makeListFromJSONObject(userMap, MElemsJElems, null);
	}	
	
	public ArrayList<HashMap<String, String>> getCoolsiteList(HashMap<String, String> userMap){
		//define mappings
		HashMap<String, String> MElemsJElems = new HashMap<String, String>();
		MElemsJElems.put("englishName", "en_name"); 
		MElemsJElems.put("localName", "local_name"); 
		MElemsJElems.put("image", "featured"); 
					
		return makeListFromJSONObject(userMap, MElemsJElems, null);
	}

	public ArrayList<HashMap<String, String>> getHomeItemList(HashMap<String, String> userMap){
		String destinationFolder = downloadedImgDir + "homeItems/";
		
		//define mappings
		HashMap<String, String> MElemsJElems = new HashMap<String, String>();
		MElemsJElems.put("englishName", "en_name"); 
		MElemsJElems.put("localName", "local_name"); 
		MElemsJElems.put("featuredImagePath", "featured"); 
					
		return makeListFromJSONObject(userMap, MElemsJElems, destinationFolder);
	}

	public ArrayList<HashMap<String, String>> getCategoryList(HashMap<String, String> userMap){					
		ArrayList<HashMap<String, String>> list = null;
//		String destinationFolder = downloadedImgDir + "featuredCategories/";
		
		//get array from the passed user map
		ArrayList<String> englishNames = getArrayFromJSONArray("categories", userMap);
		ArrayList<String> localNames = getArrayFromJSONArray("lookup_categories", userMap);
		
		if(englishNames!=null || localNames!=null){ //precheck
			list = new ArrayList<HashMap<String,String>>();
			
			//combine the arraylist into a hashmap to make an arraylist of hashmaps
			for(int i=0; i<englishNames.size(); i++){
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("englishName", englishNames.get(i));
				map.put("localName", localNames.get(i));
				
				//DO RANDOM IMAGE RETRIEVAL HERE BY UTILIZING THE ENGLISHNAME OF THIS CURRENT ITERATION
				list.add(map);
			}
		}		
		
		return list;
	}
		
	//PRIVATE METHODS
	public String getFilename(String fileDir){
		return fileDir.substring(fileDir.lastIndexOf('/')+1, fileDir.length());
	}
	
	public String extractFileExtension(String fileDir){
		return fileDir.substring(fileDir.lastIndexOf('.')+1, fileDir.length());//excluding the dot (.)
	}
	
	private CompressFormat getImageFormat(String filePath){
		String extension = extractFileExtension(filePath);
		CompressFormat format = null;
		
		if(extension.equalsIgnoreCase("jpg")||extension.equalsIgnoreCase("jpeg"))
			format = Bitmap.CompressFormat.JPEG;
		else if(extension.equalsIgnoreCase("png"))
			format = Bitmap.CompressFormat.PNG;
		
		return format;
	}
	
	public Bitmap downloadImage(String srcDir){
		try {
			return BitmapFactory.decodeStream((InputStream)new URL(srcDir).getContent());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public String saveImage(Bitmap imageBitmap, String destPath, String filename){	
		File filePath = new File(destPath);

		//manage folder creation
		if(!filePath.exists()){
			if(filePath.mkdirs()){
				System.out.println("TEST: Created a folder path for country flag");
			}else{
				System.out.println("TEST: Error in creating the file path");
			}
		}
			
		//save the file
		File file = new File(filePath.getAbsolutePath(), filename);
		if(!file.exists()){
				FileOutputStream out;
				try {
					out = new FileOutputStream(file);
					imageBitmap.compress(getImageFormat(filename), 90, out);	
					out.flush();
					out.close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("TEST: file not found: "+e.getMessage());
					e.printStackTrace();
				} catch (IOException e) {
					System.out.println("TEST: io exception: "+e.getMessage());
					e.printStackTrace();
				}
				
				System.out.println("TEST: image has been successfully saved to "+destPath+filename);
		}		
//		if(!file.exists()){
//			try {
//				if(file.mkdirs()){
//					file.createNewFile();
//					FileOutputStream out = new FileOutputStream(file);
//					Bitmap bitmap = downloadImage(srcDir);
//					bitmap.compress(getImageFormat(destPath+filename), 90, out);	
//					System.out.println("TEST: image has been successfully saved to "+destPath+filename);
//				}else{
//					System.out.println("TEST: failed to make directory to "+destPath+filename);
//					return null;
//				}
//			} catch (IOException e) {				
//				System.out.println("TEST: failed to save image to "+destPath+filename);
//				System.out.println("TEST: reason: "+e.getMessage());
//			}
//		}else{
//			System.out.println("TEST: skip saving. File already exist at "+destPath+filename);
//		}
				
		return destPath+filename;
	}

	/**
	 * Converts the JSONArray whose key is defined by the listKey parameter inside the JSONArray result of 
	 * the paramsMap conversion to an arrayList of Strings
	 * @param listKey index to be extracted
	 * @param paramsMap parameters of the conversion for GETURL
	 * @return ArrayList<String>
	 */
	private ArrayList<String> getArrayFromJSONArray(String listKey, HashMap<String, String> paramsMap){
		ArrayList<String> arr = null;
		JSONArray outerJA = getJSONArray(paramsMap);
		
		try {
			JSONArray innerJA = outerJA.getJSONObject(0).getJSONArray(listKey);
			
			if(innerJA!=null){
				arr = new ArrayList<String>();
				for(int i=0; i<innerJA.length(); i++){
					arr.add(innerJA.getString(i));
				}				
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return arr;
	}
	
	private boolean isKeyAnImage(String key){
		String excemptions = "flag featured"; //list of image type keys
		return (excemptions.contains(key))?true:false;
	}
	
	/**
	 * returns a list of associative array (client-specific string, string value from json)
	 * @param params - associative array (GETParamKey string, GETParamValue string)
	 * @param elems  - associative array (client-specific string, json-specific string)
	 * @param destination - the path where the downloaded image will be saved
	 *                    - if the its value is NULL, the image will not be saved
	 * @return ArrayList<HashMap<String, String>>
	 */
	private ArrayList<HashMap<String, String>> makeListFromJSONObject(HashMap<String, String> params, HashMap<String, String> elems, String destination){
		ArrayList<HashMap<String,String>> list; //the list to be returned
		Object[] keys = elems.keySet().toArray(); //arrays of keys extracted from the 2nd hashmap parameter
		System.out.println("TEST: Making list from json object...");
		
		JSONArray ja = getJSONArray(params);
		if(ja==null || ja.length()<1 || elems==null || elems.isEmpty()) //prechecks
			return null;

		//code will not proceed here if there are no retrieved data
		list = new ArrayList<HashMap<String,String>>();
		System.out.println("TEST: initiate list creation");
		for(int i=0; i<ja.length(); i++){
			System.out.println("TEST: --adding index "+i+" of the json array to the list");
			HashMap<String, String> myElems = new HashMap<String, String>(); //make new maps for each iterations
			
			for(int j=0; j<keys.length; j++){	
				try {
					JSONObject jo = ja.getJSONObject(i);
					String value;
					String key = elems.get(keys[j]);
					System.out.println("TEST: ---- current key: "+key);
					
					if(jo.has(key)){//check if the target element has a value defined otherwise, use the default value
						value = jo.getString(key); 
						if(isKeyAnImage(key)){//save local path instead of its online path
							String srcFile = baseURL + value;
							value = (destination!=null)?"":srcFile;							
//							value = (destination!=null)?saveImage(srcFile, destination + getFilenameFromSourceFile(srcFile)):srcFile;
							System.out.println("TEST: ----key's values is an image, converted. ("+keys[j]+", "+value+")");
						}else{
							System.out.println("TEST: ----key's values is just a string. ("+keys[j]+", "+value+")");
						}
					}else{//manage if this element of the json object does not contain the value
						if(isKeyAnImage(key)){//save local path instead of its online path
							String srcFile = DEFAULT_IMAGE;
							value = (destination!=null)?"":srcFile;
//							value = (destination!=null)?saveImage(srcFile, destination + getFilenameFromSourceFile(srcFile)):srcFile;
							System.out.println("TEST: ----no value for image. Using default. ("+keys[j]+", "+value+")");
						}else{
							value = DEFAULT_STRING;
							System.out.println("TEST: ----no value for string. Using default. ("+keys[j]+", "+value+")");
						}						
					}
					
					myElems.put(keys[j].toString(), value);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			list.add(myElems);
		}
				
		return list;
	}
	
	/**
	 * Convert a HashMap to a GETURL String
	 * @param params getParams
	 * @return String
	 */
  private String convertMapToUrl(final Map<String, String> params){
    StringBuilder geturl = new StringBuilder(mobileGETURL); 
    
  	if (params != null) {
      int i = 0;
      for (Map.Entry<String, String> param : params.entrySet()) {
        geturl.append((i == 0) ? "?" : "&");

        try {
					geturl.append(param.getKey() + "=" + URLEncoder.encode(param.getValue(), ENCODING));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
        
        ++i;
      }
    }
      
    System.out.println("ANDROID: URL: "+geturl);
    return geturl.toString();
  }
  
  /**
   * convert the parameter map to url and get its Object
   * @return JSONObject
   */
  private JSONObject getJSONObject(final HashMap<String, String> paramsMap){

  	try{
    	HttpUriRequest request = new HttpGet(convertMapToUrl(paramsMap));
	  	HttpResponse response = httpClient.execute(request);
	    
	    if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	      return new JSONObject(EntityUtils.toString(response.getEntity()));
  	}catch (Exception e) {
			e.printStackTrace();
		}
  	  	
    return null;
  }
  
  /**
   * convert the parameter map to url and get its JSON Values
   * @return JSONArray
   */
  private JSONArray getJSONArray(final HashMap<String, String> paramsMap){  
  	String url = convertMapToUrl(paramsMap);
  	System.out.println("TEST: converted URL: "+url);
  	HttpUriRequest request = new HttpGet(url);
  	
  	try{
	  	HttpResponse response = httpClient.execute(request);
	    
	    if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
	        return new JSONArray(EntityUtils.toString(response.getEntity()));
  	}catch(Exception e){
  		e.printStackTrace();
  	}
  	  	
    return null;
  }  
}
