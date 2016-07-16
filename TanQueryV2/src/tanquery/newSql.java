package tanquery;

import java.sql.SQLException;
import java.util.ArrayList;

public class newSql {

	public static boolean dropTemps(TanQuery frame) {//Método que elimina todas las tablas con el prefijo tmp

		String query="SELECT CONCAT('DROP TABLE `',t.table_schema,'`.`',t.table_name,'`;') "
				+ "AS stmt "
				+ "FROM information_schema.tables t "
				+ "WHERE t.table_schema = '"+frame.dataBase+"' AND t.table_name LIKE 'tmp\\_%' ESCAPE '\\\\'  ORDER BY t.table_name";
		String atributo="stmt";
		ArrayList<String> sentencia=new ArrayList<String>();
		System.out.println("Consulta de borrado:"+query);
		
		sentencia=Valores.getValores(frame.conex, query, atributo);
		int num_afect=0;
		for (String sen : sentencia) {
			try {
				frame.conex.setSt(frame.conex.con.createStatement());
				num_afect=frame.conex.getSt().executeUpdate(sen);
				if(num_afect>0)
					System.out.println(sen);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
		}
		
		
		return true;
		

	}
	
	public static boolean validaTemp(TanQuery frame,int id) {
		String temp="tmp_"+id;
		ArrayList<String> listaTablas=new ArrayList<String>();
		listaTablas = Valores.getValores(frame.conex, "show tables from "
				+ frame.dataBase, "Tables_in_" + frame.dataBase);
		for (String tabla : listaTablas) {
			if(tabla.equals(temp)){
				System.out.println("tabla temporal: "+temp+"encontrada");
			}
			
		}
		
		System.out.println("Obteniendo Tablas de " + frame.dataBase);
		return false;
		
	}

	public static boolean createTemp(TanQuery frame,int id) {
		
		
		return false;
				

	}
	

}
