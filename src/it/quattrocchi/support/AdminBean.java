package it.quattrocchi.support;

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

		private String user,password;

	}
