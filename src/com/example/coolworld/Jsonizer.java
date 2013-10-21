package com.example.coolworld;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class Jsonizer {
  private final String ENCODING = "UTF-8";
  private final int TIMEOUT = 10000;
  private final HttpClient httpClient;
  private final String url;

  public Jsonizer(final String pUrl) {
    final HttpParams basicParams = new BasicHttpParams();

    HttpConnectionParams.setConnectionTimeout(basicParams, TIMEOUT);
    HttpConnectionParams.setSoTimeout(basicParams, TIMEOUT);

    httpClient = new DefaultHttpClient(basicParams);

    url = pUrl+"e/";
  }
    
  public JSONObject get(final String methodName, final Map<String, String> params) throws ClientProtocolException, IOException, JSONException {
    String geturl = url + methodName;
    geturl = prepareGetRequestUrl(params, geturl);
    System.out.println("Android: "+geturl);
    return executeJsonRequest(new HttpGet(geturl), null);
  }
    
  public JSONArray getArray(final String methodName, final Map<String, String> params) throws ClientProtocolException, IOException, JSONException {
    String geturl = this.url + methodName;
    geturl = prepareGetRequestUrl(params, geturl);
    System.out.println("Android: "+geturl);

    return executeJsonRequestArray(new HttpGet(geturl), null);
  }
    

  public JSONObject post(final String methodName, final Map<String, String> map) throws JSONException, ClientProtocolException, IOException {
    final List<BasicNameValuePair> params = preparePostParams(map);

    httpClient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.RFC_2109);

    return executeJsonRequest(preparePostRequest(methodName, params), new BasicHttpContext());
  }

  private JSONObject executeJsonRequest(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException, JSONException {
    HttpResponse response = httpClient.execute(request, context);
    
    if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        return new JSONObject(EntityUtils.toString(response.getEntity()));

    return null;
  }
    
  private JSONArray executeJsonRequestArray(HttpUriRequest request, HttpContext context) throws IOException, ClientProtocolException, JSONException {
    HttpResponse response = httpClient.execute(request, context);

    if (response != null && response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
        return new JSONArray(EntityUtils.toString(response.getEntity()));
    
    return null;
  }

  private List<BasicNameValuePair> preparePostParams(final Map<String, String> map) throws JSONException {
    final List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

    for (Map.Entry<String, String> entry : map.entrySet())
      params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));

    return params;
  }

  private HttpPost preparePostRequest(final String methodName, final List<BasicNameValuePair> params) throws UnsupportedEncodingException {
    HttpPost request = new HttpPost(this.url + methodName);
    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params);
    request.setEntity(entity);
    return request;
  }

  private String prepareGetRequestUrl(final Map<String, String> params, String geturl) throws UnsupportedEncodingException {
    if (params != null) {
      int i = 0;
      for (Map.Entry<String, String> param : params.entrySet()) {
        geturl += (i == 0) ? "?" : "&";
        geturl += param.getKey() + "="
                + URLEncoder.encode(param.getValue(), ENCODING);
        ++i;
      }
    }
      
    System.out.println("Android: URL: "+geturl);
    return geturl;
  }
}