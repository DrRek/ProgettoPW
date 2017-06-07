package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.CheckoutModel;
import it.quattrocchi.support.Cart;
import it.quattrocchi.support.OrderBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/checkout")

public class CheckoutControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static CheckoutModel model = new CheckoutModel();

	public CheckoutControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		String action = request.getParameter("action");
		if (action != null) {
			if (action.equalsIgnoreCase("checkout"))
				summaryCheckout(request,response);
			else if (action.equalsIgnoreCase("submit")){
				addOrder(request, response);
				done(request, response);
			}
		}
	}
	
	private void addOrder(HttpServletRequest request, HttpServletResponse response){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		
		if(cart!=null&&user!=null){
			//Crea il bean OrderBean
		    String codice = UUID.randomUUID().toString();
		    while(!CheckoutModel.isUniqueCod(codice)){
			    codice = UUID.randomUUID().toString();
		    }
			Date dataEsecuzione = new Date();
			double costo = cart.getPrezzo();
			String cc = user.getCart();
			OrderBean ordine = new OrderBean();
			ordine.setCc(cc);
			ordine.setCliente(user);
			ordine.setCodice(codice);
			ordine.setCosto(costo);
			
			java.sql.Date rightDate = new java.sql.Date(dataEsecuzione.getTime());
			ordine.setDataEsecuzione(rightDate);
			ordine.setOrdinati(cart.getProducts());
			
			
			//Chiama il model per salvare
			try {
				model.doSave(ordine);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void done(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		if(request.getSession().getAttribute("user") == null) {
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
			dispatcher.forward(request, response);
		}
		else {
			request.getSession().removeAttribute("cart");
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
			dispatcher.forward(request, response);
		}
	}
	
	private void summaryCheckout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/CheckoutView.jsp");
		dispatcher.forward(request, response);
	}
}
