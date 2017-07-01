package it.quattrocchi.control;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.UUID;

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
import it.quattrocchi.model.CreditCardModel;
import it.quattrocchi.model.PrescriptionModel;
import it.quattrocchi.support.ArticleBean;
import it.quattrocchi.support.ContactLensesBean;
import it.quattrocchi.support.CreditCardBean;
import it.quattrocchi.support.GlassesBean;
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
				
				else if(action.equalsIgnoreCase("delCard"))
					delCard(request,response);
				
				else if(action.equalsIgnoreCase("addPrescription"))
					addPres(request,response);
				
				else if(action.equalsIgnoreCase("delPres"))
					delPres(request,response);
				
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
		String tipo = request.getParameter("tipo");
		ArticleBean toAdd = null;
		try{
			if(tipo.equalsIgnoreCase("O")){
				toAdd = new GlassesBean();

				toAdd.setTipo(tipo);
				toAdd.setNome(request.getParameter("nome"));
				toAdd.setMarca(request.getParameter("marca"));
				toAdd.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
				((GlassesBean)toAdd).setSesso(request.getParameter("sesso"));
				((GlassesBean)toAdd).setDescrizione(request.getParameter("descrizione"));
				toAdd.setDisponibilita(Integer.parseInt(request.getParameter("numeroPezziDisponibili")));

				String appPath = request.getServletContext().getContextPath();
				// constructs path of the directory to save uploaded file
				String savePath = appPath + File.separator + SAVE_DIR;
				
		     // creates the save directory if it does not exists
		        File fileSaveDir = new File(savePath);
		        if (!fileSaveDir.exists()) {
		            fileSaveDir.mkdir();
		        }
		        
		        Part part = request.getPart("img1");
		        String fileName = extractFileName(part);
		     // refines the fileName in case it is an absolute path
				fileName = new File(fileName).getName();
	            part.write(savePath+ File.separator + fileName);
				
		        System.out.println(toAdd.getImg1());
				//can also write the photo to local storage
			}
			else if(tipo.equalsIgnoreCase("L")){
				toAdd = new ContactLensesBean();

				toAdd.setTipo(tipo);
				toAdd.setNome(request.getParameter("nome"));
				toAdd.setMarca(request.getParameter("marca"));
				toAdd.setPrezzo(Double.parseDouble(request.getParameter("prezzo")));
				toAdd.setDisponibilita(Integer.parseInt(request.getParameter("numeroPezziDisponibili")));
				((ContactLensesBean)toAdd).setGradazione(Double.parseDouble(request.getParameter("gradazione")));
				((ContactLensesBean)toAdd).setRaggio(Double.parseDouble(request.getParameter("raggio")));
				((ContactLensesBean)toAdd).setDiametro(Double.parseDouble(request.getParameter("diametro")));
				((ContactLensesBean)toAdd).setColore(request.getParameter("colore"));
				((ContactLensesBean)toAdd).setTipologia(request.getParameter("tipologia"));
				((ContactLensesBean)toAdd).setNumeroPezziNelPacco(Integer.parseInt(request.getParameter("numeroPezziNelPacco")));
			}
			model.doSave(toAdd);
		} catch (SQLException e){
			e.printStackTrace();
		}

		RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/jsp/UserView.jsp");
		dispatcher.forward(request, response);
	}

	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length()-1);
			}
		}
		return "";
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
		System.out.println("test");
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
