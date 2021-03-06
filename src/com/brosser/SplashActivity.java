package com.brosser;

import com.brosser.database.DatabaseHandler;
import com.brosser.model.Element;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class SplashActivity extends Activity {
	
    protected boolean _active = true;
    protected int _splashTime = 2000;
    protected Context packageContext;
    
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);
        this.packageContext = this;
        
        // Display splash image
        ((ImageButton)findViewById(R.id.splashImage)).setBackgroundResource(R.drawable.splashatom);
        
        // Thread for displaying the splash screen
        Thread splashTread = new Thread() {
            
        	@Override
            public void run() {
        		
        		// Start database handler
        		DatabaseHandler databaseHandler = new DatabaseHandler(getResources());
        		databaseHandler.start();
        		
                try {
					databaseHandler.join();
				} catch (InterruptedException e) {
					
				}
				
                Element.setLanguage(DatabaseHandler.getLanguage(0));
                Element.setUnits(DatabaseHandler.getUnits());
        		// Languages
        		finish();
        		// Start tab host
        		startActivity(new Intent(packageContext, TabHostActivity.class));
        		stop();
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