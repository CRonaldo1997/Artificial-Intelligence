package neuralNetwork;

import java.util.ArrayList;

public class NeuralNet {
	int count;
	public int numOfHiddenNeurals;
	public int numOfInputs;
	public int numOfOutputs;
	public double a;
	public double squaredError;
	Double[] hiddenLayerOutputs;
	Double[] outputLayerOutputs;
	Double[][] inputLayerWeights;
	Double[][] hiddenLayerWeights;
	Double[][] inputLayerWeightsCorrection;
	Double[][] hiddenLayerWeightsCorrection;
	Double[] hiddenLayerThresholds;
	Double[] outputLayerThresholds;
	Double[][] inputValues = {{0.0,0.0},
			                  {0.0,1.0},
			                  {1.0,0.0},
			                  {1.0,1.0}};
	Double[][] desiredOutput = {{0.0,0.0},
			                    {1.0,0.0},
	                            {1.0,0.0},
	                            {0.0,1.0}};
	Double[][] totalErrorArray;
	Double[] errors;	
	Double[] outputErrorGradient;
	Double[] hiddenErrorGradient;
	int beforeTrainCorrectNum;
	int correctNum;
	ArrayList<Double> errorList;
//	Double[][] testInputData;
//	Double[][] desiredTestOutputData;
	
	public NeuralNet(){
		this.beforeTrainCorrectNum = 0;
		this.correctNum = 0;
		this.count = 0;
		this.numOfHiddenNeurals = 10;
		this.numOfInputs = 2;
		this.numOfOutputs = 2;
		this.a = 5;
		this.squaredError = 100;
		hiddenLayerOutputs = new Double[this.numOfHiddenNeurals];
		outputLayerOutputs = new Double[this.numOfOutputs];
		inputLayerWeights = new Double[this.numOfInputs][this.numOfHiddenNeurals];
		inputLayerWeightsCorrection = new Double[this.numOfInputs][this.numOfHiddenNeurals];
		hiddenLayerWeights = new Double[this.numOfHiddenNeurals][this.numOfOutputs];
		hiddenLayerWeightsCorrection = new Double[this.numOfHiddenNeurals][this.numOfOutputs];
		hiddenLayerThresholds = new Double[this.numOfHiddenNeurals];
		outputLayerThresholds = new Double[this.numOfOutputs];
		totalErrorArray = new Double[this.inputValues.length][this.numOfOutputs];
		errors = new Double[this.numOfOutputs];
		outputErrorGradient = new Double[this.numOfOutputs];
		hiddenErrorGradient = new Double[this.numOfHiddenNeurals];
//		testInputData = new Double[100][this.numOfInputs];
//		desiredTestOutputData = new Double[100][this.numOfOutputs];
		errorList = new ArrayList<>();
		this.getInputValueWithNoise();
		this.getRandomWeight();
		this.getRandomThreshould();
	}
	
	
//	public void getTestDataSet(){
//		for(int i=0; i<testInputData.length; i++){
//			for(int j=0; j<testInputData[i].length; j++){
//				if(i%4 == 0){
//					if(j%this.numOfInputs==0){
//						testInputData[i][j] = 0.4*Math.random()-0.2;
//					}
//					else{
//						testInputData[i][j] = 0.4*Math.random()-0.2;
//					}
//				}
//				else if(i%4 == 1){
//					if(j%this.numOfInputs==0){
//						testInputData[i][j] = 0.4*Math.random()-0.2;
//					}
//					else{
//						testInputData[i][j] = 1.0 + 0.4*Math.random()-0.2;
//					}
//				}
//				else if(i%4 == 2){
//					if(j%this.numOfInputs==0){
//						testInputData[i][j] = 1.0 + 0.4*Math.random()-0.2;
//					}
//					else{
//						testInputData[i][j] = 0.4*Math.random()-0.2;
//					}
//				}
//				else{
//					if(j%this.numOfInputs==0){
//						testInputData[i][j] = 1.0 + 0.4*Math.random()-0.2;
//					}
//					else{
//						testInputData[i][j] = 1.0 + 0.4*Math.random()-0.2;
//					}
//				}
//			}
//		}
//	}
	
	
	public void getRandomWeight(){//checked
		for(int i=0; i<this.inputLayerWeights.length;i++){
			for(int j=0; j<this.inputLayerWeights[i].length;j++){
				double randomDouble1 = (4.8/this.numOfInputs) * Math.random() - 2.4/this.numOfInputs; //generate random number between -1.2 to 1.2
				inputLayerWeights[i][j] = randomDouble1;	
			}
		}
		
		for(int i=0; i<this.hiddenLayerWeights.length;i++){
			for(int j=0; j<this.hiddenLayerWeights[i].length;j++){
				double randomDouble2 = 4.8/this.numOfHiddenNeurals * Math.random() - 2.4/this.numOfHiddenNeurals;
				hiddenLayerWeights[i][j] = randomDouble2;
			}
		}
	}
	
	
	
	public void getRandomThreshould(){//checked
		for(int i=0;i<this.numOfHiddenNeurals;i++){
			double randomForThreshould1 = 4.8/this.numOfInputs*Math.random() - 2.4/this.numOfInputs;
			hiddenLayerThresholds[i] = randomForThreshould1;
		}
		
		for(int i=0;i<this.numOfOutputs;i++){
			double randomForThreshould2 = 4.8/this.numOfHiddenNeurals * Math.random() - 2.4/this.numOfHiddenNeurals;
			outputLayerThresholds[i] = randomForThreshould2;
		}
	}
	
	
	
	public void getInputValueWithNoise(){//checked
		for(int i=0;i<this.inputValues.length;i++){
			for(int j=0; j<this.inputValues[i].length;j++){
				double randomDouble3 = 0.4 * Math.random() - 0.2;
				inputValues[i][j] += randomDouble3;
			}
		}
	}
	
	
	
	double sigmoid(double x) {
        return 1.0 / (1.0 +  (Math.exp(-x)));
    }
	
	
	
	public void getHiddenLayerOutput(int k){ //k-th row of inputOutput matrix, checked
		for(int i=0; i< this.numOfHiddenNeurals; i++){
			    double tempH = inputValues[k][0]*inputLayerWeights[0][i] + inputValues[k][1]*inputLayerWeights[1][i] - hiddenLayerThresholds[i];
			    double y = sigmoid(tempH);
			    hiddenLayerOutputs[i]=y;  
		}
	}
	
	
	
	public void getOutputLayerOutput(){ //checked
		for(int i=0; i< this.numOfOutputs; i++){
			double tempY = 0;
			for(int j=0; j< this.numOfHiddenNeurals; j++){
				tempY +=  hiddenLayerOutputs[j]*hiddenLayerWeights[j][i];
			}
			tempY -= outputLayerThresholds[i];
			double y = sigmoid(tempY);
			outputLayerOutputs[i]=y;
		}
	}
	
	
	
	public void getErrorAndErrorGradient(int k){//k-th row of inputOutput matrix, checked
		for(int i=0; i<this.numOfOutputs;i++){
			this.errors[i] = desiredOutput[k][i]-outputLayerOutputs[i];
			this.totalErrorArray[k][i] = this.errors[i];
			this.outputErrorGradient[i] = this.outputLayerOutputs[i]*(1-this.outputLayerOutputs[i])*this.errors[i];
		}
	}
	
	
	
	public  void getHiddenWeigthCorrection(){//checked
		for(int i=0; i<this.numOfHiddenNeurals;i++){
			for(int j=0; j<this.numOfOutputs;j++){
				hiddenLayerWeightsCorrection[i][j]=this.a*this.hiddenLayerOutputs[i]*this.outputErrorGradient[j];
			}
		}
	}
	
	
	
	public void getErrorGradientForHiddenLayer(){//checked
		for(int i=0; i<this.numOfHiddenNeurals;i++){
			double sum = outputErrorGradient[0]*hiddenLayerWeights[i][0]+outputErrorGradient[1]*hiddenLayerWeights[i][1];
				hiddenErrorGradient[i] = hiddenLayerOutputs[i] * (1-hiddenLayerOutputs[i]) * sum;
			}
		}
	
	
	
	public void getInputWeightCorrection(int k){ //k-th row of inputOutput matrix //checked
		for(int i=0; i < this.numOfInputs; i++){
			for(int j=0; j < this.numOfHiddenNeurals; j++){
				inputLayerWeightsCorrection[i][j] = this.a * this.inputValues[k][i] * hiddenErrorGradient[j];
			}
		}
	}
	
	public void updateWeightsAndThresholds(){ //checked
		for(int i=0;i<this.numOfInputs;i++){
			for(int j=0;j<this.numOfHiddenNeurals;j++){
				inputLayerWeights[i][j] += inputLayerWeightsCorrection[i][j];
			}
		}
		
		for(int i=0;i<this.numOfHiddenNeurals;i++){
			for(int j=0;j<this.numOfOutputs;j++){
				this.hiddenLayerWeights[i][j] += this.hiddenLayerWeightsCorrection[i][j];
			}
		}
		
		for(int i=0;i<this.numOfHiddenNeurals;i++){
			hiddenLayerThresholds[i] += this.a*(-1)*this.hiddenErrorGradient[i];
		}
		
		for(int i=0;i<this.numOfInputs;i++){
			outputLayerThresholds[i] += this.a*(-1)*this.outputErrorGradient[i];
		}
	}
	
	public double getTotalError(){//checked
		double totalError = 0;
		for(int i=0; i<this.totalErrorArray.length; i++){
			for(int j=0; j<this.totalErrorArray[i].length; j++){
				totalError += totalErrorArray[i][j] * totalErrorArray[i][j];
			}
		}
		this.errorList.add(totalError);
		return totalError;
	}
	
	public void printErrorList(){
		System.out.print("[");
		for(int i=0; i<this.errorList.size();i++){
			System.out.print(this.errorList.get(i)+" ");
		}
		System.out.println("]");
	}
	
	public static void main(String[] args){
		
		NeuralNet neuralNet = new NeuralNet();
		
		//before training the system
//		for(int m=0; m<25; m++){
//			for(int k=0; k<neuralNet.inputValues.length;k++){
//				neuralNet.getHiddenLayerOutput(k);
//				neuralNet.getOutputLayerOutput();
//				
//				for(int j=0; j<neuralNet.numOfOutputs; j++){
//					System.out.println("in "+(4*m+k)+"-th test, the output is"+neuralNet.outputLayerOutputs[j]);
//				}
//				
//				if((4*m+k)%4 == 0){
//					if(neuralNet.outputLayerOutputs[0]>=-0.2 && neuralNet.outputLayerOutputs[0]<=0.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//				}
//				else if((4*m+k)%4 == 1){
//					if(neuralNet.outputLayerOutputs[0]>=0.8 && neuralNet.outputLayerOutputs[0]<=1.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//				}
//				else if((4*m+k)%4 == 2){
//					if(neuralNet.outputLayerOutputs[0]>=0.8 && neuralNet.outputLayerOutputs[0]<=1.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//				}
//				else{
//					if(neuralNet.outputLayerOutputs[0]>=-0.2 && neuralNet.outputLayerOutputs[0]<=0.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//					if(neuralNet.outputLayerOutputs[1]>=0.8 && neuralNet.outputLayerOutputs[1]<=1.2){
//						neuralNet.beforeTrainCorrectNum++;
//					}
//				}
//			}
//		}
		
		//train the system
		while(neuralNet.squaredError > 0.001){
			neuralNet.count++;
			for(int k=0; k<neuralNet.inputValues.length;k++){
				neuralNet.getHiddenLayerOutput(k);//get hiddenLayerOutput
				neuralNet.getOutputLayerOutput(); //get outputLayerOutput
				neuralNet.getErrorAndErrorGradient(k);//get errorAndErrorGradient for output layer
				neuralNet.getHiddenWeigthCorrection();
				neuralNet.getErrorGradientForHiddenLayer();
				neuralNet.getInputWeightCorrection(k);
				neuralNet.updateWeightsAndThresholds();
			}	
			neuralNet.squaredError = neuralNet.getTotalError();
			System.out.println("In "+neuralNet.count+"-th"+" iteration, sum of squared error is "+neuralNet.squaredError);
		}
		System.out.println("totally epoch is "+neuralNet.count);
		
		//test system using 100 data
		for(int m=0; m<25; m++){
			for(int k=0; k<neuralNet.inputValues.length;k++){
				neuralNet.getHiddenLayerOutput(k);
				neuralNet.getOutputLayerOutput();
				
				for(int j=0; j<neuralNet.numOfOutputs; j++){
					System.out.println("in "+(4*m+k)+"-th test, the output is"+neuralNet.outputLayerOutputs[j]);
				}
				
				if((4*m+k)%4 == 0){
					if(neuralNet.outputLayerOutputs[0]>=-0.2 && neuralNet.outputLayerOutputs[0]<=0.2){
						neuralNet.correctNum++;
					}
					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
						neuralNet.correctNum++;
					}
				}
				else if((4*m+k)%4 == 1){
					if(neuralNet.outputLayerOutputs[0]>=0.8 && neuralNet.outputLayerOutputs[0]<=1.2){
						neuralNet.correctNum++;
					}
					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
						neuralNet.correctNum++;
					}
				}
				else if((4*m+k)%4 == 2){
					if(neuralNet.outputLayerOutputs[0]>=0.8 && neuralNet.outputLayerOutputs[0]<=1.2){
						neuralNet.correctNum++;
					}
					if(neuralNet.outputLayerOutputs[1]>=-0.2 && neuralNet.outputLayerOutputs[1]<=0.2){
						neuralNet.correctNum++;
					}
				}
				else{
					if(neuralNet.outputLayerOutputs[0]>=-0.2 && neuralNet.outputLayerOutputs[0]<=0.2){
						neuralNet.correctNum++;
					}
					if(neuralNet.outputLayerOutputs[1]>=0.8 && neuralNet.outputLayerOutputs[1]<=1.2){
						neuralNet.correctNum++;
					}
				}
			}
		}
//		System.out.println("before training the system, correct number is "+neuralNet.beforeTrainCorrectNum+" correction rate is "+neuralNet.beforeTrainCorrectNum/200*100+"%");
		System.out.println("after training the system, correct number is "+neuralNet.correctNum+" correction rate is "+neuralNet.correctNum/200*100+"%");
//		neuralNet.printErrorList();
//		System.out.println(neuralNet.errorList.size());
		
		//print result
		System.out.println("the weight for output layer is: ");
		for(int i=0;i<neuralNet.hiddenLayerWeights.length;i++){
			for(int j=0;j<neuralNet.hiddenLayerWeights[i].length;j++){
				System.out.println("the W"+i+j+" is "+neuralNet.hiddenLayerWeights[i][j]);
			}
		}
		System.out.println(" ");
		System.out.println("the weight for input layer is: ");
		for(int i=0;i<neuralNet.inputLayerWeights.length;i++){
			for(int j=0;j<neuralNet.inputLayerWeights[i].length;j++){
				System.out.println("the W"+i+j+" is "+neuralNet.inputLayerWeights[i][j]);
			}
		}
		
		System.out.println(" ");
		System.out.println("the threshoulds for hidden layer Neurals are: ");
		for(int i=0; i<neuralNet.hiddenLayerThresholds.length;i++){
			System.out.println("threshould-"+i+"is "+neuralNet.hiddenLayerThresholds[i]);
		}
		
		System.out.println(" ");
		System.out.println("the threshoulds for output layer Neurals are: ");
		for(int i=0; i<neuralNet.outputLayerThresholds.length;i++){
			System.out.println("threshould-"+i+"is "+neuralNet.outputLayerThresholds[i]);
		}
	}
}
