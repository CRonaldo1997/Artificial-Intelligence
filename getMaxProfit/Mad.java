package getMaxProfit;

import java.util.HashMap;
import java.util.Map.Entry;

public class Mad {
	public double mad;
	public HashMap<String,Double> degreeFuzzyMadPair;
	
	public void printHashMap(){
		java.util.Iterator<Entry<String, Double>> entries = degreeFuzzyMadPair.entrySet().iterator();
		while (entries.hasNext()) {  
		    Entry<String, Double> entry = entries.next();  
		    System.out.println("fuzzyMad = " + entry.getKey() + ", fuzzyMadDegree = " + entry.getValue());  
		}
	}
	
	public Mad(double mad){
		this.mad = mad;
		this.degreeFuzzyMadPair = new HashMap<String,Double>();
	}
	public void getMadDegreeOfMembership(){
		if (mad <= -1){
			degreeFuzzyMadPair.put("N", 1.0);
		}
		
		if(mad > -1 && mad <= -0.25){
			double fuzzyMadDegree = -4.0/3 * mad - 1.0/3;
			degreeFuzzyMadPair.put("N", fuzzyMadDegree);
		}
		
		if(mad >= -0.5 && mad < 0){
			double fuzzyMadDegree = 2 * mad + 1;
			degreeFuzzyMadPair.put("Z", fuzzyMadDegree);
		}
		
		if(mad >= 0 && mad < 0.5){
			double fuzzyMadDegree = -2 * mad + 1;
			degreeFuzzyMadPair.put("Z", fuzzyMadDegree);
		}
		
		if(mad >= 0.25 && mad < 1){
			double fuzzyMadDegree = 4.0/3 * mad - 1.0/3;
			degreeFuzzyMadPair.put("P", fuzzyMadDegree);
		}
		
		if(mad >= 1){
			degreeFuzzyMadPair.put("P", 1.0);
		}
	}
	
}
