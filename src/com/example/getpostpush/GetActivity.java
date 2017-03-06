package com.example.getpostpush;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class GetActivity extends Activity{
    // Set actvity name as debug tag
	public static final String TAG = HttpsClient.class.getSimpleName();
	
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get);
		String []events= new String[]{"gas","temperature","humidity","alcohol"};
		String url="https://api.spark.io/v1/devices";
		String access_token="a506bd8a69028da19af7a7f7f930e87d3b92c9e9";
		String coreid="48ff6b065067555048181187";
		
		final TextView tempview = (TextView) findViewById(R.id.datatempview);
		//temperature
		final String temperature = url+"/"+coreid+"/"+events[1]+"/"+"?"+"access_token="+access_token;
		Log.d(TAG, "urls"+temperature);   
		new HttpsClient().execute(temperature,"temperature");
		
		Button fresh = (Button) findViewById(R.id.buttonfresh);
		fresh.setOnClickListener(new OnClickListener() {
			
			public void onClick(View arg0) {
				Toast.makeText(GetActivity.this, "正在刷新", Toast.LENGTH_SHORT).show();
				// TODO Auto-generated method stub
				tempview.setText("");	
				new HttpsClient().execute(temperature,"temperature");
			}
		});
	}
	 /*
     * GET GAS
     */
	  class HttpsClient extends AsyncTask<String, Void, String> {
          private Exception exception;
          public String doInBackground(String... urls) {
                
              try {
            	  
                  Log.d(TAG, "*******************    Open Connection    *****************************");   
                  String line = null;
                  Data data = new Data();
                  URL url = new URL(urls[0]);
                  final String event=urls[1];
                  Log.d(TAG, "Received URL:  " + url);
                  
                  HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
                  Log.d(TAG, "Con Status: " + con);
  
                  InputStream in = con.getInputStream();
                  Log.d(TAG, "GetInputStream:  " + in);
  
                  Log.d(TAG, "*******************    String Builder     *****************************");

                  BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                  //String result="";
                  String s="";
                  while ((line = br.readLine())!= null) {
                      if (line.contains("false")) {
                          //do nothing since the event tag is of no interest
                          Log.d(TAG, "Failed fetching needed values.");
                         
                          return null;
                      }
                    /*  if(line.contains("result")){
                    	  Pattern p = Pattern.compile("[0-9]");
                    	  Matcher m = p.matcher(line);
                    	  while(m.find()){
                    		  result=result+m.group();    
                          }
                    	  data.setFoobar(result);
                      }*/
                      else{
                    	  s=s+line;
                    	  Log.d(TAG,s);
                      }
                  }             
                  JSONTokener jsonParser = new JSONTokener(s);
                  JSONObject person = (JSONObject) jsonParser.nextValue();  
                  double result1 = person.getDouble("result"); 
                  DecimalFormat df = new DecimalFormat("0.00");
                  String result = df.format(result1);
                  data.setFoobar(result);
                  Log.d(TAG,"result:"+result);
                  
                  // Creation of finalized containers for UI usage
                  final String gBaz = data.getFoobar();
                  // To access the findViewById we need this to runOnUiThread
                  runOnUiThread(new Runnable(){
                      public void run() {
                          Log.d(TAG, "*******************    Run UI Thread     *****************************");
                          Log.d(TAG, "gFoobar:   " + gBaz);
                          final TextView updateGetExample;
                          // Assign and declare
                        	   updateGetExample  = (TextView) findViewById(R.id.datatempview);
                          // Update the TextViews
                          Log.d(TAG, "*******************    Update TextView       *************************");
                         updateGetExample.setText(gBaz);
                      }
  
                  });
                  // Closing the stream
                  Log.d(TAG, "*******************  Stream closed, exiting     ******************************");
                  br.close();
              } catch (Exception e) {
                  this.exception = e;
                  return null;
              }
              return null;
              }
  
      }
	  
	  /**
	   * @param Json 对象转换
	   * 
	   * @param jsonObject
	   *         json对象
	   *         
	   * @return Map对象
	   * @trows JSONException
	   * */
    public Map<String, String> JsontoMap(String jsonString) throws JSONException{
		JSONObject jsonObject = new JSONObject(jsonString);
		 Map<String, String> result = new HashMap<String, String>();
	        Iterator<?> iterator = jsonObject.keys();
	        String key = null;
	        String value = null;
	        
	        while (iterator.hasNext()) {
	            key = (String) iterator.next();
	            value = jsonObject.getString(key);
	            result.put(key, value);
	        }
	        return result;
         }
}
