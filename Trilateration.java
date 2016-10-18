//TRILATERATION 

public class App {
	
	private static double 
	a = 4,						//access point 1: X-axis.
	b = 4,						//access point 1: Y-Axis.
	d = 26,						//access point 2: X-Axis.
	e = 10,						//access point 2: Y-Axis.
	g = 16,						//access point 3: X-Axis.
	h = 26,						//access point 3: Y-Axis.
	
	
// expected values of c, f, and i.	
//	c = 6,						//access point 1: distance; must be computed using computeforDistance(); 
//	f = 9,						//access point 2: distance; must be computed using computeforDistance(); 
//	i = 4.5,					//access point 3: distance; must be computed using computeforDistance(); 
	
	c = 0,						//access point 1: distance; must be computed using computeforDistance(); 
	f = 0,						//access point 2: distance; must be computed using computeforDistance(); 
	i = 0,						//access point 3: distance; must be computed using computeforDistance(); 
	
	maxRange = 30,				//maximum range of the access points, it may vary per module.
	
	signalStrengthAp1 = 0.8,	//signal strength of access point 1, must be computed in
								//terms of percentage using dbmConversion.
									
	signalStrengthAp2 = 0.7,	//signal strength of access point 1, must be computed in
								//terms of percentage using dbmConversion.
	
	signalStrengthAp3 = 0.85;	//signal strength of access point 1, must be computed in
								//terms of percentage using dbmConversion.
	
	
		
	public static void main(String[] args) {
		
		getDistance();
		System.out.println("Distance from AP1 is: " + c);			//print out the distance of the receiver from AP1.	
		System.out.println("Distance from AP2 is: " + f);			//print out the distance of the receiver from AP2.
		System.out.println("Distance from AP3 is: " + i);			//print out the distance of the receiver from AP3.
		
		System.out.println("X coordintae is: " + getXCoordinate());	//print out the xCoordinate of the receiver.
		System.out.println("Y Coordinate is: " + getYCoordinate()); //print out the yCoordinate of the receiver.
		
		System.out.println(dbmConversion(-60));						//displays the signal strength in terms of percentage. 
																	//this is a hard coded value.
	}				
	
	private static double computeForDistance(double signalStrength, double range){
		double output = 0.0;		
		output = range * (1 - signalStrength);		//formula for getting the distance of the AP.
		return output;
	}
	
	private static void getDistance(){
		c = computeForDistance(signalStrengthAp1,maxRange);	//get the distance of access point 1 from the receiver.
		f = computeForDistance(signalStrengthAp2,maxRange); //get the distance of access point 2 from the receiver.
		i = computeForDistance(signalStrengthAp3,maxRange); //get the distance of access point 3 from the receiver.
	}
	
	//xCoordinate computation.
	private static double getXCoordinate(){
		
		double xCoordinate = 0;
		getDistance();
		
		xCoordinate = ((h-e)*(Math.pow(c, 2) + Math.pow(d, 2) + Math.pow(e, 2) - Math.pow(a, 2)- Math.pow(b, 2)-Math.pow(f, 2))-
				((e - b)*(Math.pow(f, 2)+Math.pow(g, 2)+Math.pow(h, 2)-Math.pow(d, 2)-Math.pow(e, 2)-Math.pow(i, 2))))/
				(2*(((d - a)*(h - e))+((d - g)*(e - b))));
		
		return xCoordinate;
	}
	
	//yCoordinate computation.
	private static double getYCoordinate(){
		
		double yCoordinate = 0;
		getDistance();
		
		yCoordinate = ((d -g)*(Math.pow(c, 2) + Math.pow(d, 2) + Math.pow(e, 2) - Math.pow(a, 2)- Math.pow(b, 2)-Math.pow(f, 2))+
				((d - a)*(Math.pow(f, 2)+Math.pow(g, 2)+Math.pow(h, 2)-Math.pow(d, 2)-Math.pow(e, 2)-Math.pow(i, 2))))/
				(2*(((d - a)*(h - e))+((d - g)*(e - b))));
		
		return yCoordinate;
	}
	
	//convert dbm to percentage value.
	private static double dbmConversion(double dbm){
		double quality = 0;
		
		if(dbm <= -100){
			quality = 0;
		}else if(dbm >= -50){
			quality = 1;
		}else{
			quality = 0.02 * (dbm + 100.0);
		}
				
		return quality;
	}
}
