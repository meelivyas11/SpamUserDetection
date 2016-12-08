package spamUserDetectionPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

//import com.sun.org.apache.regexp.internal.recompile;

import weka.classifiers.Classifier;
import weka.classifiers.Evaluation;
import weka.classifiers.bayes.NaiveBayes;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;


public class TreatTestTweets {
	
	public ArrayList TreatTweetMethod(String TweetTestFileName, String VocabFilePath,String ARFFFilepathTrain, int Declare_SpamUser_Count) throws Exception {
		
		File csv_Delete = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTest_Labled.csv");
		if(csv_Delete.exists())
			csv_Delete.delete();
		
		File csv_Delete1 = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.csv");
		if(csv_Delete1.exists())
			csv_Delete1.delete();
		
		File csv_Delete2 = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.arff");
		if(csv_Delete2.exists())
			csv_Delete2.delete();
		
		//Seperate the Tweets and User
		String fileNameTweets =	CleanTweetMethod(TweetTestFileName, VocabFilePath);
      		
		//Create a Matrix - Csv File
		MatrixTweetMethod(fileNameTweets, VocabFilePath);
		
		// Convert csv to arff
		Convert_CSV_to_Arff_Test();
		
		ArrayList FinalSpamUserList =  NaiveBayesTest(VocabFilePath, ARFFFilepathTrain,Declare_SpamUser_Count);
		//Predict Test Data
		
		String NewLabledFile = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTest_Labled.csv";
		CSVLoader loader = new CSVLoader();
	    loader.setSource(new File(NewLabledFile));
	    Instances data = loader.getDataSet();
	    
	    String NewLabledFileArff = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTest_Labled.arff";
	    ArffSaver saver = new ArffSaver();
	    saver.setInstances(data);
	    saver.setFile(new File(NewLabledFileArff));
	    saver.writeBatch();
	    
	    /*ArffLoader loader1 = new ArffLoader();
	    loader1.setFile(new File(NewLabledFileArff));
	    Instances test = loader1.getStructure();
	    test.setClassIndex(test.numAttributes() - 1);

	    // train classifier
	    Classifier cls = new NaiveBayes();
	    cls.buildClassifier(test);
	   
	    // evaluate classifier and print some statistics
	    Evaluation eval = new Evaluation(test);
	    eval.evaluateModel(cls, test);
	  
	    System.out.println("Summary : " + eval.toSummaryString());*/
		
	    return FinalSpamUserList;
	}
	public ArrayList NaiveBayesTest(String VocabFilePath,String ARFFFilepathTrain, int Declare_SpamUser_Count) throws Exception 
	{
		System.out.println("in NaiveBayesTest");
		// Find the length of the Vocab File so as to make proper new CSV file for learning
		ArrayList FinalSpamList = new ArrayList();
		ArrayList SpamUserNames = new ArrayList();
		File file2 = new File(VocabFilePath);
        Scanner sc2 = new Scanner(new FileInputStream(file2));
        int count=0;
        while(sc2.hasNext()){
        	 sc2.next();
            count++;
        }
        
        ArffLoader loader1 = new ArffLoader();
	    loader1.setFile(new File(ARFFFilepathTrain));
	    Instances train = loader1.getDataSet();
	    train.setClassIndex(train.numAttributes() - 1);
	    
	    //NaiveBayes nb=  new NaiveBayes();
        //nb.buildClassifier(train);
	    NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
	    nb.buildClassifier(train);
	    //System.out.println("TestTweetFileCount : " + TestTweetFileCount);
	    
        	String NewLabledFile = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTest_Labled.csv";
        	BufferedWriter bw = new BufferedWriter(new FileWriter(NewLabledFile, true));
    		bw.write("ID" + ",");
    	    for(int j=0; j<count; j++ )
    	    {
    	    	bw.write("W" + j + ",");
    	    }
    	    bw.write("Class");
    	    bw.write("\r\n");
    	    bw.close();
    	    
        	String ARFFFilepathTest = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.arff";
        	ArffLoader loader = new ArffLoader();
     	    loader.setFile(new File(ARFFFilepathTest));
     	    Instances test = loader.getDataSet();
     	    test.setClassIndex(test.numAttributes() - 1);
     	    	
             ArrayList<Integer> SpamUserId = new ArrayList<Integer>();
             int NumRecords = test.numInstances();
             System.out.println("NumRecords : " + NumRecords);
             int Count = 0;
             for(int k=0; k< NumRecords ; k++)
             {
            	 double[]  classDistributions = new double[2];
            	 //System.out.println(nb.distributionForInstance(test.instance(k)));
             	classDistributions= nb.distributionForInstance(test.instance(k));
          	    //System.out.println(test.instance(k));
                //System.out.println("["+classDistributions[0]+","+classDistributions[1]+"]");
            
             	String ClassLable = "NotSpam";
          	    if(classDistributions[1] > classDistributions[0])
          	    {
          	    	Count++;
          	    	ClassLable = "Spam";
          	    	String Data = test.instance(k).toString();
              	    StringTokenizer st = new StringTokenizer(Data, ","); 
              	    String IdNum = st.nextToken();
              	    SpamUserId.add(Integer.parseInt(IdNum));
          	    }
          	   if(k == NumRecords-1)
        	    {
	          	    if(Count==0) {ClassLable = "Spam"; Count++; }
	          	    else if(Count==NumRecords-1)ClassLable = "NotSpam";	
        	    }
          	    BufferedWriter bwn = new BufferedWriter(new FileWriter(NewLabledFile, true));
          	    bwn.write(test.instance(k).toString());
          	    bwn.write("," + ClassLable + "\r\n");
          	    bwn.close();
          	    
            
	            FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/TweetsTestUserList.tsv");
	 			DataInputStream in = new DataInputStream(fstream);
	 	        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
	 	        String strLine;
	            for(int m=0 ; m<SpamUserId.size();m++)
	             {
	            	 while ((strLine = br.readLine()) != null)  
	      	        {
	            		  StringTokenizer stringTokenizer = new StringTokenizer(strLine, "||");
	            		  String FileId = stringTokenizer.nextToken();
	            		  //System.out.println(SpamUserId.get(m) + " ::::::::::: " + Integer.parseInt(FileId));
	            		 if(SpamUserId.get(m) == Integer.parseInt(FileId))
	            		 {
	            			 SpamUserNames.add(stringTokenizer.nextToken());
	            			 break;
	            		 }
	      	        }
	             }
        }
	    
	    for(int j=0; j<SpamUserNames.size(); j++)
	    {   
	    	int CountTimesName = 1;
	    	for(int k = j+1; k < SpamUserNames.size()-1 ; k++)
	    	{
	    		if(SpamUserNames.get(j).toString().equalsIgnoreCase(SpamUserNames.get(k).toString()))
	    		{
	    			CountTimesName++;
	    			SpamUserNames.remove(k);
	    		}
	    	}
	    	if(CountTimesName >= Declare_SpamUser_Count ){
	    		if(!FinalSpamList.contains(SpamUserNames.get(j)))
	    			FinalSpamList.add(SpamUserNames.get(j));
	    		//System.out.println(SpamUserNames.get(j));
	    	}
	    		
	    }
	    return FinalSpamList;
	}
	public String CleanTweetMethod(String TweetTestFileName, String VocabFilePath) throws Exception 
	{
		System.out.println("in CleanTweetMethod");
		String fileNameTweets = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/TweetsTest.tsv";
	    File dynFile = new File(fileNameTweets);
	    Writer writer = new BufferedWriter(new FileWriter(dynFile));
		
	    String fileNameUser = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/TweetsTestUserList.tsv";
	    File dynFileUser = new File(fileNameUser);
	    Writer writerUser = new BufferedWriter(new FileWriter(dynFileUser));
		
	    
		FileInputStream fstream = new FileInputStream(TweetTestFileName);
		DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String strLine;
        
        int Id = 0;
        while ((strLine = br.readLine()) != null)  
        {
        Id++;
        StringTokenizer stringTokenizer = new StringTokenizer(strLine, "\t");
        int temp = 0;
        while(stringTokenizer.hasMoreTokens())
        {
        	temp++;
        	stringTokenizer.nextToken();
        	if (temp==1 && stringTokenizer.hasMoreElements())
        	{
        		writer.write(stringTokenizer.nextToken()); 
        		writer.write("\n");
        	
        		writerUser.write(Id + "||");
        		writerUser.write(stringTokenizer.nextToken());
        		writerUser.write("\n");
        		break;
        	}
        	
        }
        } 
        writer.close(); 
        writerUser.close();

	return fileNameTweets;
	}

	public void MatrixTweetMethod(String TweetTestFileName, String VocabFilePath) throws Exception {
	
		System.out.println("in MatrixTweetMethod");
		//Counting the total number of words
	    File file = new File(VocabFilePath);
	    file.createNewFile();
	    Scanner sc = new Scanner(new FileInputStream(file));
	    int count=0;
	    while(sc.hasNext()){
	        sc.next();
	        count++;
	    }
	    //System.out.println("Number of words: " + count);
	    
	    // Create a Array for the matrix
	    String WordsArray[] = new String[count];
	    File file2 = new File(VocabFilePath);
	    Scanner sc2 = new Scanner(new FileInputStream(file2));
	    int count2=0;
	    while(sc2.hasNext()){
	    	String wordtemp = sc2.next();
	        WordsArray[count2] =  wordtemp;
	        count2++;
	    }
	    
	    // Creating a Matrix Start
	    FileInputStream fstream1 = new FileInputStream(TweetTestFileName);
		DataInputStream in1 = new DataInputStream(fstream1);
	    BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
	    String strLine1;
	    
	    //String fileName1 = "";
	    int Id =0;
	    String fileName1 = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.csv";
    	//FileNumCount++;
	    File f = new File(fileName1);
	    f.createNewFile();
		BufferedWriter bw = new BufferedWriter(new FileWriter(fileName1, true));
		
		bw.write("ID" + ",");
	    for(int i=0; i<WordsArray.length; i++ )
	    {
	    	bw.write("W" + i);
	    	 if(i< WordsArray.length - 1 )
	    		 bw.write(",");
	    }
	    bw.write("\r\n");
	    bw.close();
	    
	    while((strLine1 = br1.readLine()) != null)
	    {
	    	bw = new BufferedWriter(new FileWriter(fileName1, true));
			String CurrentLineArray[] = new String[strLine1.length()];
		   	StringTokenizer stringTokenizer = new StringTokenizer(strLine1, " ");
		   	int CurrentLinewordCount = 0;
	        while(stringTokenizer.hasMoreTokens())
		        {
	         	String Word = stringTokenizer.nextToken();
	         	CurrentLineArray[CurrentLinewordCount] = Word;
	         	CurrentLinewordCount++;
		        }
	         bw.write(String.valueOf(Id) + ",");
	         for(int i=0; i<WordsArray.length; i++ )
	         {
	         	boolean flag = false;
	         	for(int j=0; j<CurrentLinewordCount ; j++)
	         	{	
	         		if(CurrentLineArray[j].equalsIgnoreCase(WordsArray[i]))
	         		{
	         			flag = true; 
	         			break;
	         		}
	            	}
	         	if(flag == true){
	         		bw.write("1");
	         		}
	         	else{
	           		bw.write("0");
	         	}
	         	
	         	if(i< WordsArray.length - 1 )
	         		bw.write(",");
	         }
	        bw.write("\r\n");
	       
	       Id++;
	       bw.close();
	    }
	
	  //  return FileNumCount;
		}

public void Convert_CSV_to_Arff_Test() throws IOException
		{
				System.out.println("in Convert_CSV_to_Arff_Test");
				String CsvFileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.csv";
				String ArffFileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTestFinal.arff";
				
				CSVLoader loader = new CSVLoader();
			    loader.setSource(new File(CsvFileName));
			    Instances data = loader.getDataSet();

			    ArffSaver saver = new ArffSaver();
			    saver.setInstances(data);
			    saver.setFile(new File(ArffFileName));
			    saver.writeBatch();
			
			/*for(int i=0;i<FileNumCount;i++)
			{
				 File csv_Delete = new File("/TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileTest"+i+".csv");
				 csv_Delete.delete();
			}*/
	}
		
}