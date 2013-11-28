package com.example.testing;

import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.app.Activity;
import android.content.Intent;

public class Loading extends Activity {
	private static String TAG = Loading.class.getName();
	private static long SLEEP_TIME = 3;	// Sleep for some time
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
      	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
      	setContentView(R.layout.loading);
        
        // Start timer and launch main activity
        IntentLauncher launcher = new IntentLauncher();
        launcher.start();
	}
	
	private class IntentLauncher extends Thread {
    
		@Override
    	/**
    	 * Sleep for some time and than start new activity.
    	 */
		public void run() {
    		try {
            	// Sleeping
    			Thread.sleep(SLEEP_TIME*1000);
            } catch (Exception e) {
            	Log.e(TAG, e.getMessage());
            }
            
            // Start main activity
          	Intent intent = new Intent(Loading.this, LoginUser.class);
          	Loading.this.startActivity(intent);
          	Loading.this.finish();
    	}
    }
}
