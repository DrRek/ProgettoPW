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
		
		AdminBean admin = (AdminBean) request.getSession().getAttribute("admin");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
	
		//Crea un carrello vuoto se non esiste e se non si e' admin
		if (cart == null && admin == null) {
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		//In base a questo parametro viene deciso il da farsi
		String action = request.getParameter("action");
		
		//TO DO: mi sa che questo non ha più senso di esistere, poi si vede
		if (action != null){
			if (action.equalsIgnoreCase("description")){
				descript(request, response);
				return;
			}
			if(admin==null){
				if (action.equalsIgnoreCase("addCart"))
					cart = addCart(request, response, cart);
				else if (action.equalsIgnoreCase("delCart"))
					cart = delCart(request, response, cart);
				else if (action.equalsIgnoreCase("delete"))
					delete(request, response);
			}
		} else { //Alla fine per questa pagina conterà solo questo else, controllare che altri pezzi di codice siano usati
			String search = request.getParameter("daCercare");
			String tipo = request.getParameter("tipo");
			String marcaO = request.getParameter("marcaO");
			String marca;
			if(marcaO==null||marcaO.equalsIgnoreCase(""))
				marca = request.getParameter("marcaL");
			else
				marca = marcaO;
			String stringda = request.getParameter("prezzoMin");
			double da;
			if(stringda==null)da=0;
			else da=Double.parseDouble(stringda);
			String stringa = request.getParameter("prezzoMax");
			double a;
			if(stringa==null)a=0;
			else{
				try{
					a=Double.parseDouble(stringda);
				} catch (Exception e){
					a=0;
				}
			}
			String sort = request.getParameter("sort");
			try {
				request.setAttribute("articoli", model.doRetrieve(search, tipo, marca, da, a, sort));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		if(admin == null)
			request.getSession().setAttribute("cart", cart);

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
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
	
	private void descript(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleDescriptionView.jsp");
		dispatcher.forward(request, response);
	}
	
	/*private void searchByType(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type = request.getParameter("type");
		
		try {
			
			request.setAttribute("articoli",model.doRetrieveByType(type));
			
		} catch (SQLException e) {
			System.out.println("Error:" + e.getMessage());
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleView.jsp");
		dispatcher.forward(request, response);
	}*/
}
