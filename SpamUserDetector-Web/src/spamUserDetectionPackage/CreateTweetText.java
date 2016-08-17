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


public class CreateTweetText {
	
	public void createTweetTextmethod() throws IOException {

    	String fileName = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/tweetsText.tsv";
	    File dynFile = new File(fileName);
	    Writer writer = new BufferedWriter(new FileWriter(dynFile));
	    
	    int NumOfTweet =0;
	    // 0, - , -, 1, -
		//String[] Filenames = {"egypt", "libya", "syria", "tunisia", "yemen"};
	    //String[] Filenames = {"tunisia","libya"};
	   // String[] Filenames = {"libya"};
	    String[] Filenames = {"meeli"};
		
		for(int j=0; j<Filenames.length; j++)
		{
			String FName = Filenames[j];
			FileInputStream fstream = new FileInputStream(FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/"+FName+".tsv");
			System.out.println(Filenames[j]);
			DataInputStream in = new DataInputStream(fstream);
	        BufferedReader br = new BufferedReader(new InputStreamReader(in,"UTF-8"));
	        String strLine;
	        
	        while ((strLine = br.readLine()) != null)  
	        {
	        StringTokenizer stringTokenizer = new StringTokenizer(strLine, "\t");
	        int temp = 0;
	        while(stringTokenizer.hasMoreTokens())
	        {
	        	temp++;
	        	stringTokenizer.nextToken();
	        	if (temp==1)
	        	{
	        		//System.out.println(stringTokenizer.nextToken());
	        		writer.write(stringTokenizer.nextToken()); 
	        		NumOfTweet++;
	        		writer.write("\n");
	        		break;
	        	}
	        }
	        } 
			   
		}
		writer.close();
		System.out.println("Creating the tweet text end : " + NumOfTweet);

	}
}
