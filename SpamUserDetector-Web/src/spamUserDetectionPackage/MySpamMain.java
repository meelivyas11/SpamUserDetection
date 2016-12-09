package spamUserDetectionPackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MySpamMain {

	public void initialSetup() {
		System.out.println("Initial Setup Start");
		int Size_of_Each_Partition = 200;
		String TweetFilepath = FilePathData.getAbsoluteDiskPath()
				+ "TrainingDataSet/ProcessedTweets/tweetsText.tsv";
		final String VocabFilePath = FilePathData.getAbsoluteDiskPath()
				+ "TrainingDataSet/ProcessedTweets/ProcessVocabLevel2.txt";

		try {
			// Comment Start In case u want to just run the test on already
			// Classified
			CreateTweetText tweettextobj = new CreateTweetText();

			tweettextobj.createTweetTextmethod();
			CreateVocabulary vocubularyObj = new CreateVocabulary();
			vocubularyObj.createvocabularymethod(TweetFilepath, VocabFilePath);

			// Send the Tweet-file and Vocab-File
			CreateMatrix matrixobj = new CreateMatrix();
			matrixobj.createLabledMatrixmethod(TweetFilepath, VocabFilePath,
					Size_of_Each_Partition);
			int FileNumCount = matrixobj.create_UnLabled_Matrix_method(TweetFilepath, VocabFilePath, Size_of_Each_Partition);

			TryNaiveBayes tryNaiveBayesObj = new TryNaiveBayes();
			tryNaiveBayesObj.NaiveBayesFirstLevelLearning();
			tryNaiveBayesObj.TryNaiveBayesLearning(FileNumCount - 1,VocabFilePath);
			// Comment End In case u want to just run the test on already
			// Classified

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String getSpamUserNames(String TweetTestFileName) throws IOException {
		System.out.println("In getSpamUserNames method ");
		final int Declare_SpamUser_Count = 1;
		String spamUserNames = "";
		final String VocabFilePath = FilePathData.getAbsoluteDiskPath()	+ "TrainingDataSet/ProcessedTweets/ProcessVocabLevel2.txt";

		// Proper GUI for Entering the Test Tweet Location
		// Test Particular Tweets Given by the User
		String MatrixLabledTrain = FilePathData.getAbsoluteDiskPath()+ "TrainingDataSet/ProcessedTweets/MatrixFiles/MatrixFileLabledTrain.arff";
		
		// String TweetTestFileName = TestTweetFilePath.getText();
		TreatTestTweets treatTweetsObj = new TreatTestTweets();
		try {
			ArrayList FinalSpamUserList = treatTweetsObj.TreatTweetMethod(TweetTestFileName, VocabFilePath, MatrixLabledTrain,Declare_SpamUser_Count);
			if (FinalSpamUserList.size() == 0)
				spamUserNames = "None of the Tweets have Spam user";
			for (int k = 0; k < FinalSpamUserList.size(); k++) {
				spamUserNames = spamUserNames + FinalSpamUserList.get(k) + "; ";
			}
			System.out.println("Spam Users are : " + spamUserNames);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		// Test Particular Tweets Given by the User End
		System.out.println("Complete ");
		return spamUserNames;
	}

}
