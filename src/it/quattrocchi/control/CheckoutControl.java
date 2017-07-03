package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.model.OrderModel;
import it.quattrocchi.model.PrescriptionModel;
import it.quattrocchi.support.AdminBean;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.Cart;
import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.OrderBean;
import it.quattrocchi.support.PrescriptionBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/checkout")

public class CheckoutControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static OrderModel model = new OrderModel();
	static ArticleModel aModel = new ArticleModel();

	public CheckoutControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(request.getSession().getAttribute("user") == null)
			response.sendRedirect("access");
		else{
			String action = request.getParameter("action");
			if(action!=null&&!action.equalsIgnoreCase("")){
				if (action.equalsIgnoreCase("asyncGenericSearch"))
					summaryCheckout(request,response);
				else if (action.equalsIgnoreCase("updateCart"))
					updateCart(request,response);
				else if (action.equalsIgnoreCase("updatePrescription"))
					updatePrescription(request,response);
				else if (action.equalsIgnoreCase("removeCart"))
					removeCart(request,response);
				else if(action.equalsIgnoreCase("prescriptions"))
					prescriptions(request,response);
				else if (action.equalsIgnoreCase("submit")){
					try {
						addOrder(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					}
					updateCatalogo(request,response);
					RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
					dispatcher.forward(request, response);
				}
				return;
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/CheckoutView.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void removeCart(HttpServletRequest request, HttpServletResponse response) 
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
		
		//anche se non capiterà mai 
		if (cart == null && admin == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		if(admin == null)
		{
			String nome =  request.getParameter("nome");
			String marca =  request.getParameter("marca");
			String grString = request.getParameter("gradazione");
			
			try{
				if(grString != null)
				{
					double gradazione = Double.parseDouble(grString);
					cart.removeProduct(aModel.doRetrieveContactLenses(nome, marca, gradazione));	

				}
				else
					cart.removeProduct(aModel.doRetrieveGlasses(nome, marca));	

			}
			catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
			request.getSession().setAttribute("cart", cart);

		}		
	}

	private void updateCart(HttpServletRequest request, HttpServletResponse response)
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");

		//anche se non capiterà mai 
		if (cart == null && admin == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		if(admin == null)
		{
			String nome =  request.getParameter("nome");
			String marca =  request.getParameter("marca");
			int numero = Integer.parseInt(request.getParameter("numero"));
			String grString = request.getParameter("gradazione");
			try{
				if(grString != null)
				{
					double gradazione = Double.parseDouble(grString);
					cart.updateProduct(aModel.doRetrieveContactLenses(nome, marca, gradazione), numero);	

				}
				else
					cart.updateProduct(aModel.doRetrieveGlasses(nome, marca), numero);	

			}
			catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
			request.getSession().setAttribute("cart", cart);

		}
	}

	private void addOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String carta = (String) request.getParameter("carta");

		if(cart!=null&&user!=null){
			//Crea il bean OrderBean
			String codice = UUID.randomUUID().toString();
			while(!OrderModel.isUniqueCod(codice)){
				codice = UUID.randomUUID().toString();
			}
			Date dataEsecuzione = new Date();
			double costo = cart.getPrezzo();
			OrderBean ordine = new OrderBean();
			ordine.setCc(carta);
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
				e.printStackTrace();
			}
		}
		
		for(CartArticle a: cart.getProducts()){
			a.getArticle().setDisponibilita(a.getArticle().getDisponibilita() - a.getQuantity());
			System.out.println(a.getArticle().getDisponibilita() - a.getQuantity());
			aModel.doSave(a.getArticle());
		}
		
		user.setOrders(model.doRetrieveByCliente(user));
		request.getSession().setAttribute("user", user);
		request.getSession().setAttribute("cart", new Cart());
	}

	private void summaryCheckout(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart==null){
			cart=new Cart();
		}
		response.getWriter().write(new Gson().toJson(cart));
	}

	private void prescriptions(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		Collection<PrescriptionBean> prescriptions = null;
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		if(user != null){
			prescriptions = user.getPrescriptions();
			if(prescriptions!=null){
				response.getWriter().write(new Gson().toJson(prescriptions));
				return;
			}
		}
		
	}
	
	private void updateCatalogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		ArrayList<CartArticle> cArticle = cart.getProducts();

		for(CartArticle c : cArticle){
			try{
				ArticleBean art = null;

				if(aModel.isGlasses(c.getArticle().getNome(), c.getArticle().getMarca()))
					art = aModel.doRetrieveGlasses(c.getArticle().getNome(), c.getArticle().getMarca());
				else	
					art = aModel.doRetrieveContactLenses(c.getArticle().getNome(), c.getArticle().getMarca(), ((ContactLensesBean) c.getArticle()).getGradazione());

				art.setDisponibilita(art.getDisponibilita()-c.getQuantity());
				aModel.doSave(art);

			} catch (Exception e){
				e.printStackTrace();
			}
		}

	}
	
	private void updatePrescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		ArrayList<CartArticle> cArticle = cart.getProducts();
		String nome =  request.getParameter("nome");
		String marca =  request.getParameter("marca");
		String prescrizione = request.getParameter("prescrizione");

		for(CartArticle c : cArticle){
			if(c.getArticle().getNome().equalsIgnoreCase(nome)&&c.getArticle().getMarca().equalsIgnoreCase(marca)){
				if(prescrizione==null||prescrizione.equalsIgnoreCase("Neutro")){
					c.setPrescrizione(null);
				}
				try {
					c.setPrescrizione(new PrescriptionModel().doRetrieve(prescrizione));
				} catch (SQLException e) {
					c.setPrescrizione(null);
				}
			}
		}
	}
}
