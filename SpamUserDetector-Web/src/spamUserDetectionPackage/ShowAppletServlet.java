package spamUserDetectionPackage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ShowAppletServlet
 */
@WebServlet("/ShowAppletServlet")
public class ShowAppletServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ShowAppletServlet() {
        // TODO Auto-generated constructor stub
       }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ServletContext servletContext = request.getSession().getServletContext();
		String absoluteDiskPath = servletContext.getRealPath(".");
		FilePathData.setAbsoluteDiskPath(absoluteDiskPath.substring(0, absoluteDiskPath.length()).concat("/"));		

		// try starts
		String newTestFilePath = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/HelloFileName.tsv";
		ServletInputStream content = request.getInputStream();
		int formDataLength = request.getContentLength();
		createTestFile(content,formDataLength,newTestFilePath);
				
		// try ends
		MySpamMain m = new MySpamMain();
		String spamUserNames = m.getSpamUserNames(newTestFilePath);
		
		HttpSession session = request.getSession();
    	session.setAttribute("spamUserNames", spamUserNames);
    	
    	RequestDispatcher rd = null;
    	rd = request.getRequestDispatcher("index.jsp");
    	rd.forward(request, response);
		
	}
	
	private void createTestFile(ServletInputStream content, int formDataLength, String newTestFilePath) throws IOException
	{
        byte dataBytes[] = new byte[formDataLength];
        int byteRead = 0;
        int totalBytesRead = 0;
        //this loop converting the uploaded file into byte code
        while (totalBytesRead < formDataLength) {
                byteRead = content.read(dataBytes, totalBytesRead, formDataLength);
                totalBytesRead += byteRead;
                }
       // String file = new String(dataBytes);
      //  System.out.println("File content: " + file.toString());
        
      
        // creating a new file with the same name and writing the content in new file
        String tempFile = FilePathData.getAbsoluteDiskPath()+"TrainingDataSet/ProcessedTweets/tempFile.tsv";
        FileOutputStream fileOut = new FileOutputStream(tempFile);
        fileOut.write(dataBytes);
        fileOut.flush(); fileOut.close();   
        
        File newTestFile = new File(newTestFilePath);
	//	if(newTestFile.exists()) newTestFile.delete();
	
	    Writer writer = new BufferedWriter(new FileWriter(newTestFile));
        BufferedReader br = new BufferedReader(new FileReader(tempFile));
        String line;
        while ((line = br.readLine()) != null) {
           if(line.contains("WebKitFormBoundary") || line.contains("Content") || line.contains("Start"));
           else
           {
        	   writer.write(line.trim());
        	   writer.write("\n");
           }
        	   
        }
        br.close();
        writer.close();
        File tempTestFilePathDelete = new File(tempFile);
		if(tempTestFilePathDelete.exists()) tempTestFilePathDelete.delete();
	
	
	}

}
