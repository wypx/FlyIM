package me.smart.flyme.activity.wifi;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.InetAddress;

import me.smart.flyme.R;
import me.smart.flyme.view.wiget.TitleCustomBarView;
import me.smart.flyme.wifi.foregin.Defaults;
import me.smart.flyme.wifi.foregin.FTPServerService;
import me.smart.flyme.wifi.foregin.Globals;
import me.smart.flyme.wifi.foregin.UiUpdater;

@SuppressLint({ "NewApi", "HandlerLeak" })
public class WiFiActivity extends Activity
{
	 private TextView ipText;
	 private TitleCustomBarView titleCustomBarView_wifi;

		public Handler handler = new Handler()
		{
	        public void handleMessage(Message msg)
	        {
	            switch (msg.what)
	            {
	                case 0:
	                    removeMessages(0);
	                    updateUi();
	                    break;
	                case 1:
	                    removeMessages(1);
	            }
	        }
	    };

	    private AppCompatTextView instructionText;

	    private AppCompatTextView instructionTextPre;

	    private View startStopButton;

	    private Activity mActivity;

	    public WiFiActivity()
	    {

	    }
	    
	    @Override
		protected void onCreate(Bundle savedInstanceState)
	    {
					requestWindowFeature(Window.FEATURE_NO_TITLE);
					super.onCreate(savedInstanceState);
					setContentView(R.layout.ftp_pc_connect_wifi);
			
					mActivity = WiFiActivity.this;
	        Context myContext = Globals.getContext();
	        if (null == myContext)
	        {
	            myContext = mActivity.getApplicationContext();
	            if (null == myContext)
	            {
	                throw new NullPointerException("Null context!?!?!?");
	            }
	            Globals.setContext(myContext);
	        }

	        ipText = (TextView) findViewById(R.id.ip_address);
	        instructionText = (AppCompatTextView) findViewById(R.id.instruction);
	        instructionTextPre = (AppCompatTextView) findViewById(R.id.instruction_pre);
	        startStopButton = (View)this.findViewById(R.id.start_stop_button);
	        startStopButton.setOnClickListener(new View.OnClickListener()
	        {
							@Override
						public void onClick(View v)
							{
							 Globals.setLastError(null);
					            File chrootDir = new File(Defaults.chrootDir);
					            if (!chrootDir.isDirectory())
					            {
					               return;
					            }
					            Context context = mActivity.getApplicationContext();
					            Intent intent = new Intent(context, FTPServerService.class);

					            Globals.setChrootDir(chrootDir);
					            if (!FTPServerService.isRunning())
					            {
					                warnIfNoExternalStorage();
					                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
					                    context.startService(intent);
					                }
					            }
					            else
					            {
					                context.stopService(intent);
					            }

						}
			});

	        updateUi();
	        UiUpdater.registerClient(handler);

	        findViewById(R.id.wifi_state_image).setOnClickListener(new OnClickListener()
	                {
	                    public void onClick(View v) 
	                    {
	                        Intent intent = new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS);
	                        startActivity(intent);
	                    }
	                });
	        
	        titleCustomBarView_wifi = (TitleCustomBarView)findViewById(R.id.TitleCustomBarView_wifi_ftp);
	        titleCustomBarView_wifi.setTitleCustomClicker(titleCustomClicker);
	        titleCustomBarView_wifi.setTitle("WIFI FTP");
	        titleCustomBarView_wifi.setActivity(this);
		}

	
	    /**
	     * Whenever we regain focus, we should update the button text depending on
	     * the state of the server service.
	     */
	    public void onStart()
	    {
	        super.onStart();
	        UiUpdater.registerClient(handler);
	        updateUi();
	    }

	    public void onResume()
	    {
	        super.onResume();

	        UiUpdater.registerClient(handler);
	        updateUi();
	        // Register to receive wifi status broadcasts
	        //myLog.l(Log.DEBUG, "Registered for wifi updates");
	        IntentFilter filter = new IntentFilter();
	        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
	        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
	        mActivity.registerReceiver(wifiReceiver, filter);
	    }

	    /*
	     * Whenever we lose focus, we must unregister from UI update messages from
	     * the FTPServerService, because we may be deallocated.
	     */
	    public void onPause() 
	    {
	        super.onPause();
	        UiUpdater.unregisterClient(handler);
	       // myLog.l(Log.DEBUG, "Unregistered for wifi updates");
	        mActivity.unregisterReceiver(wifiReceiver);
	    }

	    public void onStop() 
	    {
	        super.onStop();
	        UiUpdater.unregisterClient(handler);
	    }

	    public void onDestroy() 
	    {
	        super.onDestroy();
	        UiUpdater.unregisterClient(handler);
	    }

	    /**
	     * This will be called by the static UiUpdater whenever the service has
	     * changed state in a way that requires us to update our UI. We can't use
	     * any myLog.l() calls in this function, because that will trigger an
	     * endless loop of UI updates.
	     */
	    @SuppressWarnings("deprecation")
		public void updateUi()
	    {
	        //myLog.l(Log.DEBUG, "Updating UI", true);

	        WifiManager wifiMgr = (WifiManager) mActivity.getSystemService(Context.WIFI_SERVICE);
	        @SuppressWarnings("unused")
					int wifiState = wifiMgr.getWifiState();
	        WifiInfo info = wifiMgr.getConnectionInfo();
	        String wifiId = info != null ? info.getSSID() : null;
	        boolean isWifiReady = FTPServerService.isWifiEnabled();

	        setText(R.id.wifi_state, isWifiReady ? wifiId : getString(R.string.no_wifi_hint));
	        ImageView wifiImg = (ImageView)findViewById(R.id.wifi_state_image);
	        wifiImg.setImageResource(isWifiReady ? R.mipmap.ftp_wifi_state4 : R.mipmap.ftp_wifi_state0);

	        boolean running = FTPServerService.isRunning();
	        if (running)
	        {
	            //myLog.l(Log.DEBUG, "updateUi: server is running", true);
	            // Put correct text in start/stop button
	            // Fill in wifi status and address
	            InetAddress address = FTPServerService.getWifiIp();
	            if (address != null)
	            {
	                String port = ":" + FTPServerService.getPort();
	                ipText.setText("ftp://" + address.getHostAddress() + (FTPServerService.getPort() == 21 ? "" : port));

	            }
	            else
	            {
	                // could not get IP address, stop the service
	                Context context = mActivity.getApplicationContext();
	                Intent intent = new Intent(context, FTPServerService.class);
	                context.stopService(intent);
	                ipText.setText("");
	            }
	        }

	        startStopButton.setEnabled(isWifiReady);
	        TextView startStopButtonText = (TextView) findViewById(R.id.start_stop_button_text);
	        if (isWifiReady)
	        {
	            startStopButtonText.setText(running ? R.string.stop_server : R.string.start_server);
	            startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(running ? R.drawable.ftp_disconnect
	                    : R.drawable.ftp_connect, 0, 0, 0);
	            startStopButtonText.setTextColor(running ? Color.parseColor("#7f070040")
	                    : Color.parseColor("#7f070040"));
	        }
	        else
	        {
	            if (FTPServerService.isRunning())
	            {
	                Context context = mActivity.getApplicationContext();
	                Intent intent = new Intent(context, FTPServerService.class);
	                context.stopService(intent);
	            }

	            startStopButtonText.setText(R.string.no_wifi);
	            startStopButtonText.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
	            startStopButtonText.setTextColor(Color.GRAY);
	        }

	        ipText.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
	        instructionText.setVisibility(running ? View.VISIBLE : View.GONE);
	        instructionTextPre.setVisibility(running ? View.GONE : View.VISIBLE);
	    }

	    private void setText(int id, String text)
	    {
	        TextView tv = (TextView) findViewById(id);
	        tv.setText(text);
	    }

	    /*OnClickListener startStopListener = new OnClickListener() {
	        public void onClick(View v) {
	            Globals.setLastError(null);
	            File chrootDir = new File(Defaults.chrootDir);
	            if (!chrootDir.isDirectory())
	                return;

	            Context context = mActivity.getApplicationContext();
	            Intent intent = new Intent(context, FTPServerService.class);

	            Globals.setChrootDir(chrootDir);
	            if (!FTPServerService.isRunning()) {
	                warnIfNoExternalStorage();
	                if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
	                    context.startService(intent);
	                }
	            } else {
	                context.stopService(intent);
	            }
	        }
	    };*/

	    private void warnIfNoExternalStorage()
	    {
	        String storageState = Environment.getExternalStorageState();
	        if (!storageState.equals(Environment.MEDIA_MOUNTED))
	        {
	            //myLog.i("Warning due to storage state " + storageState);
	            Toast toast = Toast.makeText(mActivity, "没有外部存储设备或SD卡不可用", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
	        }
	    }

	    BroadcastReceiver wifiReceiver = new BroadcastReceiver()
	    {
	        public void onReceive(Context ctx, Intent intent)
	        {
	            //myLog.l(Log.DEBUG, "Wifi status broadcast received");
	            updateUi();
	        }
	    };

	    boolean requiredSettingsDefined()
	    {
	        SharedPreferences settings = mActivity.getSharedPreferences(Defaults.getSettingsName(), Defaults.getSettingsMode());
	        String username = settings.getString("username", null);
	        String password = settings.getString("password", null);
	        if (username == null || password == null)
	        {
	            return false;
	        } else {
	            return true;
	        }
	    }

	    /**
	     * Get the settings from the FTPServerService if it's running, otherwise
	     * load the settings directly from persistent storage.
	     */
	    SharedPreferences getSettings()
	    {
	        SharedPreferences settings = FTPServerService.getSettings();
	        if (settings != null)
	        {
	            return settings;
	        }
	        else
	        {
	            return mActivity.getPreferences(Activity.MODE_PRIVATE);
	        }
	    }
	    
	    private TitleCustomBarView.TitleCustomClicker titleCustomClicker = new TitleCustomBarView.TitleCustomClicker()
		   {
			
				@Override
				public void doSub2Click() 
				{
					
					
				}
				
				@Override
				public void doSub1Click()
				{
					
				}
				
				@Override
				public void doMoreClick() 
				{
					
				}
				
				@Override
				public void doBackClick() 
				{
					finish();
				}
		};		  
}
