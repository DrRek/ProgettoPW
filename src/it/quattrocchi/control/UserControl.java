package it.quattrocchi.control;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import it.quattrocchi.model.ArticleModel;
import it.quattrocchi.model.CreditCardModel;
import it.quattrocchi.model.OrderModel;
import it.quattrocchi.model.PrescriptionModel;
import it.quattrocchi.model.UserModel;
import it.quattrocchi.support.CreditCardBean;

import it.quattrocchi.support.OrderBean;

import it.quattrocchi.support.PrescriptionBean;
import it.quattrocchi.support.UserBean;

@WebServlet("/user")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB
maxFileSize=1024*1024*10,      // 10MB
maxRequestSize=1024*1024*50)   // 50MB
public class UserControl extends HttpServlet{

	private static final long serialVersionUID = 1L;

	static ArticleModel model = new ArticleModel();
	static CreditCardModel ccModel = new CreditCardModel();
	static PrescriptionModel pModel = new PrescriptionModel();
	static OrderModel oModel = new OrderModel();
	static UserModel uModel = new UserModel();

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

				if(action.equals("logout"))
					logout(request,response);
				
				else if(action.equalsIgnoreCase("addCard"))
					addCard(request,response);
				
				else if(action.equalsIgnoreCase("delCard"))
					delCard(request,response);
				
				else if(action.equalsIgnoreCase("addPrescription"))
					addPres(request,response);
				
				else if(action.equalsIgnoreCase("delPres"))
					delPres(request,response);
				
				else if(action.equalsIgnoreCase("viewOldCheckout"))
					viewOldCheckout(request,response);
				
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

	private void viewOldCheckout(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		String codice = request.getParameter("codice");
		OrderBean order = oModel.doRetrieveByKey(codice);
		request.setAttribute("order", order);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/OrderView.jsp");
		dispatcher.forward(request, response);
	}


	private void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.getSession().invalidate();
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/article");
		dispatcher.forward(request, response);
	}
	
	private void addCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException{
		
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String numeroCC = request.getParameter("numcc");
		String intestatario = request.getParameter("intestatario");
		String circuito = request.getParameter("circuito");
		String stato = "attiva";
		String cvcCvv = request.getParameter("cvv");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date parsed=null;
		try {
			parsed = format.parse(request.getParameter("scadenza"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		java.sql.Date dataScadenza = new java.sql.Date(parsed.getTime());
		
		CreditCardBean bean = new CreditCardBean();
		bean.setNumeroCC(numeroCC);
		bean.setCircuito(circuito);
		bean.setCliente(user);
		bean.setCvcCvv(cvcCvv);
		bean.setDataScadenza(dataScadenza);
		bean.setIntestatario(intestatario);
		bean.setStato(stato);
		
		if(ccModel.doRetrieveByKey(numeroCC) == null){
			ccModel.doSave(bean);
		}
		
		else {
			ccModel.doUpdate(bean);
		}
		ArrayList<CreditCardBean> cc = user.getCards();
		cc.add(bean);
		user.setCards(cc);
		response.getWriter().write(new Gson().toJson(ccModel.doRetrieveByCliente(user)));
	}
	
	private void delCard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		String numeroCC = request.getParameter("numeroCC");
		UserBean user = (UserBean) request.getSession().getAttribute("user");

		try {
			ccModel.doDelete(numeroCC);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		user.setCards(ccModel.doRetrieveByCliente(user));
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}
	
	private void addPres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		PrescriptionBean pres = new PrescriptionBean();
		String codice = UUID.randomUUID().toString();
		while(!PrescriptionModel.isUniqueCod(codice)){
			codice = UUID.randomUUID().toString();
		}
		pres.setCodice(codice);
		pres.setNome(request.getParameter("nomeP"));
		pres.setSferaSX(Float.parseFloat(request.getParameter("sferaSX")));
		pres.setCilindroSX(Float.parseFloat(request.getParameter("cilindroSX")));
		pres.setAsseSX(Float.parseFloat(request.getParameter("asseSX")));
		pres.setSferaDX(Float.parseFloat(request.getParameter("sferaDX")));
		pres.setCilindroDX(Float.parseFloat(request.getParameter("cilindroDX")));
		pres.setAsseDX(Float.parseFloat(request.getParameter("asseDX")));
		pres.setAddVicinanza(Float.parseFloat(request.getParameter("addVicinanza")));
		pres.setPrismaOrizSX(Float.parseFloat(request.getParameter("prismaOrizSX")));
		pres.setPrismaOrizSXBD(request.getParameter("prismaOrizSXBD"));
		pres.setPrismaOrizDX(Float.parseFloat(request.getParameter("prismaOrizDX")));
		pres.setPrismaOrizDXBD(request.getParameter("prismaOrizDXBD"));
		pres.setPrismaVertSX(Float.parseFloat(request.getParameter("prismaVertSX")));
		pres.setPrismaVertSXBD(request.getParameter("prismaVertSXBD"));
		pres.setPrismaVertDX(Float.parseFloat(request.getParameter("prismaVertDX")));
		pres.setPrismaVertDXBD(request.getParameter("prismaVertDXBD"));
		pres.setPupillarDistanceSX(Float.parseFloat(request.getParameter("pdSX")));
		pres.setPupillarDistanceDX(Float.parseFloat(request.getParameter("pdDX")));
	
		pModel.doSave(pres, user);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}
	
	private void delPres(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		String codice = request.getParameter("codice");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		
		pModel.doDelete(codice, user);
		
		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}
}
