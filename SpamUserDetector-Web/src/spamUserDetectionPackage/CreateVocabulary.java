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
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class CreateVocabulary {

	public void createvocabularymethod(String TweetFilepath, String VocabFilePath) throws IOException {
		
		FileInputStream fstream2 = new FileInputStream(TweetFilepath);
		DataInputStream in2 = new DataInputStream(fstream2);
        BufferedReader br2 = new BufferedReader(new InputStreamReader(in2,"UTF-8"));
        String strLine2;
       
	    String fileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/ProcessVocabLevel1.txt";
	    File dynFile = new File(fileName);
	  //  boolean fileCreated = dynFile.createNewFile();
	   // System.out.println("The value os write is :"+dynFile.canWrite());
	    Writer writer = new BufferedWriter(new FileWriter(dynFile));
	    /*String[] stopwords = {" As ", " Was ", " A ", " The ", " Not ", " Of ", " Me ", " To "," On ", " Is ", 
	    		" An ", " I "," You "," These "," At "," Our "," For "," If "," Your "," In ",
	    		" So "," Have "," Had "," Like "," Are "," Now "," And "," Or "," Him "," Her "," Who ",
	    		" How "," Do "," Did "," While "," This "," That "};*/
	    
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
        	//System.out.println(strLine);
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
            //System.out.println(Value);
        }
		
		FileInputStream fstream1 = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/ProcessVocabLevel1.txt");
		DataInputStream in1 = new DataInputStream(fstream1);
        BufferedReader br1 = new BufferedReader(new InputStreamReader(in1,"UTF-8"));
        String strLine1;
                   
	   //String fileName1 = "G:\\DataMiningProject\\TrainingDataSet\\ProcessedTweets\\ProcessVocabLevel2.txt";
        //String fileName1 = "ProcessVocabLevel2.txt";
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
            	 }
	        }
        }
	    writer1.close();
	    /*while((strLine1 = br1.readLine()) != null)
        {
	    	for(int m=0; m<stopwords.length; m++)
            { 
           	if(strLine1.contains(stopwords[m]))
	    	{
	    		strLine1 = strLine1.replace(stopwords[m], " ");
	     	}
           	
            }
	    	//System.out.println(strLine1.trim());
	    	writer1.write(strLine1.trim()); 
    		writer1.write("\n");
        }*/
	    
	    
	}
}
