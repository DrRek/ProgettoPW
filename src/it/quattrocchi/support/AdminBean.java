package it.quattrocchi.support;

import java.util.ArrayList;

public class AdminBean {
	
		public AdminBean(){
			setUser(null);
			setPassword(null);
		}

		public String getUser() {
			return user;
		}

		public void setUser(String user) {
			this.user = user;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}
		
		public void setOrders(ArrayList<OrderBean> allOrders) {
			this.allOrders = allOrders;
		}
		
		public ArrayList<OrderBean> getOrders() {
			return this.allOrders;
		}

		private String user,password;
		private ArrayList<OrderBean> allOrders;

	}
