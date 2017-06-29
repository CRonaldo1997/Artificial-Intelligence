package getMaxProfit;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Price {
	public double price;
	public HashMap<String, Double> degreeFuzzyPricePair;

	public Price(double price){
		this.price = price;
		this.degreeFuzzyPricePair = new HashMap<String,Double>();
	}
	
	public void printHashMap(){
		java.util.Iterator<Entry<String, Double>> entries = degreeFuzzyPricePair.entrySet().iterator();
		while (entries.hasNext()) {  
		    Entry<String, Double> entry = entries.next();  
		    System.out.println("fuzzyPrice = " + entry.getKey() + ", fuzzyPriceDegree = " + entry.getValue());  
		}
	}
	
	public void getPriceDegreeOfMembership(){
		if(price >=0 && price <= 2){
			degreeFuzzyPricePair.put("VL", 1.0);
		}
		
		if(price > 2 && price <= 6 ){
			double fuzzyPriceDegree = -1.0/4 * price + 3.0/2;
			degreeFuzzyPricePair.put("VL", fuzzyPriceDegree);
		}
		
		if(price >= 2 && price <= 6){
			double fuzzyPriceDegree = 1.0/4 * price - 1.0/2;
			degreeFuzzyPricePair.put("LO", fuzzyPriceDegree);
		}
		
		if(price > 6 && price <= 10){
			double fuzzyPriceDegree = -1.0/4 * price + 5.0/2;
			degreeFuzzyPricePair.put("LO", fuzzyPriceDegree);
		}
		
		if(price >= 6 && price <= 10){
			double fuzzyPriceDegree = 1.0/4 * price - 3.0/2;
			degreeFuzzyPricePair.put("MD", fuzzyPriceDegree);
		}
		
		if(price > 10 && price <= 14){
			double fuzzyPriceDegree = -1.0/4 * price + 7.0/2;
			degreeFuzzyPricePair.put("MD", fuzzyPriceDegree);
		}
		
		if(price >= 10 && price <= 14){
			double fuzzyPriceDegree = 1.0/4 * price - 5.0/2;
			degreeFuzzyPricePair.put("HI", fuzzyPriceDegree);
		}
		
		if(price > 14 && price <= 18){
			double fuzzyPriceDegree = -1.0/4 * price + 9.0/2;
		
			degreeFuzzyPricePair.put("HI", fuzzyPriceDegree);
		}
		
		if(price >= 14 && price <= 18){
			double fuzzyPriceDegree = 1.0/4 * price - 7.0/2;
			degreeFuzzyPricePair.put("VH", fuzzyPriceDegree);
		}
		
		if(price > 18){
			degreeFuzzyPricePair.put("VH", 1.0);
		}
	}
}
