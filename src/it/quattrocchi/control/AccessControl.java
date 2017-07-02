package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.UserModel;
import it.quattrocchi.model.CreditCardModel;
import it.quattrocchi.model.OrderModel;
import it.quattrocchi.support.AdminBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/access")

public class AccessControl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static UserModel model = new UserModel();
	static CreditCardModel ccModel = new CreditCardModel();
	static OrderModel oModel = new OrderModel();

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
			if(action.equalsIgnoreCase("login"))
				try {
					login(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else if(action.equalsIgnoreCase("register"))
				register(request,response);

		}
		else{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/AccessView.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException
	{
		String userid = request.getParameter("userid");
		String passid = request.getParameter("passid");
		UserBean user;
		boolean isAdmin;

		try{
			isAdmin = model.isAdmin(userid,passid);
		}catch(SQLException e){
			isAdmin = false;
		}

		if(isAdmin){
			request.getSession().invalidate();
			AdminBean admin = new AdminBean();
			admin.setUser(userid);
			admin.setPassword(passid);
			//admin.setOrders(oModel.doRetrieveAll());
			request.getSession().setAttribute("admin", admin);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
			dispatcher.forward(request, response);
		}
		else{

			try {
				user = model.doRetrieveIdentifiedByKey(userid, passid);
				if(user!=null){ //restituisce true se l'utente esiste
					user.setCards(ccModel.doRetrieveByCliente(user));
					user.setOrders(oModel.doRetrieveByCliente(user));
					request.getSession().setAttribute("user", user);
	
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
					dispatcher.forward(request, response);
				} else {
					request.setAttribute("loginFailed", true);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/AccessView.jsp");
					dispatcher.forward(request, response);
				}
			} catch (SQLException e) {
				user = null;
			}
		}
	}

	private void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		java.sql.Date dataNascita = new java.sql.Date(parsed.getTime());
		newUser.setDataDiNascita(dataNascita);

		newUser.setStato(request.getParameter("stato"));
		newUser.setCap(request.getParameter("cap"));
		newUser.setIndirizzo(request.getParameter("indirizzo"));
		newUser.setEmail(request.getParameter("email"));
		try {
			if(model.doRetrieveByKey(newUser.getUser()) == null)
				model.doSave(newUser);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/AccessView.jsp");
		dispatcher.forward(request, response);
	}
}
