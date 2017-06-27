package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.support.AdminBean;

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

		//In base a questo parametro viene deciso il da farsi
		String action = request.getParameter("action");

		if (action != null){
			if(admin!=null){
				if (action.equalsIgnoreCase("delete"))
					try {
						delete(request, response);
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
		}
		
		String toDo = request.getParameter("toDo");
		if(toDo!=null){
			if(toDo.equalsIgnoreCase("searchFromOtherPage")){
				request.setAttribute("daCercare", request.getParameter("daCercare"));
			}else{
				try{
					response.setContentType("application/json");
					response.setHeader("Cache-Control", "no-cache");
					String daCercare = request.getParameter("daCercare");
					if(toDo.equalsIgnoreCase("asyncGenericSearch")){
						if(daCercare==null||daCercare.equalsIgnoreCase("")){
							response.getWriter().write(new Gson().toJson(model.doRetrieveAll(null)));
						}else{
							response.getWriter().write(new Gson().toJson(model.doRetrieve(daCercare)));
						}
					} else if(toDo.equalsIgnoreCase("asyncSpecificSearch")){
						String tipo = request.getParameter("tipo");
						if(tipo.equalsIgnoreCase("O")){
							String marca = request.getParameter("marca");
							String prezzoMin = request.getParameter("prezzoMin");
							String prezzoMax = request.getParameter("prezzoMax");
							String sesso = request.getParameter("sesso");
							String colore = request.getParameter("colore");
							response.getWriter().write(new Gson().toJson(model.doRetrieveGlasses(daCercare, marca, prezzoMin, prezzoMax, sesso, colore, null)));
						} else if(tipo.equalsIgnoreCase("L")){
							String marca = request.getParameter("marca");
							String prezzoMin = request.getParameter("prezzoMin");
							String prezzoMax = request.getParameter("prezzoMax");
							String gradazione = request.getParameter("gradazione");
							String tipologia = request.getParameter("tipologia");
							String raggio = request.getParameter("raggio");
							String diametro = request.getParameter("diametro");
							String colore = request.getParameter("colore");
							response.getWriter().write(new Gson().toJson(model.doRetrieveContactLenses(daCercare, marca, prezzoMin, prezzoMax, gradazione, tipologia, raggio, diametro, colore, null)));
						}
					}
					return;
				} catch(Exception e){
					e.printStackTrace();
				}
			}
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/ArticleView.jsp");
		dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	private void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		if(nome != null && marca != null)
			model.doDelete(nome, marca);
	}



}
