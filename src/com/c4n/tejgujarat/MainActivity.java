package com.c4n.tejgujarat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;

import com.airpush.android.Airpush;
import com.apperhand.device.android.AndroidSDKProvider;
import com.google.ads.Ad;
import com.startapp.android.publish.HtmlAd;
import com.startapp.android.publish.model.AdPreferences;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	Airpush airpush;
	private HtmlAd htmlAd = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SplashHandler mHandler = new SplashHandler();

		setContentView(R.layout.activity_main);
		
		AndroidSDKProvider.initSDK(this);
		AndroidSDKProvider.setTestMode(true);
		
		
		airpush = new Airpush(this);
		airpush.startSmartWallAd();
		airpush.startPushNotification(true);
		airpush.startDialogAd();
		airpush.startAppWall();
		airpush.startIconAd();
		
		if (getConnectionState() == false) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(
					MainActivity.this);
			alertbox.setMessage("Please check internet connection.");
			alertbox.setCancelable(false);
			alertbox.setNeutralButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface arg0, int arg1) {
							finish();
						}
					});
			alertbox.show();
		}

		else {

			Message msg = new Message();
			// Assign a unique code to the message.
			// Later, this code will be used to identify the message in Handler
			// class.
			msg.what = 0;
			// Send the message with a delay of 3 seconds(3000 = 3 sec).
			mHandler.sendMessageDelayed(msg, 2000);

		}
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if (htmlAd == null) { 
			   AdPreferences adPreferences = new AdPreferences("105355246", "205027103",    AdPreferences.TYPE_INAPP_EXIT);
			   htmlAd = new HtmlAd(this);
			   htmlAd.load(adPreferences, null);
			}
		super.onResume();
	}
	public void onReceiveAd(Ad ad) {
	} 

	public void onFailedToReceiveAd(Ad ad) {
	}

	@Override
	public void onPause() {
	   super.onPause(); 
	      if(htmlAd != null) { 
	         boolean showAd = htmlAd.doHome();
	         if (showAd) {
	            htmlAd = null;
	      }
	   } 
	}
	
	@Override
	public void onBackPressed() { 
	      if (htmlAd != null) {
	         htmlAd.show();
	         htmlAd = null;
	      }
	      super.onBackPressed();
	}
	// Handler class implementation to handle the message
	private class SplashHandler extends Handler {

		// This method is used to handle received messages
		public void handleMessage(Message msg) {
			// switch to identify the message by its code
			switch (msg.what) {
			default:
			case 0:
				super.handleMessage(msg);

				// Create an intent to start the new activity.
				// Our intention is to start MainActivity
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, Home.class);
				startActivity(intent);
				// finish the current activity
				MainActivity.this.finish();
			}
		}
	}

	private boolean getConnectionState() {
		ConnectivityManager cm = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo ni = cm.getActiveNetworkInfo();

		if (ni == null)
			return false;
		else
			return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			airpush.startAppWall();
			airpush.startDialogAd();
			airpush.startLandingPageAd();
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

}
