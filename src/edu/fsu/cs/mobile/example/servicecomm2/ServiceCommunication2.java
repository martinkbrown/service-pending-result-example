package edu.fsu.cs.mobile.example.servicecomm2;


import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ServiceCommunication2 extends Activity {
    
	int DOWNLOAD_REQUEST_CODE = 1;
	int TRANSFER_REQUEST_CODE = 2;
	int NOTIFICATION_ID = 1;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        

        PendingIntent contentIntent = createPendingResult(DOWNLOAD_REQUEST_CODE, new Intent(), PendingIntent.FLAG_ONE_SHOT); 
        
        final Intent sIntent = new Intent(this, MyService.class);
        sIntent.putExtra("pending",contentIntent);
        
        Button button = (Button) findViewById(R.id.button1);
        
        button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				startService(sIntent);
				
			}
		});
    }
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    	
    	if(requestCode == DOWNLOAD_REQUEST_CODE) {
    		
    		String tickerMessage = "";
    		String actionMessage = "";
    		PendingIntent pendingIntent;
    		
    		NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    		
    		if(resultCode == Activity.RESULT_OK) {
    		
    			tickerMessage = "Download Complete";
    			actionMessage = "Click to launch Activity";
    			Intent myIntent = new Intent(this, SecondActivity.class);
    			pendingIntent = PendingIntent.getActivity(this, 0, myIntent, 0);
    		
    		}
    		else {
    		
    			tickerMessage = ":( Download Failed";
    			actionMessage = "Click to retry ...";
    			
    			PendingIntent contentIntent = createPendingResult(DOWNLOAD_REQUEST_CODE, new Intent(), PendingIntent.FLAG_UPDATE_CURRENT); 
    	        
    	        final Intent myIntent = new Intent(this, MyService.class);
    	        myIntent.putExtra("pending",contentIntent);
    	        
    			pendingIntent = PendingIntent.getService(this, 0, myIntent, 0);    			
    		}
			
    		Notification notif = new Notification.Builder(this)
    			.setSmallIcon(R.drawable.ic_launcher)
    			.setTicker(tickerMessage)
    			.setWhen(System.currentTimeMillis())
    			.setContentTitle("embarrassing_pic.png")
    			.setContentText(actionMessage)
    			.setContentIntent(pendingIntent)
    			.setAutoCancel(true)
    			.build();
    				
			manager.notify(NOTIFICATION_ID, notif);
    	}
    }
}