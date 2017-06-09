package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.model.CreditCardModel;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.CreditCardBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/user")

public class UserControl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static ArticleModel model = new ArticleModel();
	static CreditCardModel ccModel = new CreditCardModel();

	public UserControl(){
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getParameter("action");
		if(action!=null){
			try{

				if (action.equalsIgnoreCase("insert"))
					insert(request,response);

				else if(action.equals("logout"))
					logout(request,response);
				
				else if(action.equalsIgnoreCase("addCard"))
					addCard(request,response);
			}
			catch(Exception e){
				e.printStackTrace();
			}
		}
		else{
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
			dispatcher.forward(request, response);
		}
	}

	private void insert(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException{

		String nome = request.getParameter("nome");
		String marca = request.getParameter("marca");
		String tipo = request.getParameter("tipo");
		int numeroPezziDisponibili = Integer.parseInt(request.getParameter("numeroPezziDisponibili"));
		float prezzo = Integer.parseInt(request.getParameter("prezzo"));
		float gradazione = Integer.parseInt(request.getParameter("gradazione"));

		ArticleBean bean = model.doRetrieveByKey(nome, marca);

		if(bean == null){
			bean = new ArticleBean();
			bean.setNome(nome);
			bean.setMarca(marca);
			bean.setTipo(tipo);
			bean.setNumeroPezziDisponibili(numeroPezziDisponibili);
			bean.setPrezzo(prezzo);
			bean.setGradazione(gradazione);

			model.doSave(bean);
		}
		else
		{
			bean.setTipo(tipo);
			bean.setNumeroPezziDisponibili(numeroPezziDisponibili);
			bean.setPrezzo(prezzo);
			bean.setGradazione(gradazione);

			model.doUpdate(bean); 
		}
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
		dispatcher.forward(request, response);
	}
	
	private void addCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		CreditCardBean ccBean = new CreditCardBean();
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		ccBean.setCliente(user);
		ccBean.setNumeroCC(request.getParameter("numcc"));
		ccBean.setIntestatario(request.getParameter("intestatario"));
		ccBean.setCircuito(request.getParameter("circuito"));
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed=null;
		try {
			parsed = format.parse(request.getParameter("scadenza"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date dataScadenza = new java.sql.Date(parsed.getTime());
		ccBean.setDataScadenza(dataScadenza);
		
		ccBean.setCvcCvv(request.getParameter("cvv"));
		
		ccModel.doSave(ccBean);
		
		user.setCards(ccModel.doRetrieveByCliente(user));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}
}
