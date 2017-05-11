package it.quattrocchi;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/article")

public class ArticleControl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static ArticleModel model = new ArticleModel();
	
	public ArticleControl(){
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(cart == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		String action = request.getParameter("action");
		
		try {
			request.setAttribute("articoli", null);//model.doRetrieveAll("Nome"));
			
			if (action != null) {
				if (action.equalsIgnoreCase("insert")) {
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					String tipo = request.getParameter("tipo");
					int numeroPezziDisponibili = Integer.parseInt(request.getParameter("numeroPezziDisponibili"));
					float prezzo = Integer.parseInt(request.getParameter("prezzo"));
					float gradazione = Integer.parseInt(request.getParameter("gradazione"));

					ArticleBean bean = new ArticleBean();
					bean.setNome(nome);
					bean.setMarca(marca);
					bean.setTipo(tipo);
					bean.setNumeroPezziDisponibili(numeroPezziDisponibili);
					bean.setPrezzo(prezzo);
					bean.setGradazione(gradazione);
					
					model.doSave(bean);
				} else if(action.equalsIgnoreCase("delete")){
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					System.out.println("prova");
					model.doDelete(nome, marca);
				}
			}
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}

		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ProductView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
