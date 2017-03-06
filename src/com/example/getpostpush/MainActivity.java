package com.example.getpostpush;

import org.androidpn.client.ServiceManager;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //开始监听服务
        ServiceManager serviceManager = new ServiceManager(this);
        serviceManager.setNotificationIcon(R.drawable.notification);
        serviceManager.startService();
        
        Button getbutton = (Button)findViewById(R.id.main_get);
        Button postbutton = (Button)findViewById(R.id.main_post);
        Button pushbutton = (Button)findViewById(R.id.main_push);
      //跳转到get页面
        getbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent getintent = new Intent(MainActivity.this,GetActivity.class);
				startActivity(getintent);
			}
		});
        //跳转到Post页面
        postbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent postintent = new Intent(MainActivity.this,PostActivity.class);
				startActivity(postintent);
			}
		});
        //跳转到Push
        postbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent postintent = new Intent(MainActivity.this,PostActivity.class);
				startActivity(postintent);
			}
		});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
