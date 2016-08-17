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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;


public class CreateMatrix {
	
	public void createLabledMatrixmethod(String TweetFilepath,String VocabFilePath, int Size_of_Each_Partition) throws IOException {
		
		//Counting the total number of words
        File file = new File(VocabFilePath);
        Scanner sc = new Scanner(new FileInputStream(file));
        int count=0;
        while(sc.hasNext()){
            sc.next();
            count++;
        }
      //  System.out.println("Number of words: " + count);
        
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
        FileInputStream fstream1 = new FileInputStream(TweetFilepath);
		DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
        String strLine1;
        
	    String fileName3 = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/LearnedSpamWords.txt";
        File dynFile3 = new File(fileName3);
	    boolean fileCreated3 = dynFile3.createNewFile();
	    Writer writer3 = new BufferedWriter(new FileWriter(dynFile3));

	    String fileName1 = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileLabled.csv";
        //String fileName1 = "MatrixFile.csv";
        File dynFile1 = new File(fileName1);
	    boolean fileCreated1 = dynFile1.createNewFile();
	    Writer writer1 = new BufferedWriter(new FileWriter(dynFile1));
	       
	    int Id =0;
	    
	    // Putting the header in the CSV File
	    writer1.write("ID" + ",");
	    for(int i=0; i<WordsArray.length; i++ )
	    {
	    	 writer1.write("W"+ i + ",");
	    }
	    writer1.write("Class");
	    writer1.write("\r\n");
	    // Putting the header in the CSV File End
	    int labledFileCount = 0;
	    
	    while((strLine1 = br1.readLine()) != null && labledFileCount< Size_of_Each_Partition)
	    {
	    	labledFileCount++;
	    	String CurrentLineArray[] = new String[strLine1.length()];
	    	StringTokenizer stringTokenizer = new StringTokenizer(strLine1, " ");
	    	int CurrentLinewordCount = 0;
            while(stringTokenizer.hasMoreTokens())
	        {
            	String Word = stringTokenizer.nextToken();
            	CurrentLineArray[CurrentLinewordCount] = Word;
            	CurrentLinewordCount++;
            	
	        }
          
            writer1.write(String.valueOf(Id) + ","); 
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
            		writer1.write("1,");
            		}
            	else
            		writer1.write("0,");
            }
          // System.out.println(Id);
            
            // Condition Spam Not-Spam Start
            boolean SpamTrueFalse = false;
            SpamTrueFalse = SpamOrNot(strLine1);
            if(SpamTrueFalse == true)
            {
    			 writer3.write(strLine1);
             	 writer3.write("\r\n");
    		     writer1.write("Spam");
            }
            else
            	writer1.write("NotSpam");
            // Condition Spam Not-Spam Ends
           
            writer1.write("\r\n");
            Id++;
	    }
	    writer1.close();
	    writer3.close(); 

	    ProcessLearnedSpamFile(fileName3);
	}
	
	
	public boolean SpamOrNot(String StrLineTweet) throws IOException {
		
		// Count number of @ and #
		int CountAt = 0, CountHash =0;
		for (int i=0; i < StrLineTweet.length(); i++)
	    {
	        if (StrLineTweet.charAt(i) == '@')
	        {
	        	CountAt++;
	        }
	        if (StrLineTweet.charAt(i) == '#')
	        {
	        	CountHash++;
	        }
	    }
		
		// Checking from the list from the file
		FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/SpamWords.txt");
		DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
        String strLine; boolean FlagSpam= false;
        while((strLine = br.readLine()) != null)
        {
        	if(StrLineTweet.contains(strLine))
        		FlagSpam = true;
        }
		
       /* FileInputStream fstream1 = new FileInputStream("G:\\DataMiningProject\\TrainingDataSet\\ProcessedTweets\\LearnedSpamWords.txt");
		DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1));
        String strLine1; boolean FlaglearnedSpam1= false;
        while((strLine1 = br.readLine()) != null)
        {
        	if(StrLineTweet.contains(strLine1))
        		FlaglearnedSpam1 = true;
        }
        */
	  
		if(CountAt > 3 || CountHash > 5 || StrLineTweet.length() < 20 || FlagSpam == true )
		{
			return true;
		}
		else
			return false;
	}
	
	
	public void ProcessLearnedSpamFile(String NewSpamWordsFilePath) throws IOException {
		
		// Clear LearnedSpamWords File
		 //System.out.println("Meeli" + NewSpamWordsFilePath.length());
		 String CleanWordsFilepath = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/SpamWordsNew.txt"; 
		  
		 ArrayList SpamWordList = CleanSpamTweet(NewSpamWordsFilePath, CleanWordsFilepath);
		 //System.out.println("CleanWordsFilepath.length() :: " + CleanWordsFilepath.length());
		 
		 FileInputStream fstream1 = new FileInputStream(CleanWordsFilepath);
		 DataInputStream in1 = new DataInputStream(fstream1);
	     BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
	     String strLine1;
	     
	     //ArrayList MoreSpamWords = new ArrayList();
	     String SpamWordsFile = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/SpamWords.txt";
	     BufferedWriter bw = new BufferedWriter(new FileWriter(SpamWordsFile, true));
	   
	     /*while((strLine1 = br1.readLine()) != null )
	     {
	    	 System.out.println(strLine1);
	    	 bw.write(strLine1);	    	 
	     }*/
	     for(int m=0;m<SpamWordList.size();m++)
	     {
	    	 //System.out.println(SpamWordList.get(m));
	    	 bw.write(SpamWordList.get(m).toString());	
	     }
		   
	    bw.close();	
	    File txt_Delete = new File(CleanWordsFilepath);
		txt_Delete.delete();
		// Combine SpamWords and LearnedSpamWords
		
		
	}
	
	public int create_UnLabled_Matrix_method(String TweetFilepath, String VocabFilePath, int Size_of_Each_Partition) throws IOException 
	{
		//Counting the total number of words
        File file = new File(VocabFilePath);
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
        FileInputStream fstream1 = new FileInputStream(TweetFilepath);
		DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
        String strLine1;
        
        String fileName1 = "";
        int UnlableCount = 0, FileNumCount =0;  int Id =0;
        while((strLine1 = br1.readLine()) != null)
	    {
        	if(UnlableCount%Size_of_Each_Partition == 0)
        	{
        		//System.out.println("File Creation : " + FileNumCount);
	        	fileName1 = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+ FileNumCount+ ".csv";
	        	//System.out.println("File names in CreateMatrix ::: " + fileName1);
	        	FileNumCount++;
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
        	}
        	
        	BufferedWriter bw = new BufferedWriter(new FileWriter(fileName1, true));
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
           UnlableCount++;
           bw.close();
	    }
	
	    // Call another method to create the Arff files
	    Convert_CSV_to_Arff(FileNumCount);
	    return FileNumCount;
	}
	
	public void Convert_CSV_to_Arff(int FileNumCount) throws IOException
	{
		for(int i=1;i<FileNumCount;i++)
		{
			//System.out.println(i);
			String CsvFileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+i+".csv";
			String ArffFileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+i+".arff";
			
			CSVLoader loader = new CSVLoader();
		    loader.setSource(new File(CsvFileName));
		    Instances data = loader.getDataSet();

		    ArffSaver saver = new ArffSaver();
		    saver.setInstances(data);
		    saver.setFile(new File(ArffFileName));
		    saver.writeBatch();
		}
		
		for(int i=0;i<FileNumCount;i++)
		{
			 File csv_Delete = new File(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFile"+i+".csv");
			 csv_Delete.delete();
		}
	}
	public ArrayList CleanSpamTweet(String TweetFilepath,String VocabFilePath) throws IOException	
	{
		//System.out.println("in CleanSpamTweet");
		ArrayList<String> SpamWordList = new ArrayList<String>();
		FileInputStream fstream2 = new FileInputStream(TweetFilepath);
		DataInputStream in2 = new DataInputStream(fstream2);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2,"UTF-8"));
        String strLine2;
       
	    String fileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/ProcessVocabLevel1.txt";
	    File dynFile = new File(fileName);
	    boolean fileCreated = dynFile.createNewFile();
	    Writer writer = new BufferedWriter(new FileWriter(dynFile));
	  
	    String[] stopwords = {"a","able","about","above","according","accordingly","across","actually","after","afterwards","again","against","all","allow","allows","almost","alone","along","already","also","although","always","am","among","amongst","an","and","another","any","anybody","anyhow","anyone","anything","anyway","anyways","anywhere","apart","appear","appreciate","appropriate","are","around","as","aside","ask","asking","associated","at","available","away","awfully",
	            "b","be","became","because","become","becomes","becoming","been","before","beforehand","behind","being","believe","below","beside","besides","best","better","between","beyond","both","brief","but","by",
	            "c","came","can","cannot","cant","cause","causes","certain","certainly","changes","clearly","co","com","come","comes","concerning","consequently","consider","considering","contain","containing","contains","corresponding","could","course","currently",
	            "d","definitely","described","despite","did","different","do","does","doing","done","down","downwards","during",
	            "e","each","edu","eg","eight","either","else","elsewhere","enough","entirely","especially","et","etc","even","ever","every","everybody","everyone","everything","everywhere","ex","exactly","example","except",
	            "f","far","few","fifth","first","five","followed","following","follows","for","former","formerly","forth","four","from","further","furthermore",
	            "g","get","gets","getting","given","gives","go","goes","going","gone","got","gotten","greetings",
	            "h","had","happens","hardly","has","have","having","he","hello","help","hence","her","here","hereafter","hereby","herein","hereupon","hers","herself","hi","him","himself","his","hither","hopefully","how","howbeit","however",
	            "i","ie","if","ignored","immediate","in","inasmuch","inc","indeed","indicate","indicated","indicates","inner","insofar","instead","into","inward","is","it","its","itself",
	            "j","just","k","keep","keeps","kept","know","knows","known", "l","last","lately","later","latter","latterly","least","less","lest","let","like","liked","likely","little","ll","look","looking","looks","ltd",
	            "m","mainly","many","may","maybe","me","mean","meanwhile","merely","might","more","moreover","most","mostly","much","must","my","myself",
	            "n","name","namely","nd","near","nearly","necessary","need","needs","neither","never","nevertheless","new","next","nine","no","nobody","non","none","noone","nor","normally","not","nothing","novel","now","nowhere",
	            "o","obviously","of","off","often","oh","ok","okay","old","on","once","one","ones","only","onto","or","other","others","otherwise","ought","our","ours","ourselves","out","outside","over","overall","own",
	            "p","particular","particularly","per","perhaps","placed","please","plus","possible","presumably","probably","provides",
	            "q","que","quite","qv","r","rather","rd","re","really","reasonably","regarding","regardless","regards","relatively","respectively","right",
	            "s","said","same","saw","say","saying","says","second","secondly","see","seeing","seem","seemed","seeming","seems","seen","self","selves","sensible","sent","serious","seriously","seven","several","shall","she","should","since","six","so","some","somebody","somehow","someone","something","sometime","sometimes","somewhat","somewhere","soon","sorry","specified","specify","specifying","still","sub","such","sup","sure",
	            "t","take","taken","tell","tends","th","than","thank","thanks","thanx","that","thats","the","their","theirs","them","themselves","then","thence","there","thereafter","thereby","therefore","therein","theres","thereupon","these","they","think","third","this","thorough","thoroughly","those","though","three","through","throughout","thru","thus","to","together","too","took","toward","towards","tried","tries","truly","try","trying","twice","two",
	            "u","un","under","unfortunately","unless","unlikely","until","unto","up","upon","us","use","used","useful","uses","using","usually","uucp",
	            "v","value","various","ve","very","via","viz","vs","w","want","wants","was","way","we","welcome","well","went","were","what","whatever","when","whence","whenever","where","whereafter","whereas","whereby","wherein","whereupon","wherever","whether","which","while","whither","who","whoever","whole","whom","whose","why","will","willing","wish","with","within","without","wonder","would","would",
	            "x", "y","yes","yet","you","your","yours","yourself","yourselves", "z","zero",
	            "1","2","3","4","5","6","7","8","9","0","wtf","Jan25","ppl","&","AC360","city","call"};
	    
	    
        while ((strLine2 = br2.readLine()) != null)  
        {
        	Pattern stylerecord1 = Pattern.compile("RT");
            Matcher mstylerecord1 = stylerecord1.matcher(strLine2);
            
            Pattern stylerecord2 = Pattern.compile("@");
            Matcher mstylerecord2 = stylerecord2.matcher(mstylerecord1.replaceAll(""));
            
            Pattern stylerecord3 = Pattern.compile("#");
            Matcher mstylerecord3 = stylerecord3.matcher(mstylerecord2.replaceAll(""));
            
            Pattern stylerecord4= Pattern.compile(":");
            Matcher mstylerecord4 = stylerecord4.matcher(mstylerecord3.replaceAll(""));
            
            Pattern stylerecord5 = Pattern.compile("|");
            Matcher mstylerecord5 = stylerecord5.matcher(mstylerecord4.replaceAll(""));
            
            Pattern stylerecord6 = Pattern.compile("-");
            Matcher mstylerecord6 = stylerecord6.matcher(mstylerecord5.replaceAll(""));
            
            Pattern stylerecord7 = Pattern.compile(",");
            Matcher mstylerecord7 = stylerecord7.matcher(mstylerecord6.replaceAll(""));
          
            Pattern stylerecord8 = Pattern.compile("http*:*/*/*[A-Za-z0-9]*.*");
            Matcher mstylerecord8 = stylerecord8.matcher(mstylerecord7.replaceAll(""));
          
            Pattern stylerecord9 = Pattern.compile("/");
            Matcher mstylerecord9 = stylerecord9.matcher(mstylerecord8.replaceAll(""));
            
            Pattern stylerecord12 = Pattern.compile("[A-Za-z0-9]%");
            Matcher mstylerecord12 = stylerecord12.matcher(mstylerecord9.replaceAll(""));
          
            String Value =mstylerecord12.replaceAll(" ");
           
            writer.write(Value); 
    		writer.write("\n");
           
        }
		
		FileInputStream fstream1 = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/ProcessVocabLevel1.txt");
		DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
        String strLine1;
                   
	    File dynFile1 = new File(VocabFilePath);
	    Writer writer1 = new BufferedWriter(new FileWriter(dynFile1));
	    
	    while((strLine1 = br1.readLine()) != null)
        {
            StringTokenizer stringTokenizer = new StringTokenizer(strLine1, " ");
            while(stringTokenizer.hasMoreTokens())
	        {
            	String Word = stringTokenizer.nextToken();
            	for(int m=0; m<stopwords.length; m++)
                 { 
                 	if(Word.equalsIgnoreCase(stopwords[m].trim()) || Word.trim().length()<4 || Word.contains("_"))
                 	{
                 		Word = "";
                 	}
                 }
            	 if(!Word.equalsIgnoreCase(""))
            	 {
            			Word = Word.replaceAll("//", " ");
            			writer1.write(Word); 
                		writer1.write("\n");
                		SpamWordList.add(Word);
                		SpamWordList.add("\n");
            	 }
	        }
        }
	    writer1.close();
	    //System.out.println(SpamWordList.size());
	 return SpamWordList;
	}
}
