package it.quattrocchi.control;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.AccessModel;
import it.quattrocchi.support.AdminBean;
import it.quattrocchi.support.UserBean;

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
				UserBean user;
				AdminBean admin = null;
				try {
					//nel caso in cui sia amministratore
					if(model.isAdmin(userid,passid)){
						admin = new AdminBean();
						admin.setUser(userid);
						admin.setPassword(passid);
						request.getSession().setAttribute("admin", admin);
						RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
						dispatcher.forward(request, response);
					}
				
					user = model.doRetrieveByKey(userid, passid);
					
				} catch (SQLException e) {
					user=null;
				}
				
				if(user!=null && admin == null){ //restituisce true se l'utente esiste
					request.getSession().setAttribute("user", user);

					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
					dispatcher.forward(request, response);
				} else if(user==null && admin == null) {
					request.setAttribute("loginFailed", true);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/access");
					dispatcher.forward(request, response);
				}
			}
			else if(action.equalsIgnoreCase("register")){
				UserBean newUser = new UserBean();
				newUser.setUser(request.getParameter("user"));
				newUser.setPassword(request.getParameter("pass"));
				newUser.setNome(request.getParameter("nome"));
				newUser.setCognome(request.getParameter("cognome"));

				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				java.util.Date parsed=null;
				try {
					parsed = format.parse(request.getParameter("nascita"));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date dataNascita = new java.sql.Date(parsed.getTime());
				newUser.setDataDiNascita(dataNascita);

				newUser.setStato(request.getParameter("stato"));
				newUser.setCap(request.getParameter("cap"));
				newUser.setIndirizzo(request.getParameter("indirizzo"));
				newUser.setEmail(request.getParameter("email"));
				System.out.println(newUser);
				try {
					model.doSave(newUser);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/AccessView.jsp");
		dispatcher.forward(request, response);
	}
}
