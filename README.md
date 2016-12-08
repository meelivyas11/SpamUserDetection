# Spam User Detection

## Setting up the Environment

### Software Requirements
 - Git Bash: https://git-scm.com/
 - Eclipse
 - Apache Tomcat: http://apache.spinellicreations.com/tomcat/tomcat-8/v8.0.39/bin/apache-tomcat-8.0.39.zip
 
### Download the Code
 - Clone the repository using `git clone https://github.com/meelivyas11/SpamUserDetection.git` from Git Bash
 - Open the repository 'SpamUserDetection' from eclipse IDE
 - Import 'SpamUserDetector-Web' Project (`File->Import->General->Existing Project into Workspace`) from eclipse IDE if it is not present
 
### Build
  - Remove the existing external jars and re-add them from `SpamUserDetector-Web Properties -> Java Build Path` 
    <b>Note: Required Jars are present repository `Jars` folder </b>
  - Create a new `Apache Tomcat v8.0` server
  - Clean and Build the Project
  
### Running
 - Start the Server
 - Open `http://localhost:8080/SpamUserDetector-Web/` from your web browser
 - Browse `ApplicationTestTweets.tsv` file from the Repository and click `Start View`
 
 
