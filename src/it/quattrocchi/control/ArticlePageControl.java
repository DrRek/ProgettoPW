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
import it.quattrocchi.support.AdminBean;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.Cart;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.GlassesBean;

@WebServlet("/articlePage")

public class ArticlePageControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static ArticleModel model = new ArticleModel();

	public ArticlePageControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if(action != null){
			if(action.equals("addCart"))
				addCart(request, response);
			if(action.equals("updateGlass"))
				updateGlass(request, response);
			if(action.equals("updateContact"))
				updateContact(request, response);
			return;
		}
		else{
			String nome = request.getParameter("nome");
			String marca = request.getParameter("marca");
			boolean isGlasses;

			try {
				isGlasses = model.isGlasses(nome,marca);
				if(isGlasses)
					request.setAttribute("occhiali", model.doRetrieveGlasses(nome, marca));
				else
					request.setAttribute("lentine", model.doRetrieveAllContactLenses(nome, marca));
			} catch (SQLException e) {

				e.printStackTrace();
			}

			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticlePageView.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void updateContact(HttpServletRequest request, HttpServletResponse response) {
		String nome = (String) request.getParameter("nome");
		String marca = (String) request.getParameter("marca");
		Double gradazione = Double.parseDouble((String)request.getParameter("gradazione"));
		try {
			ArticleBean toUpdate =  model.doRetrieveContactLenses(nome, marca, gradazione);
			toUpdate.setDisponibilita(Integer.parseInt((String)request.getParameter("quantita")));
			System.out.println(toUpdate.getNome()+toUpdate.getDisponibilita());
			model.doSave(toUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void updateGlass(HttpServletRequest request, HttpServletResponse response) {
		String nome = (String)request.getParameter("nome");
		String marca = (String) request.getParameter("marca");
		try {
			ArticleBean toUpdate =  model.doRetrieveGlasses(nome, marca);
			toUpdate.setDisponibilita(Integer.parseInt((String)request.getParameter("quantita")));
			model.doSave(toUpdate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addCart(HttpServletRequest request, HttpServletResponse response) {
		Cart cart= (Cart) request.getSession().getAttribute("cart");
		AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
		if (cart == null && admin == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		if(admin == null)
		{
			String nome = request.getParameter("nome");
			String marca = request.getParameter("marca");
			String gradazione = request.getParameter("gradazione");
			try {
				if(gradazione == null){
					cart.addProduct(model.doRetrieveGlasses(nome, marca), null);
				}
				else{
					double g = Double.parseDouble(gradazione);
					cart.addProduct(model.doRetrieveContactLenses(nome, marca, g), g);	
				}
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
			request.getSession().setAttribute("cart", cart);		
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
