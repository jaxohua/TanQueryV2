package tanquery;


import java.sql.*;

public class DBConnect {
    Connection con;
	private Statement st;
	private ResultSet rs;
	
	public DBConnect(String server,int pto, String dbname, String user,String pass){
		try {
			String myDriver = "org.gjt.mm.mysql.Driver";
			String servidor="jdbc:mysql://" + server + ":" + pto + "/" + dbname;
			Class.forName(myDriver);
			try {
				con= DriverManager.getConnection(servidor,user,pass);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Error clase DBConect " + e);
		}
		
	}

	public Statement getSt() {
		return st;
	}

	public void setSt(Statement st) {
		this.st = st;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	

}
