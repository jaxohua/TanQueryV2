package tanquery;

//import java.sql.ResultSet;
//import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.HashMap;

//import com.mysql.jdbc.ResultSetMetaData;

import TUIO.TuioObject;

public class Consulta {

	int id;
	int posX;
	int posY;
	String tipo;
	String simbolo;
	public String getSimbolo() {
		return simbolo;
	}

	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	String valorDisp;
	public String getValorDisp() {
		return valorDisp;
	}

	public void setValorDisp(String valorDisp) {
		this.valorDisp = valorDisp;
	}

	public String getValorSql() {
		return valorSql;
	}

	public void setValorSql(String valorSql) {
		this.valorSql = valorSql;
	}

	String valorSql;
	long idSesion;
	

	public Consulta(int id, int posX, int posY, String tipo,String simbolo, String valorDisp,
			String valorSql, long idSesion) {
		super();
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.tipo = tipo;
		this.simbolo=simbolo;
		this.valorDisp = valorDisp;
		this.valorSql = valorSql;
		this.idSesion = idSesion;
	}

	public Consulta() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPosX() {
		return posX;
	}

	public void setPosX(int posX) {
		this.posX = posX;
	}

	public int getPosY() {
		return posY;
	}

	public void setPosY(int posY) {
		this.posY = posY;
	}

	public static boolean addConsulta(TanQuery frame, int id) {
		int posX = getTokenX(frame, id);
		int posY = getTokenY(frame, id);
		Token token = new Token();
		token = Valores.getToken(id, frame.tokens);

		if (isInConsulta(frame.consulta, id)) {
			if (borraDeConsulta(frame.consulta, id)) {
				// int id, int posX, int posY, String tipo, String
				// valorDisp,String valorSql, long idSesion
				if (frame.consulta.add(new Consulta(id, posX, posY, token
						.getTipo(),token.getValorDisp(),token.getValorSql(), token.getValorSql(),
						token.getIdSesion()))) {
					// System.out.println("Valor agregado a Consulta:" + id);
					return true;
				}
			}
		} else if (frame.consulta.add(new Consulta(id, posX, posY, token
				.getTipo(), token.getValorDisp(),token.getValorSql(), token.getValorSql(), token
				.getIdSesion()))) {
			// System.out.println("Valor agregado a Consulta:" + id);
			return true;

		}
		return false;
	}

	public static void listaContenido(ArrayList<Consulta> lista) {

		for (Consulta object : lista) {
			System.out.print(object.getId() + ", ");
		}

	}

	public static boolean isInConsulta(ArrayList<Consulta> consulta, int id) {
		for (Consulta consulta2 : consulta) {
			if (consulta2.getId() == id) {
				return true;
			}

		}
		return false;

	}

	public static boolean borraDeConsulta(ArrayList<Consulta> consulta, int id) {
		for (Consulta consulta2 : consulta) {
			if (consulta2.getId() == id) {
				consulta.remove(consulta2);
				return true;
			}

		}
		return false;
	}

	// Función que devuelve una lista ordenada por el atributo Y
	public static ArrayList<Consulta> sortConsultaY(ArrayList<Consulta> consulta) {

		ArrayList<Consulta> miConsulta = consulta;
		Collections.sort(miConsulta, new Comparator<Consulta>() {
			@Override
			public int compare(Consulta c1, Consulta c2) {
				return new Integer(c1.getPosY()).compareTo(new Integer(c2
						.getPosY()));
			}
		});
		return miConsulta;
	}

	// Función que devuelve una lista ordenada por el atributo X

	public static ArrayList<Consulta> sortConsultaX(ArrayList<Consulta> consulta) {

		ArrayList<Consulta> miConsulta = consulta;
		Collections.sort(miConsulta, new Comparator<Consulta>() {
			@Override
			public int compare(Consulta c1, Consulta c2) {
				return new Integer(c1.getPosX()).compareTo(new Integer(c2
						.getPosX()));
			}
		});
		return miConsulta;
	}

	// Función que indica la ubicación del objeto "B" con respecto a "A"
	public static String izqDer(int Ax, int Bx) {
		String resp = "";

		if (Bx > Ax)
			resp = "derecha";
		if (Bx < Ax)
			resp = "izquierda";
		if (Ax == Bx)
			resp = "igual";
		return resp;
	}

	// Método que obtiene el id del padre del árbol
	public static Consulta getPadre(TanQuery frame) {
		Consulta padre = new Consulta();
		ArrayList<Consulta> miConsulta = new ArrayList<Consulta>();
		miConsulta = sortConsultaY(frame.consulta);
		padre.id = miConsulta.get(0).getId();
		padre.valorDisp = miConsulta.get(0).valorDisp;
		padre.valorSql = miConsulta.get(0).valorSql;
		padre.tipo = miConsulta.get(0).tipo;
		// ax
		return padre;
	}

	public static int getTokenX(TanQuery frame, int id) {
		for (TuioObject objeto : frame.tuioObjectList) {
			if (objeto.getSymbolID() == id) {
				return objeto.getScreenX(frame.width);
			}
		}
		return -1;
	}

	public static int getTokenY(TanQuery frame, int id) {
		for (TuioObject objeto : frame.tuioObjectList) {
			if (objeto.getSymbolID() == id) {
				return objeto.getScreenY(frame.height);
			}
		}
		return -1;
	}

	public static int abs(int numero) {
		return numero > 0 ? numero : -numero;
	}

	public static Nodo construirNodo(Consulta padre) {
		Nodo miNodo = new Nodo();
		miNodo.hijoDer = null;
		miNodo.hijoIzq = null;
		miNodo.id = padre.id;
		miNodo.valor = padre.valorSql;//padre.valorDisp;
		miNodo.valorSql = padre.valorSql;
		miNodo.simbolo=padre.simbolo;
		miNodo.sqlStm="";
		return miNodo;
	}

	public static void gestionaConsulta(TanQuery frame) {
		//String querySql = "";
		//querySql = frame.querySQL;
		ejecutaQuery(frame, frame.querySQL);
	}

	public static void crearArbol(TanQuery frame) {
		Consulta top = Consulta.getPadre(frame);
		Nodo newRoot = new Nodo();
		Nodo temporal = new Nodo();
		Nodo nodoEncontrado = new Nodo();
		Nodo nodoPadre = new Nodo();
		ArrayList<Consulta> ordenada = new ArrayList<Consulta>();
		ordenada = sortConsultaY(frame.consulta);

		newRoot = construirNodo(top);

		int xT1;
		int xT2;
		int yT1;
		int yT2;
		float distY = frame.distY;

		String ubicacion = "";
		System.out.println();
		System.out.println("ordenada:");
		listaContenido(ordenada);
		System.out.println();
		System.out.println("Frame.Consulta:");
		listaContenido(frame.consulta);

		if (frame.arbolConsul.getRaiz() == null) // si el arbol esta vacio
			frame.arbolConsul.setRaiz(newRoot);

		// si el arbol contiene por lo menos un elemento
		else if (frame.arbolConsul.getRaiz() != null) { 

			// Si el valor de la nueva raiz es diferente al que tiene la raíz
			// actual y es de tipo operador, se realiza el cambio de raiz
			
			  if (newRoot.valor != frame.arbolConsul.getRaiz().valor &&
			  Valores.whatIs(newRoot.id) == "operator") {
			 
			//if (newRoot.valor != frame.arbolConsul.getRaiz().valor) {

				temporal = frame.arbolConsul.getRaiz(); // copiar raiz actual a nodo temporal 
				frame.arbolConsul.setRaiz(newRoot); // el nuevo nodo es insertado en la raíz del arbol

				if (isInConsulta(ordenada, temporal.id) && temporal.id!=newRoot.id) {
					ubicacion = ubicacion(frame, frame.arbolConsul.getRaiz().id, temporal.id);
					System.out.println("Nuevo Valor de raíz=>"
							+ frame.arbolConsul.getRaiz().valor);

					if (ubicacion == "izquierda") {// Si el nodo temporal está a la izquierda, se fija como hijoIzquierdo de la
													// nueva raiz
						frame.arbolConsul.getRaiz().hijoIzq = temporal;
						temporal.padre = frame.arbolConsul.getRaiz();
					}

					if (ubicacion == "derecha") {
						// Si el nodo temporal está a la derecha, se fija como hijoDerecho de la nueva raiz
						frame.arbolConsul.getRaiz().hijoDer = temporal;
						temporal.padre = frame.arbolConsul.getRaiz();
					}

				} else {// si no se encuentra la antigua raiz en consulta ***AX
						// puede presentar error de que no se detecte el
						// marcador
					boolean tieneHijoDer = temporal.hijoDer != null ? true : false;
					boolean tieneHijoIzq = temporal.hijoIzq != null ? true : false;
					boolean tieneDos = tieneHijoDer && tieneHijoIzq;

					if (tieneDos && ArbolConsulta.isBinario(frame.arbolConsul.getRaiz().valor)) {
						if(frame.arbolConsul.getRaiz().id!=temporal.hijoIzq.id){
							frame.arbolConsul.getRaiz().hijoIzq = temporal.hijoIzq;
							temporal.hijoIzq.padre = frame.arbolConsul
								.getRaiz();
						}

						if(frame.arbolConsul.getRaiz().id!=temporal.hijoDer.id){
							frame.arbolConsul.getRaiz().hijoIzq = temporal.hijoDer;
							temporal.hijoDer.padre = frame.arbolConsul
								.getRaiz();
						}
					}

					else if (tieneHijoIzq || tieneHijoDer) {

						if (tieneHijoIzq && !tieneHijoDer) {
							if(frame.arbolConsul.getRaiz().id!=temporal.hijoIzq.id){
								frame.arbolConsul.getRaiz().hijoIzq = temporal.hijoIzq;
								temporal.hijoIzq.padre = frame.arbolConsul
									.getRaiz();
							}
						}
						if (tieneHijoDer && !tieneHijoIzq) {
							if(frame.arbolConsul.getRaiz().id!=temporal.hijoDer.id){
								frame.arbolConsul.getRaiz().hijoIzq = temporal.hijoDer;
								temporal.hijoDer.padre = frame.arbolConsul
									.getRaiz();
							}
						}

					}

				}

				// verifica si la antigua raiz tenia hijos

			}// fin IF asignación nueva raíz

		}

		// recorre los elementos de la lista "Consulta" para ver si se a
		// asignado un nodo hijo. ordenada.size()-1
		for (int i = 0; i < ordenada.size(); i++) {

			// for (int j = i + 1; j < ordenada.size(); j++) {
			for (int j = 0; j < ordenada.size(); j++) {

				xT1 = getTokenX(frame, ordenada.get(i).getId());
				yT1 = getTokenY(frame, ordenada.get(i).getId());
				xT2 = getTokenX(frame, ordenada.get(j).getId());
				yT2 = getTokenY(frame, ordenada.get(j).getId());
				int diferenciaY = yT2 - yT1;
				int diferenciaX = abs(xT2 - xT1);
				Consulta t1 = getConsulta(ordenada, ordenada.get(i).id);
				Consulta t2 = getConsulta(ordenada, ordenada.get(j).id);

				System.out.println("Valor de " + ordenada.get(i).getId()
						+ "xT1:" + xT1 + " " + ordenada.get(j).getId() + "xT2:"
						+ xT2 + " DiferenciaX:" + diferenciaX + "DiferenciaY:" + diferenciaY);
				/*System.out.println("Valor de " + ordenada.get(i).getId()
						+ "yT1:" + yT1 + " " + ordenada.get(j).getId() + "yT2:"
						+ yT2 + " DiferenciaY:" + diferenciaY);*/
				
				String whatIsT1 = Valores.whatIs(ordenada.get(i).getId());
				String whatIsT2 = Valores.whatIs(ordenada.get(j).getId());
				
				//Asigna hijo(T2) al nodo padre (T1)
				if(whatIsT1.equals("operator")){
					if (diferenciaY <= distY && diferenciaY > 50
							&& diferenciaX > 20 && diferenciaX < 80) {//80
						ubicacion = izqDer(xT1, xT2);// devuelve la ubicacion de t2
														// con respecto a t1
						temporal = construirNodo(ordenada.get(j));
						nodoPadre = frame.arbolConsul.getNodo(ordenada.get(i)
								.getId());
						nodoEncontrado = frame.arbolConsul.getNodo(temporal.id);
						if (nodoEncontrado != null) {
							frame.arbolConsul.removeNodo(nodoEncontrado); //Si encuentra el nodo en el arbol, lo elimina
						}

						if(nodoPadre!=null){
							frame.arbolConsul.insertarNodo(temporal, ubicacion,
								nodoPadre);
						}
					}
				}
				

				if (diferenciaY > -20 && diferenciaY < 40 && diferenciaX > 70 && diferenciaX < 100) {
					if (whatIsT2.equals("attribute") || whatIsT2.equals("constant") || whatIsT2.equals("comparation")) {
						if (ubicacion(frame, t1.id, t2.id).equals("derecha")) {
							nodoEncontrado = frame.arbolConsul.getNodo(t2.id);
							nodoPadre=frame.arbolConsul.getNodo(t1.id);
							if (nodoEncontrado != null) {
								frame.arbolConsul.removeNodo(nodoEncontrado);
							}
							// frame.arbolConsul.insertAttribute(t1, t2);
							if(nodoPadre!=null){
								addAttribute(frame, t1, t2);
							}
						}
					}
				}
			}// fin for con "j"1
		}// fin for con "i"

	}

	public static void addAttribute(TanQuery frame, Consulta t1, Consulta t2) {
		Nodo hijo = construirNodo(t2);
		Nodo padre = frame.arbolConsul.getNodo(t1.id);
		String nodoEs=Valores.whatIs(t1.id);

		
			if (nodoEs.equals("operator") || nodoEs.equals("attribute") || nodoEs.equals("constant")|| nodoEs.equals("comparation")) {
				frame.arbolConsul.insertAttribute(padre, hijo);
			}
	}

	public static Consulta getConsulta(ArrayList<Consulta> consultaArray, int id) {
		for (Consulta consulta : consultaArray) {
			if (consulta.id == id) {
				return consulta;
			}

		}
		return null;

	}

	public static String ubicacion(TanQuery frame, int refe, int id) {
		int xT1 = getTokenX(frame, refe);
		int xT2 = getTokenX(frame, id);
		return izqDer(xT1, xT2);
	}

	public static void ejecutaQuery(TanQuery frame, String querySQL) {
		if(querySQL!=""){
			System.out.println("Ejecutando Consulta Mysql=> " + querySQL);
		}
	}

	
	/*
	public static ArrayList resultSetToArrayList(ResultSet rs)
			throws SQLException {
		ResultSetMetaData md = (ResultSetMetaData) rs.getMetaData();
		int columns = md.getColumnCount();
		ArrayList results = new ArrayList();

		while (rs.next()) {
			HashMap row = new HashMap();
			results.add(row);

			for (int i = 1; i <= columns; i++) {
				row.put(md.getColumnName(i), rs.getObject(i));
			}
		}
		return results;
	}*/

}
