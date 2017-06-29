import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;


public class Chromosome {
	int n;
	int N;
	int nGeneration;
	ArrayList<Coordinate> protoChromosome;
	ArrayList<Double> delimiter;
	ArrayList< ArrayList<Coordinate> > chromosomes;
	ArrayList< ArrayList<Coordinate> > producedChromosomes;
	ArrayList< ArrayList<Coordinate> > selectedChromosomes;
	HashMap<Integer,Double> idxFitnessPair;
	ArrayList<Double> bestFitnessList;
	ArrayList<Double> aveFitnessList;
	double pMutation;
	double pCrossover;
	HashMap<Integer, Double> indexPercentagePair;
	

	
	public Chromosome(){
		this.bestFitnessList = new ArrayList<Double>();
		this.aveFitnessList = new ArrayList<Double>();
		this.nGeneration = 0;
		this.n = 30;
		this.N = 0;
		this.pCrossover = 0.9;
		this.pMutation = 1-this.pCrossover;
		this.protoChromosome = new ArrayList<Coordinate>();
		this.chromosomes = new ArrayList< ArrayList<Coordinate> >();
		this.producedChromosomes = new ArrayList< ArrayList<Coordinate> >();
		this.selectedChromosomes = new ArrayList< ArrayList<Coordinate> >();
		this.indexPercentagePair = new HashMap<Integer, Double>();
		this.delimiter = new ArrayList<Double>();
		this.delimiter.add(0.0);
		this.generateHomeCoordinates();
		this.generateChromosomes();
		this.idxFitnessPair = new HashMap<Integer,Double>();
	}

	public void printHomeCoordinates(ArrayList<Coordinate> aChromosome){
		Iterator<Coordinate> it = aChromosome.iterator();
//		System.out.println("The coordinate of the chromosome is ");
		while(it.hasNext()){
			Coordinate coordinate = it.next();
			System.out.print("("+ coordinate.x + " , "+coordinate.y+")"+" ");
		}
		System.out.println(" ");
	}
	
	public void printAllChromosome(ArrayList< ArrayList<Coordinate> > chroms){
		Iterator<ArrayList<Coordinate>> itor = chroms.iterator();
		while(itor.hasNext()){
			ArrayList<Coordinate> oneChromosome= itor.next();
			printHomeCoordinates(oneChromosome);
		}
	}
	
	public void generateHomeCoordinates(){
		while(protoChromosome.size()<=32){
			int randX = (int)(0+Math.random()*(30-0+1));//generate random int between 0 to 30
			int randY = (int)(0+Math.random()*(30-0+1));//generate random int between 0 to 30
			Coordinate newHomeCoordinate = new Coordinate(randX,randY);
			if (!this.protoChromosome.contains(newHomeCoordinate)){
			this.protoChromosome.add(newHomeCoordinate);
			}else{			
//				System.out.println("duplicate x is "+newHomeCoordinate.x+"duplicate y is"+newHomeCoordinate.y);
			}
		}
	}
	
	public void generateChromosomes(){
		int randN = (int)(8+Math.random()*(12-8+1));//generate random int between 8 to 12
		this.N = randN;
		System.out.println("Random N is "+ N);
		for(int i=0; i<this.N; i++){
		Collections.shuffle(protoChromosome);
		ArrayList<Coordinate> tempChromosome = new ArrayList<Coordinate>();
		
		for(int j=0;j<this.protoChromosome.size();j++){
			int tempX = protoChromosome.get(j).x;
			int tempY = protoChromosome.get(j).y;
			tempChromosome.add(new Coordinate(tempX,tempY));
		}
		tempChromosome.add(new Coordinate(5,5));
		tempChromosome.add(0, new Coordinate(5,5));
		chromosomes.add(tempChromosome);
		}
	}
	
	public void mutation(int index){
		ArrayList<Coordinate> mutateChromosome = this.chromosomes.get(index);
		ArrayList<Coordinate> producedChromosome = new ArrayList<Coordinate>();
		
		//save the original chromosome
		for(int i=0; i<mutateChromosome.size(); i++){
			int tempAX = mutateChromosome.get(i).x;
			int tempAY = mutateChromosome.get(i).y;
			producedChromosome.add(new Coordinate(tempAX,tempAY));
		}		
//		this.chromosomes.add(originalChromosome);
		
		int len = mutateChromosome.size();
		int randInx1 = (int)(1+Math.random()*(len-2-1+1));
		int randInx2 = (int)(1+Math.random()*(len-2-1+1));
		while(randInx1 == randInx2){
			randInx2 = (int)(1+Math.random()*(len-2-1+1));
		}
//		System.out.println("before mutation");
//		this.printHomeCoordinates(producedChromosome);
		
		Coordinate temp1 =  new Coordinate(producedChromosome.get(randInx1).x,producedChromosome.get(randInx1).y);
		Coordinate temp2 =  new Coordinate(producedChromosome.get(randInx2).x,producedChromosome.get(randInx2).y);
		producedChromosome.set(randInx1, temp2);
		producedChromosome.set(randInx2, temp1);
		this.producedChromosomes.add(producedChromosome);
		
//		System.out.println("after mutation");
//		this.printHomeCoordinates(producedChromosome);
	}
	
//	public void crossover(ArrayList<Coordinate> chromosomeA,ArrayList<Coordinate> chromosomeB){
	public void crossover(int index1, int index2){
		ArrayList<Coordinate> chromosomeA = this.chromosomes.get(index1);
		ArrayList<Coordinate> chromosomeB = this.chromosomes.get(index2);
		ArrayList<Coordinate> producedChromosomeA = new ArrayList<Coordinate>();
		ArrayList<Coordinate> producedChromosomeB = new ArrayList<Coordinate>();
		
		for(int i=0; i<chromosomeA.size(); i++){
			int tempAX = chromosomeA.get(i).x;
			int tempAY = chromosomeA.get(i).y;
			int tempBX = chromosomeB.get(i).x;
			int tempBY = chromosomeB.get(i).y;
			producedChromosomeA.add(new Coordinate(tempAX,tempAY));
			producedChromosomeB.add(new Coordinate(tempBX,tempBY));
		}	
//		System.out.println("producedChromosomeA is !");
//		this.printHomeCoordinates(producedChromosomeA);
		int len = chromosomeA.size();
//		int breakPoint = (int)(1+Math.random()*(len-2-1+1));
		int breakPoint = 31;
//		System.out.println("the breakPoint is "+breakPoint);
		for(int i = breakPoint; i<len-1; i++){
			if(producedChromosomeA.get(i) != producedChromosomeB.get(i)){
			int idxA = producedChromosomeA.indexOf(producedChromosomeB.get(i));
			int idxB = producedChromosomeB.indexOf(producedChromosomeA.get(i));
//			System.out.println("index A is "+idxA);
//			System.out.println("index B is "+idxB);
			
			Coordinate temp1 =  new Coordinate(producedChromosomeA.get(idxA).x,producedChromosomeA.get(idxA).y);
			Coordinate temp2 =  new Coordinate(producedChromosomeA.get(i).x,producedChromosomeA.get(i).y);
			producedChromosomeA.set(i, temp1);
			producedChromosomeA.set(idxA, temp2);
			
			Coordinate temp3 =  new Coordinate(producedChromosomeB.get(idxB).x,producedChromosomeB.get(idxB).y);
			Coordinate temp4 =  new Coordinate(producedChromosomeB.get(i).x,producedChromosomeB.get(i).y);
			producedChromosomeB.set(i, temp3);
			producedChromosomeB.set(idxB, temp4);
			}
		}
		
		if(this.producedChromosomes.size()!=this.N-1){
		this.producedChromosomes.add(producedChromosomeA);
		this.producedChromosomes.add(producedChromosomeB);
		}
		else{
			this.producedChromosomes.add(producedChromosomeA);
		}
	}
	
	public  double fitnessFunction(int index){
		double sum = 0;
		int len = this.chromosomes.get(index).size();
		ArrayList<Coordinate> oneChromosome;
		oneChromosome = chromosomes.get(index);
		for(int i=0;i<len-1;i++){
			sum += Math.sqrt((oneChromosome.get(i).x-oneChromosome.get(i+1).x)*(oneChromosome.get(i).x-oneChromosome.get(i+1).x)+
				   (oneChromosome.get(i).y-oneChromosome.get(i+1).y)*(oneChromosome.get(i).y-oneChromosome.get(i+1).y));
		}
		return sum;
	}
	
	public int rouletteWheelSelection(){
		double sumOfAll = 0;
		double sumOfAllReciparocal = 0;
		double line = 0;
//		System.out.println("in rouletteWheelSelection, chromosomes size is "+this.chromosomes.size());
		for (int i=0;i<this.chromosomes.size();i++){
			sumOfAll += this.fitnessFunction(i);
		}
		
		for (int k=0;k<this.chromosomes.size();k++){
			double percentage = this.fitnessFunction(k)/sumOfAll;
			double reciprocal = 1.0/percentage;
			sumOfAllReciparocal += reciprocal;
//			indexPercentagePair.put(k, percentage);
		}
		
		for (int k=0;k<this.chromosomes.size();k++){
			double percentage = this.fitnessFunction(k)/sumOfAll;
			double reciprocal = 1.0/percentage;
			double realPercentage = reciprocal/sumOfAllReciparocal;
			indexPercentagePair.put(k, realPercentage);
		}
		
		for (int j=0;j<this.chromosomes.size();j++){
			line += indexPercentagePair.get(j);
			this.delimiter.add(line);
//			System.out.println("chromosomes.size is "+this.chromosomes.size()+"+Line is "+line);
		}
		
//		System.out.println("line is "+line);
		
		double randDouble = Math.random();
		for(n=0;n<this.delimiter.size()-1;n++){
			if(randDouble>=delimiter.get(n) && randDouble<=delimiter.get(n+1)){
				return n;
			}
		}
		return 1999;
	}
	
	public int uniformSelection(){
		int randN = (int)(0+Math.random()*((N-1)-0+1));//generate random int between 0 to N-1
//		System.out.println("uniform N is "+randN);
		return randN;
	}
	
	public void geneticOperator(){
//		System.out.println("N is "+this.N+ "in "+this.nGeneration+" th generation, the chromosomes size is "+this.chromosomes.size());
		while(this.producedChromosomes.size()<N){
			double randP = Math.random();
			if(randP<=this.pCrossover){//crossover
				int selectedChromIdxA = uniformSelection();
				int selectedChromIdxB = uniformSelection();
				while (selectedChromIdxA == selectedChromIdxB ){
					selectedChromIdxB = uniformSelection();
				}
				this.crossover(selectedChromIdxA, selectedChromIdxB);
			}
			else{
				int selectedChromIdxA = uniformSelection();
				this.mutation(selectedChromIdxA);
			}
		}
	}
	
	public void getNextGeneration(){//chose N chromosomes out of 2N according to the fitness function
		this.selectedChromosomes.clear();
//		System.out.println("N is"+N+", The size after genetic operation is "+ this.chromosomes.size());
//		System.out.println("N is"+N+", The size of producedChormosome after genetic operation is "+ this.producedChromosomes.size());
		if(this.chromosomes.size() != this.producedChromosomes.size() ){
			System.out.println("OMG! Different size!");
		}
		this.chromosomes.addAll(this.producedChromosomes);
//		for(int i=0;i<this.chromosomes.size();i++){
//			idxFitnessPair.put(i, this.fitnessFunction(i));
//		}
		System.out.println("2N CHROMOSOMES ARE:");
		this.printAllChromosome(this.chromosomes);
		System.out.println("2N CHROMOSOMES END:");
		double minDis = this.fitnessFunction(0);
		int minIdx = 0;
		for(int i=0;i<this.chromosomes.size();i++){
			if(minDis>this.fitnessFunction(i)){
				minDis = this.fitnessFunction(i);
				minIdx = i;
			}
		}
//		System.out.println("the 2N chromosomes are");
//		this.printAllChromosome(this.chromosomes);
//		this.printFitness();
//		System.out.println("minIdx is "+minIdx);
		
		int count = 0;
		while(count<N){
			int selectedIdx = 0;
			if (count==0){
				selectedIdx = minIdx;
			}else{
			    selectedIdx = this.rouletteWheelSelection();
			}
//			System.out.println("jdSelectedIdx is "+selectedIdx);
			ArrayList<Coordinate> oneSelectedChromosome = new ArrayList<Coordinate>();
			for(int i=0;i<this.chromosomes.get(selectedIdx).size();i++){
				int tempX = this.chromosomes.get(selectedIdx).get(i).x;
				int tempY = this.chromosomes.get(selectedIdx).get(i).y;
				oneSelectedChromosome.add(new Coordinate(tempX,tempY));		
			}
			this.selectedChromosomes.add(oneSelectedChromosome);
			count++;
		}
		
		this.chromosomes.clear();
		this.chromosomes.addAll(this.selectedChromosomes);
		System.out.println("the jd selectedChromosomes are: ");
		this.printAllChromosome(this.chromosomes);

//		System.out.println("the Chromosomes are: ");
//		this.printAllChromosome(this.chromosomes);
//		System.out.println("N is "+this.N+" the size of chromosomes is "+this.chromosomes.size());
	}
	
	public void printFitness(){
		double sum = 0;
		double ave = 0;
		System.out.println("IN PRINT FITNESS, CHROMOSOMES ARE");
		this.printAllChromosome(this.chromosomes);
		double minDistance = this.fitnessFunction(0);
		for(int i=0;i<this.chromosomes.size();i++){
			if(minDistance>this.fitnessFunction(i)){
				minDistance = this.fitnessFunction(i);
			}
			System.out.println("The fitness is "+this.fitnessFunction(i));
			sum += this.fitnessFunction(i);
		}
		ave = sum/N;
		
		System.out.println("ave is "+ave);
		System.out.println("minDistance is "+ minDistance);
		this.bestFitnessList.add(minDistance);
		this.aveFitnessList.add(ave);
	}
	
	
	
	public static void main(String[] args){
		Chromosome cho = new Chromosome();
//		System.out.println("before revolution, the fitness are");
//		cho.printFitness();
		while(cho.nGeneration<400){
			System.out.println("before genetic operation,the chromosomes are ");
			cho.printAllChromosome(cho.chromosomes);
			cho.geneticOperator();
		
//			System.out.println("N is "+cho.N+"and after geneticOperator,the size is "+cho.chromosomes.size());
			cho.getNextGeneration();
			System.out.println("after genetic operation,the chromosomes are ");
			cho.printAllChromosome(cho.producedChromosomes);
			cho.printFitness();
			cho.nGeneration++;
//			System.out.println("The generation is"+cho.nGeneration);
		}
//		System.out.println("after revolution, the fitness are");
//		cho.printFitness();
//		System.out.print("[");
//		for(int i=0;i<cho.bestFitnessList.size();i++){
//			System.out.print(cho.bestFitnessList.get(i));
//			System.out.print(" ");
//			
//		}
//		System.out.println("]");
//		
//		System.out.println("average is");
//		System.out.print("[");
//		for(int i=0;i<cho.aveFitnessList.size();i++){
//			
//			System.out.print(cho.aveFitnessList.get(i));
//			System.out.print(" ");
//		
//		}
//		System.out.println("]");
		
	}
	
	
	
}
