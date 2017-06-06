package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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
			
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date parsed=null;
			try {
				parsed = format.parse(dataEsecuzione.toString());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			java.sql.Date rightDate = new java.sql.Date(parsed.getTime());
			ordine.setDataEsecuzione(rightDate);
			try {
				model.doSave(ordine);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
}
