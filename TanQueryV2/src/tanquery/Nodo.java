package tanquery;

public class Nodo {
	public int id;
	public Nodo padre;
	public Nodo hijoIzq;
	public Nodo hijoDer;
	public Nodo atributos;
	public String simbolo;
	public String valor;
	public Consulta derecha;
	public String valorSql;
	public String sqlStm;
	public String condicion;

	public Nodo(Nodo nodo) {
		super();
		this.id = nodo.id;
		this.hijoIzq = null;
		this.hijoDer = null;
		this.derecha = null;
		this.valor = nodo.valor;
		this.valorSql = nodo.valorSql;
		this.padre = nodo.padre;
		this.atributos = null;
		this.simbolo = nodo.simbolo;
		this.sqlStm = "";

	}

	public Nodo() {
		// TODO Auto-generated constructor stub
		super();
	}

	public synchronized void insertAtributo(Nodo padre, Nodo hijo) {

		try {
			hijo.padre = padre;
			padre.atributos = hijo;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// buscar punto de inserción e insertar nodo nuevo
	public synchronized void insertar(Nodo nodo, String ubicacion, Nodo padre) {
		// insertar en subarbol izquierdo
		nodo.padre = padre;
		String valor = padre.valor;
		if (ArbolConsulta.isUnario(valor)) {
			if (ubicacion == "izquierda" && padre.hijoDer == null) {
				// insertar en subarbol izquierdo
				if (padre.hijoIzq == null) {
					padre.hijoIzq = new Nodo(nodo);

				} else {
					padre.hijoIzq = null;
					padre.hijoIzq = new Nodo(nodo);
				}

			}
			// insertar nodo derecho
			else if (ubicacion == "derecha" && padre.hijoIzq == null) {
				// insertar nuevo nodoArbol
				if (padre.hijoDer == null) {
					padre.hijoDer = new Nodo(nodo);
				} else {
					padre.hijoDer = null;
					padre.hijoDer = new Nodo(nodo);
				}

			}

		}
		if (ArbolConsulta.isBinario(valor)) {
			if (ubicacion == "izquierda") {
				// insertar en subarbol izquierdo
				if (padre.hijoIzq == null) {
					padre.hijoIzq = new Nodo(nodo);

				} else {
					padre.hijoIzq = null;
					padre.hijoIzq = new Nodo(nodo);
				}

			}

			// insertar nodo derecho
			else if (ubicacion == "derecha") {
				// insertar nuevo nodoArbol
				if (padre.hijoDer == null) {
					padre.hijoDer = new Nodo(nodo);
				} else {
					padre.hijoDer = null;
					padre.hijoDer = new Nodo(nodo);
				}

			}

		}

	} // fin del metodo insertar

}

class ArbolConsulta {
	private Nodo raiz;

	public Nodo getRaiz() {
		return raiz;
	}

	public void setRaiz(Nodo raiz) {
		this.raiz = raiz;
	}

	// construir un arbol vacio
	public ArbolConsulta() {
		raiz = null;
	}

	public synchronized void insertAttribute(Nodo padre, Nodo hijo) {
		try {
			raiz.insertAtributo(padre, hijo);
			System.out.println("Nodo atributo insertado=>" + hijo.valor
					+ " en Padre:" + padre.valor);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void insertarPadre(Nodo nodo) {
		if (raiz == null) {
			raiz = new Nodo(nodo); // crea nodo raiz
			System.out.println("Nodo raiz=>" + raiz.id);
		}

	}

	public synchronized void insertarNodo(Nodo nodo, String ubicacion,
			Nodo padre) {
		raiz.insertar(nodo, ubicacion, padre); // llama al metodo insertar
		System.out.println("Nodo insertado=>" + nodo.id + "  al lado "
				+ ubicacion + " del nodo padre=>" + padre.id);
	}

	public synchronized void recorrido(TanQuery frame) {
		ayudanteRecorrido(frame, raiz);
	}
	public synchronized void recorridoLog(TanQuery frame) {
		ayudaRecorreLog(frame, raiz);
	}

	public synchronized void recorridoAlgebraRelacional(TanQuery frame) {
		recorridoAR(frame, raiz);
	}

	public synchronized void recorridoSQL(TanQuery frame) {
		// recorridoSQL(frame, raiz);
		generaSQL(frame, raiz);
	}

	// metodo recursivo para recorrido de arbol de Consulta
	public static boolean isBinario(String valor) {
		switch (valor) {

		case "UNION":
			return true;
		case "interseccion":
			return true;
		case "JOIN":
			return true;
		case "SEMI-JOIN":
			return true;

		case "X":// cross product
			return true;

		case "/":
			return true;

		case "+":
			return true;

		case "-":
			return true;
		}
		return false;

	}

	public static boolean isUnario(String valor) {
		switch (valor) {
		case "SELECCION":
			return true;

		case "PROYECCION":
			return true;
		}

		return false;

	}

	private void ayudaRecorreLog(TanQuery frame, Nodo nodo) {
		String ar = "";
		String linea = "";
		String id = Integer.toString(nodo.id);
		String hijoDer = "-", hijoIzq = "-", padre = "-", atributo = "-";
		boolean tieneHijoDer = false;
		boolean tieneHijoIzq = false;
		boolean tieneAtributos = false;
		boolean tienePadre = false;

		if (nodo == null)
			return;
		
		tieneHijoDer = nodo.hijoDer != null ? true : false;
		tieneHijoIzq = nodo.hijoIzq != null ? true : false;
		tieneAtributos = nodo.atributos != null ? true : false;
		tienePadre = nodo.padre != null ? true : false;
				
		if (tieneHijoDer) {
			hijoDer = Integer.toString(nodo.hijoDer.id);
		}
		if (tieneHijoIzq) {
			hijoIzq = Integer.toString(nodo.hijoIzq.id);
		}
		if (tienePadre) {
			padre = Integer.toString(nodo.padre.id);
		}
		if (tieneAtributos) {
			atributo = Integer.toString(nodo.atributos.id);
		}

		linea=id+","+hijoDer+","+hijoIzq+","+padre+","+atributo+frame.momento+"\n";
		System.out.println("%    %"+linea);
		
		if (nodo.atributos != null) {
			ayudanteRecorrido(frame, nodo.atributos);
		}
		
		ayudanteRecorrido(frame, nodo.hijoIzq); // recorre subarbol izquierdo
		ayudanteRecorrido(frame, nodo.hijoDer); // recorre subarbol derecho
	}
	
	private void ayudanteRecorrido(TanQuery frame, Nodo nodo) {
		String ar = "";
		if (nodo == null)
			return;
		String nodoEs = Valores.whatIs(nodo.id);
		if (nodoEs.equals("operator")) {
			if (!isBinario(nodo.valor)) {
				ar = nodo.valor + "(";
				frame.query = frame.query.concat(ar);
			}

		} else if (Valores.whatIs(nodo.id) == "relation") {
			ar = "(" + nodo.valor;
			frame.query = frame.query.concat(ar);

		}
		if (Valores.whatIs(nodo.id) == "attribute") {
			ar = nodo.valor;
			if (nodo.atributos != null) {
				ar = ar.concat(",");
			}
			frame.query = frame.query.concat(ar);
		}

		if (nodo.atributos != null) {
			ayudanteRecorrido(frame, nodo.atributos);
		}

		// frame.query.concat(nodo.valor);
		String valores = "";

		valores = "id:" + nodo.id + " " + nodo.valor + " simbolo:"
				+ nodo.simbolo;

		if (nodo.padre != null)
			valores += " padre:" + nodo.padre.id;
		if (nodo.hijoIzq != null) {
			valores += " HI:" + nodo.hijoIzq.id;
		}
		if (nodo.hijoDer != null)
			valores += " HD:" + nodo.hijoDer.id;

		System.out.println(valores); // mostrar datos del nodo

		ayudanteRecorrido(frame, nodo.hijoIzq); // recorre subarbol izquierdo
		ayudanteRecorrido(frame, nodo.hijoDer); // recorre subarbol derecho

		// frame.query = frame.query.concat(")");
	}

	public void recorridoAR(TanQuery frame, Nodo nodo) {
		String ar = "";
		// String paso;
		if (nodo == null)
			return;
		String nodoEs = Valores.whatIs(nodo.id);
		boolean isBinario = isBinario(nodo.valor);

		if (nodoEs.equals("relation")) {
			ar = " (" + nodo.valor + ") ";
			frame.queryAR = frame.queryAR.concat(ar);
		}

		if (nodoEs.equals("attribute") || nodoEs.equals("comparation")
				|| nodoEs.equals("constant")) {

			if (nodoEs.equals("constant")) {
				ar=ar.concat("'");
			}
			else if (nodoEs.equals("coomparation")) {
				ar=ar.concat(" ");
			}
			if(nodoEs.equals("attribute")){
				String [] valor=nodo.valor.split("\\.");
				ar=ar.concat(valor[1]);
			}
			else {
				ar = ar.concat(nodo.valor);
			}
			if (nodoEs.equals("constant")) {
				ar=ar.concat("'");
			}

			if (nodo.atributos != null) {
				if (Valores.whatIs(nodo.atributos.id).equals("attribute")
						&& Valores.whatIs(nodo.id).equals("attribute")) {
					ar = ar.concat(", ");
				}

			}
			frame.queryAR = frame.queryAR.concat(ar);
			if (nodo.atributos != null) {
				recorridoAR(frame, nodo.atributos);
			}
			if (nodo.atributos == null) {
				frame.queryAR = frame.queryAR.concat(")");
			}

		}

		if (nodoEs.equals("operator") && !isBinario) {
			// ar = nodo.valor + "(";
			ar = Valores.getValorToken(frame.tokens, nodo.id) + "(";
			frame.queryAR = frame.queryAR.concat(ar);
			recorridoAR(frame, nodo.atributos);
			// ar = nodo.valor + "(";
			if (nodo.hijoDer != null || nodo.hijoIzq != null) {
				ar = "(";
				frame.queryAR = frame.queryAR.concat(ar);
			}

			recorridoAR(frame, nodo.hijoIzq);
			recorridoAR(frame, nodo.hijoDer);
			if (nodo.hijoDer != null || nodo.hijoIzq != null) {
				ar = ")";
				frame.queryAR = frame.queryAR.concat(ar);
			}
			return;
		}
		if (isBinario) {// 4x
			recorridoAR(frame, nodo.hijoIzq);
			// ar = nodo.valor;
			ar = Valores.getValorToken(frame.tokens, nodo.id);
			// ar = nodo.simbolo;
			frame.queryAR = frame.queryAR.concat(ar);
			if (nodo.atributos != null) {
				frame.queryAR = frame.queryAR.concat("(");
				recorridoAR(frame, nodo.atributos);
			}
			recorridoAR(frame, nodo.hijoDer);
			// frame.queryAR = frame.queryAR.concat(")");
			return;
		}

	}

	public void generaSQL(TanQuery frame, Nodo nodo) {

		if (nodo == null)
			return;
		String nodoEs = Valores.whatIs(nodo.id);

		if (nodoEs.equals("relation")) {
			if (nodo.padre == null) {
				frame.querySQL = frame.querySQL.concat("select * from "
						+ nodo.valor + " ");

			} else {
				frame.querySQL = frame.querySQL.concat(nodo.valor + " ");
				frame.qTemp = frame.qTemp.concat(nodo.valor + " ");
				nodo.sqlStm = nodo.valor + " ";

			}

		}
		else if (nodoEs.equals("attribute") || nodoEs.equals("comparation")
				|| nodoEs.equals("constant")) {
			if (nodoEs.equals("constant")) {
				frame.querySQL = frame.querySQL.concat("'");
				nodo.sqlStm = "'";
			}
			if(nodoEs.equals("attribute")){
				String [] valor=nodo.valor.split("\\.");
				frame.querySQL = frame.querySQL.concat(valor[1]);
				frame.qTemp = frame.qTemp.concat(valor[1]);
				nodo.sqlStm = nodo.sqlStm.concat(valor[1]);
			}
			else {
				frame.querySQL = frame.querySQL.concat(nodo.valor);
				frame.qTemp = frame.qTemp.concat(nodo.valor);
				nodo.sqlStm = nodo.sqlStm.concat(nodo.valor);
			}

//			frame.querySQL = frame.querySQL.concat(nodo.valor);
//			frame.qTemp = frame.qTemp.concat(nodo.valor);
//			nodo.sqlStm = nodo.sqlStm.concat(nodo.valor);
			if (nodoEs.equals("constant")) {
				frame.querySQL = frame.querySQL.concat("'");
				nodo.sqlStm = nodo.sqlStm.concat("'");
			}
			if (nodo.atributos != null) {
				if (Valores.whatIs(nodo.atributos.id).equals("attribute")
						&& Valores.whatIs(nodo.id).equals("attribute")) {
					frame.querySQL = frame.querySQL.concat(", ");
					frame.qTemp = frame.qTemp.concat(", ");
					nodo.sqlStm = nodo.sqlStm.concat(", ");
				}
				generaSQL(frame, nodo.atributos);
			}
			if (nodo.atributos == null) {
				// frame.querySQL=frame.querySQL.concat(" from ");
			}
		}
		else if (nodoEs.equals("operator")) {
			if (isBinario(nodo.valor)) {
				if (nodo.padre != null) {
					frame.querySQL = frame.querySQL.concat("(");
				}
				if (nodo.valor.equals("X")) {
					if (nodo.padre == null) {
						frame.querySQL = frame.querySQL
								.concat("select * from ");
						frame.qTemp = frame.qTemp.concat("select * from ");
					}
					generaSQL(frame, nodo.hijoIzq);
					frame.querySQL = frame.querySQL.concat(",");
					frame.qTemp = frame.qTemp.concat(",");
					generaSQL(frame, nodo.hijoDer);

					// return;
				}
				if (nodo.valor.equals("UNION")) {
					frame.querySQL = frame.querySQL.concat("select * from ");
					frame.qTemp = frame.qTemp.concat("select * from ");
					generaSQL(frame, nodo.hijoIzq);
					frame.querySQL = frame.querySQL
							.concat(" UNION select * from ");
					frame.qTemp = frame.qTemp.concat(" UNION select * from ");
					generaSQL(frame, nodo.hijoDer);
					// return;
				}

				if (nodo.valor.equals("JOIN")) {
					// if(nodo.padre==null){
					frame.querySQL = frame.querySQL.concat("select * from ");
					frame.qTemp = frame.qTemp.concat("select * from ");
					// }
					generaSQL(frame, nodo.hijoIzq);
					frame.querySQL = frame.querySQL.concat(" INNER JOIN ");
					frame.qTemp = frame.qTemp.concat(" INNER JOIN ");
					generaSQL(frame, nodo.hijoDer);
					if (nodo.atributos != null) {
						frame.querySQL = frame.querySQL.concat(" WHERE ");
						// nodo.condicion=nodo.condicion.concat(nodo.atributos.sqlStm);
						generaSQL(frame, nodo.atributos);
					}

					try {
						nodo.sqlStm = "SELECT * FROM " + nodo.hijoIzq.sqlStm
								+ " INNER JOIN " + nodo.hijoDer.sqlStm
								+ "WHERE " + nodo.condicion;

					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

				if (nodo.valor.equals("SEMI-JOIN")) {
					// if(nodo.padre==null){
					frame.querySQL = frame.querySQL.concat("SELECT * FROM ");
					frame.qTemp = frame.qTemp.concat("SELECT * FROM ");
					// }
					generaSQL(frame, nodo.hijoIzq);
					frame.querySQL = frame.querySQL.concat(" WHERE EXISTS (SELECT * FROM  ");
					frame.qTemp = frame.qTemp.concat(" WHERE EXISTS (SELECT * FROM  ");
					generaSQL(frame, nodo.hijoDer);

					if (nodo.atributos != null) {
						frame.querySQL = frame.querySQL.concat(" WHERE ");
						// nodo.condicion=nodo.condicion.concat(nodo.atributos.sqlStm);
						generaSQL(frame, nodo.atributos);
					    frame.querySQL = frame.querySQL.concat(" )");

					}

					try {
						nodo.sqlStm = "SELECT * FROM " + nodo.hijoIzq.sqlStm
								+ " WHERE EXISTS ( SELECT * FROM " + nodo.hijoDer.sqlStm
								+ "WHERE " + nodo.condicion + " )";

					} catch (Exception e) {
						// TODO Auto-generated catch block
						// e.printStackTrace();
					}
				}

				if (nodo.padre != null) {
					// String query="create view v"+nodo.id+" as "+frame.qTemp;
					// frame.qTemp="";
					// Consulta.ejecutaQuery(frame, query);
					// frame.querySQL=frame.querySQL.concat(") as un_"+nodo.id);
					frame.querySQL = frame.querySQL.concat(") as o" + nodo.id);
				}

			}
			if (isUnario(nodo.valor)) {
				if (nodo.padre != null) {
					frame.querySQL = frame.querySQL.concat("(");
				}
				if (nodo.atributos != null) {
					if (nodo.valor.equals("PROYECCION")) {
						frame.querySQL = frame.querySQL.concat("select ");
						generaSQL(frame, nodo.atributos);
						frame.querySQL = frame.querySQL.concat(" from ");
						generaSQL(frame, nodo.hijoIzq);
						// nodo.sqlStm="create table tmp_"+nodo.id+" as"+
						// "select "+nodo.atributos.sqlStm+" from"+
						// nodo.hijoIzq.sqlStm;
						// System.out.println("SQLSTM:"+nodo.sqlStm);

						// return;

					}
					if (nodo.valor.equals("SELECCION")) {
						//if (nodo.padre != null) {
							//if (nodo.padre.valor.equals("PROYECCION")) {
						frame.querySQL = frame.querySQL.concat("select * from ");
						generaSQL(frame, nodo.hijoIzq);
						frame.querySQL = frame.querySQL.concat(" WHERE ");
						generaSQL(frame, nodo.atributos);
//						frame.querySQL = frame.querySQL.concat(" where ");
//						generaSQL(frame, nodo.atributos);


						//	}

					}

//						if(nodo.padre==null){
	//						frame.querySQL=frame.querySQL.concat("select * from ");
	//					generaSQL(frame, nodo.hijoIzq);

		//				}


					}


				if (nodo.padre != null) {
					frame.querySQL = frame.querySQL.concat(") as o" + nodo.id);
				}

			}

		}
	}

	public synchronized Nodo getNodo(int id) {
		Nodo nodoEncontrado = new Nodo();
		nodoEncontrado = ayudanteGetNodoArbol(id, raiz);
		if (nodoEncontrado != null) {
			return nodoEncontrado;
		}
		return null;
	}

	private Nodo ayudanteGetNodoArbol(int id, Nodo nodo) {
		Nodo respuesta = null;
		if (nodo == null)
			return null;

		if (nodo.id == id)
			return nodo;
		if (nodo.hijoIzq != null && respuesta == null)
			respuesta = ayudanteGetNodoArbol(id, nodo.hijoIzq);
		if (nodo.hijoDer != null && respuesta == null)
			respuesta = ayudanteGetNodoArbol(id, nodo.hijoDer);
		if (nodo.atributos != null && respuesta == null)
			respuesta = ayudanteGetNodoArbol(id, nodo.atributos);
		return respuesta;
	}

	public static boolean comparaNodos(Nodo nodo1, Nodo nodo2) {
		if (nodo1.equals(nodo2)) {
			return true;
		}
		return false;
	}

	public synchronized boolean removeNodo(Nodo nodo) {
		Nodo nodoEliminado = new Nodo();
		nodoEliminado = ayudanteRemoveNodo(nodo.id, raiz);
		if (nodoEliminado != null) {
			return true;
		}
		return false;

	}

	private Nodo ayudanteRemoveNodo(int id, Nodo nodo) {
		Nodo respuesta = null;
		boolean tieneHijoDer = false;
		boolean tieneHijoIzq = false;
		boolean tieneAtributos = false;
		if (nodo == null)
			return null;

		if (nodo.padre != null) {
			tieneHijoDer = nodo.padre.hijoDer != null ? true : false;
			tieneHijoIzq = nodo.padre.hijoIzq != null ? true : false;
			tieneAtributos = nodo.padre.atributos != null ? true : false;
		}

		if (nodo.id == id) {
			if (tieneHijoIzq) {
				if (nodo.padre.hijoIzq.id == nodo.id) {
					nodo.padre.hijoIzq = null;
				}
			} else if (tieneHijoDer) {
				if (nodo.padre.hijoDer.id == nodo.id) {
					nodo.padre.hijoDer = null;
				}

			} else if (tieneAtributos) {
				if (nodo.padre.atributos.id == nodo.id)
					nodo.padre.atributos = null;
			}

			return nodo;
		} else if (nodo.hijoIzq != null && respuesta == null)
			return ayudanteRemoveNodo(id, nodo.hijoIzq);
		else if (nodo.hijoDer != null && respuesta == null)
			return ayudanteRemoveNodo(id, nodo.hijoDer);
		else if (nodo.atributos != null && respuesta == null)
			return ayudanteRemoveNodo(id, nodo.atributos);
		return respuesta;

	}

	public synchronized void conexiones(TanQuery frame) {
		dibujaL(frame, raiz);
	}

	public void dibujaL(TanQuery frame, Nodo nodo) { // dibuja la linea de padre
														// a hijo
		float radio = 130;
		
		if (nodo == null)
			return;
		if (frame.interfaz == 0) {
			frame.stroke(0);
		}
		if (frame.interfaz == 1) {
			frame.stroke(255, 255, 255);
		}

		if (nodo.hijoIzq != null) {
			// obtener posición de padre e hijo izquierdo
			float fx = Consulta.getTokenX(nodo.id,frame.width,frame.tuioObjectList);
			float fy = Consulta.getTokenY(frame, nodo.id);
			float sx = Consulta.getTokenX(nodo.hijoIzq.id,frame.width,frame.tuioObjectList);
			float sy = Consulta.getTokenY(frame, nodo.hijoIzq.id);

			if (Busquedas.isInTrabajo(fx, fy) && Busquedas.isInTrabajo(sx, sy)) {
				frame.line(fx, fy+50, sx, sy - radio);
				//frame.line(fx - 100, fy +120 , sx, sy - radio - 14);
			}
			dibujaL(frame, nodo.hijoIzq);

		}
		if (nodo.hijoDer != null) {
			// obtener posición de padre e hijo Derecho
			float fx = Consulta.getTokenX(nodo.id,frame.width,frame.tuioObjectList);
			float fy = Consulta.getTokenY(frame, nodo.id);
			float sx = Consulta.getTokenX(nodo.hijoDer.id,frame.width,frame.tuioObjectList);
			float sy = Consulta.getTokenY(frame, nodo.hijoDer.id);
			if (Busquedas.isInTrabajo(fx, fy) && Busquedas.isInTrabajo(sx, sy)) {
				frame.line(fx, fy+50, sx, (sy - radio)-20);
				//frame.line(fx - 100, fy +120 , sx, sy - radio - 14);

			}
			dibujaL(frame, nodo.hijoDer);
		}

	}

	public static void generaTemps(TanQuery frame, Nodo nodo) {
		if (nodo == null)
			return;

		String nodoEs = Valores.whatIs(nodo.id);
		@SuppressWarnings("unused")
		boolean tieneHijoDer = false;
		@SuppressWarnings("unused")
		boolean tieneHijoIzq = false;
		@SuppressWarnings("unused")
		boolean tieneAtributos = false;
		if (nodoEs.equals("operator")) {
			if (isBinario(nodo.valor)) {

				if (nodo.padre != null) {
					tieneHijoDer = nodo.hijoDer != null ? true : false;
					tieneHijoIzq = nodo.hijoIzq != null ? true : false;
					tieneAtributos = nodo.atributos != null ? true : false;
				}

			} else if (isUnario(nodo.valor)) {

			}

		}

	}

}
