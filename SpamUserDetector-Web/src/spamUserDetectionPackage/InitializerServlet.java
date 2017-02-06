package spamUserDetectionPackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class InitializerServlet
 */
@WebServlet("/InitializerServlet")
public class InitializerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InitializerServlet() {
        super();
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
		String username =  request.getParameter("username");
		String password =  request.getParameter("password");
		HttpSession session = request.getSession();
		RequestDispatcher rd = null;
	
		if(username.equals("admin") & password.equals("admin"))
		{
			ServletContext servletContext = request.getSession().getServletContext();
			String absoluteDiskPath = servletContext.getRealPath(".");
			FilePathData.setAbsoluteDiskPath(absoluteDiskPath.substring(0, absoluteDiskPath.length()).concat("/"));		

			MySpamMain m = new MySpamMain();
			m.initialSetup();
	    	session.setAttribute("InitializationStatus", "Initialization successful!!");
		}
		else
		{
	    	session.setAttribute("InitializationStatus", "You Are Not an Authorized Admin");
	    	
		}
		
		rd = request.getRequestDispatcher("index.jsp");
    	rd.forward(request, response);
    	System.out.println("Completed");
	}

}
