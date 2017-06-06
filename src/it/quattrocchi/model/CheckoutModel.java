package it.quattrocchi.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

import it.quattrocchi.support.OrderBean;


public class CheckoutModel {
private static final String TABLE_NAME = "quattrocchidb.ordine";
	
	public void doSave(OrderBean ordine) throws SQLException{ //bisogna salvare il pataterno, forse bisogna creare dei bean
	}
	//Questo metodo dovrà essere usato per verificare che i codici scelti automaticamente non siano già presenti nel database
	public static boolean isUniqueCod(String codice) {
		// TODO Auto-generated method stub
		return false;
	}

}
