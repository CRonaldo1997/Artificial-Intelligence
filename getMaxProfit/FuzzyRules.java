package getMaxProfit;

import java.util.HashMap;
import java.util.Random;
import java.util.Map.Entry;

public class FuzzyRules {
	double priceOfDay;
	double madOfDay;
	public HashMap<String,Double> degreeSharePair;
	public HashMap<String,Integer> defuzzyPair;
	
	public FuzzyRules(){
		priceOfDay = 0.0;
		madOfDay = 0.0;
		degreeSharePair = new HashMap<String,Double>();
		defuzzyPair = new HashMap<String,Integer>();
		defuzzyPair.put("BM", -800-700-600);
		defuzzyPair.put("BF", -500-400-300);
		defuzzyPair.put("DT", -200-100+0+100+200);
		defuzzyPair.put("SF", 300+400+500);
		defuzzyPair.put("SM", 600+700+800);
	}
	
	public double defuzzification(){
		double sum1 = 0;
		double sum2 = 0;
		double cog;
		java.util.Iterator<Entry<String, Double>> entries = degreeSharePair.entrySet().iterator();
		while (entries.hasNext()) {  
		    Entry<String, Double> entry = entries.next();  
		    System.out.println("fuzzyShare = " + entry.getKey() + ", fuzzyShareDegree = " + entry.getValue());
		    
		    if(entry.getKey().equals("BM")){
		    	sum1 += this.defuzzyPair.get("BM") * entry.getValue();
		    	sum2 += 3 * entry.getValue();
		    }
		    else if(entry.getKey().equals("BF")){
		    	sum1 += this.defuzzyPair.get("BF") * entry.getValue();
		    	sum2 += 3 * entry.getValue();
		    }
		    else if(entry.getKey().equals("DT")){
		    	sum1 += this.defuzzyPair.get("DT") * entry.getValue();
		    	sum2 += 5 * entry.getValue();
		    }
		    else if(entry.getKey().equals("SF")){
		    	sum1 += this.defuzzyPair.get("SF") * entry.getValue();
		    	sum2 += 3 * entry.getValue();
		    }
		    else if(entry.getKey().equals("SM")){
		    	sum1 += this.defuzzyPair.get("SF") * entry.getValue();
		    	sum2 += 3 * entry.getValue();
		    }
		}
	    cog = 100*sum1/sum2;
	    return cog;
	}
	
	public void printHashMap(){
		java.util.Iterator<Entry<String, Double>> entries = degreeSharePair.entrySet().iterator();
		while (entries.hasNext()) {  
		    Entry<String, Double> entry = entries.next();  
		    System.out.println("fuzzyShare = " + entry.getKey() + ", fuzzyShareDegree = " + entry.getValue());  
		}
	}
	
	public void getPriceAndMadOfDay(int dayNum){
		Random rand = new Random();
		double randDouble = -1.0 + rand.nextDouble()*2.0;
		System.out.println("The random double is "+ randDouble);
		this.madOfDay = 0.5*Math.cos(0.3*dayNum)-Math.sin(0.3*dayNum)+0.4*randDouble*(dayNum % 3);
		
		double angleSin = 2 * Math.PI * (dayNum / 19.0);
		double angleCos = 2 * Math.PI * (dayNum / 5.0);
		this.priceOfDay = 10 + 2.5 * Math.sin(angleSin) + 0.8 * Math.cos(angleCos) +7*randDouble*((dayNum)%2);
	}
	
	//sum the degree if get same fuzzy variable
	public void updateDegreeSharePair(String keys, double degree){
		if(this.degreeSharePair.containsKey(keys)){
			double newValue = this.degreeSharePair.get(keys) + degree;
			this.degreeSharePair.put(keys, newValue);
		}
		else{
			this.degreeSharePair.put(keys, degree);
		}
	}
	
	public  void fuzzyRules4Output(int dayNum){
		getPriceAndMadOfDay(dayNum);
		Price price = new Price(priceOfDay);
		price.getPriceDegreeOfMembership();
		System.out.println("today's price is "+priceOfDay);
		price.printHashMap();
		
		Mad mad = new Mad(madOfDay);
		mad.getMadDegreeOfMembership();
		System.out.println("today's mad is "+madOfDay);
		mad.printHashMap();

		//MAD = N
		if(price.degreeFuzzyPricePair.containsKey("VL") && 
		   mad.degreeFuzzyMadPair.containsKey("N")){
			double min = Math.min(price.degreeFuzzyPricePair.get("VL"), mad.degreeFuzzyMadPair.get("N"));
			System.out.println("price is VL and mad is N, then BM, min is "+ min);
			updateDegreeSharePair("BM", min);
		}
		
		if(price.degreeFuzzyPricePair.containsKey("LO") && 
				   mad.degreeFuzzyMadPair.containsKey("N")){
					double min = Math.min(price.degreeFuzzyPricePair.get("LO"), mad.degreeFuzzyMadPair.get("N"));
					System.out.println("price is LO and mad is N, then BF, min is "+ min);
					updateDegreeSharePair("BF", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("MD") && 
				   mad.degreeFuzzyMadPair.containsKey("N")){
					double min = Math.min(price.degreeFuzzyPricePair.get("MD"), mad.degreeFuzzyMadPair.get("N"));
					System.out.println("price is MD and mad is N, then SF, min is "+ min);
					updateDegreeSharePair("SF", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("HI") && 
				   mad.degreeFuzzyMadPair.containsKey("N")){
					double min = Math.min(price.degreeFuzzyPricePair.get("HI"), mad.degreeFuzzyMadPair.get("N"));
					System.out.println("price is HI and mad is N, then SM, min is "+ min);
					updateDegreeSharePair("SM", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("VH") && 
				   mad.degreeFuzzyMadPair.containsKey("N")){
					double min = Math.min(price.degreeFuzzyPricePair.get("VH"), mad.degreeFuzzyMadPair.get("N"));
					System.out.println("price is VH and mad is N, then SM, min is "+ min);
					updateDegreeSharePair("SM", min);
				}
		
		//MAD = Z
		if(price.degreeFuzzyPricePair.containsKey("VL") && 
				   mad.degreeFuzzyMadPair.containsKey("Z")){
					double min = Math.min(price.degreeFuzzyPricePair.get("VL"), mad.degreeFuzzyMadPair.get("Z"));
					System.out.println("price is VL and mad is Z, then BM, min is "+ min);
					updateDegreeSharePair("BM", min);
				}
				
		if(price.degreeFuzzyPricePair.containsKey("LO") && 
				   mad.degreeFuzzyMadPair.containsKey("Z")){
					double min = Math.min(price.degreeFuzzyPricePair.get("LO"), mad.degreeFuzzyMadPair.get("Z"));
					System.out.println("price is LO and mad is Z, then BF, min is "+ min);
					updateDegreeSharePair("BF", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("MD") && 
				   mad.degreeFuzzyMadPair.containsKey("Z")){
					double min = Math.min(price.degreeFuzzyPricePair.get("MD"), mad.degreeFuzzyMadPair.get("Z"));
					System.out.println("price is MD and mad is Z, then DT, min is "+ min);
					updateDegreeSharePair("DT", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("HI") && 
				   mad.degreeFuzzyMadPair.containsKey("Z")){
					double min = Math.min(price.degreeFuzzyPricePair.get("HI"), mad.degreeFuzzyMadPair.get("Z"));
					System.out.println("price is HI and mad is Z, then SF, min is "+ min);
					updateDegreeSharePair("SF", min);
		}
		
		if(price.degreeFuzzyPricePair.containsKey("VH") && 
				   mad.degreeFuzzyMadPair.containsKey("Z")){
					double min = Math.min(price.degreeFuzzyPricePair.get("VH"), mad.degreeFuzzyMadPair.get("Z"));
					System.out.println("price is VH and mad is Z, then SM, min is "+ min);	
					updateDegreeSharePair("SM", min);
		}
		
		//MAD = P
		if(price.degreeFuzzyPricePair.containsKey("VL") && 
				   mad.degreeFuzzyMadPair.containsKey("P")){
					double min = Math.min(price.degreeFuzzyPricePair.get("VL"), mad.degreeFuzzyMadPair.get("P"));
					System.out.println("price is VL and mad is P, then BM, min is "+ min);	
					updateDegreeSharePair("BM", min);
		}
				
		if(price.degreeFuzzyPricePair.containsKey("LO") && 
				   mad.degreeFuzzyMadPair.containsKey("P")){
					double min = Math.min(price.degreeFuzzyPricePair.get("LO"), mad.degreeFuzzyMadPair.get("P"));
					System.out.println("price is LO and mad is P, then BM, min is "+ min);		
					updateDegreeSharePair("BM", min);
		}
		
		if(price.degreeFuzzyPricePair.containsKey("MD") && 
				   mad.degreeFuzzyMadPair.containsKey("P")){
					double min = Math.min(price.degreeFuzzyPricePair.get("MD"), mad.degreeFuzzyMadPair.get("P"));
					System.out.println("price is MD and mad is P, then BF, min is "+ min);	
					updateDegreeSharePair("BF", min);
		}
		
		if(price.degreeFuzzyPricePair.containsKey("HI") && 
				   mad.degreeFuzzyMadPair.containsKey("P")){
					double min = Math.min(price.degreeFuzzyPricePair.get("HI"), mad.degreeFuzzyMadPair.get("P"));
					System.out.println("price is HI and mad is P, then SF, min is "+ min);
					updateDegreeSharePair("SF", min);
				}
		
		if(price.degreeFuzzyPricePair.containsKey("VH") && 
				   mad.degreeFuzzyMadPair.containsKey("P")){
					double min = Math.min(price.degreeFuzzyPricePair.get("VH"), mad.degreeFuzzyMadPair.get("P"));
					System.out.println("price is VH and mad is P, then SM, min is "+ min);
					updateDegreeSharePair("SM", min);
				}
	}
	
	public static void main(String[] args){
		FuzzyRules fuzzyRules = new FuzzyRules();
		double maxPriceOfDay = 0;
		double maxMadOfDay = 0;
		double minMadOfDay = 0;
		double currentMoney = 10000.0;
		double currentShare = 0.0;
		
		for (int i = 1; i <= 150; i++){
			fuzzyRules.fuzzyRules4Output(i);
			double shareOfDay = fuzzyRules.defuzzification();
			System.out.println("day "+i+"'s share is "+shareOfDay);
			if (fuzzyRules.priceOfDay > maxPriceOfDay){
				maxPriceOfDay = fuzzyRules.priceOfDay;
			}
			if(fuzzyRules.madOfDay > maxMadOfDay){
				maxMadOfDay = fuzzyRules.madOfDay;
			}
			if(fuzzyRules.madOfDay < minMadOfDay){
				minMadOfDay = fuzzyRules.madOfDay;
			}
			
			//buy
			if(shareOfDay < 0){
				if(currentMoney >= -1 * shareOfDay * fuzzyRules.priceOfDay){
					currentMoney += shareOfDay * fuzzyRules.priceOfDay;
					currentShare -= shareOfDay;
					System.out.println("in day "+i+",need to buy, money is enough, share of day is "+ shareOfDay);
					System.out.println("in day "+i+",need to buy, current money is "+currentMoney);
					System.out.println("in day "+i+",need to buy, current share is "+currentShare);
				}
				else{
					double buyShare = (currentMoney*1.0)/fuzzyRules.priceOfDay;
					currentShare += buyShare;
					currentMoney -= buyShare * fuzzyRules.priceOfDay;
					System.out.println("in day "+i+",need to buy, money is not enough,share to buy is "+ buyShare);
					System.out.println("in day "+i+",need to buy, current money is "+currentMoney);
					System.out.println("in day "+i+",need to buy, current share is "+currentShare);
				}
			}
			//sell
			else{
				if(currentShare >= shareOfDay){
					currentShare -= shareOfDay;
					currentMoney += fuzzyRules.priceOfDay*shareOfDay;
					System.out.println("in day "+i+",need to sell, current share is enough, share of day is "+ shareOfDay);
					System.out.println("in day "+i+",need to sell, current money is "+currentMoney);
					System.out.println("in day "+i+",need to sell, current share is "+currentShare);
				}
				else{
					System.out.println("in day "+i+",need to sell, current share is not enough, share of day is "+ shareOfDay+" current share is "+currentShare);
					currentMoney += fuzzyRules.priceOfDay * currentShare;
					currentShare = 0;
					System.out.println("in day "+i+",need to sell, current money is "+currentMoney);
					System.out.println("in day "+i+",need to sell, current share is "+currentShare);
				}
			}
		}
//		System.out.println("The max price of day is "+ maxPriceOfDay);
//		System.out.println("The max mad of day is "+ maxMadOfDay);
//		System.out.println("The min mad of day is "+ minMadOfDay);
		System.out.println("after 150 days, current money is "+currentMoney);
		System.out.println("after 150 days, current Share is "+currentShare);
		double totalMoney = currentMoney + currentShare*fuzzyRules.priceOfDay;
		System.out.println("after 150 days, total money is "+ totalMoney);
	}

}
