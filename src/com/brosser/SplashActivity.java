package com.brosser;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;

public class SplashActivity extends Activity {
	
    protected boolean _active = true;
    protected int _splashTime = 10000;
    protected Context packageContext;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        this.packageContext = this;
        
        // Thread for displaying the splash screen
        Thread splashTread = new Thread() {
            
        	@Override
            public void run() {
        		
        		// Start database handler
        		DatabaseHandler databaseHandler = new DatabaseHandler(getResources());
        		databaseHandler.start();
        		
        		try {
        			int waited = 0;
                    while(_active && (waited < _splashTime)) {
                        sleep(100);
                        if(_active) {
                            waited += 100;
                        }
                    }
                } catch(InterruptedException e) {    
                	// Do nothing
                	} finally {
                		finish();
                		// Start the real application
                		//startActivity(new Intent("com.brosser.TabHostActivity"));
                		//startActivity(new Intent("com.brosser.SettingsActivity"));
                		startActivity(new Intent(packageContext, TabHostActivity.class));
                		stop();
                }
            }
        };
        splashTread.start();
    }
    
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            _active = false;
        }
        return true;
    }
}