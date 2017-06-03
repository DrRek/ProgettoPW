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

@WebServlet("/article")

public class ArticleControl extends HttpServlet {

	private static final long serialVersionUID = 1L;

	static ArticleModel model = new ArticleModel();

	public ArticleControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}

		String action = request.getParameter("action");

		if (action != null) {

			if (action.equalsIgnoreCase("checkout"))
				checkout(request,response);

			else if (action.equalsIgnoreCase("insert"))
				insert(request, response);

			else if (action.equalsIgnoreCase("delete"))
				delete(request, response);

			else if (action.equalsIgnoreCase("addCart"))
				cart = addCart(request, response, cart);

			else if (action.equalsIgnoreCase("delCart"))
				cart = delCart(request, response, cart);

		}

		String sort = request.getParameter("sort");
		
		try {
			if (sort == null)
				request.setAttribute("articoli", model.doRetrieveAll("Nome"));
			else
				request.setAttribute("articoli", model.doRetrieveAll(sort));
		
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		request.getSession().setAttribute("cart", cart);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	private void insert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		String tipo = request.getParameter("tipo");
		int numeroPezziDisponibili = Integer.parseInt(request.getParameter("numeroPezziDisponibili"));
		float prezzo = Integer.parseInt(request.getParameter("prezzo"));
		float gradazione = Integer.parseInt(request.getParameter("gradazione"));
		ArticleBean bean = null;

		try {
			
			bean = model.doRetrieveByKey(nome, marca);
	
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		if (bean == null) {
			bean = new ArticleBean();
			bean.setNome(nome);
			bean.setMarca(marca);
			bean.setTipo(tipo);
			bean.setNumeroPezziDisponibili(numeroPezziDisponibili);
			bean.setPrezzo(prezzo);
			bean.setGradazione(gradazione);
			
			try {
			
				model.doSave(bean);
			
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
			
		} else {
			bean.setTipo(tipo);
			bean.setNumeroPezziDisponibili(numeroPezziDisponibili);
			bean.setPrezzo(prezzo);
			bean.setGradazione(gradazione);
			
			try {
			
				model.doUpdate(bean);
			
			} catch (SQLException e) {
				System.out.println("Error:" + e.getMessage());
			}
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		
		try {
		
			model.doDelete(nome, marca);
		
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
	}

	private Cart addCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		
		try {
			
			cart.addProduct(model.doRetrieveByKey(nome, marca));		
		
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		return cart;
	}

	private Cart delCart(HttpServletRequest request, HttpServletResponse response, Cart cart) throws ServletException, IOException {
		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		
		try {
			
			cart.deleteProduct(model.doRetrieveByKey(nome, marca));
		
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		return cart;
	}
	
	private void checkout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/CartCheckout.jsp");
		dispatcher.forward(request, response);
	}
}
