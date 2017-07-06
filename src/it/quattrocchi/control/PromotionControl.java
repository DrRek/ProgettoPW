package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.model.PromotionModel;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.PromotionBean;

@WebServlet("/promotion")

public class PromotionControl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static PromotionModel model = new PromotionModel();
	static ArticleModel aModel = new ArticleModel();
	
	public PromotionControl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String action = request.getParameter("action");
		if(action!=null&&!action.equalsIgnoreCase("")){
			if(action.equalsIgnoreCase("addPromotion")){
		        try {
					String nome = request.getParameter("nome");
					String desc = request.getParameter("descrizione");
					Double sconto = Double.parseDouble(request.getParameter("sconto").replaceAll(",", "."));
					String tipo = request.getParameter("tipo");
			        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			        Date parsed = format.parse(request.getParameter("inizio"));
			        java.sql.Date inizio = new java.sql.Date(parsed.getTime());
						parsed = format.parse(request.getParameter("fine"));
			        java.sql.Date fine = new java.sql.Date(parsed.getTime());
			        boolean cumulabile = Boolean.parseBoolean(request.getParameter("cumulabile"));
			        
			        PromotionBean bean = new PromotionBean();
			        bean.setNome(nome);
			        bean.setDescrizione(desc);
			        bean.setSconto(sconto);
			        bean.setTipo(tipo);
			        bean.setDataInizio(inizio);
			        bean.setDataFine(fine);
			        bean.setCumulabile(cumulabile);
			        
			        model.doSave(bean);
			        
			        response.getWriter().write(new Gson().toJson(model.doRetriveAllValid()));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else if(action.equalsIgnoreCase("addArticleToPromotion")){
				try{
					String promozione = request.getParameter("promozione");
					String nome = request.getParameter("nome");
					String marca = request.getParameter("marca");
					PromotionBean toUpdate = model.doRetriveByKey(promozione);
					if(aModel.isArticle(nome,marca)){
						ArticleBean toadd = new ArticleBean();
						toadd.setNome(nome);
						toadd.setMarca(marca);
						toUpdate.addToValidi(toadd);
						model.doSave(toUpdate);
						response.getWriter().write(new Gson().toJson(toUpdate.getValidi()));
					}
				}catch(Exception e){
					e.printStackTrace();
				}
			}
			return;
		}

		String name = request.getParameter("nome");
		if(name!=null&&!name.equalsIgnoreCase("")){
			try {
				request.setAttribute("promozione", model.doRetriveByKey(name));
			} catch (SQLException e) {
				e.printStackTrace();
			}
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/PromotionView.jsp");
			dispatcher.forward(request, response);
		}
	}
}
