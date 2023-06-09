package com.results;

import jakarta.annotation.Resource;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import javax.sql.DataSource;

/**
 * Servlet implementation class ResultControllerServlet
 */
public class ResultControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ResultDbUtil resultSet;
	
	@Resource(name="jdbc/scoresdb")
	private DataSource dataSource;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResultControllerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resultSet = new ResultDbUtil(dataSource);
		try {
			// read the "command" parameter
			String theCommand = request.getParameter("command");
			
			// if the command is missing, then default to listing students
			if (theCommand == null) {
				theCommand = "LIST";
			}
			if(request.getParameter("studentId")!=null) {
				request.getSession().setAttribute("studentId", request.getParameter("studentId"));
			}
			if(request.getParameter("courseId")!=null) {
				request.getSession().setAttribute("courseId", request.getParameter("courseId"));
			}
			// route to the appropriate method
			switch (theCommand) {
			
			case "LIST":
				listResults(request, response);
				break;	
			case "INSERT":
				addResult(request, response);
				break;
			case "LOAD":
				loadResult(request, response);
				break;
			case "UPDATE":
				updateResult(request, response);
				break;
			case "DELETE":
				deleteResult(request, response);
				break;
			case "VIEW":
				viewResult(request, response);
				break;
			default:
				listResults(request, response);
			}
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		}
		private void listResults(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
			try {
				String eval_type = "";
			Map<String, Object> resultMap = resultSet.getResultsAndAverageFromAcourse(eval_type,Integer.parseInt(request.getSession().getAttribute(
						"courseId").toString()), Integer.parseInt(request.getSession().getAttribute("studentId").toString()));
			;
			// add students to the request
			request.setAttribute("result_list", resultMap.get("results"));
			request.setAttribute("average", resultMap.get("average"));
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-results.jsp");
			dispatcher.forward(request, response);
			}
			catch (Exception e) {
				request.setAttribute("exception", e);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			    dispatcher.forward(request, response);
			}
		}
		
		private void loadResult(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
			try {
		Results result = new Results(Integer.parseInt(request.getParameter("score").toString()),
				request.getParameter("eval_name"),request.getParameter("eval_type"),
				Integer.parseInt(request.getSession().getAttribute("courseId").toString()),
				Integer.parseInt(request.getSession().getAttribute("studentId").toString()));
		request.setAttribute("result", result);
		RequestDispatcher dispatcher = 
				request.getRequestDispatcher("/update-result.jsp");
		dispatcher.forward(request, response);
			}
			catch (Exception e) {
				request.setAttribute("exception", e);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			    dispatcher.forward(request, response);
			}
		}	
		
		private void updateResult(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
			try { 
			resultSet.updateResult(Integer.parseInt(request.getParameter("new_score").toString()),
					request.getParameter("eval_name"), request.getParameter("eval_type"), 
					Integer.parseInt(request.getSession().getAttribute("courseId").toString()),
					Integer.parseInt(request.getSession().getAttribute("studentId").toString()));
			listResults(request, response);
			}
			catch (Exception e) {
				request.setAttribute("exception", e);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			    dispatcher.forward(request, response);
			}
		}
		
		private void deleteResult(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
			try {
			resultSet.deleteResult(request.getParameter("eval_type"),
					request.getParameter("eval_name"),
					Integer.parseInt(request.getSession().getAttribute("courseId").toString()),
					Integer.parseInt(request.getSession().getAttribute("studentId").toString()));
			listResults(request, response);
			}
			catch (Exception e) {
				request.setAttribute("exception", e);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			    dispatcher.forward(request, response);
			}
		}
		private void addResult(HttpServletRequest request, HttpServletResponse response) 
			throws Exception {
		try{
			int score = Integer.parseInt(request.getParameter("score"));
		String eval_name = request.getParameter("eval_name");
		String eval_type = request.getParameter("eval_type");
		int student_id = Integer.parseInt(request.getSession().getAttribute("studentId").toString());
		int course_id = Integer.parseInt(request.getSession().getAttribute("courseId").toString());
		resultSet.addResult(score, eval_name, eval_type, course_id, student_id);
		listResults(request, response);
		}
		catch (Exception e) {
			request.setAttribute("exception", e);
		    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
		    dispatcher.forward(request, response);
		}
	}
		private void viewResult(HttpServletRequest request, HttpServletResponse response) 
				throws Exception {
			try {

				String eval_type = "";
				if(request.getParameter("eval_type")!=null) {
					eval_type = request.getParameter("eval_type");
				}
			Map<String, Object> resultMap = resultSet.getResultsAndAverageFromAcourse(eval_type,Integer.parseInt(request.getSession().getAttribute(
					"courseId").toString()), Integer.parseInt(request.getSession().getAttribute("studentId").toString()));
			// add students to the request
			request.setAttribute("result_list", resultMap.get("results"));
			request.setAttribute("average", resultMap.get("average"));
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("/view-results.jsp");
			dispatcher.forward(request, response);
			}
			catch (Exception e) {
				request.setAttribute("exception", e);
			    RequestDispatcher dispatcher = request.getRequestDispatcher("/error.jsp");
			    dispatcher.forward(request, response);
			}
		}
}
