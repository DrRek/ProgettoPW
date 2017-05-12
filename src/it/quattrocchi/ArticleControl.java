package it.quattrocchi;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
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
			
			if (action != null) {
				//aggiunta del prodotto nel catalogo
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
					
				} 
				
				//cancellazione del prodotto nel catalogo
				else if(action.equalsIgnoreCase("delete")){
					
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					
					model.doDelete(nome, marca);
					
				} 
				
				//aggiunta prodotto al carrello
				else if(action.equalsIgnoreCase("addCart")){
					
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					
					cart.addProduct(model.doRetrieveByKey(nome, marca));
					
				} 
				
				//rimozione prodotto dal carrello
				else if(action.equalsIgnoreCase("delCart")){
					
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					
					cart.deleteProduct(model.doRetrieveByKey(nome, marca));
				}
			}
			
			String sort = request.getParameter("sort");
			
			//se non è stato indicato nessun ordinamento, default: by nome
			if(sort== null)
				request.setAttribute("articoli", model.doRetrieveAll("Nome"));
			else 
				request.setAttribute("articoli", model.doRetrieveAll(sort));
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
		//aggiornamento dell'attributo cart della sessione
		request.getSession().setAttribute("cart", cart);
		request.setAttribute("cart", cart);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/ArticleView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
