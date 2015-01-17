package com.go.magneticsensor;




import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.os.Bundle;

public class MainActivity extends Activity implements SensorEventListener{
    /** Called when the activity is first created. */
	
	private TextView magneticX;
    private TextView magneticY;
    private TextView magneticZ;
    private TextView magneticTo;
    private SensorManager sensorManager = null;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         //Capture magnetic sensor related view elements
       // magneticX = (TextView) findViewById(R.id.magneticX);
        //magneticY = (TextView) findViewById(R.id.magneticY);
        //magneticZ = (TextView) findViewById(R.id.magneticZ);
        magneticTo = (TextView) findViewById(R.id.magneticTotal);
        
        // Register magnetic sensor
        sensorManager.registerListener(this,
                sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_NORMAL);
    }
        @Override
        protected void onPause() {
            // Unregister the listener
            sensorManager.unregisterListener(this);
            super.onPause();
        }

        @Override
        protected void onStop() {
            // Unregister the listener
            sensorManager.unregisterListener(this);
            super.onStop();
        }

        @Override
        protected void onResume() {
            super.onResume();

            // Register magnetic sensor
            sensorManager.registerListener(this,
                    sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
            // Ignoring this for now

        }

        public void onSensorChanged(SensorEvent sensorEvent) {
            synchronized (this) {
                if (sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
                	
                    //magneticX.setText( Integer.toString( (int)sensorEvent.values[0]));
                    //magneticY.setText( Integer.toString( (int)sensorEvent.values[1]));
                    //magneticZ.setText( Integer.toString( (int)sensorEvent.values[2]));
                    int val = (int)(Math.abs(sensorEvent.values[0]) + Math.abs(sensorEvent.values[1]) + Math.abs(sensorEvent.values[2]));
                    magneticTo.setText(String.valueOf(val) + " µT");
                }
            }

        }
        

        @Override
    	public boolean onCreateOptionsMenu(Menu menu) {
    	    MenuInflater inflater = getMenuInflater();
    	    inflater.inflate(R.menu.menu, menu);
    	    return true;
    	}
    	
    	private void onMarketLaunch(String url) {
    		
    		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
    				"market://details?id=%s", url)));
    		startActivity(donate);
    	}
    	
    	public void share()
    	{
    		Intent intent = new Intent(Intent.ACTION_SEND);
    		intent.setType("text/plain");
    		intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.go.magneticsensor");
    		startActivity(Intent.createChooser(intent, "Share with"));
    	}
    	
    	private void showHelp()
    	{
    /*
            final String message = this.getString(R.string.help);
    		
    		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        	builder.setTitle("SMS Combo  -  Help");
        	builder.setMessage(message);
        	builder.setPositiveButton("Back", null);
        	AlertDialog dialog = builder.show();
    	*/
    	}
    	
    	private void onDevLaunch(String url) {
    		
    		Intent donate = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(
    				"market://search?q=pub:%s", url)));
    		startActivity(donate);
    	}
    	
    	@Override
    	public boolean onOptionsItemSelected(MenuItem item) {
    	    switch (item.getItemId()) {
    	        case R.id.menu_rate:    onMarketLaunch("com.go.magneticsensor");
    	                            break;
    	                            
    	        //case R.id.menu_help:    showHelp();
                //break;
    	        case R.id.menu_moreapps:	onDevLaunch("Ranebord");
    	        break; 
    	        case R.id.menu_share:	share();
    	        break;

    	    }
    	    return true;
    	}
    }