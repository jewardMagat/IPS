package com.example.beacontest2;

import java.util.ArrayList;

//import static android.support.v4.app.FragmentActivity.TAG;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity implements BeaconConsumer{
	
	private BeaconManager beaconManager;
	private TextView 
	txtUUID1, txtDistance1, 
	txtUUID2, txtDistance2, 
	txtUUID3, txtDistance3,
	txtXPos, txtYPos;
	private String UUID;
	private double distance;
	private int RSSI;
	private int txPower;
	private String comparator;
	
	private double 
	a  = 0,
	b = 0,
	d = 1,
	e = 0,
	g = 0,
	h = 1,
	
	c = 0,
	f = 0,
	i = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtUUID1 = (TextView)findViewById(R.id.txtUUID);
		txtDistance1 = (TextView)findViewById(R.id.txtMajor);
		txtUUID2 = (TextView)findViewById(R.id.txtMinor);
		txtDistance2 = (TextView)findViewById(R.id.txtRSSI);
		txtUUID3 = (TextView)findViewById(R.id.txtDistance);
		txtDistance3 = (TextView)findViewById(R.id.txtTxPower);
		txtXPos = (TextView)findViewById(R.id.txtXPos);
		txtYPos = (TextView)findViewById(R.id.txtYPos);
		
		beaconManager = BeaconManager.getInstanceForApplication(this);
		beaconManager.getBeaconParsers().add(new BeaconParser().
				setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));
		beaconManager.setBackgroundScanPeriod(1100l);
		beaconManager.setBackgroundBetweenScanPeriod(250l);
		beaconManager.bind(this);
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
	
	protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override 
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            	
                if (beacons.size() > 0) {
                	Iterator<Beacon>beaconIterator = beacons.iterator();
                	while(beaconIterator.hasNext()){
           
                		Beacon beacon = beaconIterator.next();
                	
//                		Log.d("Beacon: ", beacon.getId1().toString() + " Distance: " + beacon.getDistance());
                		UUID = beacon.getId1().toString();
                		distance = beacon.getDistance();
                		
                		RSSI = beacon.getRssi();
                		txPower = beacon.getTxPower();
                		comparator = UUID.substring(UUID.length()-6);
                		         		                		
                		runOnUiThread(new Runnable(){
    						public void run(){
    														
    							switch(comparator){
    							
    							case "beac01":
    								txtUUID1.setText("UUID: beacon1");
    								txtDistance1.setText("Distance: " + distance);
    								c = distance;
    								break;
    							case "beac02":
    								txtUUID2.setText("UUID: beacon2");
    								txtDistance2.setText("Distance: " + distance);
    								f = distance;
    								break;
    							case "beac03":
    								txtUUID3.setText("UUID: beacon3");
    								txtDistance3.setText("Distance: " + distance);
    								i = distance;
    								break;
    							default:
    								break;    								
    							}
    							
    							txtXPos.setText("X-Position: " + getXCoordinate());
    							txtYPos.setText("Y-Position: " + getYCoordinate());
    						}
    					});        
                	}                	
                }
            }
        });
        
        try {
            beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", null, null, null));
        } catch (RemoteException e) {    }
    }
    
//    private double computeDistance(int RSSI, int TxPower){
//    	double distance = 0;
//    	
//    	distance = 10*((RSSI-TxPower)/(10*1.7));
//    	
//    	return distance;
//    }
    
//    private double computeDistance(double rssi){
//    	 double result =  (-2.2286 + Math.sqrt(Math.pow(2.2286, 2) - 4 * 0.4286 * (37.8 - (rssi * -1)))) / (2 * 0.4286);
//
//         return result;
//         
//    }
    
    private double computeDistance(int RSSI, int txPower){	
    	
    	if(RSSI == 0)
    		return -1.0;
    	
    	double ratio = RSSI*1.0/txPower;
    	
    	if(ratio < 1.0)
    		return Math.pow(ratio, 10);
    	else{
    		double accuracy = (0.89976)*Math.pow(ratio, 7.7095) + 0.111;
    		return accuracy;
    	}
    }
    
    private double aveDistance(double distance){
    	double average = 0;
    	double output = 0;
    	
    	for(int x=0; x<25; x++){
    		average+=distance;
    	}
    	
    	output = average/25;
    	return output;
    }
    
    
	//xCoordinate computation.
	private double getXCoordinate(){
		
		double xCoordinate = 0;
//		getDistance();
		
		xCoordinate = ((h-e)*(Math.pow(c, 2) + Math.pow(d, 2) + Math.pow(e, 2) - Math.pow(a, 2)- Math.pow(b, 2)-Math.pow(f, 2))-
				((e - b)*(Math.pow(f, 2)+Math.pow(g, 2)+Math.pow(h, 2)-Math.pow(d, 2)-Math.pow(e, 2)-Math.pow(i, 2))))/
				(2*(((d - a)*(h - e))+((d - g)*(e - b))));
		
		return xCoordinate;
	}
	
	//yCoordinate computation.
	private double getYCoordinate(){
		
		double yCoordinate = 0;
//		getDistance();
		
		yCoordinate = ((d -g)*(Math.pow(c, 2) + Math.pow(d, 2) + Math.pow(e, 2) - Math.pow(a, 2)- Math.pow(b, 2)-Math.pow(f, 2))+
				((d - a)*(Math.pow(f, 2)+Math.pow(g, 2)+Math.pow(h, 2)-Math.pow(d, 2)-Math.pow(e, 2)-Math.pow(i, 2))))/
				(2*(((d - a)*(h - e))+((d - g)*(e - b))));
		
		return yCoordinate;
	}	
}
