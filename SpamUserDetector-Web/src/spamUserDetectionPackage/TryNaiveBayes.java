package spamUserDetectionPackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Scanner;

import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;


public class TryNaiveBayes {

	 
	public void NaiveBayesFirstLevelLearning() throws Exception {
		String CSVFilepathName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileLabled.csv";
		String ARFFFilepathName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileLabledTrain.arff";
		
		// Convert Csv to Arff
		CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(CSVFilepathName));
	    Instances data = loader.getDataSet();

	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(ARFFFilepathName));
	    saver.writeBatch();
	    //Convert Csv to Arff End
	    
	    ArffLoader loader1 = new ArffLoader();
	    loader1.setFile(new File(ARFFFilepathName));
	    Instances structure = loader1.getStructure();
	    structure.setClassIndex(structure.numAttributes() - 1);

	    // train NaiveBayes
	    NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
	    nb.buildClassifier(structure);
	    Instance current;
	    while ((current = loader1.getNextInstance(structure)) != null)
	      nb.updateClassifier(current);

	    // output generated model
	    System.out.println(nb);
	}
	public void TryNaiveBayesLearning(int FileNumCount, String VocabFilePath) throws Exception {
		
		// Find the length of the Vocab File so as to make proper new CSV file for learning
        File file2 = new File(VocabFilePath);
        Scanner sc2 = new Scanner(new FileInputStream(file2));
        int count=0;
        while(sc2.hasNext()){
        	 sc2.next();
            count++;
        }
		//System.out.println(count);
		
	   // Working on Arff vs Arff Starts
	 	/*String ARFFFilepathTrain = "G:\\DataMiningProject\\TrainingDataSet\\ProcessedTweets\\MatrixFileLabledTrain.arff";
	 	String ARFFFilepathTest = "G:\\DataMiningProject\\TrainingDataSet\\ProcessedTweets\\MatrixFile2.arff";
	
		ArffLoader loader1 = new ArffLoader();
	    loader1.setFile(new File(ARFFFilepathTrain));
	    Instances train = loader1.getStructure();
	    train.setClassIndex(train.numAttributes() - 1);

	    ArffLoader loader = new ArffLoader();
	    loader.setFile(new File(ARFFFilepathTest));
	    Instances test = loader.getStructure();
	    test.setClassIndex(test.numAttributes() - 1);

	    // train classifier
	    Classifier cls = new NaiveBayes();
	    cls.buildClassifier(train);
	   
	    // evaluate classifier and print some statistics
	    Evaluation eval = new Evaluation(train);
	    eval.evaluateModel(cls, test);
	  
	    System.out.println("Hello World : " + eval.toClassDetailsString());
	    System.out.println("Hello World : " + eval.correct());*/
	   // Working on Arff vs Arff Ends
		
		String ARFFFilepathTrain = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileLabledTrain.arff";
	 	
		ArffLoader loader1 = new ArffLoader();
	    loader1.setFile(new File(ARFFFilepathTrain));
	    Instances train = loader1.getDataSet();
	    train.setClassIndex(train.numAttributes() - 1);
	    
	    //NaiveBayes nb=  new NaiveBayes();
        //nb.buildClassifier(train);
	    NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
	    nb.buildClassifier(train);
        for(int i=1; i< FileNumCount; i++)
        {
        	String NewLabledFile = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileNewLabled"+i+".csv";
        	BufferedWriter bw = new BufferedWriter(new FileWriter(NewLabledFile, true));
    		bw.write("ID" + ",");
    	    for(int j=0; j<count; j++ )
    	    {
    	    	bw.write("W" + j + ",");
    	    }
    	    bw.write("Class");
    	    bw.write("\r\n");
    	    bw.close();
    	    
        	String ARFFFilepathTest = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+i+".arff";
        	ArffLoader loader = new ArffLoader();
     	    loader.setFile(new File(ARFFFilepathTest));
     	    Instances test = loader.getDataSet();
     	    test.setClassIndex(test.numAttributes() - 1);
     	  
             
             int NumRecords = test.numInstances();
            //System.out.println("NumRecords :: " + NumRecords);
             int Count = 0;
             for(int k=1; k < NumRecords ; k++)
             {
            	// System.out.println("Meeli : " + test.instance(k));
             	double[] classDistributions= nb.distributionForInstance(test.instance(k));
          	   //System.out.println(test.instance(k));
               //System.out.println("["+classDistributions[0]+","+classDistributions[1]+"]");
            
             	String ClassLable = "NotSpam";
          	    if(classDistributions[1] > classDistributions[0])
          	    {
          	    	Count++;
          	    	ClassLable = "Spam";
          	    }
          	    if(k == NumRecords-1)
          	    {
	          	    if(Count==0){ClassLable = "Spam";  Count++; }
	          	    if(Count==NumRecords-1){ClassLable = "NotSpam";}	
          	    }
          	    BufferedWriter bwn = new BufferedWriter(new FileWriter(NewLabledFile, true));
          	    bwn.write(test.instance(k).toString());
          	    bwn.write("," + ClassLable + "\r\n");
          	    bwn.close();
             }
             //System.out.println("Value of " + i + " :::::::::: "+ Count);
           
           //  String ARFFFilepathTestNewLabled = "G:\\DataMiningProject\\TrainingDataSet\\ProcessedTweets\\MatrixFiles\\MatrixFileNewLabled"+i+".arff";
           
             //Convert Csv to Arff Start
            CSVLoader loader2 = new CSVLoader();
     	    loader2.setSource(new File(NewLabledFile));
     	    Instances data = loader2.getDataSet();

     	    ArffSaver saver = new ArffSaver();
     	    saver.setInstances(data);
     	    saver.setFile(new File(ARFFFilepathTrain));
     	    saver.writeBatch();
     	    //Convert Csv to Arff End
     	    
     	    ArffLoader loader3 = new ArffLoader();
     	    loader3.setFile(new File(ARFFFilepathTrain));
     	    Instances Teststructure = loader3.getDataSet();
     	    Teststructure.setClassIndex(Teststructure.numAttributes() - 1);
     	    
     	   /*  nb = new NaiveBayesUpdateable();
     	    nb.buildClassifier(Teststructure);
     	   Instance current;
     	   while ((current = loader1.getNextInstance(structure)) != null)
   	      nb.updateClassifier(current);
*/			
     	  //System.out.println("NumRecords 2nd : " + NumRecords);  
     	  for(int k=0; k < NumRecords-1 ; k++)
            {
     	  	  nb.updateClassifier(Teststructure.instance(k));
            }
     	    //System.out.println(nb.getCapabilities());
     	   
        }
        for(int i=0;i<FileNumCount;i++)
		{
        	File csv_Delete = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileNewLabled"+i+".csv");
			csv_Delete.delete();
        	File csv_Delete1 = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+i+".arff");
			csv_Delete1.delete();
		}
      //  return ARFFFilepathTrain;
        
       	}
}
