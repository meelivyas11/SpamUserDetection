# What is it?

# How do I set up?
## Software Requirements
 - Git Bash: https://git-scm.com/
 - Eclipse Java EE IDE for Web Developers
 - Apache Tomcat: http://apache.spinellicreations.com/tomcat/tomcat-8/v8.0.39/bin/apache-tomcat-8.0.39.zip
 
## Environment Setup
 - Clone the repository using `git clone https://github.com/meelivyas11/SpamUserDetection.git` from Git Bash
 - Open the repository 'SpamUserDetection' from eclipse IDE
 - Import 'SpamUserDetector-Web' Project (`File->Import->General->Existing Project into Workspace`) from eclipse IDE
 - Resolve the build path erros by re-adding the jars present in `Jars` folder of the repository
 - Create a new `Apache Tomcat v8.0` server
 - Clean and Build the Project
  
# How do I use it?
 - Start the Server
 - Open `http://localhost:8080/SpamUserDetector-Web/` from your web browser
 - Enter the Username (`admin`) and Password (`admin`) to initialize the Learner
 - Now the learner in all set to identify the spamed users, So choose `ApplicationTestTweets.tsv` file from the Repository and click `Start View`
 
 
