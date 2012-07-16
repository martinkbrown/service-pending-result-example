package edu.fsu.cs.mobile.example.servicecomm2;

import android.app.Activity;
import android.app.IntentService;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.content.Intent;
import android.widget.Toast;

public class MyService extends IntentService {

	public MyService() {
		super("MyService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {

		int i;
		
		int LIMIT = 50000000;

		Toast.makeText(getBaseContext(), "Download started ...", Toast.LENGTH_SHORT).show();
		
		for(i = 0; i < LIMIT; i++);
		
		PendingIntent pIntent = (PendingIntent) intent.getParcelableExtra("pending");

		try {
			pIntent.send(Activity.RESULT_CANCELED);
		} catch (CanceledException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
