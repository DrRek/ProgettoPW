package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.Cart;

@WebServlet("/access")

public class AccessControl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static AccessModel model = new AccessModel();

	public AccessControl(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		
		if(action!=null){
			if(action.equalsIgnoreCase("login")){
				String userid = request.getParameter("userid");
				String passid = request.getParameter("passid");
				UserBean user = model.doRetrieveByKey(userid, passid);
				if(user!=null){ //restituisce true se l'utente esiste
					request.getSession().setAttribute("user", user);
					
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("article");
					dispatcher.forward(request, response);
				} else {
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleView.jsp");
					dispatcher.forward(request, response);
				}
			}
			else if(action.equalsIgnoreCase("register")){
				
			}
		}
	}
}
