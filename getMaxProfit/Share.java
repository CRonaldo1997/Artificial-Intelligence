package getMaxProfit;

import java.util.HashMap;

public class Share {
	public double share;
	public HashMap<String,Double> degreeFuzzySharePair;

	public void getShareDegreeOfMembership(int share){
		if(share >= -800 && share <= -700){
			degreeFuzzySharePair.put("BM", 1.0);
		}
		
		if(share > -700 && share <= -500){
			double fuzzyShareDegree = -1.0/2 * share - 5.0/2; 
			degreeFuzzySharePair.put("BM", fuzzyShareDegree);
		}
		
		if(share >= -600 && share <= -400){
			double fuzzyShareDegree = 1.0/2 * share + 3;
			degreeFuzzySharePair.put("BF", fuzzyShareDegree);
		}
		
		if(share > -400 && share <= -200){
			double fuzzyShareDegree = -1.0/2 * share - 1;
			degreeFuzzySharePair.put("BF", fuzzyShareDegree);
		}
		
		if(share >= -300 && share <= 0){
			double fuzzyShareDegree = 1.0/3 * share + 1;
			degreeFuzzySharePair.put("DT", fuzzyShareDegree);
		}
		
		if(share > 0 && share <= 300){
			double fuzzyShareDegree = -1.0/3 * share + 1;
			degreeFuzzySharePair.put("DT", fuzzyShareDegree);
		}
		
		if(share >= 200 && share <= 400){
			double fuzzyShareDegree = 1.0/2 * share - 1;
			degreeFuzzySharePair.put("SF",fuzzyShareDegree);
		}
		
		if(share > 400 && share <= 600){
			double fuzzyShareDegree = -1.0/2 * share + 3;
			degreeFuzzySharePair.put("SF",fuzzyShareDegree);
		}
		
		if(share >= 500 && share <= 700){
			double fuzzyShareDegree = 1.0/2 * share - 5.0/2;
			degreeFuzzySharePair.put("SM",fuzzyShareDegree);
		}
		
		if(share > 700){
			degreeFuzzySharePair.put("SM", 1.0); 
		}
	}
}
