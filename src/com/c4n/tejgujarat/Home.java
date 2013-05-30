package com.c4n.tejgujarat;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.tabwidget.TabHostProvider;
import com.tabwidget.TabView;

public class Home extends Activity {
	
	
	private TabHostProvider tabProvider;
	private TabView tabView;

	WebView webView = null;
	
	
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home);

		//tabProvider = new Tabbarview(this);
		//tabView = tabProvider.getTabHost("main");
		//tabView.setCurrentView(R.layout.home);

		
		webView = (WebView) findViewById(R.id.webhome);

		//webView.loadUrl("http://care4nature.org/tejgujarat/?page_id=129");
		webView.setWebViewClient(new webclient());

		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);

		new asynctask().execute();
	}

	public void Onlatestnews(View v) {

		Intent i = new Intent(Home.this, Home.class);
		startActivity(i);

	}

	public void Onarchieve(View v) {
		
		if (getConnectionState() == false) {
			AlertDialog.Builder alertbox = new AlertDialog.Builder(
					Home.this);
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
		else{
		Intent i = new Intent(Home.this, Archive.class);
		startActivity(i);
		}

	}

	@Override
	public void onBackPressed() {

		Home.this.finish();
	}

	public class webclient extends WebViewClient {

		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			view.loadUrl(url);
			return true;
		}

	}
	public class asynctask extends AsyncTask<Void, Void, Void> {

		private ProgressDialog dialog = new ProgressDialog(Home.this);

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub

			dialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			dialog.setMessage("Loading..Please wait.");

			dialog.show();
			dialog.setCanceledOnTouchOutside(false);
			
			if (getConnectionState() == false) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						Home.this);
				alertbox.setMessage("Please check internet connection.");
				alertbox.setCancelable(false);
				alertbox.setNeutralButton("Ok",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface arg0, int arg1) {
								finish();
							}
						});
				alertbox.show();}
		}

		@Override
		protected Void doInBackground(Void... params) {
			
			
			if (getConnectionState() == false) {
				AlertDialog.Builder alertbox = new AlertDialog.Builder(
						Home.this);
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
			// TODO Auto-generated method stub
			webView.loadUrl("http://care4nature.org/tejgujarat/?page_id=129");
			}

			return null;
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
}