package com.example.getpostpush;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ToggleButton;

public class PostActivity extends Activity{
	
	public static final String TAG = PostActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post);
		//Button 触发
	    final ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton);
		toggleButton.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public String event;
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				// TODO Auto-generated method stub
			 	toggleButton.setChecked(arg1);
			 	event = null;
			 	if(arg1==true){
			 		event="on";
			 	}
			 	else{
			 		event="off";
			 	}
				new PostClient().execute(event);
			}
		});
	}
	
    /*
     * POST EXAMPLE
     */
    // We must do this as a background task, elsewhere our app crashes
    class PostClient extends AsyncTask<String, Void, String> {
        public String doInBackground(String... IO) {

            // Predefine variables
            String io = new String(IO[0]);
            URL url;

            try {
                // Stuff variables
                url = new URL("https://api.spark.io/v1/devices/48ff6b065067555048181187/led/");
                String param = "access_token=a506bd8a69028da19af7a7f7f930e87d3b92c9e9&args="+io;
                Log.d(TAG, "param:" + param);

                // Open a connection using HttpURLConnection
                HttpsURLConnection con = (HttpsURLConnection) url.openConnection();

                con.setReadTimeout(7000);
                con.setConnectTimeout(7000);
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setInstanceFollowRedirects(false);
                con.setRequestMethod("POST");
                con.setFixedLengthStreamingMode(param.getBytes().length);
                con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

                // Send
                PrintWriter out = new PrintWriter(con.getOutputStream());
                out.print(param);
                out.close();

                con.connect();

                BufferedReader in = null;
                if (con.getResponseCode() != 200) {
                    in = new BufferedReader(new InputStreamReader(con.getErrorStream()));
                    Log.d(TAG, "!=200: " + in);
                } else {
                    in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    Log.d(TAG, "POST request send successful: " + in);
                };


            } catch (Exception e) {
                Log.d(TAG, "Exception");
                e.printStackTrace();
                return null;
            }
            // Set null and we´e good to go
            return null;
        }
    }

    
}
