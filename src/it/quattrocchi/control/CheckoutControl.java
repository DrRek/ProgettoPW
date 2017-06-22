package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.model.CheckoutModel;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.Cart;
import it.quattrocchi.support.CartArticle;
import it.quattrocchi.support.OrderBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/checkout")

public class CheckoutControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static CheckoutModel model = new CheckoutModel();
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
			if (action != null) {
				if (action.equalsIgnoreCase("checkout"))
					summaryCheckout(request,response);
				else if (action.equalsIgnoreCase("submit")){
					addOrder(request, response);
					updateCatalogo(request,response);
					done(request, response);
				}
			}
		}
	}

	private void addOrder(HttpServletRequest request, HttpServletResponse response){
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String carta = (String) request.getParameter("carta");

		if(cart!=null&&user!=null){
			//Crea il bean OrderBean
			String codice = UUID.randomUUID().toString();
			while(!CheckoutModel.isUniqueCod(codice)){
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
	
	private void updateCatalogo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		ArrayList<CartArticle> cArticle = cart.getProducts();
		ArticleBean bean = new ArticleBean();
		
		for(CartArticle c : cArticle){
			try{
			ArticleBean art = aModel.doRetrieveByKey(c.getArticle().getNome(), c.getArticle().getMarca());
			if(art.getTipo().equalsIgnoreCase("o")){
				art.setNumeroPezziDisponibili(art.getNumeroPezziDisponibili()-c.getQuantity());
			}
			aModel.doSave(art);
			} catch (Exception e){
				e.printStackTrace();
			}
		}
		
		for(int i = 0; i<cArticle.size(); i++) {
			try {
			bean = aModel.doRetrieveByKey(cArticle.get(i).getArticle().getNome(), cArticle.get(i).getArticle().getMarca());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			int num = bean.getDisponibilita() - cArticle.get(i).getQuantity();
			bean.setDisponibilita(num);		
			try {
				aModel.doSave(bean);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
