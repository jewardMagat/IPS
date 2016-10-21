package com.example.beacontest2;


//import static android.support.v4.app.FragmentActivity.TAG;

import java.util.Collection;
import java.util.Iterator;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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
	a  = 0,			//X coordinate of beacon 1
	b = 0,			//Y coordinate of beacon 1
	d = 1,			//X coordinate of beacon 2
	e = 0,			//Y coordinate of beacon 2
	g = 0,			//X coordinate of beacon 3
	h = 1,			//Y coordinate of beacon 3
	
	c = 0,			//initial value of the distance of the receiver to beacon 1
	f = 0,			//initial value of the distance of the receiver to beacon 2
	i = 0;			//initial value of the distance of the receiver to beacon 3
	
	ListView listView;
	ArrayAdapter<String>adapter;

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
	
	protected void onDestroy() {
        super.onDestroy();
        beaconManager.unbind(this);
    }
    @SuppressWarnings("deprecation")
	@Override
    public void onBeaconServiceConnect() {
        beaconManager.setRangeNotifier(new RangeNotifier() {
            @Override 
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
            	
                if (beacons.size() > 0) {
                	Iterator<Beacon>beaconIterator = beacons.iterator();
                	while(beaconIterator.hasNext()){
           
                		Beacon beacon = beaconIterator.next();
                		
                		UUID = beacon.getId1().toString();				//ID of the beacon
                		RSSI = beacon.getRssi();         				//Rssi(Received signal strength indicator) measurement between the 
                														//receiver and the beacons
                		distance = beacon.getDistance();				//computed distance between the receiver and the beacons (Library 
                														//based: altbeacon) not used in the application
                		txPower = beacon.getTxPower();					//txPower of the beacon
                		comparator = UUID.substring(UUID.length()-6);   //A String for filtering the ID of the beacons           		
                		
                		runOnUiThread(new Runnable(){
    						public void run(){  							
    													 								
    							switch(comparator){
    							
    							case "beac01":
    								txtUUID1.setText("UUID: beacon1");
    								txtDistance1.setText("Distance: " + averageDistance(distance));
    								c = averageDistance(distance);
    								break;
    							case "beac02":
    								txtUUID2.setText("UUID: beacon2");
    								txtDistance2.setText("Distance: " + averageDistance(distance));
    								f = averageDistance(distance);
    								break;
    							case "beac03":
    								txtUUID3.setText("UUID: beacon3");
    								txtDistance3.setText("Distance: " + averageDistance(distance));
    								i = averageDistance(distance);
    								break;
    							default:
    								break;    								
    							}
    							
    							//get the x and y coordinates of the receiver
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
     
    //Computation for getting the distance between the receiver and the beacons
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
    
    private double averageDistance(double distance ){
    	double averageData = 0;
    	double outputData = 0;
    	for(int x=0; x<50; x++){
    		averageData+=distance;
    	}
    	
    	outputData = averageData/50;
    	return outputData;
    }
    
    //************************TRILATERATION************************//
    
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
