package it.quattrocchi.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.google.gson.Gson;
import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.support.AdminBean;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.GlassesBean;

@WebServlet("/article")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class ArticleControl extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SAVE_DIR = "C://catalogoPW//";

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
		String sort = request.getParameter("sort");
		String toDo = request.getParameter("toDo");
		if(toDo!=null){
			if(toDo.equalsIgnoreCase("searchFromOtherPage")){
				request.setAttribute("daCercare", request.getParameter("daCercare"));
			}
			else if(toDo.equalsIgnoreCase("searchOcchiale")){
				request.setAttribute("cercaPerTipo", "O");
			}
			else if(toDo.equalsIgnoreCase("searchLente")){
				request.setAttribute("cercaPerTipo", "L");
			}
			else if(toDo.equalsIgnoreCase("addProduct")){
				insert(request, response);
				return;
			}
			else{
				try{
					response.setContentType("application/json");
					response.setHeader("Cache-Control", "no-cache");
					String daCercare = request.getParameter("daCercare");
					
					if(toDo.equalsIgnoreCase("asyncGenericSearch")){
						
						if(daCercare==null||daCercare.equalsIgnoreCase(""))
							response.getWriter().write(new Gson().toJson(model.doRetrieveAll(sort)));
						else
							response.getWriter().write(new Gson().toJson(model.doRetrieve(daCercare,sort)));
						
					} 
					
					else if(toDo.equalsIgnoreCase("asyncSpecificSearch")){
						
						String tipo = request.getParameter("tipo");
						
						if(tipo.equalsIgnoreCase("O")){
							String marca = request.getParameter("marca");
							String prezzoMin = request.getParameter("prezzoMin");
							String prezzoMax = request.getParameter("prezzoMax");
							String sesso = request.getParameter("sesso");
							String colore = request.getParameter("colore");
							response.getWriter().write(new Gson().toJson(model.doRetrieveGlasses(daCercare, marca, prezzoMin, prezzoMax, sesso, colore, sort)));
						} 
						else if(tipo.equalsIgnoreCase("L")){
							String marca = request.getParameter("marca");
							String prezzoMin = request.getParameter("prezzoMin");
							String prezzoMax = request.getParameter("prezzoMax");
							String gradazione = request.getParameter("gradazione");
							String tipologia = request.getParameter("tipologia");
							String raggio = request.getParameter("raggio");
							String diametro = request.getParameter("diametro");
							String colore = request.getParameter("colore");
							response.getWriter().write(new Gson().toJson(model.doRetrieveContactLenses(daCercare, marca, prezzoMin, prezzoMax, gradazione, tipologia, raggio, diametro, colore, sort)));
						}
					}
					
					return;
				} 
				catch(Exception e){
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

	private void insert(HttpServletRequest request, HttpServletResponse response){
		response.setContentType("text/plain");
		ArticleBean toAdd = null;
		try{
			String nome = request.getParameter("nome");
			String marca = request.getParameter("marca");
			String tipo = request.getParameter("tipo");
			Double prezzo = Double.parseDouble(request.getParameter("prezzo"));
	        Part img1 = request.getPart("img1");
	        String fileName = new File(nome+"_"+marca+"_1."+img1.getSubmittedFileName().substring(img1.getSubmittedFileName().lastIndexOf('.')+1)).getName();
	        img1.write(SAVE_DIR + fileName);
	        if(tipo.equalsIgnoreCase("O")){
				toAdd = new GlassesBean();
	    		String descrizione = request.getParameter("descrizione");
	    		String sesso = request.getParameter("sesso");
	    		int quantita = Integer.parseInt(request.getParameter("quantita"));
	            Part img2 = request.getPart("img2");
	            Part img3 = request.getPart("img3");
				toAdd.setNome(nome);
				toAdd.setMarca(marca);
				toAdd.setTipo(tipo);
				toAdd.setPrezzo(prezzo);
				toAdd.setImg1(fileName);
	            ((GlassesBean)toAdd).setDescrizione(descrizione);
	            ((GlassesBean)toAdd).setSesso(sesso);
	            ((GlassesBean)toAdd).setDisponibilita(quantita);
	            fileName = new File(nome+"_"+marca+"_2."+img2.getSubmittedFileName().substring(img2.getSubmittedFileName().lastIndexOf('.')+1)).getName();
	            img2.write(SAVE_DIR + fileName);
	            ((GlassesBean)toAdd).setImg2(fileName);
	            fileName = new File(nome+"_"+marca+"_3."+img3.getSubmittedFileName().substring(img3.getSubmittedFileName().lastIndexOf('.')+1)).getName();
	            img3.write(SAVE_DIR + fileName);
	            ((GlassesBean)toAdd).setImg3(fileName);
	        }else{
				toAdd = new ContactLensesBean();
	    		Double gradazione = Double.parseDouble(request.getParameter("gradazione"));
	    		String tipologia = request.getParameter("tipologia");
	    		Double raggio = Double.parseDouble(request.getParameter("raggio"));
	    		Double diametro = Double.parseDouble(request.getParameter("diametro"));
	    		String colore = request.getParameter("colore");
	    		int quantita = Integer.parseInt(request.getParameter("quantita"));
	    		int pezziPerPacco = Integer.parseInt(request.getParameter("pezziPerPacco"));
				toAdd.setNome(nome);
				toAdd.setMarca(marca);
				toAdd.setTipo(tipo);
				toAdd.setPrezzo(prezzo);
				toAdd.setImg1(fileName);
	    		((ContactLensesBean)toAdd).setGradazione(gradazione);
	    		((ContactLensesBean)toAdd).setTipologia(tipologia);
	    		((ContactLensesBean)toAdd).setRaggio(raggio);
	    		((ContactLensesBean)toAdd).setDiametro(diametro);
	    		((ContactLensesBean)toAdd).setColore(colore);
	            ((ContactLensesBean)toAdd).setDisponibilita(quantita);
	            ((ContactLensesBean)toAdd).setNumeroPezziNelPacco(pezziPerPacco);
	        }
		} catch (Exception e){
			e.printStackTrace();
		}
        try {
			model.doSave(toAdd);
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
